package uladzislau.zhurauliou.spring_ai_calorie_counter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uladzislau.zhurauliou.spring_ai_calorie_counter.dto.NutritionDataResponse;
import uladzislau.zhurauliou.spring_ai_calorie_counter.service.CalorieCountService;

@RestController
@RequiredArgsConstructor
public class CalorieCounterController {

    private final CalorieCountService calorieCountService;

    @PostMapping(value = "ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getIngredients(@RequestParam("photo") MultipartFile photo) {
        return calorieCountService.getIngredients(photo);
    }

    @PostMapping(value = "calories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public NutritionDataResponse countCalories(@RequestParam("photo") MultipartFile photo) {
        return calorieCountService.countCalories(photo);
    }

}
