package uladzislau.zhurauliou.spring_ai_calorie_counter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.stereotype.Service;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.Question;

@Service
@RequiredArgsConstructor
public class SpeechGenerationService {

    private final SpeechModel speechModel;

    public byte[] generateSpeech(Question question) {
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.FABLE)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withSpeed(2F)
                .build();
        SpeechPrompt speechPrompt = new SpeechPrompt(question.question(), options);
        return speechModel.call(speechPrompt)
                .getResult().getOutput();
    }

}
