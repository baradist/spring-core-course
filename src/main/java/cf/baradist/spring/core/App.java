package cf.baradist.spring.core;

import cf.baradist.spring.core.beans.Client;
import cf.baradist.spring.core.loggers.Event;
import cf.baradist.spring.core.loggers.EventLogger;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Data
@AllArgsConstructor
public class App {
    private Client client;
    private EventLogger eventLogger;
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        app.logEvent("Message 1");
        app.logEvent("Message 2");
        app.logEvent("Message 3");
        app.logEvent("Message 4");
        app.logEvent("Message 5");
        app.logEvent("Message 6");
        app.logEvent("Message 7");
        app.logEvent("Message 8");
        app.logEvent("Message 9");

        ctx.close();
    }

    public void logEvent(String msg) {
        String message = msg.replaceAll(String.valueOf(client.getId()), client.getFullName());
        Event event = (Event) ctx.getBean("event");
        event.setMsg(message);
        eventLogger.logEvent(event);
    }
}
