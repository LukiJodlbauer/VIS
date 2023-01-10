//
// Created by Nico Oliver on 05.12.22.
//

#include "http_server.h"
#include "../headers/http_server.h"

#include <sstream>

#define BUFFER_SIZE 1024

using namespace std;

void HttpServer::InitializeSocket(int _port, int _backlog) {
    int server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd < 0) {
        char erno_buffer[ 256 ];
        strerror_r( errno, erno_buffer, 256 );
        printf("Error at socket creation %s", erno_buffer);
        exit(EXIT_FAILURE);
    }
    printf("socket was created ...\n");

    sockaddr_in serverAddr = {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(_port);

    if (bind(server_fd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        char erno_buffer[ 256 ];
        strerror_r( errno, erno_buffer, 256 );
        printf("Error at binding method %s", erno_buffer);
        exit(EXIT_FAILURE);
    }
    printf("port was bound ...\n");

    if (listen(server_fd, _backlog) < 0) {
        char erno_buffer[ 256 ];
        strerror_r( errno, erno_buffer, 256 );
        printf("Error at listen method %s", erno_buffer);
        exit(EXIT_FAILURE);
    }
    printf("listening for connections ...\n");

    sockaddr_in clientAddr = {};
    int clientAddrLen = sizeof(sockaddr_in);

    while(true) {
        int new_socket = accept(server_fd, (struct sockaddr *) &clientAddr,
                                (socklen_t *) &clientAddrLen);
        if (new_socket < 0) {
            char erno_buffer[ 256 ];
            strerror_r( errno, erno_buffer, 256 );
            printf("Error at accept method %s", erno_buffer);
            exit(EXIT_FAILURE);
        }
        printf("accepted connection ...\n");

        char serverIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &serverAddr.sin_addr, serverIp, INET_ADDRSTRLEN);
        int serverPort = ntohs(serverAddr.sin_port);

        char clientIp[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, (const void *) &clientAddr.sin_addr, clientIp, INET_ADDRSTRLEN);
        int clientPort = ntohs(clientAddr.sin_port);

        printf("connection established with socket\n");
        printf("[client (%s, %i); server (%s, %i)]\n", clientIp, clientPort, serverIp, serverPort);

        char msg[BUFFER_SIZE] = {0};
        int code = recv(new_socket, msg, BUFFER_SIZE, 0);
        if (code < 0) {
            perror("error receiving _msg");
            exit(EXIT_FAILURE);
        }
        cout << "received msg: " << msg;
        string msg_string(msg);

        ReplaceAll(msg_string, "\r\n", "<br>");

        stringstream html_stream;
        html_stream << "<html>"
            << "<head>"
            << "<title>HTTP-ECHO</title>"
            << "</head>"
            << "<body>"
            << "<h1>"
            << "Browser Request"
            << "</h1>"
            << "<p>"
            << msg_string
            << "</p>"
            << "</body>"
            << "</html>";

        string html_string = html_stream.str();
        cout << "html-string: " << html_string << endl;

        stringstream request_stream;
        request_stream << "HTTP/1.1 200 OK \r\n"
           << "Content-Type: text/html \r\n"
           << "Content-Length: " << html_string.length() << " \r\n"
           << "\r\n"
           << html_string;

        string request = request_stream.str();

        cout << "Request: " << request << endl;

        char ret[request.length()+1];
        strcpy(ret, request.c_str());

        if (send(new_socket, ret, request.length() + 1, 0) < 0) {
            perror("error sending message");
            exit(EXIT_FAILURE);
        }

        printf("closing connection to socket now ...\n");
        close(new_socket);
    }
}

void HttpServer::CloseSocket() const {
    if(close(m_server_fd) < 0){
        char erno_buffer[ 256 ];
        strerror_r( errno, erno_buffer, 256 );
        printf("Error at closing main socket %s", erno_buffer);
        exit(EXIT_FAILURE);
    }
}

HttpServer::HttpServer() {
    m_server_fd = -1;
}

void HttpServer::ReplaceAll(string& str, const string& from, const string& to) {
    if(from.empty())
        return;
    size_t start_pos = 0;
    while((start_pos = str.find(from, start_pos)) != string::npos) {
        str.replace(start_pos, from.length(), to);
        start_pos += to.length();
    }
}


HttpServer::~HttpServer() = default;
