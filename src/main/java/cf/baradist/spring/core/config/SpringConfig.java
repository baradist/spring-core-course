package cf.baradist.spring.core.config;

import cf.baradist.spring.core.loggers.EventLogger;
import cf.baradist.spring.core.loggers.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("cf.baradist.spring.core")
public class SpringConfig {

    @Autowired
    @Qualifier("consoleEventLogger")
    EventLogger consoleEventLogger;

    @Autowired
    @Qualifier("combinedEventLogger")
    EventLogger combinedEventLogger;

    @Bean
    DateFormat getDateFormat() {
        return DateFormat.getDateInstance();
    }

    @Bean(name = "eventTypeToLogger")
    Map<EventType, EventLogger> getEventTypeToLogger() {
        return new HashMap<EventType, EventLogger>() {{
            put(EventType.INFO, consoleEventLogger);
            put(EventType.ERROR, combinedEventLogger);
        }};
    }

    @Bean(name = "loggers")
    List<EventLogger> getLoggers() {
        return new ArrayList<EventLogger>(2) {{
            add(consoleEventLogger);
            add(combinedEventLogger);
        }};
    }
}
