<h2 align="center">SQUAD 1</h2>

## Intregrantes:
- Francisco
- Josué

## Distribuição das questões:

Francisco: [Classes Livro e Venda]  
Josué: [Classes Impresso, Eletronico, LivrariaVirtual]

## Comandos SQL para o banco de dados:

```
create database livraria;
```
```
use livraria;
```
```
CREATE TABLE Livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autores VARCHAR(255) NOT NULL,
    editora VARCHAR(255) NOT NULL,
    preco FLOAT NOT NULL
);

CREATE TABLE Impresso (
    id INT PRIMARY KEY,
    frete FLOAT,
    estoque INT,
    FOREIGN KEY (id) REFERENCES Livro(id)
);

CREATE TABLE Eletronico (
    id INT PRIMARY KEY,
    tamanho INT,
    FOREIGN KEY (id) REFERENCES Livro(id)
);
```
```
CREATE TABLE Venda (
    numero INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL,
    valor FLOAT NOT NULL
);

CREATE TABLE VendaLivro (
    venda_numero INT,
    livro_id INT,
    PRIMARY KEY (venda_numero, livro_id),
    FOREIGN KEY (venda_numero) REFERENCES Venda(numero),
    FOREIGN KEY (livro_id) REFERENCES Livro(id)
);
```