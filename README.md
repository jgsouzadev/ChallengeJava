# Challenge Java

## Para executar local

Requisitos mínimos: 
- JDK v8+
- Maven v3+

Como rodar:
 - Clone o projeto e no terminal rode os seguintes comandos:
 ```
 
mvn clean install -U

mvn spring-boot:run
```
 
Como testar:
 - Basta acessar no navegador: http://localhost:8080/swagger-ui.html#/
 
 <h1> Link da API em Cloud para testar </h1>
 
 https://rest-challenge-fcamara.herokuapp.com/swagger-ui.html#/
 
 <h1> Testar usando curl: </h1>
 
 Recuperar um terminal:
 - curl -X GET "https://rest-challenge-fcamara.herokuapp.com/v1/terminal/{logic}" -H "accept: */*"
 
 Cadastrar um terminal: 
 - curl -X POST "https://rest-challenge-fcamara.herokuapp.com/v1/terminal" -H "accept: */*" -H "Content-Type: text/html" -d "412422;'123';PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN"
 
 
 Atualizar um terminal:
 - curl -X PUT "https://rest-challenge-fcamara.herokuapp.com/v1/terminal/{logic}" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"VERMF\": \"string\", \"model\": \"string\", \"mxf\": 0, \"mxr\": 0, \"plat\": 0, \"ptid\": \"string\", \"sam\": 0, \"serial\": \"string\", \"version\": \"string\"}"
 
