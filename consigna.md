# Consigna: Venta de Películas - Arquitectura de Microservicios

## Requerimientos Generales

Implementar una aplicación de venta de películas utilizando arquitectura de microservicios.

## Funcionalidades Principales

### 1. Catálogo de Películas
- La página principal (home) debe listar el catálogo de películas, ordenadas de la más nueva a la más vieja.
- Cada ítem del listado debe mostrar:
  - Título
  - Fecha de salida a la venta
  - Precio
  - Directores
  - Actores
  - Imagen pequeña
- Al hacer clic en una película, se accede al detalle, que incluye:
  - Condición (nuevo o usado)
  - Título
  - Directores
  - Precio
  - Formato (DVD, Blu-ray, etc.)
  - Género
  - Resumen o sinopsis
  - Actores
  - Imagen ampliada
  - Fecha de salida
  - Rating (1 a 5 estrellas)
  - Detalle de cada voto de cada cliente con su comentario

### 2. Usuarios
- Tipos de usuario:
  - **Cliente**: compra películas, vota y comenta.
  - **Administrador**: mantiene actualizado el catálogo y los descuentos.

### 3. Carrito de Compras
- El cliente, tras autenticarse, puede:
  - Agregar películas al carrito desde el detalle de cada película.
  - Ver el detalle del carrito (productos y total).
  - Quitar productos del carrito.
  - Proceder con la compra.

### 4. Compras
- Cada compra debe registrar:
  - Fecha de la transacción
  - Cliente
  - Productos comprados
- El cliente puede acceder a su historial de compras.

### 5. Descuentos
- El administrador puede especificar descuentos:
  - Anunciados en el sitio web
  - Definidos por rango de fechas y monto a descontar del total comprado

### 6. Notificaciones
- Tras cada compra, el cliente recibe un email con el detalle.
- (A futuro) Se podrá enviar el detalle por WhatsApp.

### 7. Votación y Comentarios
- El cliente puede votar/rankear una película (1 a 5 estrellas) y dejar un comentario.
- Un cliente no puede votar dos veces la misma película.

### 8. Administración
- El administrador puede:
  - Agregar nuevas películas
  - Agregar/modificar descuentos
  - Modificar películas existentes

## Consideraciones de Desarrollo
- Durante el desarrollo pueden surgir nuevos requerimientos o cambios.

## Entregables
1. Implementación con arquitectura de microservicios.
2. Tests automatizados de cada vertical.
3. Cobertura alta (>= 90%).
4. Front-end monolítico.
5. Herramienta de CI que ejecute los tests.
