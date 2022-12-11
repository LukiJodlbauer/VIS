//
// Created by luki on 05.12.22.
//

#include "environment_server.h"

/**
 *
 * @param _port port which should be usesd
 * @param _buffer_size size of buffer for user input
 * This function start the main socket which accepts the client connections and creates threads for each client
 */
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
        //output some log
        printf("connection established with socket\n");
        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
        //parameter creation for thread
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

/**
 *  Constructor initializes m_server_fd with default value and shutdown with false
 */
EnvironmentServer::EnvironmentServer() {
    m_server_fd = -1;
    shutdown = false;
}
/**
 *  Default Constructor
 */
EnvironmentServer::~EnvironmentServer() = default;

/**
 *
 * @param _parameter
 * This function handles the client communication
 * Possible client methods: drop(), shutdown(), getSensorTypes(), getAllSensors(), getSensor(_sensor).
 * Every other input will be returned as Echo
 */
void *EnvironmentServer::ClientCommunication(void *_parameter) {
    //extract inforgmation from parameter
    auto *p = (m_socketParam *) _parameter;
    int clientSocket = p->socket;
    int _bufferSize = p->bufferSize;
    EnvironmentServer *server = p->serverClass;
    //timestamp generation
    const auto longTimestamp = std::chrono::duration_cast<std::chrono::seconds>(
            std::chrono::system_clock::now().time_since_epoch()).count();
    char timestamp[1024] = {0};
    strcpy(timestamp, to_string(longTimestamp).c_str());

    //char[] for user input
    char buffer[1024] = {0};
    //char[] will be returned to user
    char result[1024] = {0};

    //const strings
    char dest[1024] = "ECHO: ";
    char light[6] = "light";
    char noise[6] = "noise";
    char air[4] = "air";

    long valread;
    while (!server->shutdown) {
        //clear used char[] for next input
        memset(&buffer[0], 0, sizeof(buffer));
        memset(&dest[6], 0, sizeof(dest) - 6);
        memset(&result[0], 0, sizeof(result));
        //brake in each if, to exit loop, close socket and kill thread
        if ((valread = recv(clientSocket, buffer, _bufferSize, 0)) <= 0 && !server->shutdown) {
            if (valread <= 0) {
                //TODO ERROR
            }
            break;
        }
        if (strcmp(buffer, "drop") == 0 || strcmp(buffer, "quit") == 0) {
            break;
        }
        if (strcmp(buffer, "shutdown") == 0) {
            server->shutdown = true;
            server->CloseSocket();
            printf("after close Socket");
            break;
        }

        //continue in each if, method result is send to client no further actions to be done
        if (regex_match(buffer, regex("getSensortypes\\(\\)"))) {
            send(clientSocket, "light;noise;air\0", strlen("light;noise;air\0"), 0);
            continue;
        }
        if (regex_match(buffer, regex("getSensor\\([A-z]{3,5}\\)"))) {
            //regex for extracting method parameter
            //(?<=\().+?(?=\)) would be nicer but c++ does not support lookbehinds
            cmatch m;
            regex_search(buffer, m, regex("\\(.*?\\)"));
            //append timestamp to result
            strcat(result, timestamp);
            //generate 3 values for air
            if (m[0].compare("(air")) {
                server->getRandomNumbers(3, result, nullptr);
                send(clientSocket, result, strlen(result), 0);
            }
            //generate 1 value light and noise
            if (m[0].compare("(ligth") || m[0].compare("(noise")) {
                server->getRandomNumbers(1, result, nullptr);
                send(clientSocket, result, strlen(result), 0);
            } else {
                //send information if given sensor type is not supported
                send(clientSocket, "Parameter not supported\0", strlen("Parameter not supported\0"), 0);
            }
            continue;
        }
        //call getRandomNumbers for each sensor and send message to client
        if (regex_match(buffer, regex("getAllSensors\\(\\)"))) {
            strcat(result, timestamp);
            server->getRandomNumbers(1, result, light);
            server->getRandomNumbers(3, result, air);
            server->getRandomNumbers(1, result, noise);

            send(clientSocket, result, strlen(result), 0);
            continue;
        }
        strncat(dest, buffer, sizeof(dest) - strlen(dest) - 1);
        send(clientSocket, dest, strlen(dest), 0);
    }
    //destroy thread
    int rVal = pthread_detach(pthread_self());
    if (rVal <= 0) {
        //TODO ERROR
    }

    //call CleanUp method for closing socket
    pthread_cleanup_push(CleanUp, _parameter);
    pthread_cleanup_pop(1);
}

/**
 *
 * @param _target holds socket and client information
 * This function closes the client socket and deletes the stored client information.
 * This is going to be executed before the the thread is destroyed
 *
 */
void EnvironmentServer::CleanUp(void *_target) {
    auto *target = (m_socketParam *) _target;
    int clientSocket = target->socket;
    close(clientSocket);
    delete target;
}

/**
 * This function closes the main socket
 */
void EnvironmentServer::CloseSocket() const {
    printf(" in close socket");
    if (close(m_server_fd) < 0) {
        printf("error");
        printf("success");
    }
}

/**
 *
 * @param amount amount of sensor values to be created
 * @param result char[] in which the values will be stored
 * @param sensor type of sensor; nullptr if it should not be added
 * This function creates the char[](with generates values for the given sensor)which is going to be returned to the client
 */
void EnvironmentServer::getRandomNumbers(int _amount, char *_result, char _sensor[]) { // NOLINT(readability-convert-member-functions-to-static)
    //random number generator
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> distr(25, 250);

    char delimiter[32] = "|";

    //add sensor char[]
    if (_sensor != nullptr) {
        strcat(delimiter, _sensor);
        cout << _sensor;
        strcat(_result, delimiter);
        strncpy(delimiter, ";", sizeof(delimiter));
    }
    //generate amount many values and append to result
    for (int i = 0; i < _amount; i++) {
        char integer_string[32];
        sprintf(integer_string, "%d", distr(gen));
        strcat(delimiter, integer_string);
        strcat(_result, delimiter);
        strncpy(delimiter, ";", sizeof(delimiter));
    }
}
