<h2 align="center">SQUAD 1</h2>

## Intregrantes:
- Francisco
- Josué
- Juvenal

## Distribuição das questões:

Francisco: [Classes Livro e Venda]  
Josué: [Classes Impresso, Eletronico, LivrariaVirtual]  
Juvenal: []

## Comandos SQL para o banco de dados:

```
create database livraria;
```
```
use livraria;
```
```
CREATE TABLE livros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autores VARCHAR(255) NOT NULL,
    editora VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    frete DECIMAL(10, 2),
    estoque INT,
    tamanho INT
);
```