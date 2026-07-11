package com.profumi.profumi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerRecomendacion(Map<String, String> respuestas) {
        String systemPrompt = "Eres un Maestro Perfumista de élite mundial para la boutique de lujo 'Profumi'.\n" +
                "CRITICAL: DO NOT write any 'thinking process', reasoning, or planning monologue. Output ONLY the raw HTML requested.";
        String userPrompt = "Has recibido un perfil detallado de un cliente exigente:\n" +
                "- Química de Piel: " + respuestas.get("piel") + "\n" +
                "- Escenario/Ocasión: " + respuestas.get("ocasion") + "\n" +
                "- Clima Predominante: " + respuestas.get("clima") + "\n" +
                "- Preferencia Sensorial: " + respuestas.get("notas") + ".\n\n" +
                "INSTRUCCIÓN:\n" +
                "1. Selecciona UN perfume de nicho o diseñador de alta gama.\n" +
                "2. Explica de forma POÉTICA y TÉCNICA por qué esa fragancia es su 'alma gemela' olfativa.\n" +
                "3. Describe la 'Vibe' o el impacto que causará.\n" +
                "FORMATO: Devuelve una estructura HTML sofisticada:\n" +
                "<div class='result-header'>[Nombre del Perfume]</div>\n" +
                "<div class='result-explanation'><p>[Explicación profunda]</p><p><strong>Notas Clave:</strong> [Mencionar notas]</p></div>";

        return llamarGroq(systemPrompt, userPrompt);
    }

    public String chatear(String mensaje, String historial, String catalogo, String perfil) {
        String systemPrompt = "Eres 'Nova', la Concierge de Lujo y Sommelier de Perfumería de Élite de 'Profumi'.\n" +
                "Tu objetivo es dar una asesoría de fragancias ultra-premium de nivel internacional, mezclando la sofisticación de Louis Vuitton, la personalización de Sephora Beauty Advisor, el soporte impecable de Apple y la inteligencia de ChatGPT.\n\n" +
                "REGLAS DE ORO:\n" +
                "1. Habla con un lenguaje elegante, poético pero técnico, digno de un experto perfumista de alta gama. Explica las notas de salida, corazón y fondo, la proyección y longevidad, la historia de las casas, el clima y ocasiones ideales.\n" +
                "2. NO inventes perfumes que no estén en el catálogo. Usa EXCLUSIVAMENTE los perfumes listados abajo.\n" +
                "3. Mantén tus respuestas conversacionales relativamente breves y fluidas, pero llenas de clase.\n" +
                "4. Si el usuario te da datos sobre sí mismo (como edad, género, gustos), regístralos mentalmente y úsalos para guiar tus recomendaciones futuras.\n" +
                "5. ETIQUETAS DE COMPONENTES INTERACTIVOS:\n" +
                "   Para activar los elementos visuales premium de la interfaz, debes añadir AL FINAL de tu respuesta (en una nueva línea) la etiqueta correspondiente con el ID exacto del perfume de la lista:\n" +
                "   - Si recomiendas un solo perfume: `[CARD:id]` (Ejemplo: `[CARD:65d3a1...]`)\n" +
                "   - Si recomiendas o listas varios perfumes (más de uno): `[CAROUSEL:id1,id2,id3...]` (Ejemplo: `[CAROUSEL:65d3a1...,65d3a2...]`)\n" +
                "   - Si el usuario te pide comparar dos o más perfumes: `[COMPARE:id1,id2...]`\n" +
                "   - Si el usuario te pide una lista general, perfumes por género (hombre/mujer) o exploración libre: `[GALLERY:id1,id2,id3,id4...]` (máximo 4 o 6)\n" +
                "   *Nota: Nunca inventes IDs, usa estrictamente los proporcionados en el catálogo.*\n\n" +
                "CRITICAL: DO NOT write any 'thinking process', reasoning steps, or internal monologue. Do not start with 'Here's a thinking process...' or similar. Output ONLY the direct premium conversational response to the client.";

        String userPrompt = "PERFIL DEL CLIENTE ACTUAL:\n" +
                perfil + "\n\n" +
                "CATÁLOGO DE PERFUMES DISPONIBLES:\n" +
                catalogo + "\n\n" +
                "INFORMACIÓN ADICIONAL DE LA TIENDA:\n" +
                "- Envíos: Mismo día en Cartagena, Envío Express Priority al resto de Colombia.\n" +
                "- Pagos: Métodos locales (Nequi, Contra Entrega, Tarjetas de crédito). Validación final mediante WhatsApp con un concierge humano.\n" +
                "- Lógica de Carrito: El usuario puede agregar productos al carrito directamente desde las tarjetas o botones del chat.\n\n" +
                "CONVERSACIÓN:\n" +
                "Historial:\n" + historial + "\n" +
                "Cliente: " + mensaje + "\n\n" +
                "Nova:";

        return llamarGroq(systemPrompt, userPrompt);
    }

    private String llamarGroq(String systemPrompt, String userPrompt) {
        if (apiKey == null || apiKey.isEmpty() || apiKey.startsWith("${")) {
            System.err.println("ERROR: API Key de Groq no configurada correctamente.");
            return "Lo siento, mi esencia no ha sido configurada. Por favor, verifica la API Key.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> sysMessage = new HashMap<>();
        sysMessage.put("role", "system");
        sysMessage.put("content", systemPrompt);

        Map<String, Object> usrMessage = new HashMap<>();
        usrMessage.put("role", "user");
        usrMessage.put("content", userPrompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.1-8b-instant");
        requestBody.put("messages", List.of(sysMessage, usrMessage));
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 500);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            System.out.println("Enviando prompt a Groq...");
            
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>) restTemplate.postForEntity(apiUrl, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();
            
            if (responseBody != null && responseBody.get("choices") instanceof List<?> choicesList) {
                if (!choicesList.isEmpty()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> choice = (Map<String, Object>) choicesList.get(0);
                    @SuppressWarnings("unchecked")
                    Map<String, Object> messageObj = (Map<String, Object>) choice.get("message");
                    if (messageObj != null && messageObj.get("content") instanceof String content) {
                        // Clean thinking tags and monologue headers
                        String cleaned = content.replaceAll("(?s)<think>.*?</think>", "");
                        cleaned = cleaned.replaceAll("(?s)Here's a thinking process.*?:", "");
                        cleaned = cleaned.replaceAll("(?s)Here's a thinking process.*?\n", "");
                        return cleaned.trim();
                    }
                }
            }
            return "Recibí una respuesta con formato inesperado de mi fuente de conocimiento.";
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            System.err.println("Error de API Groq (Status " + e.getStatusCode() + "): " + e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 429) {
                return "Mis disculpas, querido cliente. He recibido demasiadas consultas exclusivas en este breve instante. Por favor, concédeme un suspiro de un minuto para reponer mi esencia, o actualiza la clave API de Groq en la configuración.";
            }
            return "Mi conexión con la inteligencia superior ha devuelto un error: " + e.getStatusCode();
        } catch (Exception e) {
            System.err.println("Error inesperado al llamar a Groq: " + e.getMessage());
            e.printStackTrace();
            return "Como Nova AI, siempre estoy aquí para ti, aunque ahora mi conexión es tenue. (Error: " + e.getMessage() + ")";
        }
    }
}
