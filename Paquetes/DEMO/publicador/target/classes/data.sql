-- Limpia tablas para permitir reinicializar datos base
DELETE FROM compra_paquetes;
DELETE FROM compras;
DELETE FROM paquete_destinos;
DELETE FROM paquetes_turisticos;
DELETE FROM destinos;

INSERT INTO destinos (id, nombre, ciudad, region, descripcion, precio) VALUES
 (1, 'Ciudad amurallada de Cartagena', 'Cartagena', 'Caribe', 'Calles coloniales, Getsemani y atardecer desde las murallas', 520000),
 (2, 'Islas del Rosario full day', 'Cartagena', 'Caribe', 'Salida en lancha, snorkel y playa en Cholón y Playa Blanca', 420000),
 (3, 'Parque Tayrona + Cabo San Juan', 'Santa Marta', 'Caribe', 'Senderismo, playas y miradores dentro del Tayrona', 390000),
 (4, 'Guatape y Piedra del Penol', 'Antioquia', 'Antioquia', 'Subida a la piedra, recorrido en lancha y pueblo colorido', 310000),
 (5, 'Comuna 13 grafiti tour', 'Medellin', 'Antioquia', 'Escaleras electricas, arte urbano y cafes locales', 280000),
 (6, 'Salento y Valle de Cocora', 'Quindio', 'Eje Cafetero', 'Bosque de palmas de cera y finca cafetera con cata', 350000),
 (7, 'Termales de Santa Rosa', 'Risaralda', 'Eje Cafetero', 'Cascadas, aguas termales y cena campesina', 270000),
 (8, 'Centro historico y Monserrate', 'Bogota', 'Andes', 'La Candelaria, museos y subida al cerro al atardecer', 250000),
 (9, 'Barichara y Camino Real', 'Santander', 'Andes', 'Pueblo patrimonio y caminata hasta Guane', 330000),
 (10, 'Desierto de la Tatacoa nocturno', 'Huila', 'Andes', 'Observatorio astronomico y recorrido por los laberintos', 300000),
 (11, 'Cabo de la Vela y Dunas de Taroa', 'La Guajira', 'Caribe', 'Tour 4x4, rancheria wayuu y atardecer en el faro', 450000),
 (12, 'San Andres vuelta a la isla', 'San Andres', 'Caribe', 'Jonny Cay, acuario y mantarrayas en West View', 550000);

INSERT INTO paquetes_turisticos (id, nombre, descripcion, precio_total) VALUES
 (1, 'Caribe colonial', 'Cartagena + Islas Rosario + Tayrona', 1330000),
 (2, 'Islas y mar de siete colores', 'Islas del Rosario + San Andres', 970000),
 (3, 'Eje cafetero clasico', 'Salento, Valle de Cocora y termales', 900000),
 (4, 'Andes urbano', 'Bogota historica + Guatape + Comuna 13', 840000),
 (5, 'Aventura desertica', 'Tatacoa nocturna y La Guajira en 4x4', 750000);

INSERT INTO paquete_destinos (paquete_id, destino_id) VALUES
 (1, 1), (1, 2), (1, 3),
 (2, 2), (2, 12),
 (3, 6), (3, 7), (3, 5),
 (4, 8), (4, 4), (4, 5),
 (5, 10), (5, 11);
