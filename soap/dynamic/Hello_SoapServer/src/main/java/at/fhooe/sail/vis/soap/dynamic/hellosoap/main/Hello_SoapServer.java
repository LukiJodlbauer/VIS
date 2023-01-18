package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * Dynamic SOAP-Server implementing ISimpleInterface
 */
@WebService(endpointInterface =
        "at.fhooe.sail.vis.soap.dynamic.hellosoap.main.ISimpleInterface")
public class Hello_SoapServer implements ISimpleInterface {
    @Override
    @WebMethod
    public String saySomething() {
        return "Hello World!";
    }

    @Override
    @WebMethod
    public DummyData getData(String _name) {
        DummyData data = new DummyData(_name, "Some Value");
        return data;
    }
}