Spring Boot JPA + H2

Desarrollo de una aplicación que exponga una API RESTful para la creación de usuarios y manejo de JWT como token.

REQUISITO:

● Banco de datos en memoria. H2.

● Proceso de build vía Gradle.

● Persistencia con JPA. Ejemplo: Hibernate.

● Framework SpringBoot.

● Java 8+, en este proyecto se uso java 17.

Patron de Diseño Utilizado: MVC.

Diagrama de Clases:
![image](https://github.com/fhernandez204/apiRest/blob/master/diagrama%20clases.png)

Estructura del Proyecto:

![image](https://github.com/fhernandez204/apiRest/blob/master/project%20structure.png)

ENDPOINT

CREAR USUARIO
 http://localhost:8080/api/users

 
![image](https://github.com/fhernandez204/apiRest/blob/master/createUser.png)

SI EL CORREO YA EXISTE

![image](https://github.com/fhernandez204/apiRest/blob/master/createUser2.png)

SI EL CORREO NO TIENE EL FORMATO CORRECTO

![image](https://github.com/fhernandez204/apiRest/blob/main/createUser3.png)


GET USUARIO POR ID

  http://localhost:8080/api/users/19152

 ![image](https://github.com/fhernandez204/apiRest/blob/master/getUsers.png)


 MODIFICAR USUARIO

 http://localhost:8080/api/users/6352

 ![image](https://github.com/fhernandez204/apiRest/blob/master/putUser.png)

 TestUnit
 ![image](https://github.com/fhernandez204/apiRest/blob/master/testUnit.png)
 
