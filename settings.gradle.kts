rootProject.name = "VIS"
include(
    ":socket:java:HelloWorld",
    ":socket:java:Echo_SocketServer",
    ":socket:java:Echo_SocketClient",
    ":socket:java:Environment_SocketClient",
    ":socket:cpp:Hello",
    ":socket:cpp:Primitive_SocketClient",
    ":socket:cpp:Primitive_SocketServer",
    ":socket:cpp:Echo_SocketServer",
    "socket:cpp:Udp_SocketServer",
    "socket:cpp:Udp_SocketClient",
    "socket:cpp:Ipv6_SocketServer",
    "socket:cpp:Ipv6_SocketClient")