# API de envío de correos

### Endpoint
- **POST** `/api/email/send`

### Body (JSON)
```json
{
  "from": "tucorreo@dominio.com",
  "to": "destinatario@dominio.com",
  "subject": "Asunto de prueba",
  "text": "<b>Hola</b>, este es un correo de prueba con HTML."
}
```

### Respuesta
- `200 OK` → `"Correo enviado correctamente"`
- `4xx/5xx` → mensaje de error

### Configuración requerida
En `application.properties` o `application.yml` define tu servidor SMTP, por ejemplo (Gmail):
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=TU_CORREO@gmail.com
spring.mail.password=TU_PASSWORD_O_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
