package com.detectortrampas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String id;
    private String type;       // "email", "sms", "social", "whatsapp"
    private String tag;        // Label shown to user
    private String title;      // Sender/subject line
    private String body;       // Message content
    private String detail;     // URL, extra info
    private boolean isTrap;    // Is this a scam?
    private String difficulty; // "facil", "medio", "dificil"
}
