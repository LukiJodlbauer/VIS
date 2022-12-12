#include <udp_server.h>

#define BUFFER_SIZE 1024

/**
*
* @param _argc amount of given parameters
* @param _argv parameter from run command(port which should be used)
* @return exit type of program 0-> run successfully
* This function is the entry point of the program
*/
int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting server ...\n");

    if (_argc < 2) {
        perror("not enough arguments (port[1])");
        return -1;
    }

    int port = std::stoi(_argv[1]);
    if(port == 0){
        perror("Not able to obtain port from method parameter");
        return -1;
    }

    UdpServer server;
    server.InitializeSocket(port, BUFFER_SIZE);
    server.CloseSocket();

    return 0;
}
