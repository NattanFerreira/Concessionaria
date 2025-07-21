CREATE TABLE IF NOT EXISTS Funcionario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    id_cargo int NOT NULL
);

CREATE TABLE IF NOT EXISTS Venda (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valorTotal REAL NOT NULL,
    dataVenda STRING NOT NULL,
    idFuncionario INTEGER NOT NULL,
    FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Carrinho (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    idVenda INTEGER NOT NULL,
    idFuncionario INTEGER NOT NULL,
    idVeiculo INTEGER NOT NULL,
    idTipoVeiculo INTEGER NOT NULL,
    FOREIGN KEY (idVenda) REFERENCES Venda(id) ON DELETE CASCADE,
    FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS carros (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi VARCHAR(255) NOT NULL,
    quilometragem DECIMAL(10, 2) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Carro
    cavalo_potencia int NOT NULL,
    numero_portas INT NOT NULL,
    id_tipo_combustivel INT NOT NULL
);

CREATE TABLE IF NOT EXISTS motocicletas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi VARCHAR(20) NOT NULL,
    quilometragem DECIMAL(10, 2) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    id_status INT NOT NULL,
    -- Dados específicos para Motocicleta
    cilindrada INT NOT NULL
);

CREATE TABLE IF NOT EXISTS caminhoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    modelo VARCHAR(255) NOT NULL,
    num_chassi VARCHAR(20) NOT NULL,
    quilometragem DECIMAL(10, 2) NOT NULL,
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