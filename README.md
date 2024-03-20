# Trabajo Práctico Anual Integrador - GRUPO 2

**Monitoreo de Estado de Servicios de Transporte Público y de Establecimientos**:

Este trabajo consiste en registrar la accesibilidad a los servicios públios para las personas con movilidad reducida (PMR).

## Requerimientos de la primera entrega:
1. Se debe permitir la administración de servicios públicos
2. Se debe permitir la administración de servicios
3. Se debe permitir la administración de prestación de servicios
4. Se debe permitir la administración de comunidades y miembros
5. El sistema debe permitir el registro de usuarios guardando usuario y contraseña siguiendo las recomendaciones del OWASP

### Servicios

En esta primera entrega se consideran solamente los servicios públicos de Subterraneos y Ferrocarriles.

Para poder registrar de que servicios disponen cada linea se modela la clase `Linea`, que indica que tipo de transporte es y que estaciones forman parte de su recorrido.
Las estaciones están en una lista ordenada para poder conocer su origen y destino.

Se modelan la `Estacion` con su dirección y los servicios que tienen disponibles.

Puesto que por ahora solo sabemos que existen los servicios Escaleras mecánicas, Ascensores y Baños, la clase `Servicio` se modela con un Enum y un booleano que indica su disponibilidad.

![image](/imagenes/SERVICIOS%20-%20DDS.png)

### Comunidades

Para poder registrar a las Comunidades y sus miembros se modela la clase `Comunidad`, con su lista de miembros y su lista de administradores designados. La clase `Miembro` registra nombre, apellido y mail del miembro.

![image](/imagenes/COMNIDADES%20-%20DDS.png)

### Usuarios

Para poder permitir el registro de un usuario, se modela la clase `Usuario` qe registra nombre de usuario, contraseña y el rol, que por ahora solo sabemos que puede ser un usuario común o un administrador.

Para poder validar que el usuario utilize una contraseña válida se crea también el `ValidadorPassword`, que verificará que la contraseña ingresada cumpla con los requerimientos.

![image](/imagenes/USUARIOS%20-%20DDS.png)