# Detective Digital

Juego educativo para adultos mayores que enseña a reconocer estafas y ciberdelitos digitales comunes en Perú.

**Demo en vivo:** [detective-digital-production.up.railway.app](https://detective-digital-production.up.railway.app)

<img width="1562" height="896" alt="image" src="https://github.com/user-attachments/assets/7d60ce48-068a-4b08-a9f3-77d0ea2fdcd1" />

---

## ¿Qué es?

El jugador recibe tarjetas que simulan mensajes reales: SMS del banco, correos, WhatsApp, publicaciones de Facebook. Debe decidir si es una trampa o un mensaje legítimo. Al responder, Claude AI analiza la situación y da feedback personalizado explicando las señales de alerta.

Incluye 45 casos basados en estafas reales reportadas en Perú: phishing bancario (BCP, Interbank, BBVA), fraudes por Yape, falsos trabajos, cuentos del tío modernos, sorteos falsos de EsSalud y SUNAT, entre otros.

---

## Tecnologías

| Capa | Tecnología |
|------|-----------|
| Backend | Java 17 + Spring Boot 3.2 |
| Frontend | HTML5 + CSS3 + Vanilla JS |
| Template | Thymeleaf |
| Iconos | Lucide Icons |
| IA | Anthropic Claude API (claude-sonnet-4-6) |
| Deploy | Railway |

---

## Correr localmente

**Requisitos:** Java 17+, Maven 3.8+, API key de Anthropic

```bash
git clone https://github.com/lucia-gil/detective-digital.git
cd detective-digital

# Configura tu API key
export ANTHROPIC_API_KEY=sk-ant-...      # Mac/Linux
set ANTHROPIC_API_KEY=sk-ant-...         # Windows CMD

mvn spring-boot:run
# Abre http://localhost:8080
```

---

## Estructura del proyecto

```
src/main/java/com/detectortrampas/
├── controller/
│   ├── GameController.java     REST API: /api/cards, /api/answer
│   └── WebController.java      Sirve el HTML
├── service/
│   ├── CardService.java        45 tarjetas con selección aleatoria balanceada
│   └── ClaudeService.java      Integración con Anthropic API
└── model/
    ├── Card.java
    ├── AnswerRequest.java
    └── FeedbackResponse.java

src/main/resources/
├── templates/index.html        UI principal
├── static/css/styles.css
└── static/js/game.js
```

---

## API

```
GET  /api/cards?difficulty=facil&limit=6    Cartas para una sesión
POST /api/answer                            Evalúa respuesta, retorna feedback IA
GET  /api/health                            Health check
```

---

## Posibles mejoras

- Base de datos para guardar puntuaciones por familia
- Modo offline sin IA
- Versión en quechua

---

Desarrollado por [Lucía Gil](https://github.com/lucia-gil)
