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


#define BUFFER_SIZE 1024
#define BACKLOG 5

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("Starting Server ...\n");
    int server_fd, new_socket, valread;
    sockaddr_in6 serverAddr = {};
    char buffer[1024] = {0};
    char dest[1024] = "ECHO: ";
    char *hello = "Hello from server\0";
    char *ack = "ack\0";

    // Creating socket file descriptor
    if ((server_fd = socket(AF_INET6, SOCK_STREAM, 0)) < 0) {
        perror("socket initialization failed");
        exit(EXIT_FAILURE);
    }

    serverAddr.sin6_family = AF_INET6;
    serverAddr.sin6_flowinfo = 0;
    serverAddr.sin6_scope_id = 0;
    serverAddr.sin6_addr = IN6ADDR_ANY_INIT;
    serverAddr.sin6_port = htons(4949);

    if (bind(server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("bind method failed");
        exit(EXIT_FAILURE);
    }
    if (listen(server_fd, 3) < 0) {
        perror("listen method failed");
        exit(EXIT_FAILURE);
    }

    sockaddr_in6 clientAddr = {};
    int clientAddrLen = sizeof(sockaddr_in);
    while(true) {
        if ((new_socket = accept(server_fd, (struct sockaddr *) &clientAddr,
                                 (socklen_t *) &clientAddrLen)) < 0) {
            perror("accept method failed");
            exit(EXIT_FAILURE);
        }
        bool closeAllConnections = false;
        char serverIp[INET6_ADDRSTRLEN];
        inet_ntop(AF_INET6, (const void *) &serverAddr.sin6_addr, serverIp, INET_ADDRSTRLEN);
        int serverPort = ntohs(serverAddr.sin6_port);

        char clientIp[INET6_ADDRSTRLEN];
        inet_ntop(AF_INET6, (const void *) &clientAddr.sin6_addr, clientIp, INET_ADDRSTRLEN);
        int clientPort = ntohs(clientAddr.sin6_port);

        printf("connection established with socket\n");
        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
        while(true) {
            memset(&buffer[0], 0, sizeof(buffer));
            memset(&dest[6], 0, sizeof(dest)-6);
            printf(dest);
            if ((valread = recv(new_socket, buffer, BUFFER_SIZE, 0)) <= 0) {
                printf("receive failed\n");
                return -1;
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
    close(server_fd);
    //send(new_socket, hello, strlen(hello), 0);
    //printf("Hello message sent\n");

    //close(new_socket);

    //shutdown(server_fd, SHUT_RDWR);
    return 0;
}
