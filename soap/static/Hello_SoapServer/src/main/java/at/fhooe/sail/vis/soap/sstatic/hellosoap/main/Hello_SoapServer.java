package at.fhooe.sail.vis.soap.sstatic.hellosoap.main;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class Hello_SoapServer {
    @WebMethod
    public String saySomething() {
        return "Hello World!";
    }

    @WebMethod
    public DummyData getData(String _name) {
        DummyData data = new DummyData(_name, "Some Value");
        return data;
    }
}