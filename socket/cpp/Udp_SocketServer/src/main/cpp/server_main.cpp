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

    return 0;
}
