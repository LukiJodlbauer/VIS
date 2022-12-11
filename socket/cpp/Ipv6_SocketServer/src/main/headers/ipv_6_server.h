//
// Created by Nico Oliver on 07.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_IPV_6_SERVER_H
#define VIS_IPV_6_SERVER_H


class Ipv6Server {
public:
    int m_server_fd;

    Ipv6Server();
    ~Ipv6Server();
    void InitializeSocket(char *_ip, int _port, int _buffer_size, int _backlog);
    void CloseSocket() const;
};


#endif //VIS_IPV_6_SERVER_H
