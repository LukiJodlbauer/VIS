package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

import jakarta.jws.WebService;

/**
 * Basic Interface for testing dynamic SOAP project
 */
@WebService
public interface ISimpleInterface {
    /**
     * Basic Method for testing.
     * @return String with dummy content.
     */
    String saySomething();

    /**
     * Basic Method for testing.
     * @param _name used for creating Dummy-object.
     * @return DummyData-object
     */
    DummyData getData(String _name);
}
