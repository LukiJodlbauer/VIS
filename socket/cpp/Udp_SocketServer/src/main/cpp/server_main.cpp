#include <udp_server.h>

#define BUFFER_SIZE 1024

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting server ...\n");

    if (_argc < 2) {
        perror("not enough arguments (port[1])");
        return -1;
    }

    int port = atoi(_argv[1]);

    UdpServer server;
    server.InitializeSocket(port, BUFFER_SIZE);
    server.CloseSocket();

//    setbuf(stdout, nullptr);
//    printf("Starting Server ...\n");
//    int server_fd;
//    ssize_t valread = 0;
//    sockaddr_in serverAddr = {};
//    char buffer[1024] = {0};
//    char dest[1024] = "ECHO: ";
//    char *hello = "Hello from server\0";
//    char *ack = "ack\0";
//
//    // Creating socket file descriptor
//    if ((server_fd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
//        perror("socket initialization failed");
//        exit(EXIT_FAILURE);
//    }
//
//    serverAddr.sin_family = AF_INET;
//    serverAddr.sin_addr.s_addr = INADDR_ANY;
//    serverAddr.sin_port = htons(6661);
//    memset(&(serverAddr.sin_zero),'\0',8);
//    if (bind(server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
//        perror("bind method failed");
//        exit(EXIT_FAILURE);
//    }
//
//
//    sockaddr_in clientAddr = {};
//    memset(&(clientAddr.sin_zero),'\0',8);
//    socklen_t clientAddrLen = sizeof(sockaddr_in);
////    while(true) {
////        if ((new_socket = accept(server_fd, (struct sockaddr *) &clientAddr,
////                                 (socklen_t *) &clientAddrLen)) < 0) {
////            perror("accept method failed");
////            exit(EXIT_FAILURE);
////        }
//        bool closeAllConnections = false;
////        char serverIp[INET_ADDRSTRLEN];
////        inet_ntop(AF_INET, (const void *) &serverAddr.sin_addr, serverIp, INET_ADDRSTRLEN);
////        int serverPort = ntohs(serverAddr.sin_port);
////
////        char clientIp[INET_ADDRSTRLEN];
////        inet_ntop(AF_INET, (const void *) &clientAddr.sin_addr, clientIp, INET_ADDRSTRLEN);
////        int clientPort = ntohs(clientAddr.sin_port);
//
//        //printf("connection established with socket\n");
//        //printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
//        while(true) {
//            memset(&buffer[0], 0, sizeof(buffer));
//            memset(&dest[6], 0, sizeof(dest)-6);
//            if ((valread = recvfrom(server_fd, buffer, BUFFER_SIZE, 0, (sockaddr*) &clientAddr,
//                                    &clientAddrLen)) <= 0) {
//                printf("receive failed\n");
//                return -1;
//            }
//            //printf(buffer);
//            if(strcmp(buffer,"drop") == 0 || strcmp(buffer,"quit") == 0){
//                printf("in drop");
//                break;
//            }
//            if(strcmp(buffer,"shutdown") == 0){
//                printf("in shutdown");
//                closeAllConnections = true;
//                break;
//            }
//            strncat(dest, buffer, sizeof(dest) - strlen(dest) - 1);
//            //printf("Received %i bytes, Message: %s\n", valread, buffer);
//            sendto(server_fd, dest, strlen(dest), 0, (sockaddr*) &clientAddr, clientAddrLen);
//        }
//        //close(new_socket);
////        if(closeAllConnections){
////            break;
////        }
// //   }
//    close(server_fd);
//    //send(new_socket, hello, strlen(hello), 0);
//    //printf("Hello message sent\n");
//
//    //close(new_socket);
//
//    //shutdown(server_fd, SHUT_RDWR);
    return 0;
}
