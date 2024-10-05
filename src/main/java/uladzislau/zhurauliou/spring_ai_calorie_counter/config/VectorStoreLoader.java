package uladzislau.zhurauliou.spring_ai_calorie_counter.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VectorStoreLoader implements CommandLineRunner {

    @Value("classpath:nutrients.csv")
    private Resource dataToLoad;

    private final VectorStore vectorStore;

    @Override
    public void run(String... args) throws Exception {
        log.info("Start data loading");
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(dataToLoad);
        List<Document> documents = tikaDocumentReader.get();

        TextSplitter textSplitter = new TokenTextSplitter();

        List<Document> splitDocuments = textSplitter.apply(documents);

        vectorStore.add(splitDocuments);
        log.info("Data loaded");
    }
}
