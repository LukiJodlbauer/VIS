rootProject.name = "VIS"
include(
    ":general:EnvironmentI",
    ":soap:dynamic:Environment_SoapServer",
    ":soap:dynamic:Environment_SoapClient",
    ":soap:static:Environment_SoapClientStatic",
    ":soap:dynamic:Hello_SoapServer",
    ":soap:dynamic:Hello_SoapClient",
    ":soap:static:Hello_SoapServer",
    ":soap:static:Hello_SoapClient",
    ":jaxb:BruteForce",
    ":jaxb:JSON",
    ":jaxb:Parser",
    ":jaxb:XML",
    ":servlet:Hello_Servlet",
    ":servlet:Info_Servlet",
    ":servlet:Session_Servlet",
    ":servlet:EnvironmentService_Servlet",
    ":socket:java:HelloWorld",
    ":socket:java:Echo_SocketServer",
    ":socket:java:Echo_SocketClient",
    ":socket:java:Environment_SocketClient",
    ":socket:cpp:Hello",
    ":socket:cpp:Primitive_SocketClient",
    ":socket:cpp:Primitive_SocketServer",
    ":socket:cpp:Echo_SocketServer",
    ":socket:cpp:Http_SocketServer",
    ":socket:cpp:Udp_SocketServer",
    ":socket:cpp:Udp_SocketClient",
    ":socket:cpp:Ipv6_SocketServer",
    ":socket:cpp:Ipv6_SocketClient",
    ":socket:cpp:Environment_SocketServer",
    ":rest:Hello_RestServer",
    ":rest:Environment_RestServer",
    ":rest:Environment_RestClient")
include("rmi:HelloWorld_RmiInterface")
findProject(":rmi:HelloWorld_RmiInterface")?.name = "HelloWorld_RmiInterface"
include("rmi:HelloWorld_RmiServer")
findProject(":rmi:HelloWorld_RmiServer")?.name = "HelloWorld_RmiServer"
include("rmi:HelloWorld_RmiClient")
findProject(":rmi:HelloWorld_RmiClient")?.name = "HelloWorld_RmiClient"
include("rmi:Environment_RmiServer")
findProject(":rmi:Environment_RmiServer")?.name = "Environment_RmiServer"
include("rmi:Environment_RmiClient")
findProject(":rmi:Environment_RmiClient")?.name = "Environment_RmiClient"
