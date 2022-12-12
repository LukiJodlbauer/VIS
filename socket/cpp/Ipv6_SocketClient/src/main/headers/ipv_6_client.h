//
// Created by Nico Oliver on 07.12.22.
//
#include <iostream> // cout, cin
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <regex>

#ifndef VIS_IPV_6_CLIENT_H
#define VIS_IPV_6_CLIENT_H


/**
 * Basic Udp-Client. Holds functionality to start connection
 * to server socket and close it.
 */
class Ipv6Client {
public:
    /**
     * Stores descriptor referencing the connection socket
     */
    int m_connection;

    /**
     *  Constructor initializes m_connection with default value
     */
    Ipv6Client();

    /**
     *  Default Destructor
     */
    ~Ipv6Client();

    /**
     *
     * @param _ip ip address which should be used
     * @param _port port which should be used
     * @param _buffer_size size of buffer for user input
     * This function start the main socket which accepts the client connections and handles the communication flow
     * Incoming messages are returned as Echo to the client
     * Messages are send over TCP with ipv6
    */
    void ConnectSocket(char *_ip, int _port, int _buffer_size);

    /**
    * This function closes the main socket
    */
    void CloseSocket() const;
};


#endif //VIS_IPV_6_CLIENT_H
