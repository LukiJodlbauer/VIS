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

/**
 * Basic Echo-Server. Holds functionality to start server socket
 * and close it.
 */
class HttpServer {
public:
    /**
     * Stores descriptor referencing the main socket
     */
    int m_server_fd;

    /**
     *  Constructor initializes m_server_fd with default value
     */
    HttpServer();

    /**
     *  Default Destructor
     */
    ~HttpServer();

    /**
     *
     * @param _port port which should be used
     * @param _buffer_size size of buffer for user input
     * @param _backlog number of connections allowed
     * This function start the main socket which accepts the client connections and handles the communication flow
     * Incoming messages are returned as Echo to the client
     * Messages are send over TCP via ipv4
     */
    void InitializeSocket(int _port, int _buffer_size, int _backlog);

    /**
     * This function closes the main socket
     */
    void CloseSocket() const;

    void ReplaceAll(std::string &str, const std::string &from, const std::string &to);

    void InitializeSocket(int _port, int _backlog);
};


#endif //VIS_ECHO_SERVER_H
