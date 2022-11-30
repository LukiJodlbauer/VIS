#include <iostream> // cout, cin
#include <iostream> // cout, cin
#include <sys/socket.h> // socket, bind, listen, accept
#include <netinet/in.h> // IPPROTO_TCP, sockaddr_in,
// htons/ntohs, INADDR_ANY
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <semaphore.h> // sem_init
#include <pthread.h>
#include <cstring>

#define BUFFER_SIZE 1024

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("Starting Client ...\n");

    if (_argc < 3) {
        perror("Not enough Arguments (port[1], ip[2])");
        return -1;
    }

    int port = atoi(_argv[1]);
    char *ip = _argv[2];

    printf("IP: %s\n", ip);
    printf("Port: %i\n", port);

    int sock = 0, client_fd;
    ssize_t valread = 0;
    struct sockaddr_in serv_addr;
    char *hello = "Hello from client\0";
    char buffer[BUFFER_SIZE] = {0};

    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        printf("\n Socket creation error \n");
        return -1;
    }

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(port);

    if (inet_pton(AF_INET, ip, &serv_addr.sin_addr) <= 0) {
        printf("\nInvalid address/ Address not supported \n");
        return -1;
    }
    memset(&(serv_addr.sin_zero),'\0',8);
    socklen_t server_addr_length = sizeof(sockaddr_in);


    //send(sock, hello, strlen(hello), 0);
    //printf("Hello message sent\n");
    char message[BUFFER_SIZE];
    while(true) {
        memset(&buffer[0],0,sizeof(buffer));
        cout << "please enter your message : ";
        cin.getline(message, BUFFER_SIZE - 1);
        cout << message << endl;

        sendto(sock, message, strlen(message), 0,(sockaddr*) &serv_addr, server_addr_length);
        //printf("Hello message sent\n");
        if(strcmp(message,"quit") == 0){
            break;
        }
        if ((valread = recvfrom(sock, buffer, BUFFER_SIZE, 0, (sockaddr*) &serv_addr,
                                &server_addr_length)) <= 0) {
            printf("receive failed\n");
            return -1;
        }
        printf("Received %i bytes, Message: %s\n", valread, buffer);
    }
    close(client_fd);
    return 0;
}