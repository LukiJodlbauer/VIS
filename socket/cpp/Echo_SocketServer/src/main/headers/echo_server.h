//
// Created by Nico Oliver on 05.12.22.
//
#include <iostream> // cout, cin
#include <iostream> // cout, cin
#include <sys/socket.h> // socket, bind, listen, accept
#include <netinet/in.h> // IPPROTO_TCP, sockaddr_in,
// htons/ntohs, INADDR_ANY
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <semaphore.h> // sem_init
#include <pthread.h>
#include <errno.h>
#include <cstring>

#ifndef VIS_ECHO_SERVER_H
#define VIS_ECHO_SERVER_H


class EchoServer {
public:
    int m_server_fd;

    EchoServer();
    ~EchoServer();
    void InitializeSocket(int _port, int _buffer_size, int _backlog);
    void CloseSocket();
};


#endif //VIS_ECHO_SERVER_H
