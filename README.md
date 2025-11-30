# AutoManager

## Tecnologias

- **Java 17**
- **Spring Boot 2.7.x**
- **Spring Data JPA**
- **Spring HATEOAS**
- **Spring Security (JWT)**
- **MySQL 8.0**
- **Lombok**
- **Maven**

---

## Requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [MySQL 8.0+](https://dev.mysql.com/downloads/mysql/)
- [Maven](https://maven.apache.org/download.cgi) (ou use o wrapper incluído)

---

## Como Executar

### Configurar o Banco de Dados

Crie um banco de dados MySQL:

```sql
CREATE DATABASE automanager;
```

### Configurar application.properties

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/automanager
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT
jwt.secret=SUA_CHAVE_SECRETA
jwt.expiration=86400000
```

### Executar o Projeto

Execute a classe `AutomanagerApplication.java` (pelo IDE ou via terminal):

```sh
./mvnw spring-boot:run
```
ou
```sh
mvn spring-boot:run
```

### Acessar a Aplicação

A aplicação estará disponível em:
```
http://localhost:8080
```

---

## Autenticação

A autenticação é feita via JWT.  
Para acessar qualquer rota protegida, faça login em `/api/auth/login` e utilize o token retornado no header `Authorization: Bearer {token}`.

Exemplo de login:
```http
POST /api/auth/login
Content-Type: application/json

{
  "nomeUsuario": "admin",
  "senha": "admin123"
}
```

---

## Endpoints da API

Consulte o arquivo [`endpoints.md`](./endpoints.md) para ver a lista completa de rotas, métodos e exemplos de uso.

---

## Permissões por Perfil

| Endpoint           | ADMIN | GERENTE | VENDEDOR      | CLIENTE   |
|--------------------|:-----:|:-------:|:-------------:|:---------:|
| /api/auth/login    |  ✅   |   ✅    |      ✅       |    ✅     |
| /usuario/**        |  ✅   |   ✅    |      ✅       |    ❌     |
| /empresa/**        |  ✅   |   ❌    |      ❌       |    ❌     |
| /mercadoria/**     |  ✅   |   ✅    |   ✅ (READ)   |    ❌     |
| /servico/**        |  ✅   |   ✅    |   ✅ (READ)   |    ❌     |
| /venda/**          |  ✅   |   ✅    | ✅ (LIMITED)  |    ✅     |
| /veiculo/**        |  ✅   |   ✅    |      ✅       |    ✅     |

- **✅**: acesso total (CRUD)
- **✅ (READ)**: apenas leitura
- **✅ (LIMITED)**: acesso restrito (ex: vendedor só vê/cria suas vendas)
- **❌**: sem acesso

---

## Observações

- Todos os endpoints (exceto `/api/auth/login`) exigem o header `Authorization: Bearer {seu_token_jwt}`.
- Para cadastrar, atualizar ou excluir, envie o objeto completo no corpo da requisição (JSON).
- Consulte os controllers para detalhes de cada entidade e exemplos de JSON.

---