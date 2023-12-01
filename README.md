# Proyecto Java


## Generación del reporte de coverage

Para generar el reporte de coverage es necesario ejecutar el siguiente comando.


```groovy
gradle test
 ```

### Consultar reporte de coverage
Se generará un reporte en la siguiente ruta 

```html
{{PROJECT_PATH}}\usermanagement\build\report\test\index.html
 ```

## Generación del artefacto

Para generar el artefacto JAR se usa el siguiente comando


```groovy
gradle bootJar
 ```

Se generará el siguiente ejecutable

```
{{PROJECT_PATH}}\usermanagement\build\libs\usermanagement-0.0.1-SNAPSHOT.jar
 ```

Para iniciar el servicio es necesario ejecutar el siguiente comando
```
java -jar usermanagement-0.0.1-SNAPSHOT.jar
```

## Testear servicios
Una vez levantado el servicio podremos ejecutar las validaciones de los servicios /sign-up y /login

### Sign-up
Ejecutamos el siguiente comando _curl_ con el dato de prueba


```
curl --location 'localhost:8080/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{

    "name": "User",
    "email": "user.test@gmail.com",
    "password": "Ga2asffdfdf4",
    "phones" : [
{
"number" : 123456,
"citycode" : 1,
"countrycode" :"+51"

    }
    ]
}'
 ```

Te devolverá un JSON de la siguiente estructura 
 ```
{
"httpStatus": "CREATED",
"message": {
"id": "56bfa9cb-17f4-4863-99b7-1eac3d722450",
"created": "2023-12-01T16:28:04.9537496",
"lastLogin": null,
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyLnRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzAxNDY2MDg1LCJleHAiOjE3MDE0NjYxNDV9.B0vehWJdXBtYHLOiL3UacpLzVf7NIKZPoyrm_3TCeLA",
"active": true
}
}
 ```

### Login

Ejecutamos el siguiente comando _curl_ con el dato de prueba , en donde el bearer token será el que se obtuvo en el request del /sign-up

 ```
curl --location --request POST 'localhost:8080/login' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyLnRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzAxNDY2MDg1LCJleHAiOjE3MDE0NjYxNDV9.B0vehWJdXBtYHLOiL3UacpLzVf7NIKZPoyrm_3TCeLA'
 ```

Te devolverá un JSON de la siguiente estructura 
 ```
{
"id": "56bfa9cb-17f4-4863-99b7-1eac3d722450",
"created": "2023-12-01T16:28:04.95375",
"lastLogin": "2023-12-01T16:28:15.0530626",
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyLnRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzAxNDY2MDk1LCJleHAiOjE3MDE0NjYxNTV9.qbfnLOXyBQFIxv5MUrxFS4kqs9mmkjTdwxzUf237zNE",
"name": "User",
"email": "user.test@gmail.com",
"password": "$2a$10$Q6YcVcSqWZqTNHSwsPoaQ.Ocne5cINAZj3WFBcONCoN2gOkWO7dea",
"phones": [
{
"number": 123456,
"citycode": 1,
"countrycode": "+51"
}
],
"active": true
}
 ```