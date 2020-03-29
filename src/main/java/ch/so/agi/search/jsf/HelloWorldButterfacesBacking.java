package ch.so.agi.search.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class HelloWorldButterfacesBacking {
    public String showGreeting() {
        return "Hello John Doe!";
    }
}
