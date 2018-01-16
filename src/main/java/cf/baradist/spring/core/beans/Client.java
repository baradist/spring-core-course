package cf.baradist.spring.core.beans;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@ToString
@Component("client")
public class Client {
    private long id;
    private String fullName;
    private String greeting;

    public Client() {
    }

    public Client(@Value("${id}") long id, @Value("${name}") String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    @Value("${greeting}")
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
