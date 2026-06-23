package com.detectortrampas.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackResponse {
    private boolean correct;
    private String feedback;
    private int pointsEarned;
}
