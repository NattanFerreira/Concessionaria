CREATE TABLE Funcionario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    cargo TEXT
);
