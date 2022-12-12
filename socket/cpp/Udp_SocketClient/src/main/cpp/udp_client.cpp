//
// Created by Nico Oliver on 05.12.22.
//

#include "../headers/udp_client.h"

/**
 *  Constructor initializes m_connection with default value
 */
UdpClient::UdpClient() {
    m_connection = -1;
}
/**
 * @param _ip ip address which should be used
 * @param _port port which should be used
 * @param _buffer_size size of buffer for user input
 * This function start the main socket which accepts the client connections and handles the communication flow
 * Incoming messages are returned as Echo to the client
 * Messages are sent over UDP via ipv4
*/
void UdpClient::ConnectSocket(char *_ip, int _port, int _buffer_size) { // NOLINT(readability-convert-member-functions-to-static)
    char buffer[_buffer_size];

    int client_fd = socket(AF_INET, SOCK_DGRAM, 0);
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

        long ret = send(client_fd, message, (int) strlen(message), 0);
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

        printf("Received %li bytes, Message: %s\n", ret, buffer);
    }
}
/**
 * This function closes the main socket
 */
void UdpClient::CloseSocket() const {
    if(close(m_connection) < 0){
        perror("close main socket failed");
        exit(EXIT_FAILURE);
    }
}
/**
 *  Default Constructor
 */
UdpClient::~UdpClient() = default;
