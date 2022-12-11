//
// Created by Nico Oliver on 05.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_UDP_CLIENT_H
#define VIS_UDP_CLIENT_H


class UdpClient {
public:
    int m_connection;

    UdpClient();
    ~UdpClient();
    void ConnectSocket(char *_ip, int _port, int _buffer_size);
    void CloseSocket() const;
};


#endif //VIS_UDP_CLIENT_H
