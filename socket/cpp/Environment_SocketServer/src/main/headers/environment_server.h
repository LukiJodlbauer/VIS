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

/**
 * Environment-Server. Is using threads and holds functionality to start server socket
 * and close it.
 */
class EnvironmentServer {
public:
    /**
     * main socket
     */
    int m_server_fd;

    /**
     * triggers shutdown
     */
    bool m_shutdown;

    /**
     * parameter for thread creation
     */
    struct m_socketParam {
        int socket;
        int bufferSize;
        EnvironmentServer *serverClass;
    };

    /**
     * struct to store and manage connections in vectors
     */
    struct m_thread_manger {
        pthread_t threadId;
        int socket;

        bool operator==(const m_thread_manger &a) const {
            return (threadId == a.threadId && socket == a.socket);
        }
    };

    /**
     * vector to store and manager connections
     */
    std::vector<m_thread_manger> m_threads;

    /**
     *
     * @param con element which should be removed
     * This function removes a connection from all open connections
     */
    void CloseSingleCon(m_thread_manger con);

    /**
     * This function closes all open connections and triggers the shutdown to kill th main socket
     */
    void CloseAllConnections();

    /**
     *  Constructor initializes m_server_fd with default value and m_shutdown with false
     */
    EnvironmentServer();

    /**
     *  Default Destructor
     */
    ~EnvironmentServer();

    /**
     *
     * @param _port port which should be used
     * @param _buffer_size size of buffer for user input
     * This function start the main socket which accepts the client connections and creates threads for each client
     * Messages are send over TCP via ipv4
     */
    void InitializeSocket(int _port, int _buffer_size);

    /**
     *
     * @param _parameter
     * This function handles the client communication
     * Possible client methods: drop(), shutdown(), getSensorTypes(), getAllSensors(), getSensor(_sensor).
     * Every other input will be returned as Echo
     */
    static void *ClientCommunication(void *_parameter);

    /**
     *
     * @param _target holds socket and client information
     * This function closes the client socket and deletes the stored client information.
     * This is going to be executed before the the thread is destroyed
     *
     */
    static void CleanUp(void *_target);

    /**
     *
     * @param amount amount of sensor values to be created
     * @param result char[] in which the values will be stored
     * @param sensor type of sensor; nullptr if it should not be added
     * This function creates the char[](with generates values for the given sensor)which is going to be returned to the client
     */
    void getRandomNumbers(int amount, char *result, char *sensor);

    /**
     * This function closes the main socket
     */
    void CloseSocket() const;
};


#endif //VIS_ENVIRONMENT_SERVER_H
