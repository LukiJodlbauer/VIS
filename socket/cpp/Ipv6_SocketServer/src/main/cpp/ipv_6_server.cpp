//
// Created by Nico Oliver on 07.12.22.
//

#include "../headers/ipv_6_server.h"


Ipv6Server::Ipv6Server() {
    m_server_fd = -1;
}

Ipv6Server::~Ipv6Server() = default;

void Ipv6Server::InitializeSocket(char *_ip, int _port, int _buffer_size, int _backlog) { // NOLINT(readability-convert-member-functions-to-static)
    char buffer[1024] = {0};
    char dest[1024] = "ECHO: ";

    m_server_fd = socket(AF_INET6, SOCK_STREAM, 0);
    if (m_server_fd < 0) {
        perror("socket initialization failed");
        exit(EXIT_FAILURE);
    }
    printf("socket was created ...\n");

    sockaddr_in6 serverAddr = {};
    serverAddr.sin6_family = AF_INET6;
    serverAddr.sin6_flowinfo = 0;
    serverAddr.sin6_scope_id = 0;
    serverAddr.sin6_port = htons(_port);

    int ip = inet_pton(AF_INET6, _ip, &serverAddr.sin6_addr);
    if (ip == -1) {
        perror("error while converting ipv6");
        exit(EXIT_FAILURE);
    } else if (ip == 0) {
        perror("error ip is not a valid string");
        exit(EXIT_FAILURE);
    }
    printf("successfully converted ipv6 ...\n");

    if (bind(m_server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("bind method failed");
        exit(EXIT_FAILURE);
    }
    printf("port was bound ...\n");

    if (listen(m_server_fd, _backlog) < 0) {
        perror("listen method failed");
        exit(EXIT_FAILURE);
    }
    printf("listening for connections ...\n");

    sockaddr_in6 clientAddr = {};
    int clientAddrLen = sizeof(sockaddr_in);
    bool doShutdown = false;

    while(true) {
        int new_socket = accept(m_server_fd, (struct sockaddr *) &clientAddr,
                                (socklen_t *) &clientAddrLen);
        if (new_socket < 0) {
            perror("accept method failed");
            exit(EXIT_FAILURE);
        }
        printf("accepted connection ...\n");

        char serverIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &serverAddr.sin6_addr, serverIp, INET_ADDRSTRLEN);
        int serverPort = ntohs(serverAddr.sin6_port);

        char clientIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &clientAddr.sin6_addr, clientIp, INET_ADDRSTRLEN);
        int clientPort = ntohs(clientAddr.sin6_port);

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
            if(send(new_socket, dest, strlen(dest), 0) < 0){
                perror("send failed");
            }
        }

        printf("closing connection to socket now ...\n");
        if(close(new_socket) < 0){
            perror("close client socket failed");
            exit(EXIT_FAILURE);
        }
        if (doShutdown) {
            break;
        }
    }
}

void Ipv6Server::CloseSocket() const {
    if(close(m_server_fd) < 0){
        perror("close main socket failed");
        exit(EXIT_FAILURE);
    }
}
