package cf.baradist.spring.core.loggers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CachedFileEventLogger extends FileEventLogger {
    public static final int DEFAULT_CASHE_SIZE = 100;
    private int casheSize;
    private List<Event> cashe;

    public CachedFileEventLogger(String fileName) {
        this(fileName, DEFAULT_CASHE_SIZE);
    }

    public CachedFileEventLogger(String fileName, int casheSize) {
        super(fileName);
        this.casheSize = casheSize;
        cashe = new ArrayList<>(casheSize);
    }

    @Override
    public void init() throws IOException {
        super.init();
    }

    @Override
    protected void destroy() {
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
