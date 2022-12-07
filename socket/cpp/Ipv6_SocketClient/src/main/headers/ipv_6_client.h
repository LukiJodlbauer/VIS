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

#ifndef VIS_IPV_6_CLIENT_H
#define VIS_IPV_6_CLIENT_H


class Ipv6Client {
public:
    int m_connection;

    Ipv6Client();
    ~Ipv6Client();
    void ConnectSocket(char *_ip, int _port, int _buffer_size);
    void CloseSocket();
};


#endif //VIS_IPV_6_CLIENT_H
