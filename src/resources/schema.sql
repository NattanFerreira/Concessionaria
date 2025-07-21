CREATE TABLE Funcionario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    id_cargo int NOT NULL
);

CREATE TABLE IF NOT EXISTS Venda (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valorTotal REAL NOT NULL,
    dataVenda INTEGER NOT NULL,
    idVeiculo INTEGER NOT NULL,
    idFuncionario INTEGER NOT NULL,
    FOREIGN KEY (idVeiculo) REFERENCES Veiculo(id),
    FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id)
);

CREATE TABLE Status (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT NOT NULL UNIQUE -- 1: Disponível, 2: Reservado, 3: Vendido
);

CREATE TABLE carros (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi VARCHAR(20) UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Carro
    cavalo_potencia DECIMAL(10, 2) NOT NULL,
    numero_portas INT NOT NULL,
    id_tipo_combustivel VARCHAR(50) NOT NULL
);

CREATE TABLE motocicletas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi VARCHAR(20) UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Motocicleta
    cilindrada INT NOT NULL
);

CREATE TABLE caminhoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi VARCHAR(20) UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Caminhão
    eixos INT NOT NULL,
    capacidade_carga DECIMAL(10, 2) NOT NULL,
    altura DECIMAL(10, 2) NOT NULL,
    tipo_carroceria VARCHAR(100) NOT NULL
);