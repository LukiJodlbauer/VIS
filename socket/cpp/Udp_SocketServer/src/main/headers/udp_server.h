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

#ifndef VIS_UDP_SERVER_H
#define VIS_UDP_SERVER_H


class UdpServer {
public:
    int m_server_fd;

    UdpServer();
    ~UdpServer();
    void InitializeSocket(int _port, int _buffer_size);
    void CloseSocket();
};


#endif //VIS_UDP_SERVER_H
