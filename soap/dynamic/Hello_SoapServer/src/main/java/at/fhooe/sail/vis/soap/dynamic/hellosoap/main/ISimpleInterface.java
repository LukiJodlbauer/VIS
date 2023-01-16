package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

import jakarta.jws.WebService;

@WebService
public interface ISimpleInterface {
    String saySomething();
    DummyData getData(String _name);
}
