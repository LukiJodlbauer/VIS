//
// Created by luki on 05.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <pthread.h>
#include <regex>
#include <random>
#include <chrono>

#ifndef VIS_ENVIRONMENT_SERVER_H
#define VIS_ENVIRONMENT_SERVER_H

class EnvironmentServer {
public:
    int m_server_fd;
    bool m_shutdown;
    struct m_socketParam {
        int socket;
        int bufferSize;
        EnvironmentServer *serverClass;
    };

    EnvironmentServer();
    ~EnvironmentServer();
    void InitializeSocket(int _port, int _buffer_size);
    static void* ClientCommunication(void* _parameter);
    static void CleanUp(void *_target);
    void getRandomNumbers(int amount, char *result, char *sensor);
    void CloseSocket() const;
};


#endif //VIS_ENVIRONMENT_SERVER_H
