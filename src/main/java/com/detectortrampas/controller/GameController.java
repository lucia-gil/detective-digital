package com.detectortrampas.controller;

import com.detectortrampas.model.AnswerRequest;
import com.detectortrampas.model.Card;
import com.detectortrampas.model.FeedbackResponse;
import com.detectortrampas.service.CardService;
import com.detectortrampas.service.ClaudeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    private final CardService cardService;
    private final ClaudeService claudeService;

    public GameController(CardService cardService, ClaudeService claudeService) {
        this.cardService = cardService;
        this.claudeService = claudeService;
    }

    /**
     * GET /api/cards?difficulty=facil&limit=6
     * Returns a shuffled list of cards for a new game session
     */
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getCards(
            @RequestParam(defaultValue = "") String difficulty,
            @RequestParam(defaultValue = "6") int limit) {
        List<Card> cards = cardService.getSessionCards(difficulty, limit);
        return ResponseEntity.ok(cards);
    }

    /**
     * POST /api/answer
     * Evaluates the player's answer and returns AI feedback
     */
    @PostMapping("/answer")
    public ResponseEntity<FeedbackResponse> submitAnswer(@RequestBody AnswerRequest req) {
        boolean correct = (req.isUserSaysItsTrap() == req.isCardIsTrap());
        int points = correct ? 10 : 0;

        String feedback = claudeService.getFeedback(
            req.getCardTag(),
            req.getCardTitle(),
            req.getCardBody(),
            req.getCardDetail(),
            req.isCardIsTrap(),
            req.isUserSaysItsTrap()
        );

        return ResponseEntity.ok(new FeedbackResponse(correct, feedback, points));
    }

    /**
     * GET /api/health
     * Simple health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok", "app", "Detector de Trampas v1.0"));
    }
}
