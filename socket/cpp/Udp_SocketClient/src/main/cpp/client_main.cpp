#include <udp_client.h>

#define BUFFER_SIZE 1024

using namespace std;

int main(int _argc, char **_argv) {
    setbuf(stdout, nullptr);
    printf("starting server ...\n");

    if (_argc < 3) {
        perror("Not enough Arguments (port[1], ip[2])");
        return -1;
    }

    int port = atoi(_argv[1]);
    char *ip = _argv[2];

    UdpClient client;
    client.ConnectSocket(ip, port, BUFFER_SIZE);
    client.CloseSocket();

    return 0;
}