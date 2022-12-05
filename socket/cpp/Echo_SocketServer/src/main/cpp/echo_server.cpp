//
// Created by Nico Oliver on 05.12.22.
//

#include "echo_server.h"

void EchoServer::InitializeSocket(int _port, int _buffer_size) {
    int new_socket, valread;
    sockaddr_in serverAddr = {};
    char buffer[1024] = {0};
    char dest[1024] = "ECHO: ";

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
    while(true) {
        if ((new_socket = accept(m_server_fd, (struct sockaddr *) &clientAddr,
                                 (socklen_t *) &clientAddrLen)) < 0) {
            perror("accept method failed");
            exit(EXIT_FAILURE);
        }
        bool closeAllConnections = false;
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
            printf(dest);
            if ((valread = recv(new_socket, buffer, _buffer_size, 0)) <= 0) {
                printf("receive failed\n");
            }
            //printf(buffer);
            if(strcmp(buffer,"drop") == 0 || strcmp(buffer,"quit") == 0){
                printf("in drop");
                break;
            }
            if(strcmp(buffer,"shutdown") == 0){
                printf("in shutdown");
                closeAllConnections = true;
                break;
            }
            strncat(dest, buffer, sizeof(dest) - strlen(dest) - 1);
            //printf("Received %i bytes, Message: %s\n", valread, buffer);
            send(new_socket, dest, strlen(dest), 0);
        }
        close(new_socket);
        if(closeAllConnections){
            break;
        }
    }
}

void EchoServer::CloseSocket() {
    close(m_server_fd);
}

EchoServer::EchoServer() {
    m_server_fd = -1;
}

EchoServer::~EchoServer() {

}
