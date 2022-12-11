//
// Created by Nico Oliver on 05.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_UDP_SERVER_H
#define VIS_UDP_SERVER_H


class UdpServer {
public:
    int m_server_fd;

    UdpServer();
    ~UdpServer();
    void InitializeSocket(int _port, int _buffer_size);
    void CloseSocket() const;
};


#endif //VIS_UDP_SERVER_H
