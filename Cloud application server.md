# Cloud Server Application

-   Server
-   Tomcat
-   Aplicación web
-   Spring boot application
-   Aplicando patrón MVC (Model-View-Controller)
-   Flujo de ejecución interna: Controller → Service→ Repository
-   Server Tomcat embebido
-   BD relacional
-   Postgres
-   Artefacto desplegable: jar
## Conceptos técnicos y Libraries involucradas en el proyecto

-   Libraries
	-   ***Lombok***: generador de código boilerplate (`Getters`, `Setters`, `Constructor`, `EqualsAndHashCode`, `ToString`)
		-   Activar annotation-processor en el IDE.
	-   ***Spring Web Starter***: añade clases y anotaciones necesarias para crear una Aplicación Web.
	-   ***Spring Data***: Añade interfaz `JpaRepository` y dependencias necesarias para el manejo de BD como por ejemplo ***Hibernate***.
-   ***Gradle***: es una herramienta de buildeo y gestor de dependencias
	-   *Gradle* → `reimport` (en caso de haber modificado alguna dependencia)
-   ***Hibernate***: ORM (Mapeo Objeto Relacional)
	-   Se encarga de persistir y recuperar objetos hacia y desde la BD respectivamente.
	-   Cada fila de una tabla se corresponde a una instancia de la clase entidad asociada.
	-   “Jerarquia” de tablas:
		-   Extender las clases entidades correspondientes    
		-   Anotar la clase padre con `@Inheritance(strategy = InheritanceType.JOINED)`
-   `application.properties`: Es el archivo de configuracion de propiedades (clave=valor) de ***Spring Boot***, que permite configurar todo tipo de parámetros desde un mismo lugar. Properties comunes de configuración:
```properties
server.port: 8080
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=admin
```
- Spring Data: provee funcionalidad de consultas a la BD sin escribir SQL
	-   Queries by method name
	-   [JPA Repositories](https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html) → Puntos 2.3 hasta 2.3.2 inclusive.

-   Anotaciones Spring Beans: `@RestController`, `@Service`, `@Repository` y `@Component`
	-   Permite a ***Spring*** generar una instancia de la clase anotada y mantenerla en su contenedor de objetos creados.
	-   Luego haciendo uso de `@Autowired`, las instancias pueden ser inyectadas sobre otros objetos anotados con las anotaciones de ***Spring Beans***
	-   NO hacer `new` de una clase anotada con estas anotaciones porque se “mata” la inyección de dependencias.
-   ***Swagger***: API Client (para realizar llamados HTTP y poder probar la API)
	-   agregar en `build.gradle` las siguientes dos dependencias `SpringFox Swagger` y anotar con `@EnableSwagger2` en la clase principal anotada con `SpringBootApplication`
		-   `compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.9.2'`
		-   `compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.9.2'`
	-   Facilita el uso de la API ya que provee JSONs según el objeto que esperan los endpoints
	-   Además provee funcionalidad para exportar una documentación de la API (endpoints, interfaces de request y response)
-   ***DBeaver***: Cliente de BD para manejarla mediante interfaz gráfica.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwODIwOTE2OTUsLTEyNjc0NDU1Ml19
-->