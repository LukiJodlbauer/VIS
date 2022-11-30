#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>

#define BUFFER_SIZE 1024

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting client ...\n");

    if (_argc < 3) {
        perror("not enough arguments (port[1], ip[2])");
        return -1;
    }

    int port = atoi(_argv[1]);
    char *ip = _argv[2];

    printf("found the following ip and port: ");
    printf("%s:%i\n", ip, port);

    char buffer[BUFFER_SIZE] = {0};

    int client_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (client_fd < 0) {
        perror("socket creation error");
        return -1;
    }
    printf("socket was created ...\n");

    sockaddr_in serverAddr = {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(port);

    if (inet_pton(AF_INET, ip, &serverAddr.sin_addr) <= 0) {
        perror("invalid address/address not supported");
        return -1;
    }

    int connection = connect(client_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr));
    if (connection < 0) {
        perror("connection failed");
        return -1;
    }
    printf("connection established ...\n");

    while (true) {
        char message[BUFFER_SIZE];
        cout << "please enter your message : ";
        cin.getline(message, BUFFER_SIZE - 1);

        if (strcmp(message, "quit") == 0) {
            break;
        }

        int ret = send(client_fd, message, (int) strlen(message), 0);
        if (ret < 0) {
            perror("send failed");
            return -1;
        }

        ret = recv(client_fd, buffer, (size_t) BUFFER_SIZE, 0);
        if (ret < 0) {
            perror("receive failed");
            return -1;
        } else if (ret == 0) {
            printf("connection closed by partner\n");
            break;
        }

        printf("Received %i bytes, Message: %s\n", ret, buffer);
    }

    printf("closing connection now ...\n");
    close(connection);
    return 0;
}
