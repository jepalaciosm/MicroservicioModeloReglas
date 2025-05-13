-- Insertar algunos comerciales de ejemplo
INSERT INTO comerciales (nombre, email) VALUES
('Ana Gómez', 'ana.gomez@example.com'),
('Luis Torres', 'luis.torres@example.com'),
('Sofía Castro', 'sofia.castro@example.com'),
('Carlos Peña', 'carlos.pena@example.com');

-- Insertar algunas reglas de asignación de ejemplo
-- Las prioridades más bajas se evalúan primero.

-- Regla 1: Clientes Corporativos de Zona Norte van con Ana Gómez (ID Comercial 1)
INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
('Corp Zona Norte', 10, 'tipoCliente', 'IGUAL_A', 'Corporativo', 1, true, 'Asigna clientes corporativos de la Zona Norte a Ana Gómez.');
-- Esta regla anterior es muy general, para que sea específica de Zona Norte, necesitaríamos una regla compuesta o
-- una regla que verifique la ubicación. Por simplicidad del motor, haremos reglas más directas.

-- Regla 1 (Corregida): Clientes Corporativos van con Ana Gómez
INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
('Clientes Corporativos', 10, 'tipoCliente', 'IGUAL_A', 'Corporativo', 1, true, 'Asigna clientes de tipo "Corporativo" a Ana Gómez.');

-- Regla 2: Clientes PYME de la ubicacion "Ciudad Capital" van con Luis Torres (ID Comercial 2)
-- Para esto, necesitaríamos lógica de AND en el motor, o dos reglas separadas y confiar en la prioridad.
-- Simplificaremos: Clientes de "Ciudad Capital" van con Luis Torres.
INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
('Ubicacion Ciudad Capital', 20, 'ubicacionGeografica', 'IGUAL_A', 'Ciudad Capital', 2, true, 'Asigna clientes ubicados en "Ciudad Capital" a Luis Torres.');

-- Regla 3: Clientes con volumen de compra anual > 50000 van con Sofía Castro (ID Comercial 3)
INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
('Alto Volumen Compra', 30, 'volumenCompraAnual', 'MAYOR_QUE', '50000', 3, true, 'Asigna clientes con compras anuales superiores a 50000 a Sofía Castro.');

-- Regla 4: Clientes del sector "Tecnologia" o "Salud" van con Carlos Peña (ID Comercial 4)
INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
('Sector Tech o Salud', 40, 'sectorIndustrial', 'EN_LISTA', 'Tecnologia,Salud', 4, true, 'Asigna clientes del sector Tecnología o Salud a Carlos Peña.');

-- Regla 5: Clientes PYME (si no cumplen otras reglas de mayor prioridad) van con Luis Torres (ID Comercial 2)
INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
('Clientes PYME General', 50, 'tipoCliente', 'IGUAL_A', 'PYME', 2, true, 'Asigna clientes de tipo "PYME" (general) a Luis Torres.');

-- Regla por defecto (baja prioridad): Todos los demás clientes (si ninguna otra regla aplica) van con Ana Gómez (ID Comercial 1)
-- Esta regla es conceptual. Para implementarla directamente, podríamos usar un campo que siempre sea verdadero o
-- simplemente si ninguna regla anterior coincide, se asigna un comercial por defecto en el código.
-- Por ahora, la dejaremos fuera y el servicio devolverá "no asignado" si nada coincide.
-- INSERT INTO reglas_asignacion (nombre_regla, prioridad, campo_cliente, operador, valor_condicion, id_comercial_asignado, activa, descripcion_no_tecnica) VALUES
-- ('Default General', 100, 'nombreCompleto', 'NO_CONTIENE', 'ESTO_NUNCA_SE_CUMPLE_PARA_NO_APLICAR', 1, true, 'Asignación por defecto a Ana Gómez.');

