package org.example.models.User;

import java.time.LocalDateTime;

public record LearningAchievements(
        int currentStreakDays,   // сколько дней подряд учился
        int longestStreakDays,   // рекордная серия
        LocalDateTime lastStudyDate  // когда в последний раз учился
){}
