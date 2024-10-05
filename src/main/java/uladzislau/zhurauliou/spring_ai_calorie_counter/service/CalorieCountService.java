package uladzislau.zhurauliou.spring_ai_calorie_counter.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.NutritionDataResponse;

import java.util.List;
import java.util.Map;

@Service
public class CalorieCountService {

    private final ChatClient client;
    private final VectorStore vectorStore;

    @Value("classpath:calorie-count-message.st")
    private Resource calorieCountText;

    private static final String GET_INGREDIENTS_TEXT = """
           Analyze the food in this photo. What type of food is it?
           List the individual ingredients or components you believe make up this dish.
           Be sure to include any ingredients that might not be visible but are typically part of this dish.
           
           Include weight for each ingredient or component.
            """;

    public CalorieCountService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.client = builder
                .defaultOptions(OpenAiChatOptions.builder()
                        .withModel(OpenAiApi.ChatModel.GPT_4_O)
                        .build())
                .build();
        this.vectorStore = vectorStore;
    }

    public NutritionDataResponse countCalories(MultipartFile multipartFile) {

        var ingredients = getIngredients(multipartFile);

        var searchRequest = SearchRequest.query(ingredients)
                .withTopK(2);
        var documents = vectorStore.similaritySearch(searchRequest)
                .stream()
                .map(Document::getContent)
                .toList();
        var promptTemplate = new PromptTemplate(calorieCountText);
        var model = Map.of("nutrition", documents, "dish", ingredients);
        var message = promptTemplate.createMessage(model);

        return client.prompt()
                .messages(message)
                .call()
                .entity(NutritionDataResponse.class);
    }

    public String getIngredients(MultipartFile multipartFile) {
        var media = new Media(MimeTypeUtils.IMAGE_JPEG, multipartFile.getResource());
        var userMessage = new UserMessage(GET_INGREDIENTS_TEXT, media);
        return client.prompt()
                .messages(userMessage)
                .call()
                .content();
    }




















}
