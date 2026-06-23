// ======================================
// DETECTIVE DIGITAL — Game Logic
// Conectado al backend Spring Boot
// ======================================

const API_BASE = '/api';

let state = {
    cards: [],
    currentIndex: 0,
    score: 0,
    streak: 0,
    correctCount: 0,
    wrongCount: 0,
    difficulty: '',
    answered: false
};

// ===== DIFICULTAD =====
function setDifficulty(btn, diff) {
    document.querySelectorAll('.diff-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    state.difficulty = diff;
}

// ===== NAVEGACIÓN ENTRE PANTALLAS =====
function showScreen(id) {
    document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
    document.getElementById(id).classList.add('active');
    window.scrollTo(0, 0);
}

function goHome() {
    showScreen('screen-start');
}

// ===== INICIAR JUEGO =====
async function startGame() {
    resetState();
    showScreen('screen-game');

    try {
        const url = `${API_BASE}/cards?difficulty=${state.difficulty}&limit=6`;
        const res = await fetch(url);
        if (!res.ok) throw new Error('Error cargando cartas');
        state.cards = await res.json();

        if (state.cards.length === 0) {
            alert('No hay cartas disponibles para este nivel. Intenta con otro.');
            goHome();
            return;
        }

        loadCard();
    } catch (err) {
        console.error('Error:', err);
        alert('No se pudo conectar al servidor. ¿Está corriendo el backend?');
        goHome();
    }
}

function resetState() {
    state.currentIndex = 0;
    state.score = 0;
    state.streak = 0;
    state.correctCount = 0;
    state.wrongCount = 0;
    state.answered = false;

    updateHUD();
    setProgressBar(0);

    document.getElementById('feedback-panel').style.display = 'none';
    document.getElementById('answer-section').style.display = 'block';
    enableButtons();
}

// ===== CARGAR CARTA ACTUAL =====
function loadCard() {
    const card = state.cards[state.currentIndex];
    const total = state.cards.length;
    const num = state.currentIndex + 1;

    // Resetear UI
    state.answered = false;
    document.getElementById('feedback-panel').style.display = 'none';
    document.getElementById('answer-section').style.display = 'block';
    enableButtons();

    // Llenar card
    const badge = document.getElementById('card-badge');
    badge.textContent = card.tag;
    badge.className = 'card-type-badge ' + card.type;

    document.getElementById('card-num').textContent = `${num}/${total}`;
    document.getElementById('card-sender').textContent = card.title;
    document.getElementById('card-content').textContent = card.body;
    document.getElementById('card-link').textContent = card.detail;

    // Progreso
    document.getElementById('card-progress').textContent = `Carta ${num} de ${total}`;
    setProgressBar((num - 1) / total * 100);

    // Animar entrada de la card
    const cardEl = document.getElementById('message-card');
    cardEl.style.opacity = '0';
    cardEl.style.transform = 'translateY(12px)';
    setTimeout(() => {
        cardEl.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
        cardEl.style.opacity = '1';
        cardEl.style.transform = 'translateY(0)';
    }, 50);

    lucide.createIcons();
}

// ===== ENVIAR RESPUESTA =====
async function submitAnswer(userSaysItsTrap) {
    if (state.answered) return;
    state.answered = true;

    const card = state.cards[state.currentIndex];
    disableButtons();

    // Mostrar feedback panel con loading
    const panel = document.getElementById('feedback-panel');
    panel.style.display = 'block';
    document.getElementById('feedback-loading').style.display = 'flex';
    document.getElementById('feedback-content').style.display = 'none';
    document.getElementById('btn-next').style.display = 'none';
    document.getElementById('answer-section').style.display = 'none';

    // Scroll suave al feedback
    setTimeout(() => panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' }), 100);

    try {
        const body = {
            cardId: card.id,
            userSaysItsTrap: userSaysItsTrap,
            cardTag: card.tag,
            cardTitle: card.title,
            cardBody: card.body,
            cardDetail: card.detail,
            cardIsTrap: card.trap
        };

        const res = await fetch(`${API_BASE}/answer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });

        if (!res.ok) throw new Error('Error del servidor');

        const data = await res.json();
        showFeedback(data.correct, data.feedback, data.pointsEarned);

    } catch (err) {
        console.error('Error al evaluar respuesta:', err);
        const correct = (userSaysItsTrap === card.trap);
        const fallback = correct
            ? '¡Muy bien! Confiaste en tus instintos correctamente.'
            : 'No te preocupes, estas estafas son muy difíciles de detectar. ¡Sigue practicando!';
        showFeedback(correct, fallback, correct ? 10 : 0);
    }
}

function showFeedback(correct, feedbackText, points) {
    // Actualizar estado
    state.score += points;
    if (correct) {
        state.streak++;
        state.correctCount++;
    } else {
        state.streak = 0;
        state.wrongCount++;
    }
    updateHUD();

    // Mostrar resultado
    document.getElementById('feedback-loading').style.display = 'none';
    document.getElementById('feedback-content').style.display = 'block';

    const resultEl = document.getElementById('feedback-result');
    resultEl.className = 'feedback-result ' + (correct ? 'correct' : 'wrong');
    resultEl.innerHTML = correct
        ? '<i data-lucide="circle-check"></i> ¡Correcto!'
        : '<i data-lucide="circle-x"></i> Incorrecto';

    document.getElementById('feedback-text').textContent = feedbackText;

    const nextBtn = document.getElementById('btn-next');
    nextBtn.style.display = 'block';
    const isLast = state.currentIndex >= state.cards.length - 1;
    nextBtn.innerHTML = isLast
        ? 'Ver mis resultados <i data-lucide="flag"></i>'
        : 'Siguiente carta <i data-lucide="arrow-right"></i>';

    lucide.createIcons();
}

// ===== SIGUIENTE CARTA =====
function nextCard() {
    state.currentIndex++;
    if (state.currentIndex >= state.cards.length) {
        showResult();
        return;
    }
    loadCard();
}

// ===== PANTALLA RESULTADO =====
function showResult() {
    const total = state.cards.length;
    const pct = Math.round((state.correctCount / total) * 100);
    const maxScore = total * 10;

    showScreen('screen-result');
    setProgressBar(100);

    document.getElementById('result-score-big').textContent =
        `${state.score}/${maxScore}`;

    document.getElementById('res-correct').textContent = state.correctCount;
    document.getElementById('res-wrong').textContent = state.wrongCount;
    document.getElementById('res-pct').textContent = `${pct}%`;

    let iconName, title, subtitle;
    if (pct >= 90) {
        iconName = 'trophy';
        title = '¡Eres un detective experto!';
        subtitle = 'Reconociste casi todas las trampas. Tus familiares están en buenas manos.';
    } else if (pct >= 70) {
        iconName = 'star';
        title = '¡Muy buen trabajo!';
        subtitle = 'Ya detectas la mayoría de estafas. Con más práctica serás imbatible.';
    } else if (pct >= 50) {
        iconName = 'zap';
        title = '¡Buen comienzo!';
        subtitle = 'Vas aprendiendo. Las estafas son sofisticadas, ¡pero tú eres más listo!';
    } else {
        iconName = 'book-open';
        title = 'A seguir practicando';
        subtitle = 'Las estafas digitales son muy engañosas. Vuelve a intentarlo, aprenderás mucho.';
    }

    document.getElementById('result-icon').innerHTML =
        `<i data-lucide="${iconName}"></i>`;
    document.getElementById('result-title').textContent = title;
    document.getElementById('result-subtitle').textContent = subtitle;

    lucide.createIcons();
}

// ===== HELPERS =====
function updateHUD() {
    document.getElementById('score').textContent = state.score;
    const streakEl = document.getElementById('streak');
    if (state.streak > 0) {
        streakEl.innerHTML = `<i data-lucide="flame"></i> ${state.streak}`;
    } else {
        streakEl.textContent = '0';
    }
    lucide.createIcons();
}

function setProgressBar(pct) {
    document.getElementById('progress-fill').style.width = `${pct}%`;
}

function disableButtons() {
    document.getElementById('btn-trap').disabled = true;
    document.getElementById('btn-safe').disabled = true;
}

function enableButtons() {
    document.getElementById('btn-trap').disabled = false;
    document.getElementById('btn-safe').disabled = false;
}