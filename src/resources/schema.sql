CREATE TABLE Funcionario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    id_cargo int NOT NULL
);

CREATE TABLE Status (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT NOT NULL UNIQUE -- 1: Disponível, 2: Reservado, 3: Vendido
);

CREATE TABLE carros (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi INT UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Carro
    cavalo_potencia DECIMAL(10, 2) NULL,
    numero_portas INT NULL,
    tipo_combustivel VARCHAR(50) NULL
);

CREATE TABLE motocicletas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi INT UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Motocicleta
    cilindrada INT NULL
);

CREATE TABLE caminhoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi INT UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Caminhão
    eixos INT NULL,
    capacidade_carga DECIMAL(10, 2) NULL,
    altura DECIMAL(10, 2) NULL,
    tipo_carroceria VARCHAR(100) NULL
);