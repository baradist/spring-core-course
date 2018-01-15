package cf.baradist.spring.core.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Client {
    private long id;
    private String fullName;
    private String greeting;

    public Client(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
