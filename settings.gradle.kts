rootProject.name = "VIS"
include(
    ":java:HelloWorld",
    ":socket:cpp:Hello",
    ":socket:cpp:Primitive_SocketClient",
    ":socket:cpp:Primitive_SocketServer",
    ":socket:cpp:Echo_SocketServer",
    "socket:cpp:Udp_SocketServer",
    "socket:cpp:Udp_SocketClient")