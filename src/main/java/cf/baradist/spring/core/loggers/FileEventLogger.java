package cf.baradist.spring.core.loggers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component("fileEventLogger")
public class FileEventLogger implements EventLogger {
    private String fileName;
    private File file;

    @Autowired
    public FileEventLogger(@Value("logs/log.txt") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void logEvent(Event event) {
        writeStringToFile(event.toString());
    }

    protected void writeStringToFile(String msg) {
        try {
            FileUtils.writeStringToFile(file, msg + "\n", (Charset) null, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            if (file.getParent() != null) {
                new File(file.getParent()).mkdirs();
            }
            file.createNewFile();
        }
        if (!file.canWrite()) {
            throw new IOException();
        }
    }

    @PreDestroy
    protected void destroy() {
    }
}
