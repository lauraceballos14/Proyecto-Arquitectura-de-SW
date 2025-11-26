package com.paquetes.consumidor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paquetes.consumidor.entity.Notificacion;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.mail.username}")
    private String remitente;

    public void enviarNotificacion(Notificacion notificacion) {
        if (notificacion.getCorreo() == null || notificacion.getCorreo().isBlank()) {
            System.out.println("[Email] Correo destino vacio, no se envia email.");
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(notificacion.getCorreo());
            helper.setFrom(remitente);
            helper.setSubject("Estado de tu compra #" + notificacion.getCompraId() + " - " + notificacion.getEstado());
            helper.setText(construirHtml(notificacion), true);

            mailSender.send(message);
            System.out.printf("[Email] Enviado a %s para compra #%s%n",
                    notificacion.getCorreo(), notificacion.getCompraId());
        } catch (Exception e) {
            System.err.println("[Email] No se pudo enviar correo: " + e.getMessage());
        }
    }

    private String construirHtml(Notificacion n) {
        String paquetesHtml = "Sin detalle";

        try {
            if (n.getPaquetes() != null && !n.getPaquetes().isBlank()) {
                List<Map<String, Object>> paquetes =
                        objectMapper.readValue(n.getPaquetes(), new TypeReference<>() {});

                StringBuilder sb = new StringBuilder();

                for (Map<String, Object> paquete : paquetes) {
                    sb.append("<h3>").append(paquete.get("nombre")).append("</h3>");

                    List<Map<String, Object>> destinos =
                            (List<Map<String, Object>>) paquete.get("destinos");

                    sb.append("<ul>");
                    for (Map<String, Object> d : destinos) {
                        sb.append("<li>")
                                .append(d.get("nombre"))
                                .append(" - ")
                                .append(d.get("ciudad"))
                                .append(" $")
                                .append(d.get("precio"))
                                .append("</li>");
                    }
                    sb.append("</ul>");
                }
                paquetesHtml = sb.toString();
            }

        } catch (Exception e) {
            paquetesHtml = "Detalle no disponible";
        }

        return """
                <html>
                  <body style="font-family: Arial; color:#222;">
                    <h2>Hola %s</h2>
                    <p>Estado actualizado de tu compra <strong>#%s</strong>:</p>

                    <p><strong>Estado:</strong> %s<br/>
                       <strong>Total:</strong> $%s</p>

                    <h3>Paquetes adquiridos:</h3>
                    %s

                    <p>Fecha de actualizacion: %s</p>
                    <hr/>
                    <p>Gracias por viajar con nosotros.</p>
                  </body>
                </html>
                """.formatted(
                n.getClienteNombre(),
                n.getCompraId(),
                n.getEstado(),
                n.getTotal(),
                paquetesHtml,
                n.getRecibidoEn()
        );
    }
}
