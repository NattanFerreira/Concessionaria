CREATE TABLE Funcionario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    cargo TEXT
);

CREATE TABLE veiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_veiculo VARCHAR(50) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    num_chassi INT UNIQUE NOT NULL,
    quilometragem DECIMAL(10, 2),
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50),
    ano_fabricacao INT,
    id_status INT NOT NULL,

    -- Dados para Motocicleta
    cilindrada INT NULL,

    -- Dados para Carro
    cavalo_potencia DECIMAL(10, 2) NULL,
    numero_portas INT NULL,
    tipo_combustivel VARCHAR(50) NULL,

    -- Dados para Caminhao
    eixos INT NULL,
    capacidade_carga DECIMAL(10, 2) NULL,
    altura DECIMAL(10, 2) NULL,
    tipo_carroceria VARCHAR(100) NULL
);
