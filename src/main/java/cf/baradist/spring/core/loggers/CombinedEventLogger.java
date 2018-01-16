package cf.baradist.spring.core.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("combinedEventLogger")
public class CombinedEventLogger implements EventLogger {
    private Collection<EventLogger> loggers;

    @Autowired
    public CombinedEventLogger(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }
}
