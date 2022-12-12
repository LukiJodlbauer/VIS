//
// Created by Nico Oliver on 05.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_UDP_SERVER_H
#define VIS_UDP_SERVER_H


/**
 * Basic Udp-Server. Holds functionality to start server socket
 * and close it.
 */
class UdpServer {
public:
    /**
     * Stores descriptor referencing the main socket
     */
    int m_server_fd;

    /**
     *  Constructor initializes m_server_fd with default value
     */
    UdpServer();

    /**
     *  Default Destructor
     */
    ~UdpServer();

    /**
     *
     * @param _port port which should be used
     * @param _buffer_size size of buffer for user input
     * This function start the main socket which accepts the client connections and handles the communication flow
     * Incoming messages are returned as Echo to the client.
     * Messages are sent over UDP via ipv4
    */
    void InitializeSocket(int _port, int _buffer_size);

    /**
     * This function closes the main socket
     */
    void CloseSocket() const;
};


#endif //VIS_UDP_SERVER_H
