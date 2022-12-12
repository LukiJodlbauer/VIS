#include <unistd.h>
#include <arpa/inet.h>
#include <regex>

#define BUFFER_SIZE 1024
#define BACKLOG 5

/**
*
* @param _argc amount of given parameters
* @param _argv parameter from run command(port & ip address which should be used)
* @return exit type of program 0-> run successfully
* This function is the entry point of the program it handles the client connections and the communication flow
*/
int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting server ...\n");

    if (_argc < 2) {
        perror("not enough arguments (port[1])");
        return -1;
    }

    int port = std::stoi(_argv[1]);
    const char *ack = "ack\0";
    char buffer[1024] = {0};

    int server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd < 0) {
        perror("socket initialization failed");
        exit(EXIT_FAILURE);
    }
    printf("socket was created ...\n");

    sockaddr_in serverAddr = {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(port);

    if (bind(server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("bind method failed");
        exit(EXIT_FAILURE);
    }
    printf("port was bound ...\n");

    if (listen(server_fd, BACKLOG) < 0) {
        perror("listen method failed");
        exit(EXIT_FAILURE);
    }
    printf("listening for connections ...\n");

    sockaddr_in clientAddr = {};
    int clientAddrLen = sizeof(sockaddr_in);
    bool doShutdown = false;

    while (true) {
        int new_socket = accept(server_fd, (struct sockaddr *) &clientAddr,
                                (socklen_t *) &clientAddrLen);
        if (new_socket < 0) {
            perror("accept method failed");
            exit(EXIT_FAILURE);
        }
        printf("accepted connection ...\n");

        char serverIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &serverAddr.sin_addr, serverIp, INET_ADDRSTRLEN);
        int serverPort = ntohs(serverAddr.sin_port);

        char clientIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &clientAddr.sin_addr, clientIp, INET_ADDRSTRLEN);
        int clientPort = ntohs(clientAddr.sin_port);

        printf("connection established with socket\n");
        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);
        while (true) {
            memset(&buffer[0], 0, sizeof(buffer));

            long ret = recv(new_socket, buffer, BUFFER_SIZE, 0);
            if (ret < 0) {
                perror("receive failed");
                return -1;
            } else if (ret == 0) {
                printf("connection closed by partner\n");
                break;
            }

            if (strcmp(buffer, "drop") == 0) {
                printf("received drop request\n");
                break;
            } else if (strcmp(buffer, "shutdown") == 0) {
                printf("received shutdown request\n");
                doShutdown = true;
                break;
            }

            ret = send(new_socket, ack, strlen(ack), 0);
            if (ret < 0) {
                perror("send failed");
                return -1;
            }
        }

        printf("closing connection to socket now ...\n");
        if(close(new_socket) < 0){
            perror("close client socket failed");
            exit(EXIT_FAILURE);
        }
        if (doShutdown) {
            break;
        }
    }

    printf("shutting down server now ...\n");
    if(close(server_fd) < 0){
        perror("close main socket failed");
        exit(EXIT_FAILURE);
    }
    //shutdown(server_fd, SHUT_RDWR);
    return 0;
}
