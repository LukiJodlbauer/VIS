//
// Created by Nico Oliver on 07.12.22.
//
#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <cerrno>
#include <cstring>

#ifndef VIS_IPV_6_SERVER_H
#define VIS_IPV_6_SERVER_H


class Ipv6Server {
public:
    int m_server_fd;

    Ipv6Server();
    ~Ipv6Server();
    void InitializeSocket(char *ip, int _port, int _buffer_size, int _backlog);
    void CloseSocket();
};


#endif //VIS_IPV_6_SERVER_H
