# 🛍️ GestPro - Backend

**GestPro** é um sistema de gestão de lojas que ajuda comerciantes a organizarem seu **estoque**, controlarem **vendas** e gerenciarem **produtos** de forma simples e eficiente.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA (Hibernate)**
- **Spring Security + JWT**
- **Bean Validation**
- **MySQL (Banco de Produção)**
- **H2 Database (Testes)**
- **Swagger (Documentação da API)**
- **Lombok**

---

## 📂 Estrutura do Projeto

```
gestpro-backend/
┣ src/main/java/br/com/gestpro/gestpro_backend
┃ ┣ infra/ # Configurações de segurança, JWT, Swagger, CORS, exceptions
┃ ┣ model/ # Entidades (JPA)
┃ ┣ dto/ # Objetos de transferência de dados (request/response)
┃ ┣ service/ # Regras de negócio
┃ ┣ repository/ # Interfaces JPA
┃ ┣ controller/ # Endpoints REST
┃ ┗ GestproBackendApplication.java # Classe principal
┗ src/main/resources
┣ application.properties # Configurações da aplicação
┗ schema.sql / data.sql # Scripts opcionais para inicialização
```

---

## ⚙️ Configuração do Ambiente

### Pré-requisitos
- [Java 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/)

### Rodando o projeto localmente
1. Clone o repositório:
   ```bash
   git clone https://github.com/MartnsDev/GestPro.git
Entre no diretório:

cd gestpro-backend


Configure o banco de dados no arquivo application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/gestpro_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

Rode a aplicação:
```
mvn spring-boot:run
```
🔐 Autenticação e Segurança
```

Login e cadastro serão feitos via JWT (Bearer Token).

Cada requisição protegida deve incluir no header:

Authorization: Bearer {seu_token_jwt}
```

📖 Documentação da API

Após rodar a aplicação, acesse:
```
http://localhost:8080/swagger-ui.html
```

🛠️ Como atualizar o repositório

Sempre que fizer alterações:
```
git add .
git commit -m "Mensagem explicando a mudança"
git push
```

📌 Próximos Passos
```

 Implementar LoginController e CadastroController

 Finalizar configuração do JWT

 Criar entidades iniciais (Usuário, Produto, Venda)

 Implementar dashboard de estatísticas
```

👨‍💻 Autor
```

Projeto desenvolvido por Matheus Martins
📌 GitHub: MartnsDev
```
