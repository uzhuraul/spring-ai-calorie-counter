package uladzislau.zhurauliou.spring_ai_calorie_counter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.Question;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.UrlAnswer;
import uladzislau.zhurauliou.spring_ai_calorie_counter.service.ImageGenerationService;

@RestController
@RequiredArgsConstructor
public class ImageGenerationController {

    private final ImageGenerationService imageGenerationService;

    @PostMapping("image/url")
    public UrlAnswer generateImageUrl(@RequestBody Question question) {
        return imageGenerationService.generateImageUrl(question);
    }

    @PostMapping(value = "image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateImage(@RequestBody Question question) {
        return imageGenerationService.generateImage(question);
    }

}
