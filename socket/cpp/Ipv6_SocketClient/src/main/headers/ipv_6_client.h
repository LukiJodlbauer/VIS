//
// Created by Nico Oliver on 07.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_IPV_6_CLIENT_H
#define VIS_IPV_6_CLIENT_H


class Ipv6Client {
public:
    int m_connection;

    Ipv6Client();
    ~Ipv6Client();
    void ConnectSocket(char *_ip, int _port, int _buffer_size);
    void CloseSocket() const;
};


#endif //VIS_IPV_6_CLIENT_H
