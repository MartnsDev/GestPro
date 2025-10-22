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
api/
 ├─ controller/   → Controllers REST (endpoints)
 └─ dto/          → Data Transfer Objects

domain/
 ├─ model/        → Entidades/Models do banco
 ├─ repository/   → Interfaces JPA (CRUD)
 └─ service/      → Regras de negócio

infra/
 ├─ configs/      → Configurações (CORS, etc)
 ├─ swagger/      → Configuração do Swagger/OpenAPI
 ├─ filters/      → Filtros de requisição, JWT, etc
 ├─ exceptions/   → Tratamento de exceções customizadas
 ├─ security/     → Segurança (Spring Security, JWT, roles)
 └─ util/         → Helpers, validadores, funções genéricas
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
<<<<<<< HEAD
=======

>>>>>>> d3723a1 (Atualização do backend GestPro: login com Google, JWT e dashboard)
