package com.detectortrampas.service;

import com.detectortrampas.model.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    private final List<Card> allCards = new ArrayList<>();

    public CardService() {
        initCards();
    }

    private void initCards() {

        // ============================================================
        // FÁCIL — señales de alerta muy obvias
        // ============================================================

        allCards.add(new Card("c1", "sms", "SMS del banco", "BANCO SEGURO: Alerta de seguridad",
                "Su cuenta ha sido BLOQUEADA por actividad sospechosa. Ingrese su clave AHORA para desbloquear:",
                "http://banco-seguro-alertas.com/desbloquear",
                true, "facil"));

        allCards.add(new Card("c2", "email", "Correo electrónico", "De: soporte@faceb00k-ayuda.com",
                "Hemos detectado un acceso no autorizado. Tu cuenta será eliminada en 24 horas si no verificas tu identidad.",
                "Asunto: URGENTE — Verifica tu cuenta o la perderás",
                true, "facil"));

        allCards.add(new Card("c3", "email", "Correo electrónico", "De: noreply@bcp.com.pe",
                "Tu estado de cuenta de mayo está disponible. Descárgalo en nuestra app oficial o en la web. No se requiere ninguna acción adicional.",
                "bcp.com.pe/estados-de-cuenta",
                false, "facil"));

        allCards.add(new Card("c4", "whatsapp", "WhatsApp desconocido", "Número desconocido +51 987 654 321",
                "Hola mamá, soy tu hijo. Se me malogró el celular y este es mi número nuevo. Necesito que me mandes 200 soles por Yape urgente, mañana te devuelvo.",
                "Enviado hace 2 minutos — número NO guardado en tus contactos",
                true, "facil"));

        // NUEVO — Falso premio de supermercado
        allCards.add(new Card("c13", "whatsapp", "WhatsApp desconocido", "TOTTUS PERÚ OFICIAL 🏆",
                "¡Felicitaciones! Fuiste seleccionado ganador de S/1,000 en vales de compra. Para reclamar tu premio responde con tu DNI y número de cuenta bancaria.",
                "Número desconocido, no figura en la web oficial de Tottus",
                true, "facil"));

        // NUEVO — SMS falso de Yape con urgencia
        allCards.add(new Card("c14", "sms", "SMS de Yape", "Yape Alerta: Cuenta suspendida",
                "Tu cuenta Yape ha sido suspendida por uso sospechoso. Ingresa TU CLAVE y PIN en el siguiente enlace para reactivarla en los próximos 30 minutos.",
                "yape-reactivar-cuenta.net/verificar",
                true, "facil"));

        // NUEVO — Llamada falsa de secuestro (vishing)
        allCards.add(new Card("c15", "whatsapp", "WhatsApp desconocido", "Número desconocido +51 941 222 333",
                "Señora, tenemos a su hijo detenido en la comisaría de Miraflores. Para liberarlo debe depositar S/3,000 en esta cuenta ahora mismo o lo trasladamos al penal.",
                "Número no guardado — su hijo nunca avisó que iría a Miraflores",
                true, "facil"));

        // NUEVO — Mensaje legítimo de BBVA
        allCards.add(new Card("c16", "sms", "SMS del banco", "BBVA: Información de cuenta",
                "Tu tarjeta terminada en 4821 realizó un consumo de S/85.00 en Wong San Isidro el 15/06. Si no reconoces este cobro, llama al 311-9000.",
                "bbva.pe — número oficial 311-9000",
                false, "facil"));

        // ============================================================
        // MEDIO — señales presentes pero requieren atención
        // ============================================================

        allCards.add(new Card("c5", "sms", "SMS de paquetería", "Olva Courier: Tu paquete #PE4829",
                "Tu paquete ha sido retenido en aduana. Debes pagar S/15.50 de gastos antes de las 11:59 PM para no perderlo.",
                "olva-courier-aduana.click/pagar",
                true, "medio"));

        allCards.add(new Card("c6", "social", "Facebook", "BCP Oficial — Concurso de aniversario",
                "Para celebrar nuestro 75 aniversario, regalamos S/500 a 100 clientes. Comparte esta publicación y envía tu número de cuenta por mensaje privado.",
                "Perfil creado hace 3 días — solo 12 seguidores",
                true, "medio"));

        allCards.add(new Card("c7", "email", "Correo electrónico", "De: noreply@reniec.gob.pe",
                "Su DNI vence este mes. Renuévelo en cualquier oficina RENIEC o en nuestra web oficial. No es necesario enviar fotos ni datos por correo.",
                "reniec.gob.pe/renovacion",
                false, "medio"));

        allCards.add(new Card("c8", "sms", "SMS de SUNAT", "SUNAT: Devolución pendiente",
                "Tiene una devolución de S/850 pendiente. Para recibirla, confirme sus datos bancarios en las próximas 48 horas.",
                "sunat-devoluciones-peru.net/confirmar",
                true, "medio"));

        allCards.add(new Card("c9", "whatsapp", "WhatsApp conocido", "Tu hija — número guardado",
                "Mamá, mira esta foto de las vacaciones del fin de semana. Está en el grupo familiar.",
                "Imagen adjunta: foto_familia_julio.jpg",
                false, "medio"));

        // NUEVO — Falso trabajo por WhatsApp (muy común en Perú)
        allCards.add(new Card("c17", "whatsapp", "WhatsApp desconocido", "Recursos Humanos - Lima +51 932 111 444",
                "Buenos días, lo contactamos de una empresa importadora. Ofrecemos trabajo desde casa S/1,500 semanales solo dando 'me gusta' a videos en TikTok. Para iniciar deposite S/50 de garantía.",
                "Número desconocido — ningún trabajo legítimo pide dinero para empezar",
                true, "medio"));

        // NUEVO — SMS falso de SBS (Superintendencia de Banca)
        allCards.add(new Card("c18", "sms", "SMS oficial", "SBS Peru: Alerta fraude",
                "Detectamos que su cuenta fue comprometida por terceros. Llame AHORA al 0800-12345 para proteger sus ahorros. No realice ninguna operación.",
                "sbs-alerta-fraude.pe/proteger — la SBS NUNCA solicita datos por SMS",
                true, "medio"));

        // NUEVO — Falso sorteo de EsSalud
        allCards.add(new Card("c19", "social", "Facebook", "EsSalud Perú — Sorteo Día del Asegurado",
                "Por el Día del Asegurado sorteamos 50 canastas de S/300. Para participar comparte esta publicación y envíanos tu número de asegurado por mensaje privado.",
                "Página con solo 89 seguidores — logo diferente al oficial",
                true, "medio"));

        // NUEVO — Notificación legítima de Interbank
        allCards.add(new Card("c20", "email", "Correo electrónico", "De: notificaciones@interbank.com.pe",
                "Tu pago de S/320.00 al servicio de Luz del Sur fue registrado exitosamente el 10 de junio. Puedes ver el comprobante en tu app Interbank.",
                "interbank.com.pe — dominio oficial verificado",
                false, "medio"));

        // NUEVO — Falso cuento del tío moderno (llamada policial)
        allCards.add(new Card("c21", "whatsapp", "WhatsApp desconocido", "Policía Nacional del Perú — Comisaría",
                "Su pariente está involucrado en un accidente de tránsito. Para evitar que lo lleven al penal necesitamos que deposite S/2,500 de fianza a esta cuenta CCI ahora mismo.",
                "La PNP nunca solicita dinero por WhatsApp ni por teléfono",
                true, "medio"));

        // NUEVO — Alerta real del banco por consumo inusual
        allCards.add(new Card("c22", "sms", "SMS del banco", "Scotiabank: Alerta de consumo",
                "Se realizó un consumo de S/1,200.00 con tu tarjeta en una tienda de electrodomésticos en Gamarra. Si no reconoces esta compra, bloquea tu tarjeta en la app o llama al 311-6000.",
                "scotiabank.com.pe — número oficial 311-6000",
                false, "medio"));

        // ============================================================
        // DIFÍCIL — muy elaboradas, casi imposibles de distinguir
        // ============================================================

        allCards.add(new Card("c10", "email", "Correo electrónico", "De: seguridad@interbank.com.pe",
                "Hemos detectado un inicio de sesión desde un dispositivo nuevo en Lima. Si no fuiste tú, cancela el acceso de inmediato haciendo clic aquí.",
                "interbank-seguridad-acceso.com/cancelar",
                true, "dificil"));

        allCards.add(new Card("c11", "social", "Instagram", "Tu nieto te etiquetó en una foto",
                "Carlos Quispe te etiquetó: 'Abuela, mira qué linda saliste aquí'. Toca para ver la foto.",
                "Notificación real de Instagram — cuenta verificada de tu nieto",
                false, "dificil"));

        allCards.add(new Card("c12", "sms", "SMS de Yape", "Yape: Transferencia recibida",
                "¡Recibiste S/50.00 de María López! Pero para liberar el dinero debes confirmar tu número de cuenta en el siguiente enlace.",
                "yape-confirmacion-transferencia.app/liberar",
                true, "dificil"));

        // NUEVO — Phishing muy elaborado del BCP
        allCards.add(new Card("c23", "email", "Correo electrónico", "De: seguridad.digital@bcp-peru.com",
                "Detectamos un intento de acceso a tu Banca por Internet desde Piura. Si no reconoces esta actividad, verifica tu identidad antes de las 6:00 PM para no perder acceso.",
                "bcp-peru.com — ojo: el dominio real es viabcp.com, no bcp-peru.com",
                true, "dificil"));

        // NUEVO — Falsa oferta de departamento en alquiler
        allCards.add(new Card("c24", "social", "Facebook Marketplace", "Alquiler Dpto. Miraflores S/800",
                "Alquilo departamento amoblado en Miraflores a S/800 mensuales. El dueño vive en el extranjero. Para apartar envíe S/300 de garantía por Yape y le mandamos las llaves por courier.",
                "Precio muy por debajo del mercado — dueño en el extranjero que no puede mostrar el inmueble",
                true, "dificil"));

        // NUEVO — Falso mensaje de AFAP / ONP
        allCards.add(new Card("c25", "sms", "SMS oficial", "ONP: Devolución aprobada",
                "Su solicitud de devolución de aportes fue aprobada por S/4,200. Para acreditar el monto a su cuenta, ingrese sus datos en nuestra plataforma de pagos.",
                "onp-devoluciones-aportes.com/reclamar — la ONP solo opera en onp.gob.pe",
                true, "dificil"));

        // NUEVO — Falso técnico de Movistar/Claro
        allCards.add(new Card("c26", "whatsapp", "WhatsApp desconocido", "Soporte Técnico Movistar +51 923 444 888",
                "Buenos días, soy técnico de Movistar. Detectamos una falla en su línea que puede robarle datos. Necesito que instale esta app de diagnóstico para solucionar el problema.",
                "Movistar nunca envía técnicos por WhatsApp ni pide instalar apps externas",
                true, "dificil"));

        // NUEVO — Notificación legítima de SUNAT RUC
        allCards.add(new Card("c27", "email", "Correo electrónico", "De: notificaciones@sunat.gob.pe",
                "Su declaración mensual del periodo 05/2025 ha sido recibida exitosamente. Puede verificar el estado de su declaración ingresando a SOL con su RUC y clave.",
                "sunat.gob.pe — dominio oficial del Estado peruano",
                false, "dificil"));

        // NUEVO — Inversión falsa en criptomonedas (muy común en Lima)
        allCards.add(new Card("c28", "whatsapp", "WhatsApp desconocido", "María Fernanda — Inversiones +51 976 333 211",
                "Hola, soy asesora financiera certificada. Tengo clientes ganando S/3,000 semanales invirtiendo en Bitcoin. Solo se necesita S/500 iniciales. ¿Le interesa conocer más?",
                "Número desconocido — ninguna inversión legítima garantiza ganancias fijas semanales",
                true, "dificil"));

        // NUEVO — Falsa multa de tránsito (MTC/SAT)
        allCards.add(new Card("c29", "sms", "SMS oficial", "SAT Lima: Infracción de tránsito",
                "Usted tiene una papeleta pendiente por S/430. Si paga antes del 30/06 accede a un descuento del 50%. Ingrese su placa en el siguiente enlace para confirmar.",
                "sat-lima-infracciones.net/pagar — el SAT oficial opera en sat.gob.pe",
                true, "dificil"));

        // NUEVO — Notificación legítima de Yape
        allCards.add(new Card("c30", "sms", "SMS de Yape", "Yape: Transferencia enviada",
                "Transferiste S/120.00 a Rosa Huanca el 18/06 a las 10:32 am. Si no reconoces esta operación, llama al 311-9898 inmediatamente.",
                "yape.com.pe — SMS informativo sin enlaces ni solicitud de datos",
                false, "dificil"));

        // NUEVO — Falso empleo en empresa de delivery
        allCards.add(new Card("c31", "social", "Facebook", "Rappi Perú — Repartidores urgente",
                "Reclutamos repartidores en Lima, gana S/80 diarios. Para registrarte y recibir tu kit de trabajo debes pagar S/120 de inscripción. Cupos limitados.",
                "Rappi nunca cobra inscripción — perfil con 34 seguidores y sin verificación",
                true, "dificil"));

        // NUEVO — SMS real de Claro con recarga
        allCards.add(new Card("c32", "sms", "SMS de Claro", "Claro: Confirmación de recarga",
                "Recargaste S/20.00 a tu línea 987-654-321 el 19/06 a las 4:15 PM. Tu saldo disponible es S/20.80 incluyendo el bono de bienvenida.",
                "SMS informativo de Claro — sin enlaces ni solicitud de datos personales",
                false, "dificil"));
    }

    public List<Card> getCardsByDifficulty(String difficulty) {
        List<Card> filtered = new ArrayList<>();
        for (Card c : allCards) {
            if (difficulty == null || difficulty.isBlank() || c.getDifficulty().equals(difficulty)) {
                filtered.add(c);
            }
        }
        Collections.shuffle(filtered);
        return filtered;
    }

    public List<Card> getAllCards() {
        List<Card> copy = new ArrayList<>(allCards);
        Collections.shuffle(copy);
        return copy;
    }

    public List<Card> getSessionCards(String difficulty, int limit) {
        List<Card> pool = difficulty == null || difficulty.isBlank()
                ? getAllCards()
                : getCardsByDifficulty(difficulty);
        return pool.subList(0, Math.min(limit, pool.size()));
    }
}