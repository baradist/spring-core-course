package cf.baradist.spring.core;

import cf.baradist.spring.core.beans.Client;
import cf.baradist.spring.core.config.SpringConfig;
import cf.baradist.spring.core.loggers.Event;
import cf.baradist.spring.core.loggers.EventLogger;
import cf.baradist.spring.core.loggers.EventType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Data
@Component("app")
public class App {
    private static ConfigurableApplicationContext ctx;
    private Client client;
    private EventLogger eventLogger;
    private Map<EventType, EventLogger> loggers;

    @Autowired
    public App(Client client,
               @Qualifier("cachedFileEventLogger") EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        App app = (App) ctx.getBean("app");

        app.logEvent(EventType.INFO, "Message 1");
        app.logEvent(EventType.ERROR, "Message 2");
        app.logEvent(null, "Message 3");
        app.logEvent(EventType.INFO, "Message 4");
        app.logEvent(EventType.ERROR, "Message 5");
        app.logEvent(null, "Message 6");
        app.logEvent(EventType.INFO, "Message 7");
        app.logEvent(EventType.ERROR, "Message 8");
        app.logEvent(null, "Message 9");

        Client client = (Client) ctx.getBean("client");
        System.out.println(client);

        ctx.close();
    }

    @Resource(name = "eventTypeToLogger")
    public void setLoggers(Map<EventType, EventLogger> loggers) {
        this.loggers = loggers;
    }

    public void logEvent(EventType type, String msg) {
        EventLogger logger = loggers.get(type);
        if (logger == null) {
            logger = eventLogger;
        }
        String message = msg.replaceAll(String.valueOf(client.getId()), client.getFullName());
        Event event = (Event) ctx.getBean("event");
        event.setMsg(message);
        logger.logEvent(event);
    }
}
