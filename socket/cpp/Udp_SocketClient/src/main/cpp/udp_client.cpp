//
// Created by Nico Oliver on 05.12.22.
//

#include "../headers/udp_client.h"


UdpClient::UdpClient() {
    m_connection = -1;
}

void UdpClient::ConnectSocket(char *_ip, int _port, int _buffer_size) { // NOLINT(readability-convert-member-functions-to-static)
    char buffer[_buffer_size];

    m_connection = socket(AF_INET, SOCK_DGRAM, 0);
    if (m_connection < 0) {
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

    int connection = connect(m_connection, (struct sockaddr *) &serverAddr, sizeof(serverAddr));
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

        long ret = send(m_connection, message, (int) strlen(message), 0);
        if (ret < 0) {
            perror("send failed");
            exit(EXIT_FAILURE);
        }

        ret = recv(m_connection, buffer, (size_t) _buffer_size, 0);
        if (ret < 0) {
            perror("receive failed");
            exit(EXIT_FAILURE);
        } else if (ret == 0) {
            printf("connection closed by partner\n");
            break;
        }

        if (strcmp(message, "shutdown") == 0) {
            break;
        }

        printf("Received %li bytes, Message: %s\n", ret, buffer);
    }
}

void UdpClient::CloseSocket() const {
    if(close(m_connection) < 0){
        perror("close main socket failed");
        exit(EXIT_FAILURE);
    }
}

UdpClient::~UdpClient() = default;
