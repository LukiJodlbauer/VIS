#include <ipv_6_server.h>


#define BUFFER_SIZE 1024
#define BACKLOG 5

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting server ...\n");

    if (_argc < 3) {
        perror("not enough arguments (port[1], ip[2])");
        return -1;
    }

    int port = atoi(_argv[1]);
    char *ip = _argv[2];

    Ipv6Server server;
    server.InitializeSocket(ip, port, BUFFER_SIZE, BACKLOG);
    server.CloseSocket();

    return 0;
}
