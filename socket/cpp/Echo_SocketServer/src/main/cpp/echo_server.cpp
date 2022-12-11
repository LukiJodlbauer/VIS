//
// Created by Nico Oliver on 05.12.22.
//

#include "echo_server.h"
/**
 *
 * @param _port port which should be used
 * @param _buffer_size size of buffer for user input
 * @param _backlog number of connections allowed
 * This function start the main socket which accepts the client connections and handles the communication flow
 * Incoming messages are returned as Echo to the client
 * Messages are send over TCP via ipv4
 */
void EchoServer::InitializeSocket(int _port, int _buffer_size, int _backlog) { // NOLINT(readability-convert-member-functions-to-static)
    char buffer[1024] = {0};
    char dest[1024] = "ECHO: ";

    int server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd < 0) {
        perror("socket initialization failed");
        exit(EXIT_FAILURE);
    }
    printf("socket was created ...\n");

    sockaddr_in serverAddr = {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(_port);

    if (bind(server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("bind method failed");
        exit(EXIT_FAILURE);
    }
    printf("port was bound ...\n");

    if (listen(server_fd, _backlog) < 0) {
        perror("listen method failed");
        exit(EXIT_FAILURE);
    }
    printf("listening for connections ...\n");

    sockaddr_in clientAddr = {};
    int clientAddrLen = sizeof(sockaddr_in);
    bool doShutdown = false;

    while(true) {
        int new_socket = accept(server_fd, (struct sockaddr *) &clientAddr,
                                (socklen_t *) &clientAddrLen);
        if (new_socket < 0) {
            perror("accept method failed");
            exit(EXIT_FAILURE);
        }
        printf("accepted connection ...\n");

        char serverIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &serverAddr.sin_addr, serverIp, INET_ADDRSTRLEN);
        int serverPort = ntohs(serverAddr.sin_port);

        char clientIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &clientAddr.sin_addr, clientIp, INET_ADDRSTRLEN);
        int clientPort = ntohs(clientAddr.sin_port);

        printf("connection established with socket\n");
        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
        while(true) {
            memset(&buffer[0], 0, sizeof(buffer));
            memset(&dest[6], 0, sizeof(dest)-6);

            long ret = recv(new_socket, buffer, _buffer_size, 0);
            if (ret < 0) {
                perror("receive failed");
                break;
            } else if (ret == 0) {
                printf("connection closed by partner\n");
                break;
            }
            printf("received: %s\n", buffer);

            if (strcmp(buffer, "drop") == 0) {
                printf("received drop request\n");
                break;
            } else if (strcmp(buffer, "shutdown") == 0) {
                printf("received shutdown request\n");
                doShutdown = true;
                break;
            }

            strncat(dest, buffer, sizeof(dest) - strlen(dest) - 1);
            send(new_socket, dest, strlen(dest), 0);
        }

        printf("closing connection to socket now ...\n");
        close(new_socket);
        if (doShutdown) {
            break;
        }
    }
}
/**
 * This function closes the main socket
 */
void EchoServer::CloseSocket() const {
    close(m_server_fd);
}

/**
 *  Constructor initializes m_server_fd with default value
 */
EchoServer::EchoServer() {
    m_server_fd = -1;
}
/**
 *  Default Constructor
 */
EchoServer::~EchoServer() = default;
