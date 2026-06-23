package com.detectortrampas.model;

import lombok.Data;

@Data
public class AnswerRequest {
    private String cardId;
    private boolean userSaysItsTrap;
    private String cardTag;
    private String cardTitle;
    private String cardBody;
    private String cardDetail;
    private boolean cardIsTrap;
}
