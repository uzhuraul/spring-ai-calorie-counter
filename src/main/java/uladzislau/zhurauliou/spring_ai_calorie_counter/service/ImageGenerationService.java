package uladzislau.zhurauliou.spring_ai_calorie_counter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.stereotype.Service;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.Question;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.UrlAnswer;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageGenerationService {

    private final ImageModel imageModel;

    public byte[] generateImage(Question question) {
        ImageOptions imageOptions = ImageOptionsBuilder.builder()
                .withHeight(1024)
                .withWidth(1024)
                .withResponseFormat("b64_json")
                .withModel(OpenAiImageApi.ImageModel.DALL_E_2.getValue())
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(question.question(), imageOptions);
        String b64Json = imageModel.call(imagePrompt)
                .getResult()
                .getOutput()
                .getB64Json();
        return Base64.getDecoder().decode(b64Json);
    }

    public UrlAnswer generateImageUrl(Question question) {
        ImageOptions imageOptions = ImageOptionsBuilder.builder()
                .withHeight(1024)
                .withWidth(1024)
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(question.question(), imageOptions);
        String url = imageModel.call(imagePrompt)
                .getResult()
                .getOutput()
                .getUrl();
        return new UrlAnswer(url);
    }

}
