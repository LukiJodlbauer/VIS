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
#include <sstream>

#ifndef VIS_HTTP_SERVER_H
#define VIS_HTTP_SERVER_H

using namespace std;

/**
 * Basic Http-Server. Holds functionality to start server socket
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
     * It sends a basic HTTP-Website with client details.
     */
    void InitializeSocket(int _port, int _backlog);

    /**
     * This function closes the main socket
     */
    void CloseSocket() const;

    /**
     * Replaces all instances equal to "from" with "to" in the str string
     * @param str string to replace in
     * @param from string to replace
     * @param to string to replace with
     */
    void ReplaceAll(string& str, const string& from, const string& to);
};


#endif //VIS_HTTP_SERVER_H
