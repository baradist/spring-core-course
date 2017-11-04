package cf.baradist.spring.core;

import cf.baradist.spring.core.beans.Client;
import cf.baradist.spring.core.loggers.EventLogger;
import lombok.Data;

@Data
public class App {
    private Client client;
    private EventLogger eventLogger;

    public static void main(String[] args) {

    }

    public void logEvent(String msg) {
        String message = msg.replaceAll(String.valueOf(client.getId()), client.getFullName());
        eventLogger.logEvent(message);
    }
}
