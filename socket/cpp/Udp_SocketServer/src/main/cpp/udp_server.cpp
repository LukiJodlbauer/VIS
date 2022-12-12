//
// Created by Nico Oliver on 05.12.22.
//

#include "../headers/udp_server.h"

/**
 *
 * @param _port port which should be used
 * @param _buffer_size size of buffer for user input
 * This function start the main socket which accepts the client connections and handles the communication flow
 * Incoming messages are returned as Echo to the client.
 * Messages are sent over UDP via ipv4
*/
void UdpServer::InitializeSocket(int _port, int _buffer_size) {
    char buffer[1024] = {0};
    char dest[1024] = "ECHO: ";

    m_server_fd = socket(AF_INET, SOCK_DGRAM, 0);
    if (m_server_fd < 0) {
        perror("socket initialization failed");
        exit(EXIT_FAILURE);
    }
    printf("socket was created ...\n");

    sockaddr_in serverAddr = {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(_port);

    if (bind(m_server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("bind method failed");
        exit(EXIT_FAILURE);
    }
    printf("port was bound ...\n");

    sockaddr_in clientAddr = {};
    socklen_t clientAddrLen = sizeof(sockaddr_in);

    while(true) {
        memset(&buffer[0], 0, sizeof(buffer));
        memset(&dest[6], 0, sizeof(dest)-6);

        ssize_t ret = recvfrom(m_server_fd, buffer, _buffer_size, 0, (sockaddr*) &clientAddr, &clientAddrLen);
        if (ret < 0) {
            perror("receive failed");
            break;
        }
        printf("received: %s\n", buffer);

        if (strcmp(buffer, "drop") == 0) {
            printf("received drop request\n");
            break;
        } else if (strcmp(buffer, "shutdown") == 0) {
            printf("received shutdown request\n");
            break;
        }

        strncat(dest, buffer, sizeof(dest) - strlen(dest) - 1);
        if(sendto(m_server_fd, dest, strlen(dest), 0, (sockaddr*) &clientAddr, clientAddrLen) < 0 ){
            perror("send failed");
        }
    }
}
/**
 * This function closes the main socket
 */
void UdpServer::CloseSocket() const {
    if(close(m_server_fd) < 0){
        perror("close main socket failed");
        exit(EXIT_FAILURE);
    }
}
/**
 *  Constructor initializes m_server_fd with default value
 */
UdpServer::UdpServer() {
    m_server_fd = -1;
}
/**
 *  Default Constructor
 */
UdpServer::~UdpServer() = default;
