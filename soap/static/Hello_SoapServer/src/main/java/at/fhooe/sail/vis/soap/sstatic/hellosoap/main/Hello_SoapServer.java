package at.fhooe.sail.vis.soap.sstatic.hellosoap.main;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * Hello-Soap Server for testing SOAP functionality.
 */
@WebService
public class Hello_SoapServer {
    /**
     * Basic Method for testing.
     * @return String with dummy content.
     */
    @WebMethod
    public String saySomething() {
        return "Hello World!";
    }

    /**
     * Basic Method for testing.
     * @param _name used for creating Dummy-object.
     * @return DummyData-object
     */
    @WebMethod
    public DummyData getData(String _name) {
        DummyData data = new DummyData(_name, "Some Value");
        return data;
    }
}