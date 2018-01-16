package cf.baradist.spring.core.loggers;

import org.springframework.stereotype.Component;

@Component("consoleEventLogger")
public class ConsoleEventLogger implements EventLogger {
    @Override
    public void logEvent(Event event) {
        System.out.println(event);
    }
}
