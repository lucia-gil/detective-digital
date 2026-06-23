# 🕵️ Detective Digital — Detector de Trampas Digitales

Juego educativo para **adultos mayores** que enseña a reconocer estafas digitales.  
Construido con **Java Spring Boot** (backend) + **HTML/CSS/JS** (frontend) + **Claude AI** (feedback personalizado).

---

## 🚀 Cómo correrlo localmente

### Requisitos
- Java 17+
- Maven 3.8+
- Una API key de Anthropic (gratis en [console.anthropic.com](https://console.anthropic.com))

### Pasos

```bash
# 1. Clonar el repo
git clone https://github.com/TU_USUARIO/detector-trampas.git
cd detector-trampas

# 2. Poner tu API key (elige una opción):
# Opción A: Variable de entorno (recomendado)
export ANTHROPIC_API_KEY=sk-ant-...

# Opción B: Editar src/main/resources/application.properties
# anthropic.api.key=sk-ant-...

# 3. Correr
./mvnw spring-boot:run

# 4. Abrir en el navegador
# http://localhost:8080
```

---

## 📁 Estructura del proyecto

```
detector-trampas/
├── src/main/java/com/detectortrampas/
│   ├── DetectorTrampasApplication.java  ← Punto de entrada
│   ├── controller/
│   │   ├── GameController.java          ← REST API (/api/cards, /api/answer)
│   │   └── WebController.java           ← Sirve el HTML
│   ├── service/
│   │   ├── CardService.java             ← Banco de cartas del juego
│   │   └── ClaudeService.java           ← Llama a la API de Anthropic
│   └── model/
│       ├── Card.java                    ← Modelo de carta
│       ├── AnswerRequest.java           ← Request del jugador
│       └── FeedbackResponse.java        ← Respuesta con feedback
│
├── src/main/resources/
│   ├── templates/index.html             ← HTML principal (Thymeleaf)
│   ├── static/css/styles.css            ← Estilos accesibles
│   ├── static/js/game.js               ← Lógica del juego
│   └── application.properties           ← Configuración
│
└── pom.xml                              ← Dependencias Maven
```

---

## 🌐 API Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/cards?difficulty=facil&limit=6` | Devuelve cartas para una sesión |
| `POST` | `/api/answer` | Evalúa respuesta y devuelve feedback IA |
| `GET` | `/api/health` | Health check |

### Ejemplo: POST /api/answer
```json
// Request
{
  "cardId": "c1",
  "userSaysItsTrap": true,
  "cardTag": "SMS del banco",
  "cardTitle": "BANCO SEGURO: Alerta",
  "cardBody": "Su cuenta ha sido BLOQUEADA...",
  "cardDetail": "http://banco-falso.com",
  "cardIsTrap": true
}

// Response
{
  "correct": true,
  "feedback": "¡Excelente! Reconociste perfectamente esta trampa. Los estafadores siempre...",
  "pointsEarned": 10
}
```

---

## 🚢 Despliegue en Railway / Render

### Railway (más fácil)
1. Sube el código a GitHub
2. Entra a [railway.app](https://railway.app) → New Project → Deploy from GitHub
3. Agrega la variable de entorno: `ANTHROPIC_API_KEY=sk-ant-...`
4. Railway detecta el `pom.xml` y despliega automáticamente ✅

### Render
1. New Web Service → conecta tu repo GitHub
2. Build command: `./mvnw clean package -DskipTests`
3. Start command: `java -jar target/detector-trampas-1.0.0.jar`
4. Variables de entorno: `ANTHROPIC_API_KEY=sk-ant-...`

---

## 🎮 Características del juego

- **12 cartas** con niveles fácil / medio / difícil
- **Tipos de estafa**: SMS, correo electrónico, WhatsApp, redes sociales
- **Feedback con IA**: Claude explica por qué cada mensaje es trampa o legítimo
- **Sistema de puntos y racha** para motivar al jugador
- **Accesible**: letras grandes, alto contraste, sin cronómetros

---

## ➕ Ideas para extender el proyecto

- [ ] Base de datos (H2 → PostgreSQL) para guardar puntuaciones
- [ ] Tabla de líderes por familia
- [ ] Modo sin IA (para cuando no hay internet)
- [ ] Más cartas de estafas locales peruanas
- [ ] Versión en quechua

---

## 👩‍💻 Tecnologías

| Capa | Tecnología |
|------|-----------|
| Backend | Java 17 + Spring Boot 3.2 |
| Frontend | HTML5 + CSS3 + Vanilla JS |
| Template | Thymeleaf |
| IA | Anthropic Claude API |
| Build | Maven |

---

Hecho con ❤️ para proteger a los adultos mayores de estafas digitales.
