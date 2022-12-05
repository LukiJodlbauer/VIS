//
// Created by Nico Oliver on 05.12.22.
//
#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <cerrno>
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
