package uladzislau.zhurauliou.spring_ai_calorie_counter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.Question;
import uladzislau.zhurauliou.spring_ai_calorie_counter.service.SpeechGenerationService;

@RestController
@RequiredArgsConstructor
public class SpeechGenerationController {

    private final SpeechGenerationService speechGenerationService;

    @PostMapping(value = "/speech", produces = "audio/mpeg")
    public byte[] generateSpeech(@RequestBody Question question) {
        return speechGenerationService.generateSpeech(question);
    }

}
