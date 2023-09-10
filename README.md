# API Fórum Alura

Esta é uma API Rest para um Fórum, onde é possível realizar operações como cadastrar, listar, detalhar, atualizar e deletar tópicos.

## Executando a Aplicação Localmente

Para executar a aplicação localmente, siga os passos abaixo:

1. Clone o repositório para sua máquina local.
2. Navegue até o diretório do projeto.
3. Execute a aplicação Spring Boot usando o comando: `./mvnw spring-boot:run`
4. A aplicação estará disponível em `http://localhost:8080`.

## Banco de Dados H2

O H2 é um banco de dados relacional escrito em Java. Ele pode ser incorporado em aplicações Java ou executado no modo cliente-servidor. Devido à sua natureza leve e capacidade de ser iniciado rapidamente, é frequentemente usado para fins de desenvolvimento e testes.

## Usuários e Permissões

Ao iniciar a aplicação, dois usuários são inicializados no banco de dados H2:

- **Aluno**: `aluno@email.com`
- **Moderador**: `moderador@email.com`

Ambos os usuários têm a mesma senha: `123456`.

Aqui estão as permissões para cada tipo de usuário:

| Endpoint                 | Método | Aluno | Moderador |
|--------------------------|--------|-------|-----------|
| `/topicos`               | GET    | ✅    | ✅        |
| `/topicos/*`             | GET    | ✅    | ✅        |
| `/auth`                  | POST   | ✅    | ✅        |
| `/actuator/**`           | GET    | ✅    | ✅        |
| `/topicos/*`             | DELETE | ❌    | ✅        |
| Qualquer outro endpoint  | *      | ❌    | ✅        |

## Autenticação JWT

A API utiliza autenticação JWT. Para autenticar e gerar o token JWT, faça uma requisição POST para `http://localhost:8080/auth` com o email e senha:

```
{
 "email": "moderador@email.com",
 "senha": "123456"
}
```
## Documentação Swagger
A documentação da API está disponível no Swagger em http://localhost:8080/swagger-ui.html.




