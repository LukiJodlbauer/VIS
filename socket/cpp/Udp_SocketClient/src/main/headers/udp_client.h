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

#ifndef VIS_UDP_CLIENT_H
#define VIS_UDP_CLIENT_H


class UdpClient {
public:
    int m_connection;

    UdpClient();
    ~UdpClient();
    void ConnectSocket(char *_ip, int _port, int _buffer_size);
    void CloseSocket();
};


#endif //VIS_UDP_CLIENT_H
