package com.detectortrampas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class ClaudeService {

    @Value("${anthropic.api.key:#{null}}")
    private String apiKey;

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = "claude-sonnet-4-6";

    private final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private final ObjectMapper mapper = new ObjectMapper();

    public String getFeedback(String cardTag, String cardTitle, String cardBody,
                              String cardDetail, boolean isTrap,
                              boolean userSaysItsTrap) {

        if (apiKey == null || apiKey.isBlank() || apiKey.equals("tu-api-key-aqui")) {
            return getFallbackFeedback(isTrap, userSaysItsTrap);
        }

        boolean correct = (userSaysItsTrap == isTrap);

        String prompt = String.format("""
            Eres un asistente educativo amable que ayuda a adultos mayores a reconocer estafas digitales.
            
            El jugador vio este mensaje:
            - Tipo: %s
            - Remitente/título: %s
            - Contenido: %s
            - Detalle adicional: %s
            
            Este mensaje ES %s.
            El jugador respondió: "%s" — lo cual fue %s.
            
            Escribe un feedback corto (máximo 3 oraciones) en español peruano, con tono muy amable y empático,
            apropiado para adultos mayores. %s
            
            Empieza directamente con el feedback, sin saludos ni introducciones.
            """,
            cardTag, cardTitle, cardBody, cardDetail,
            isTrap ? "una ESTAFA (trampa)" : "LEGÍTIMO (seguro)",
            userSaysItsTrap ? "Es una trampa" : "Parece seguro",
            correct ? "CORRECTO" : "INCORRECTO",
            correct
                ? "Felicítalo calurosamente y explica brevemente por qué este mensaje es " +
                  (isTrap ? "una trampa (señala 1 señal clave)." : "legítimo.")
                : "Anímalos sin hacerlos sentir mal y explica por qué este mensaje es " +
                  (isTrap ? "una trampa (señala 1 señal clave)." : "legítimo.")
        );

        try {
            ObjectNode body = mapper.createObjectNode();
            body.put("model", MODEL);
            body.put("max_tokens", 300);

            ArrayNode messages = mapper.createArrayNode();
            ObjectNode message = mapper.createObjectNode();
            message.put("role", "user");
            message.put("content", prompt);
            messages.add(message);
            body.set("messages", messages);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .timeout(Duration.ofSeconds(30))
                .build();

            HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode json = mapper.readTree(response.body());
                return json.path("content").get(0).path("text").asText();
            }

        } catch (Exception e) {
            System.err.println("Error llamando a Claude API: " + e.getMessage());
        }

        return getFallbackFeedback(isTrap, userSaysItsTrap);
    }

    private String getFallbackFeedback(boolean isTrap, boolean userSaysItsTrap) {
        boolean correct = (userSaysItsTrap == isTrap);
        if (correct && isTrap) {
            return "¡Excelente! Reconociste perfectamente esta trampa. Los estafadores siempre crean urgencia para que no pienses con calma. ¡Sigue así!";
        } else if (correct) {
            return "¡Muy bien! Este mensaje sí era legítimo. Supiste distinguir un comunicado oficial. ¡Qué buen ojo tienes!";
        } else if (isTrap) {
            return "No te preocupes, estas estafas están diseñadas para engañar. La clave es desconfiar de los mensajes urgentes que piden tus datos o dinero. ¡Lo harás mejor la próxima!";
        } else {
            return "Tranquilo/a, este mensaje sí era legítimo. Con la práctica aprenderás a distinguir los mensajes oficiales de los falsos. ¡Sigue jugando!";
        }
    }
}
