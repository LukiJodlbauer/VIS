//
// Created by Nico Oliver on 05.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <pthread.h>
#include <regex>
#include <random>
#include <chrono>


#ifndef VIS_ECHO_SERVER_H
#define VIS_ECHO_SERVER_H


class EchoServer {
public:
    int m_server_fd;

    EchoServer();
    ~EchoServer();
    void InitializeSocket(int _port, int _buffer_size, int _backlog);
    void CloseSocket() const;
};


#endif //VIS_ECHO_SERVER_H
