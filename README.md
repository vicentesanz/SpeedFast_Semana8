# SpeedFast - Semana 7

Actividad correspondiente a la Semana 7 de Desarrollo Orientado a Objetos II.

## Base de datos

El proyecto utiliza MySQL mediante JDBC.

El archivo `speedfast_db.sql` ubicado en la raíz del proyecto permite crear la base de datos y las tablas necesarias.

Base de datos utilizada:

`speedfast_db`

## Configuración de conexión

La aplicación utiliza el usuario MySQL:

`root`

Por seguridad, la contraseña no se encuentra almacenada en el código fuente.

Antes de ejecutar el proyecto se debe configurar la variable de entorno:

`SPEEDFAST_DB_PASSWORD`

con la contraseña correspondiente al usuario MySQL local.

## Ejecución

1. Ejecutar `speedfast_db.sql` en MySQL.
2. Configurar la variable de entorno `SPEEDFAST_DB_PASSWORD`.
3. Ejecutar `Main.java` desde IntelliJ IDEA.

## Funcionalidades

- Registrar pedidos en MySQL.
- Registrar repartidores en MySQL.
- Consultar los pedidos almacenados.
- Mostrar los pedidos mediante JTable.
- Asignar repartidores a pedidos.
- Registrar entregas.
- Actualizar el estado de los pedidos.