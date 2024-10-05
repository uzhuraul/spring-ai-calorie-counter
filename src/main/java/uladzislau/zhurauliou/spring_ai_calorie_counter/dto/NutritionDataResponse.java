package uladzislau.zhurauliou.spring_ai_calorie_counter.dto;

import java.util.List;

public record NutritionDataResponse(String dishName, Long totalCalories, Long totalProtein, Long totalFat,
                                    Long totalCarbohydrates, Long totalFiber, List<NutritionDataComponent> components) {
}
