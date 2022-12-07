//
// Created by Nico Oliver on 05.12.22.
//

#include "../headers/udp_client.h"

UdpClient::UdpClient() {

}

void UdpClient::ConnectSocket(char *_ip, int _port, int _buffer_size) {
    char buffer[_buffer_size];

    int client_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (client_fd < 0) {
        perror("socket creation error");
        exit(EXIT_FAILURE);
    }
    printf("socket was created ...\n");

    sockaddr_in serverAddr = {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(_port);

    if (inet_pton(AF_INET, _ip, &serverAddr.sin_addr) <= 0) {
        perror("invalid address/address not supported");
        exit(EXIT_FAILURE);
    }

    int connection = connect(client_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr));
    if (connection < 0) {
        perror("connection failed");
        exit(EXIT_FAILURE);
    }
    printf("connection established ...\n");

    while (true) {
        char message[_buffer_size];
        memset(&buffer[0], 0, sizeof(buffer));

        std::cout << "please enter your message : ";
        std::cin.getline(message, _buffer_size - 1);

        if (strcmp(message, "quit") == 0) {
            break;
        }

        int ret = send(client_fd, message, (int) strlen(message), 0);
        if (ret < 0) {
            perror("send failed");
            exit(EXIT_FAILURE);
        }

        ret = recv(client_fd, buffer, (size_t) _buffer_size, 0);
        if (ret < 0) {
            perror("receive failed");
            exit(EXIT_FAILURE);
        } else if (ret == 0) {
            printf("connection closed by partner\n");
            break;
        }

        printf("Received %i bytes, Message: %s\n", ret, buffer);
    }
}

void UdpClient::CloseSocket() {
    close(m_connection);
}

UdpClient::~UdpClient() = default;
