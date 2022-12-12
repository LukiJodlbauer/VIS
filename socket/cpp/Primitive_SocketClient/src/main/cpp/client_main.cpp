#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#define BUFFER_SIZE 1024

/**
*
* @param _argc amount of given parameters
* @param _argv parameter from run command(port & ip address which should be used)
* @return exit type of program 0-> run successfully
* This function is the entry point of the program it handles the client connections and the communication flow
*/
int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting client ...\n");

    if (_argc < 2) {
        perror("not enough arguments (port[1], ip[2])");
        return -1;
    }

    int port = std::stoi(_argv[1]);
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
        memset(&buffer[0], 0, sizeof(buffer));

        std::cout << "please enter your message : ";
        std::cin.getline(message, BUFFER_SIZE - 1);

        if (strcmp(message, "quit") == 0) {
            break;
        }

        long ret = send(client_fd, message, (int) strlen(message), 0);
        if (ret < 0) {
            perror("send failed");
            return -1;
        }
        memset(&buffer[0], 0, sizeof(buffer));
        ret = recv(client_fd, buffer, (size_t) BUFFER_SIZE, 0);
        if (ret < 0) {
            perror("receive failed");
            return -1;
        } else if (ret == 0) {
            printf("connection closed by partner\n");
            break;
        }

        printf("Received %li bytes, Message: %s\n", ret, buffer);
    }

    printf("closing connection now ...\n");
    if (close(connection) < 0) {
        perror("close socket failed");
        return -1;
    }
    return 0;
}
