#include <ipv_6_server.h>


#define BUFFER_SIZE 1024
#define BACKLOG 5
/**
*
* @param _argc amount of given parameters
* @param _argv parameter from run command(port & ip address which should be used)
* @return exit type of program 0-> run successfully
* This function is the entry point of the program
*/
int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("Server ready ...\n");

    if (_argc < 3) {
        perror("not enough arguments (port[1], ip[2])");
        return -1;
    }

    int port = std::stoi(_argv[1]);
    if(port == 0){
        perror("Not able to obtain port from method parameter");
        return -1;
    }
    char *ip = _argv[2];

    Ipv6Server server;
    server.InitializeSocket(ip, port, BUFFER_SIZE, BACKLOG);
    server.CloseSocket();

    return 0;
}
