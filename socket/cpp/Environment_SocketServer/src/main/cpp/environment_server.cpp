//
// Created by luki on 05.12.22.
//

#include "environment_server.h"

void EnvironmentServer::InitializeSocket(int _port, int _buffer_size) {
    int new_socket;
    sockaddr_in serverAddr = {};
    // Creating socket file descriptor
    if ((m_server_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket initialization failed");
        exit(EXIT_FAILURE);
    }

    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(_port);

    if (bind(m_server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("bind method failed");
        exit(EXIT_FAILURE);
    }
    if (listen(m_server_fd, 3) < 0) {
        perror("listen method failed");
        exit(EXIT_FAILURE);
    }

    sockaddr_in clientAddr = {};
    int clientAddrLen = sizeof(sockaddr_in);
    while (!shutdown) {
        if ((new_socket = accept(m_server_fd, (struct sockaddr *) &clientAddr,
                                 (socklen_t *) &clientAddrLen)) <= 0) {
            perror("accept method failed");
            exit(EXIT_FAILURE);
        }
        printf("after accept");
        char serverIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &serverAddr.sin_addr, serverIp, INET_ADDRSTRLEN);
        int serverPort = ntohs(serverAddr.sin_port);

        char clientIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &clientAddr.sin_addr, clientIp, INET_ADDRSTRLEN);
        int clientPort = ntohs(clientAddr.sin_port);
        printf("connection established with socket\n");
        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
        auto *parameter = new m_socketParam();
        parameter->socket = new_socket;
        parameter->bufferSize = _buffer_size;
        parameter->serverClass = this;
        pthread_t threadID;
        int rVal = pthread_create(&threadID,
                                  nullptr,
                                  ClientCommunication,
                                  (void *) parameter);
        if (rVal <= 0) {
            //TODO ERROR
        }
    }
}

EnvironmentServer::EnvironmentServer() {
    m_server_fd = -1;
    shutdown = false;
}

EnvironmentServer::~EnvironmentServer() = default;

void *EnvironmentServer::ClientCommunication(void *_parameter) {
    auto *p = (m_socketParam *) _parameter;
    int clientSocket = p->socket;
    int _bufferSize = p->bufferSize;
    EnvironmentServer *server = p->serverClass;
    char buffer[1024] = {0};
    char result[1024] = {0};
    char randomNumber[1024] = {0};
    char timestamp[1024] = {0};
    char dest[1024] = "ECHO: ";
    char light[6] = "light";
    char noise[6] = "noise";
    char air[4] = "air";

    long valread;
    while (!server->shutdown) {
        memset(&buffer[0], 0, sizeof(buffer));
        memset(&dest[6], 0, sizeof(dest) - 6);
        memset(&result[0], 0, sizeof(result));
        memset(&randomNumber[0], 0, sizeof(randomNumber));
        memset(&timestamp[0], 0, sizeof(timestamp));

        const auto longTimestamp = std::chrono::duration_cast<std::chrono::seconds>(
                std::chrono::system_clock::now().time_since_epoch()).count();
        strcpy(timestamp, to_string(longTimestamp).c_str());

        if ((valread = recv(clientSocket, buffer, _bufferSize, 0)) <= 0 && !server->shutdown) {
            if (valread <= 0) {
                //TODO ERROR
            }
            break;
        }
        printf("received: %s\n", buffer);

        if (strcmp(buffer, "drop") == 0 || strcmp(buffer, "quit") == 0) {
            break;
        }
        if (strcmp(buffer, "shutdown") == 0) {
            server->shutdown = true;
            server->CloseSocket();
            printf("after close Socket");
            break;
        }

        if (regex_match(buffer, regex("getSensortypes\\(\\)"))) {
            send(clientSocket, "light;noise;air\n", strlen("light;noise;air\n"), 0);
            continue;
        }
        if (regex_match(buffer, regex("getSensor\\([A-z]{3,5}\\)"))) {
            printf("getsensor\n");
            cmatch m;
            regex_search(buffer, m, regex("\\(.*?\\)"));
            //strcat(result, timestamp);

            if (m[0].compare("(air)") == 0) {
                printf("air\n");
                server->getRandomNumbers(3, randomNumber, nullptr);
                printf("random Number: %s\n", randomNumber);
                sprintf(result, "%s%s\n", timestamp, randomNumber);
                send(clientSocket, result, strlen(result), 0);
            } else if (m[0].compare("(light)") == 0 || m[0].compare("(noise)") == 0) {
                printf("light or noise\n");
                server->getRandomNumbers(1, randomNumber, nullptr);
                printf("random Number: %s\n", randomNumber);
                sprintf(result, "%s%s\n", timestamp, randomNumber);
                send(clientSocket, result, strlen(result), 0);
            } else {
                printf("other parameter\n");
                send(clientSocket, "Parameter not supported\n", strlen("Parameter not supported\n"), 0);
            }
            continue;
        }
        if (regex_match(buffer, regex("getAllSensors\\(\\)"))) {
            //strcat(result, timestamp);
            server->getRandomNumbers(1, randomNumber, light);
            server->getRandomNumbers(3, randomNumber, air);
            server->getRandomNumbers(1, randomNumber, noise);
            sprintf(result, "%s%s\n", timestamp, randomNumber);

            send(clientSocket, result, strlen(result), 0);
            continue;
        }
        strncat(dest, buffer, sizeof(dest) - strlen(dest) - 1);
        send(clientSocket, dest, strlen(dest), 0);
    }
    int rVal = pthread_detach(pthread_self());
    if (rVal <= 0) {
        //TODO ERROR
    }
    pthread_cleanup_push(CleanUp, _parameter);
    pthread_cleanup_pop(1);
}

void EnvironmentServer::CleanUp(void *_target) {
    auto *target = (m_socketParam *) _target;
    int clientSocket = target->socket;
    close(clientSocket);
    delete target;
}

void EnvironmentServer::CloseSocket() const {
    printf(" in close socket");
    if (close(m_server_fd) < 0) {
        printf("error");
        printf("success");
    }
}

void EnvironmentServer::getRandomNumbers(int amount, char *result, char sensor[]) { // NOLINT(readability-convert-member-functions-to-static)
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> distr(25, 250);
    char delimiter[32] = "|";
    if (sensor != nullptr) {
        strcat(delimiter, sensor);
        cout << sensor;
        strcat(result, delimiter);
        strncpy(delimiter, ";", sizeof(delimiter));
    }
    for (int i = 0; i < amount; i++) {
        char integer_string[32];
        memset(&integer_string[0], 0, sizeof(integer_string));
        sprintf(integer_string, "%d", distr(gen));
        strcat(delimiter, integer_string);
        strcat(result, delimiter);
        strncpy(delimiter, ";", sizeof(delimiter));
    }
}
