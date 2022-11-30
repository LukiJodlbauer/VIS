#include <iostream> // cout, cin
#include <iostream> // cout, cin
#include <sys/socket.h> // socket, bind, listen, accept
#include <netinet/in.h> // IPPROTO_TCP, sockaddr_in,
// htons/ntohs, INADDR_ANY
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <string.h> // strlen
#include <semaphore.h> // sem_init
#include <pthread.h>

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

    int sock = 0, valread, client_fd;
    struct sockaddr_in6 serv_addr;
    char *hello = "Hello from client\0";
    char buffer[BUFFER_SIZE] = {0};

    if ((sock = socket(AF_INET6, SOCK_STREAM, 0)) < 0) {
        printf("\n Socket creation error \n");
        return -1;
    }

    serv_addr.sin6_family = AF_INET6;
    serv_addr.sin6_port = htons(port);
    if (inet_pton(AF_INET6, ip, &serv_addr.sin6_addr) <= 0) {
        printf("\nInvalid address/ Address not supported \n");
        return -1;
    }

    if ((client_fd = connect(sock, (struct sockaddr *) &serv_addr,
                             sizeof(serv_addr))) < 0) {
        printf("\nConnection Failed \n");
        return -1;
    }

    //send(sock, hello, strlen(hello), 0);
    //printf("Hello message sent\n");
    char message[BUFFER_SIZE];
    while(true) {
        memset(&buffer[0],0,sizeof(buffer));
        cout << "please enter your message : ";
        cin.getline(message, BUFFER_SIZE - 1);
        cout << message << endl;

        send(sock, message, strlen(message), 0);
        //printf("Hello message sent\n");
        if(strcmp(message,"quit") == 0){
            break;
        }
        if ((valread = recv(sock, buffer, BUFFER_SIZE, 0)) <= 0) {
            printf("receive failed\n");
            return -1;
        }
        printf("Received %i bytes, Message: %s\n", valread, buffer);
    }
    close(client_fd);
    return 0;
}