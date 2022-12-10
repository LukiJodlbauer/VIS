#include <iostream> // cout, cin
#include <iostream> // cout, cin
#include <sys/socket.h> // socket, bind, listen, accept
#include <netinet/in.h> // IPPROTO_TCP, sockaddr_in,
// htons/ntohs, INADDR_ANY
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <semaphore.h> // sem_init
#include <pthread.h>
#include <errno.h>
#include <cstring>
#include "environment_server.h"


#define BUFFER_SIZE 1024
#define BACKLOG 5

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("Starting Server ...\n");
    int server_fd, new_socket, valread;
    sockaddr_in serverAddr = {};
    char buffer[1024] = {0};
    char dest[1024] = "ECHO: ";
    char *hello = "Hello from server\0";
    char *ack = "ack\0";
    int port = atoi(_argv[1]);
    pthread_t threadID;

    EnvironmentServer server;
    server.InitializeSocket(port, BUFFER_SIZE);
    //server.CloseSocket();
    // Creating socket file descriptor
//    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
//        perror("socket initialization failed");
//        exit(EXIT_FAILURE);
//    }
//
//    serverAddr.sin_family = AF_INET;
//    serverAddr.sin_addr.s_addr = INADDR_ANY;
//    serverAddr.sin_port = htons(port);
//
//    if (bind(server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
//        perror("bind method failed");
//        exit(EXIT_FAILURE);
//    }
//    if (listen(server_fd, 3) < 0) {
//        perror("listen method failed");
//        exit(EXIT_FAILURE);
//    }
//
//    sockaddr_in clientAddr = {};
//    int clientAddrLen = sizeof(sockaddr_in);
//    while(true) {
//        if ((new_socket = accept(server_fd, (struct sockaddr *) &clientAddr,
//                                 (socklen_t *) &clientAddrLen)) < 0) {
//            perror("accept method failed");
//            exit(EXIT_FAILURE);
//        }
//        bool closeAllConnections = false;
//        char serverIp[INET_ADDRSTRLEN];
//        inet_ntop(AF_INET, (const void *) &serverAddr.sin_addr, serverIp, INET_ADDRSTRLEN);
//        int serverPort = ntohs(serverAddr.sin_port);
//
//        char clientIp[INET_ADDRSTRLEN];
//        inet_ntop(AF_INET, (const void *) &clientAddr.sin_addr, clientIp, INET_ADDRSTRLEN);
//        int clientPort = ntohs(clientAddr.sin_port);
//
//        printf("connection established with socket\n");
//        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
//        int rVal = pthread_create(&threadID,
//                                  NULL,
//                                  ClientCommunication,
//                                  (void*)&parameter);
//        close(new_socket);
//        if(closeAllConnections){
//            break;
//        }
//    }
//    close(server_fd);
    //send(new_socket, hello, strlen(hello), 0);
    //printf("Hello message sent\n");

    //close(new_socket);

    //shutdown(server_fd, SHUT_RDWR);
    return 0;
}

