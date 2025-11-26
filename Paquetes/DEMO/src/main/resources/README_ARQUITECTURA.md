# Demo Paquetes (Publicador + Consumidor)

- `publicador`: expone API de catalogo (destinos/paquetes), crea compras (EN_PROCESO) y simula el pago en Pagame. Publica eventos de estado en RabbitMQ.
- `consumidor`: escucha la cola `estado-paquete`, guarda el evento en MySQL y simula la notificacion SMS/email devolviendo el ultimo/historial.
- `data.sql`: carga destinos y paquetes colombianos en MySQL (`paquetes_demo`). El precio del paquete es la suma de sus destinos; el total de la compra es la suma de los paquetes elegidos.

Cola RabbitMQ usada por ambos:
- exchange: `estado-paquete-exchange` (direct)
- routing key: `estado.paquete`
- queue: `estado-paquete`

Endpoints publicador (puerto 8810):
- `GET /api/catalogo/destinos`
- `GET /api/catalogo/paquetes`
- `POST /api/compras` (cedula, nombre, correo, celular, paquetesIds[])
- `POST /api/compras/{id}/pagos` (estado: APROBADO/RECHAZADO/EN_PROCESO)
- `GET /api/compras/{id}`

Endpoints consumidor (puerto 8811):
- `GET /api/notificaciones/ultimo`
- `GET /api/notificaciones/historial`
- `GET /api/notificaciones`

Correr servicios:
- `./mvnw -pl publicador spring-boot:run`
- `./mvnw -pl consumidor spring-boot:run`
