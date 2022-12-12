//
// Created by Lukas Jodlbauer on 05.12.22.
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
    //main socket
    int m_server_fd;
    //triggers shutdown
    bool m_shutdown;
    //parameter for thread creation
    struct m_socketParam {
        int socket;
        int bufferSize;
        EnvironmentServer *serverClass;
    };
    //struct to store and manage connections in vectors
    struct m_thread_manger{
        pthread_t threadId;
        int socket;
        bool operator==(const m_thread_manger& a) const
        {
            return (threadId == a.threadId && socket == a.socket);
        }
    };
    //vector to store and manager connections
    std::vector<m_thread_manger> m_threads = {};
    void CloseSingleCon(m_thread_manger con);
    void CloseAllConnections();
    EnvironmentServer();
    ~EnvironmentServer();
    void InitializeSocket(int _port, int _buffer_size);
    static void* ClientCommunication(void* _parameter);
    static void CleanUp(void *_target);
    void getRandomNumbers(int amount, char *result, char *sensor);
    void CloseSocket() const;
};


#endif //VIS_ENVIRONMENT_SERVER_H
