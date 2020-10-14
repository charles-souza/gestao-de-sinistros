## API Gerenciamento de Sinistros
Este programa tem o objetivo implementar uma api rest para gerenciamento de patrimônio empresarial.

## Implementação
Este projeto foi implementado usando spring boot com padrão MVC contendo as seguintes dependências:<br>
Spring Web<br>
Spring Data JPA<br>
Spring Boot DevTools<br>
Postgres Driver<br>
Lombok
Model Mapper
Spring Security<br>

IDE: Spring Tool Suite 4


## Iniciando o projeto
Clone o repositório

```
git clone https://github.com/charles-souza/gestao-de-sinistros.git
```
Crie a base de dados postgres com o nome sinistro.

```
create database sinistro;
```
1. Instale o lombok na IDE
2. Import como Existing Maven Projects
3. Selecione o diretório e finish
4. Clique com obotão direito no projeto e run > java application

## Obter Autorização
Utilizado Basic Auth para autorização.<br>
Digite as credenciais para obter autorização:<br>
Username: **sinistro**<br>
Password: **123**<br>

## Acesso aos Recursos

### Person
GET Recebe um ID como parâmetro e retorna uma pessoa buscando pelo ID.<br>
**/v1/pessoas/{id}**

GET Retorna uma lista com todas as pessoas cadastrados.<br>
**/v1/pessoas**

POST Recebes os dados na requisição e insere um patrimônio.<br>
**/v1/pessoas**

```
{	
	"name": "Nome",
	"lastName": "Sobrenome",
  "zipcode":"74000000",
	"street": "rua",
	"number": 10,
	"complement": "complemento",
	"district": "bairro",
	"city": "Cidade",
  "state": "UF",
	"rg": 1234567,
  "emitter": "SPTC",
  "expedition": "GO",
  "cpf": "12345678912",
  "gender": "masculino",
  "email": "email@email.com.br",
  "maritalStatus": "solteiro",
  "birth": "2020-08-01"
}
```

PUT Recebe um ID e os dados na requisição como parâmetro e atualiza uma pessoa.<br>
**/v1/pessoas/{id}**

```
{	
	"name": "Nome",
	"lastName": "Sobrenome",
  "zipcode":"74000000",
	"street": "rua",
	"number": 10,
	"complement": "complemento",
	"district": "bairro",
	"city": "Cidade",
  "state": "UF",
	"rg": 1234567,
  "emitter": "SPTC",
  "expedition": "GO",
  "cpf": "12345678912",
  "gender": "masculino",
  "email": "email@email.com.br",
  "maritalStatus": "solteiro",
  "birth": "2020-08-01"
}
```

DELETE Recebe um ID como parâmetro e deleta uma pessoa.<br>
**/v1/pessoas/{id}**

### Marca
GET Recebe um ID como parâmetro e retorna uma marca buscando pelo ID.<br>
**/v1/brands/{id}**

GET Retorna uma lista com todos as marcas cadastradas.<br>
**/v1/brands**

POST Recebes os dados na requisição e insere uma marca.<br>
**/v1/brands**

```
{
    "name": "Marca"
}
```

PUT Recebe um ID e os dados na requisição como parâmetro e atualiza uma marca. ID da marca é opcional na atualização<br>
**/v1/brands/{id}**

```
{
    "name": "Marca"
}
```

DELETE Recebe um ID como parâmetro e deleta uma marca.<br>
**/v1/brands/{id}**

### Usuário
GET Recebe um ID como parâmetro e retorna um usuário buscando pelo ID.<br>
**/v1/users/{id}**

GET Retorna uma lista com todos os usuários cadastrados.<br>
**/v1/users**

POST Recebes os dados na requisição e insere um usuário. Este recurso não necessita de autenticação.<br>
**/v1/users**

```
{
    "name": "Nome da Pessoa",
    "email": "email@pes.com",
    "password": "123"
}
```

PUT Recebe um ID e os dados na requisição como parâmetro e atualiza um usuário.<br>
**/v1/users/{id}**

```
{
    "name": "Nome da Pessoa",
    "email": "email@pes.com",
    "password": "123"
}
```

DELETE Recebe um ID como parâmetro e deleta um usuário.<br>
**/v1/users/{id}**


