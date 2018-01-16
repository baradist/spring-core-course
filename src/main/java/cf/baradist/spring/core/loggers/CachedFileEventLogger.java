package cf.baradist.spring.core.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("cachedFileEventLogger")
public class CachedFileEventLogger extends FileEventLogger {
    public static final int DEFAULT_CASHE_SIZE = 100;
    private int casheSize;
    private List<Event> cashe;

    @Autowired
    public CachedFileEventLogger(@Value("logs/log.txt") String fileName) {
        this(fileName, DEFAULT_CASHE_SIZE);
    }

    public CachedFileEventLogger(String fileName, int casheSize) {
        super(fileName);
        this.casheSize = casheSize;
        cashe = new ArrayList<>(casheSize);
    }

    @PostConstruct
    @Override
    public void init() throws IOException {
        super.init();
    }

    @PreDestroy
    @Override
    protected void destroy() {
        super.destroy();
        writeEventsFromAndClearCashe();
    }

    @Override
    public void logEvent(Event event) {
        cashe.add(event);
        if (cashe.size() == casheSize) {
            writeEventsFromAndClearCashe();
        }
    }

    private void writeEventsFromAndClearCashe() {
        if (cashe.isEmpty()) {
            return;
        }
        String string = cashe.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
        writeStringToFile(string);
        cashe.clear();
    }
}
