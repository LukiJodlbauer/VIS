//
// Created by Nico Oliver on 07.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_IPV_6_SERVER_H
#define VIS_IPV_6_SERVER_H

/**
 * Basic IPv6-Server. Holds functionality to start server socket
 * and close it.
 */
class Ipv6Server {
public:
    /**
     * Stores descriptor referencing the main socket
     */
    int m_server_fd;

    /**
     *  Constructor initializes m_server_fd with default value
     */
    Ipv6Server();

    /**
     *  Default Destructor
     */
    ~Ipv6Server();

    /**
     * @param _ip ip address which should be used
     * @param _port port which should be used
     * @param _buffer_size size of buffer for user input
     * @param _backlog number of connections allowed
     * This function start the main socket which accepts the client connections and handles the communication flow
     * Incoming messages are returned as Echo to the client
     * Messages are send over TCP with ipv6
    */
    void InitializeSocket(char *_ip, int _port, int _buffer_size, int _backlog);

    /**
     * This function closes the main socket
     */
    void CloseSocket() const;
};


#endif //VIS_IPV_6_SERVER_H
