# SpeedFast - Semana 8

Actividad sumativa correspondiente a la Semana 8 de Desarrollo Orientado a Objetos II.

## Descripción

SpeedFast es una aplicación desarrollada en Java que permite gestionar repartidores, pedidos y entregas mediante una interfaz gráfica Swing y una base de datos MySQL.

Durante esta semana se completó la integración entre la lógica de negocio, la interfaz gráfica y la persistencia de datos mediante operaciones CRUD.

## Tecnologías utilizadas

- Java
- Java Swing
- JDBC
- MySQL
- IntelliJ IDEA
- Git y GitHub

## Base de datos

El proyecto utiliza la base de datos:

`speedfast_db`

El archivo `speedfast_db.sql`, ubicado en la raíz del proyecto, permite crear la base de datos y las tablas necesarias.

Las entidades principales son:

- Repartidores
- Pedidos
- Entregas

## Configuración de conexión

La aplicación utiliza el usuario MySQL:

`root`

Por seguridad, la contraseña de MySQL no se encuentra almacenada directamente en el código fuente.

Antes de ejecutar el proyecto se debe configurar la variable de entorno:

`SPEEDFAST_DB_PASSWORD`

con la contraseña correspondiente al usuario MySQL local.

## Ejecución

1. Ejecutar `speedfast_db.sql` en MySQL.
2. Configurar la variable de entorno `SPEEDFAST_DB_PASSWORD`.
3. Abrir el proyecto en IntelliJ IDEA.
4. Ejecutar `Main.java`.

## Funcionalidades

### Gestión de Repartidores

- Registrar repartidores.
- Listar repartidores mediante JTable.
- Actualizar repartidores.
- Eliminar repartidores.
- Validar campos obligatorios.

### Gestión de Pedidos

- Registrar pedidos.
- Seleccionar tipo de pedido:
    - COMIDA
    - ENCOMIENDA
    - EXPRESS
- Seleccionar estado:
    - PENDIENTE
    - EN_REPARTO
    - ENTREGADO
- Listar pedidos mediante JTable.
- Actualizar pedidos.
- Eliminar pedidos.
- Validar los datos ingresados.

### Gestión de Entregas

- Registrar entregas.
- Asociar un pedido con un repartidor.
- Seleccionar pedidos y repartidores mediante JComboBox.
- Registrar fecha y hora.
- Listar entregas mediante JTable.
- Actualizar entregas.
- Eliminar entregas.
- Validar formatos de fecha y hora.

## Persistencia de datos

La aplicación utiliza JDBC para conectarse a MySQL.

La capa DAO incluye:

- `RepartidorDAO`
- `PedidoDAO`
- `EntregaDAO`

Cada DAO implementa operaciones para:

- Crear registros.
- Leer registros.
- Actualizar registros.
- Eliminar registros.

Las consultas utilizan `PreparedStatement` y `ResultSet`, junto con manejo de excepciones y cierre automático de recursos.

## Organización del proyecto

El código se encuentra separado por responsabilidades:

- `modelo`: clases y lógica del sistema.
- `dao`: conexión y acceso a la base de datos.
- `vista`: interfaz gráfica Swing.
- `main`: punto de inicio de la aplicación.

## Autor

Vicente Sanz