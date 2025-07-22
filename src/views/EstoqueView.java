package views;

import utils.Funcoes;
import data.Banco;
//import models.Veiculo;
import models.Carro;
import models.Motocicleta;
import models.Caminhao;
import controllers.CarroDao;
import controllers.MotocicletaDao;
import java.util.ArrayList;
import java.util.List;

import controllers.CaminhaoDao;

/**
 * Classe responsável pela interface de gerenciamento de estoque de veículos.
 * Fornece funcionalidades completas de CRUD (Create, Read, Update, Delete) 
 * para todos os tipos de veículos da concessionária.
 * 
 * Esta classe implementa um sistema robusto de gerenciamento de estoque com:
 * - Operações básicas: adicionar, remover, atualizar e listar veículos
 * - Sistema avançado de busca com múltiplos critérios
 * - Suporte para três categorias de veículos: Carros, Motocicletas e Caminhões
 * - Interface intuitiva baseada em menus hierárquicos
 * 
 * Funcionalidades por tipo de veículo:
 * 
 * CARROS:
 * - Busca por ID, modelo, chassi
 * - Filtros por preço, quilometragem, ano
 * - Busca por cor, potência, número de portas
 * - Filtro por tipo de combustível
 * 
 * MOTOCICLETAS:
 * - Busca por ID, modelo, chassi
 * - Filtros por preço, quilometragem, ano
 * - Busca por cor e cilindrada
 * 
 * CAMINHÕES:
 * - Busca por ID, modelo, chassi
 * - Filtros por preço, quilometragem, ano
 * - Busca por cor, número de eixos
 * - Filtros por capacidade de carga, altura
 * - Busca por tipo de carroceria
 * 
 * Arquitetura:
 * - Utiliza padrão MVC (Model-View-Controller)
 * - Integração com DAOs específicos para cada tipo de veículo
 * - Validação de entrada de dados em todas as operações
 * - Tratamento de erros e casos especiais
 */
public class EstoqueView {

    /**
     * Menu principal do sistema de estoque.
     * Controla o fluxo principal de navegação entre as funcionalidades de estoque.
     * 
     * Funcionalidades disponíveis:
     * 1. Adicionar Veículo - Cadastro de novos veículos no estoque
     * 2. Remover Veículo - Exclusão de veículos do estoque
     * 3. Atualizar Veículo - Modificação de dados de veículos existentes
     * 4. Listar Veículos - Visualização de todos os veículos por categoria
     * 5. Buscar Veículo - Sistema avançado de busca com múltiplos filtros
     * 0. Sair - Retorna ao menu anterior
     * 
     * @param banco Instância do banco de dados para operações de persistência
     */
    public static void menuEstoque(Banco banco) {
        int opcao;
        do {
            TelaMenuEstoque();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    menuAdicionarVeiculo(banco);
                    break;
                case 2:
                    menuRemoverVeiculo(banco);
                    break;
                case 3:
                    menuAtualizarVeiculo(banco);
                    break;
                case 4:
                    menuListarVeiculos(banco);
                    break;
                case 5:
                    menuBuscarVeiculo(banco);
                    break;
                case 0:
                    System.out.println("Saindo do menu de estoque.");
                    Funcoes.pressEnterToContinue();
                    return;
                default:
                    System.out.println("Opção inválida.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Menu para adição de novos veículos ao estoque.
     * Permite o cadastro de veículos por categoria específica.
     * 
     * Categorias disponíveis:
     * 1. Carros - Veículos de passeio com características específicas
     * 2. Motocicletas - Veículos de duas rodas com atributos únicos
     * 3. Caminhões - Veículos pesados para transporte de carga
     * 
     * Cada categoria utiliza seu respectivo DAO para persistência no banco.
     * Valida a entrada de dados e fornece feedback ao usuário.
     * 
     * @param banco Instância do banco de dados para operações de inserção
     */
    public static void menuAdicionarVeiculo(Banco banco) {
        int opcao;
        do {
            TelaMenuAdicionarVeiculo();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    CarroDao carroDao = new CarroDao();
                    carroDao.adicionarCarro(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 2:
                    MotocicletaDao motocicletaDao = new MotocicletaDao();
                    motocicletaDao.adicionarMotocicleta(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 3:
                    CaminhaoDao caminhaoDao = new CaminhaoDao();
                    caminhaoDao.adicionarCaminhao(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Menu para remoção de veículos do estoque.
     * Permite a exclusão segura de veículos por categoria.
     * 
     * Funcionalidades:
     * - Seleção do tipo de veículo a ser removido
     * - Verificação de existência antes da remoção
     * - Confirmação de exclusão para evitar perdas acidentais
     * - Feedback ao usuário sobre o resultado da operação
     * 
     * Categorias disponíveis:
     * 1. Carros - Remove veículos da categoria carro
     * 2. Motocicletas - Remove veículos da categoria motocicleta
     * 3. Caminhões - Remove veículos da categoria caminhão
     * 
     * @param banco Instância do banco de dados para operações de exclusão
     */
    public static void menuRemoverVeiculo(Banco banco) {
        int opcao;
        do {
            TelaMenuRemoverVeiculo();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    CarroDao carroDao = new CarroDao();
                    carroDao.removerCarro(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 2:
                    MotocicletaDao motocicletaDao = new MotocicletaDao();
                    motocicletaDao.removerMotocicleta(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 3:
                    CaminhaoDao caminhaoDao = new CaminhaoDao();
                    caminhaoDao.removerCaminhao(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }


    /**
     * Menu para atualização de dados de veículos existentes.
     * Permite a modificação de informações de veículos já cadastrados.
     * 
     * Funcionalidades:
     * - Seleção do tipo de veículo a ser atualizado
     * - Busca do veículo específico por ID
     * - Edição de campos modificáveis
     * - Validação de novos dados inseridos
     * - Persistência das alterações no banco de dados
     * 
     * Campos típicos editáveis:
     * - Preço de venda
     * - Quilometragem
     * - Status do veículo
     * - Descrição e observações
     * 
     * @param banco Instância do banco de dados para operações de atualização
     */
    public static void menuAtualizarVeiculo(Banco banco) {
        int opcao;
        do {
            TelaMenuAtualizarVeiculo();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    CarroDao carroDao = new CarroDao();
                    carroDao.atualizarCarro(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 2:
                    MotocicletaDao motocicletaDao = new MotocicletaDao();
                    motocicletaDao.atualizarMotocicleta(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 3:
                    CaminhaoDao caminhaoDao = new CaminhaoDao();
                    caminhaoDao.atualizarCaminhao(banco);
                    Funcoes.pressEnterToContinue();
                    break;
                case 0:
                    System.out.println("Saindo do menu de atualização de veículos.");
                    Funcoes.pressEnterToContinue();
                    return;
                default:
                    System.out.println("Opção inválida.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Menu para listagem de veículos por categoria.
     * Exibe todos os veículos cadastrados organizados por tipo.
     * 
     * Funcionalidades:
     * - Listagem completa por categoria de veículo
     * - Formatação organizada das informações
     * - Verificação de existência de veículos
     * - Separação visual entre registros
     * 
     * Informações exibidas:
     * - Dados básicos do veículo (ID, modelo, marca)
     * - Características técnicas específicas
     * - Informações comerciais (preço, status)
     * - Detalhes únicos por categoria
     * 
     * Categorias disponíveis:
     * 1. Carros - Lista todos os carros do estoque
     * 2. Motocicletas - Lista todas as motocicletas
     * 3. Caminhões - Lista todos os caminhões
     * 
     * @param banco Instância do banco de dados para consulta de veículos
     */
    public static void menuListarVeiculos(Banco banco) {
        List<Carro> carros;
        List<Motocicleta> motocicletas;
        List<Caminhao> caminhoes;
        int opcao;
        do {
            TelaMenuListarVeiculos();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    CarroDao carroDao = new CarroDao();
                    carros = carroDao.listarCarros(banco);
                    if (carros.isEmpty()) {
                        System.out.println("Nenhum carro encontrado.");
                    } else {
                        System.out.println("Carros disponíveis:");
                        for (Carro c : carros) {
                            System.out.println(c);
                            System.out.println("\n---------------------------------\n");
                        }
                    }
                    Funcoes.pressEnterToContinue();
                    break;
                case 2:
                    MotocicletaDao motocicletaDao = new MotocicletaDao();
                    motocicletas = motocicletaDao.listarMotocicletas(banco);
                    if (motocicletas.isEmpty()) {
                        System.out.println("Nenhuma motocicleta encontrada.");
                    } else {
                        System.out.println("Motocicletas disponíveis:");
                        for (Motocicleta m : motocicletas) {
                            System.out.println(m);
                            System.out.println("\n---------------------------------\n");
                        }
                    }
                    Funcoes.pressEnterToContinue();
                    break;
                case 3:
                    CaminhaoDao caminhaoDao = new CaminhaoDao();
                    caminhoes = caminhaoDao.listarCaminhoes(banco);
                    if (caminhoes.isEmpty()) {
                        System.out.println("Nenhum caminhão encontrado.");
                    } else {
                        System.out.println("Caminhões disponíveis:");
                        for (Caminhao c : caminhoes) {
                            System.out.println(c);
                            System.out.println("\n---------------------------------\n");
                        }
                    }
                    Funcoes.pressEnterToContinue();
                    break;
                case 0:
                    System.out.println("Saindo do menu de listagem de veículos.");
                    Funcoes.pressEnterToContinue();
                    return;
                default:
                    System.out.println("Opção inválida.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Sistema avançado de busca de veículos com múltiplos critérios.
     * Oferece opções especializadas de pesquisa para cada categoria de veículo.
     * 
     * SISTEMA DE BUSCA POR CATEGORIA:
     * 
     * === CARROS (10 critérios de busca) ===
     * 1. ID - Busca direta por identificador único
     * 2. Modelo - Busca por nome/modelo do veículo
     * 3. Chassi - Busca por número de chassi
     * 4. Faixa de Preço - Filtro por valor mínimo e máximo
     * 5. Quilometragem - Filtro por quilometragem máxima
     * 6. Faixa de Ano - Filtro por período de fabricação
     * 7. Cor - Busca por cor específica
     * 8. Potência - Filtro por potência máxima (cavalos)
     * 9. Número de Portas - Filtro por configuração de portas
     * 10. Tipo de Combustível - Filtro por combustível (Gasolina/Etanol/Diesel/Elétrico)
     * 
     * === MOTOCICLETAS (8 critérios de busca) ===
     * 1. ID - Busca direta por identificador único
     * 2. Modelo - Busca por nome/modelo do veículo
     * 3. Chassi - Busca por número de chassi
     * 4. Faixa de Preço - Filtro por valor mínimo e máximo
     * 5. Quilometragem - Filtro por quilometragem máxima
     * 6. Faixa de Ano - Filtro por período de fabricação
     * 7. Cor - Busca por cor específica
     * 8. Cilindrada - Filtro por cilindrada máxima (cm³)
     * 
     * === CAMINHÕES (11 critérios de busca) ===
     * 1. ID - Busca direta por identificador único
     * 2. Modelo - Busca por nome/modelo do veículo
     * 3. Chassi - Busca por número de chassi
     * 4. Faixa de Preço - Filtro por valor mínimo e máximo
     * 5. Quilometragem - Filtro por quilometragem máxima
     * 6. Faixa de Ano - Filtro por período de fabricação
     * 7. Cor - Busca por cor específica
     * 8. Número de Eixos - Filtro por configuração de eixos
     * 9. Capacidade de Carga - Filtro por capacidade máxima (toneladas)
     * 10. Altura - Filtro por altura máxima (metros)
     * 11. Tipo de Carroceria - Filtro por tipo (Baú/Sider/Graneleiro/Frigorífico)
     * 
     * VALIDAÇÕES IMPLEMENTADAS:
     * - Verificação de IDs válidos (> 0)
     * - Validação de strings não vazias
     * - Verificação de faixas de valores lógicas
     * - Validação de anos (mínimo 1886 - primeiro automóvel)
     * - Verificação de valores numéricos positivos
     * 
     * TRATAMENTO DE ERROS:
     * - Mensagens informativas para buscas sem resultados
     * - Validação de entrada de dados
     * - Retorno seguro em caso de erro
     * - Feedback ao usuário em todas as operações
     * 
     * @param banco Instância do banco de dados para operações de consulta
     */
    public static void menuBuscarVeiculo(Banco banco) {
        int opcao;
        int subOpcao;
        int id;
        String modelo;
        String chassi;
        double precoMinimo;
        double precoMaximo;
        double quilometragemMaxima;
        int anoMinimo, anoMaximo;
        String cor;
        do {
            TelaMenuBuscarVeiculo();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    CarroDao carroDao = new CarroDao();
                    List<Carro> carros = new ArrayList<>();
                    Carro carro = null;
                    do {
                        TelaMenuBuscasCarros();
                        subOpcao = Funcoes.lerInt();
                        switch (subOpcao) {
                            case 1:
                                System.out.println("Digite o ID do carro:");
                                id = Funcoes.lerInt();
                                if (id <= 0) {
                                    System.out.println("ID inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carro = carroDao.buscarCarroPorId(banco, id);
                                if (carro == null) {
                                    System.out.println("Carro não encontrado com o ID fornecido.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carro encontrado: " + carro);
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 2:
                                System.out.println("Digite o modelo do carro:");
                                modelo = Funcoes.lerString();
                                if (modelo == null || modelo.isEmpty()) {
                                    System.out.println("Modelo inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorModelo(banco, modelo);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com o modelo especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 3:
                                System.out.println("Digite o número do chassi do carro:");
                                chassi = Funcoes.lerString();
                                if (chassi == null || chassi.isEmpty()) {
                                    System.out.println("Número de chassi inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carro = carroDao.buscarCarroPorChassi(banco, chassi);
                                if (carro == null) {
                                    System.out.println("Carro não encontrado com o chassi fornecido.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carro encontrado: " + carro);
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 4:
                                System.out.println("Digite o preço mínimo:");
                                precoMinimo = Funcoes.lerDouble();
                                System.out.println("Digite o preço máximo:");
                                precoMaximo = Funcoes.lerDouble();
                                if (precoMinimo < 0 || precoMaximo < 0 || precoMinimo > precoMaximo) {
                                    System.out.println("Faixa de preço inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorFaixaPreco(banco, precoMinimo, precoMaximo);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado na faixa de preço especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados na faixa de preço:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 5:
                                System.out.println("Digite a quilometragem máxima:");
                                quilometragemMaxima = Funcoes.lerDouble();
                                if (quilometragemMaxima < 0) {
                                    System.out.println("Quilometragem inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorQuilometragem(banco, quilometragemMaxima);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com a quilometragem especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados com quilometragem máxima:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 6:
                                System.out.println("Digite o ano de fabricação mínimo:");
                                anoMinimo = Funcoes.lerInt();
                                System.out.println("Digite o ano de fabricação máximo:");
                                anoMaximo = Funcoes.lerInt();
                                if (anoMinimo < 1886 || anoMaximo < 1886 || anoMinimo > anoMaximo) {
                                    System.out.println("Faixa de ano de fabricação inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorFaixaAno(banco, anoMinimo, anoMaximo);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado na faixa de ano de fabricação especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados na faixa de ano de fabricação:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 7:
                                System.out.println("Digite a cor do carro:");
                                cor = Funcoes.lerString();
                                if (cor == null || cor.isEmpty()) {
                                    System.out.println("Cor inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorCor(banco, cor);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com a cor especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados com a cor especificada:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 8:
                                System.out.println("Digite a potência máxima (em cavalos):");
                                double potenciaMaxima = Funcoes.lerDouble();
                                if (potenciaMaxima < 0) {
                                    System.out.println("Potência inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorPotencia(banco, potenciaMaxima);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com a potência especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados com potência máxima:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 9:
                                System.out.println("Digite o número de portas:");
                                int numeroPortas = Funcoes.lerInt();
                                if (numeroPortas <= 0) {
                                    System.out.println("Número de portas inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorNumeroPortas(banco, numeroPortas);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com o número de portas especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados com o número de portas especificado:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 10:
                                System.out.println("Digite o tipo de combustível (1 - Gasolina, 2 - Etanol, 3 - Diesel, 4 - Elétrico):");
                                int tipoCombustivel = Funcoes.lerInt();
                                if (tipoCombustivel < 1 || tipoCombustivel > 4) {
                                    System.out.println("Tipo de combustível inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorCombustivel(banco, tipoCombustivel);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com o tipo de combustível especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Carros encontrados com o tipo de combustível especificado:");
                                    for (Carro c : carros) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 0:
                                System.out.println("Saindo do menu de busca de carros.");
                                Funcoes.pressEnterToContinue();
                                return;
                            default:
                                System.out.println("Opção inválida.");
                                Funcoes.pressEnterToContinue();
                        }
                    } while (subOpcao != 0);
                    break;
                case 2:
                    MotocicletaDao motocicletaDao = new MotocicletaDao();
                    List<Motocicleta> motocicletas = new ArrayList<>();
                    Motocicleta motocicleta = null;
                    do {
                        TelaMenuBuscarMotocicleta();
                        subOpcao = Funcoes.lerInt();
                        switch (subOpcao) {
                            case 1:
                                System.out.println("Digite o ID da motocicleta:");
                                id = Funcoes.lerInt();
                                if (id <= 0) {
                                    System.out.println("ID inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicleta = motocicletaDao.buscarMotocicletaPorId(banco, id);
                                if (motocicleta == null) {
                                    System.out.println("Motocicleta não encontrada com o ID fornecido.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicleta encontrada: " + motocicleta);
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 2:
                                System.out.println("Digite o modelo da motocicleta:");
                                modelo = Funcoes.lerString();
                                if (modelo == null || modelo.isEmpty()) {
                                    System.out.println("Modelo inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue(); 
                                    return;
                                }
                                motocicletas = motocicletaDao.buscarMotocicletasPorModelo(banco, modelo);
                                if (motocicletas.isEmpty()) {
                                    System.out.println("Nenhuma motocicleta encontrada com o modelo especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicletas encontradas:");
                                    for (Motocicleta m : motocicletas) {
                                        System.out.println(m);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 3:
                                System.out.println("Digite o número do chassi da motocicleta:");
                                chassi = Funcoes.lerString();
                                if (chassi == null || chassi.isEmpty()) {
                                    System.out.println("Número de chassi inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicleta = motocicletaDao.buscarMotocicletaPorChassi(banco, chassi);
                                if (motocicleta == null) {
                                    System.out.println("Motocicleta não encontrada com o chassi fornecido.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicleta encontrada: " + motocicleta);
                                }
                                Funcoes.pressEnterToContinue();
                                break;
                            case 4:
                                System.out.println("Digite o preço mínimo:");
                                precoMinimo = Funcoes.lerDouble();
                                System.out.println("Digite o preço máximo:");
                                precoMaximo = Funcoes.lerDouble();
                                if (precoMinimo < 0 || precoMaximo < 0 || precoMinimo > precoMaximo) {
                                    System.out.println("Faixa de preço inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicletas = motocicletaDao.buscarMotocicletasPorFaixaPreco(banco, precoMinimo, precoMaximo);
                                if (motocicletas.isEmpty()) {
                                    System.out.println("Nenhuma motocicleta encontrada na faixa de preço especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicletas encontradas na faixa de preço:");
                                    for (Motocicleta m : motocicletas) {
                                        System.out.println(m);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 5:
                                System.out.println("Digite a quilometragem máxima:");
                                quilometragemMaxima = Funcoes.lerDouble();
                                if (quilometragemMaxima < 0) {
                                    System.out.println("Quilometragem inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicletas = motocicletaDao.buscarMotocicletasPorQuilometragem(banco, quilometragemMaxima);
                                if (motocicletas.isEmpty()) {
                                    System.out.println("Nenhuma motocicleta encontrada com a quilometragem especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicletas encontradas com quilometragem máxima:");
                                    for (Motocicleta m : motocicletas) {
                                        System.out.println(m);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 6:
                                System.out.println("Digite o ano de fabricação mínimo:");
                                anoMinimo = Funcoes.lerInt();
                                System.out.println("Digite o ano de fabricação máximo:");
                                anoMaximo = Funcoes.lerInt();
                                if (anoMinimo < 1886 || anoMaximo < 1886 || anoMinimo > anoMaximo) {
                                    System.out.println("Faixa de ano de fabricação inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicletas = motocicletaDao.buscarMotocicletasPorFaixaAno(banco, anoMinimo, anoMaximo);
                                if (motocicletas.isEmpty()) {
                                    System.out.println("Nenhuma motocicleta encontrada na faixa de ano de fabricação especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicletas encontradas na faixa de ano de fabricação:");
                                    for (Motocicleta m : motocicletas) {
                                        System.out.println(m);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 7:
                                System.out.println("Digite a cor da motocicleta:");
                                cor = Funcoes.lerString();
                                if (cor == null || cor.isEmpty()) {
                                    System.out.println("Cor inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicletas = motocicletaDao.buscarMotocicletasPorCor(banco, cor);
                                if (motocicletas.isEmpty()) {
                                    System.out.println("Nenhuma motocicleta encontrada com a cor especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicletas encontradas com a cor especificada:");
                                    for (Motocicleta m : motocicletas) {
                                        System.out.println(m);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 8:
                                System.out.println("Digite a cilindrada máxima (em cm³):");
                                int cilindradaMaxima = Funcoes.lerInt();
                                if (cilindradaMaxima < 0) {
                                    System.out.println("Cilindrada inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                motocicletas = motocicletaDao.buscarMotocicletasPorCilindrada(banco, cilindradaMaxima);
                                if (motocicletas.isEmpty()) {
                                    System.out.println("Nenhuma motocicleta encontrada com a cilindrada especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Motocicletas encontradas com cilindrada máxima:");
                                    for (Motocicleta m : motocicletas) {
                                        System.out.println(m);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 0:
                                System.out.println("Saindo do menu de busca de motocicletas.");
                                Funcoes.pressEnterToContinue();
                                return;
                            default:
                                System.out.println("Opção inválida.");
                                Funcoes.pressEnterToContinue();
                        }
                    } while (subOpcao != 0);
                        
                    break;
                case 3:
                    CaminhaoDao caminhaoDao = new CaminhaoDao();
                    List<Caminhao> caminhoes = new ArrayList<>();
                    Caminhao caminhao = null;
                    do {
                        TelaMenuBuscarCaminhao();
                        subOpcao = Funcoes.lerInt();
                        switch (subOpcao) {
                            case 1:
                                System.out.println("Digite o ID do caminhão:");
                                id = Funcoes.lerInt();
                                if (id <= 0) {
                                    System.out.println("ID inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue(); 
                                    return;
                                }
                                caminhao = caminhaoDao.buscarCaminhaoPorId(banco, id);
                                if (caminhao == null) {
                                    System.out.println("Caminhão não encontrado com o ID fornecido.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhão encontrado: " + caminhao);
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 2:
                                System.out.println("Digite o modelo do caminhão:");
                                modelo = Funcoes.lerString();
                                if (modelo == null || modelo.isEmpty()) {
                                    System.out.println("Modelo inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorModelo(banco, modelo);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com o modelo especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 3:
                                System.out.println("Digite o número do chassi do caminhão:");
                                chassi = Funcoes.lerString();
                                if (chassi == null || chassi.isEmpty()) {
                                    System.out.println("Número de chassi inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhao = caminhaoDao.buscarCaminhaoPorChassi(banco, chassi);
                                if (caminhao == null) {
                                    System.out.println("Caminhão não encontrado com o chassi fornecido.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhão encontrado: " + caminhao);
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 4:
                                System.out.println("Digite o preço mínimo:");
                                precoMinimo = Funcoes.lerDouble();
                                System.out.println("Digite o preço máximo:");
                                precoMaximo = Funcoes.lerDouble();
                                if (precoMinimo < 0 || precoMaximo < 0 || precoMinimo > precoMaximo) {
                                    System.out.println("Faixa de preço inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorFaixaPreco(banco, precoMinimo, precoMaximo);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado na faixa de preço especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados na faixa de preço:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 5:
                                System.out.println("Digite a quilometragem máxima:");
                                quilometragemMaxima = Funcoes.lerDouble();
                                if (quilometragemMaxima < 0) {
                                    System.out.println("Quilometragem inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue(); 
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorQuilometragem(banco, quilometragemMaxima);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com a quilometragem especificada.");
                                    Funcoes.pressEnterToContinue(); 
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados com quilometragem máxima:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 6:
                                System.out.println("Digite o ano de fabricação mínimo:");
                                anoMinimo = Funcoes.lerInt();
                                System.out.println("Digite o ano de fabricação máximo:");
                                anoMaximo = Funcoes.lerInt();
                                if (anoMinimo < 1886 || anoMaximo < 1886 || anoMinimo > anoMaximo) {
                                    System.out.println("Faixa de ano de fabricação inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorFaixaAno(banco, anoMinimo, anoMaximo);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado na faixa de ano de fabricação especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados na faixa de ano de fabricação:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 7:
                                System.out.println("Digite a cor do caminhão:");
                                cor = Funcoes.lerString();
                                if (cor == null || cor.isEmpty()) {
                                    System.out.println("Cor inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorCor(banco, cor);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com a cor especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados com a cor especificada:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 8:
                                System.out.println("Digite o número de eixos:");
                                int numeroEixos = Funcoes.lerInt();
                                if (numeroEixos <= 0) {
                                    System.out.println("Número de eixos inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorEixo(banco, numeroEixos);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com o número de eixos especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados com o número de eixos especificado:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 9:
                                System.out.println("Digite a capacidade de carga máxima (em toneladas):");
                                double capacidadeCargaMaxima = Funcoes.lerDouble();
                                if (capacidadeCargaMaxima < 0) {
                                    System.out.println("Capacidade de carga inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorCapacidadeCarga(banco, capacidadeCargaMaxima);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com a capacidade de carga especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados com capacidade de carga máxima:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 10:
                                System.out.println("Digite a altura máxima (em metros):");
                                double alturaMaxima = Funcoes.lerDouble();
                                if (alturaMaxima < 0) {
                                    System.out.println("Altura inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorAltura(banco, alturaMaxima);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com a altura especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados com altura máxima:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 11:
                                System.out.println("Digite o tipo de carroceria (1 - Baú, 2 - Sider, 3 - Graneleiro, 4 - Frigorífico):");
                                String tipoCarroceria = Funcoes.lerString();
                                if (tipoCarroceria == null || tipoCarroceria.isEmpty()) {
                                    System.out.println("Tipo de carroceria inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorTipoCarroceria(banco, tipoCarroceria);
                                if (caminhoes.isEmpty()) {
                                    System.out.println("Nenhum caminhão encontrado com o tipo de carroceria especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    System.out.println("Caminhões encontrados com o tipo de carroceria especificado:");
                                    for (Caminhao c : caminhoes) {
                                        System.out.println(c);
                                        System.out.println("\n---------------------------------\n");
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 0:
                                System.out.println("Saindo do menu de busca de caminhões.");
                                Funcoes.pressEnterToContinue();
                                return;
                            default:
                                System.out.println("Opção inválida.");
                                Funcoes.pressEnterToContinue();
                        }
                    } while (subOpcao != 0);
                    break;
                case 0:
                    System.out.println("Saindo do menu de busca de veículos.");
                    Funcoes.pressEnterToContinue();
                    return;
                default:
                    System.out.println("Opção inválida.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe a tela principal do menu de estoque.
     * Interface padronizada com cabeçalho e opções numeradas.
     */
    public static void TelaMenuEstoque() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Adicionar Veículo");
        System.out.println("2. Remover Veículo");
        System.out.println("3. Atualizar Veículo");
        System.out.println("4. Listar Veículos");
        System.out.println("5. Buscar Veículo");
        System.out.println("0. Sair");
    }

    /**
     * Exibe a tela do menu de adição de veículos.
     * Interface para seleção do tipo de veículo a ser cadastrado.
     */
    public static void TelaMenuAdicionarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ADICIONAR VEÍCULO");
        System.out.println("1. Adicionar Carro");
        System.out.println("2. Adicionar Moto");
        System.out.println("3. Adicionar Caminhão");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela do menu de atualização de veículos.
     * Interface para seleção do tipo de veículo a ser atualizado.
     */
    public static void TelaMenuAtualizarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR VEÍCULO");
        System.out.println("1. Atualizar Carro");
        System.out.println("2. Atualizar Moto");
        System.out.println("3. Atualizar Caminhão");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela do menu de remoção de veículos.
     * Interface para seleção do tipo de veículo a ser removido.
     */
    public static void TelaMenuRemoverVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("REMOVER VEÍCULOS");
        System.out.println("1. Remover Carros");
        System.out.println("2. Remover Motos");
        System.out.println("3. Remover Caminhão");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela do menu de listagem de veículos.
     * Interface para seleção do tipo de veículo a ser listado.
     */
    public static void TelaMenuListarVeiculos() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("LISTAR VEÍCULOS");
        System.out.println("1. Listar Carros");
        System.out.println("2. Listar Motos");
        System.out.println("3. Listar Caminhões");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela do menu principal de busca de veículos.
     * Interface para seleção da categoria de veículo a ser pesquisada.
     */
    public static void TelaMenuBuscarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR VEÍCULOS");
        System.out.println("1. Buscar Carros");
        System.out.println("2. Buscar Motos");
        System.out.println("3. Buscar Caminhão");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela especializada para busca de carros.
     * Interface com 10 critérios diferentes de busca para veículos de passeio.
     * 
     * Critérios disponíveis:
     * 1-3: Identificação (ID, modelo, chassi)
     * 4-6: Características físicas (preço, quilometragem, ano)
     * 7-10: Especificações técnicas (cor, potência, portas, combustível)
     */
    public static void TelaMenuBuscasCarros() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CARROS");
        System.out.println("1. Buscar Carro por Id");
        System.out.println("2. Buscar Carro por Modelo");
        System.out.println("3. Buscar Carro por Numero de chassi");
        System.out.println("4. Buscar Carro por Faixa de Preço");
        System.out.println("5. Buscar Carro por Quilometragem Máxima");
        System.out.println("6. Buscar Carro por Faixa de Ano de Fabricação");
        System.out.println("7. Buscar Carro por Cor");
        System.out.println("8. Buscar Carro por Potencia");
        System.out.println("9. Buscar Carro por Numero de Portas");
        System.out.println("10. Buscar Carro por Tipo de Combustível");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela especializada para busca de motocicletas.
     * Interface com 8 critérios específicos para veículos de duas rodas.
     * 
     * Critérios disponíveis:
     * 1-3: Identificação (ID, modelo, chassi)
     * 4-6: Características físicas (preço, quilometragem, ano)
     * 7-8: Especificações únicas (cor, cilindrada)
     */
    public static void TelaMenuBuscarMotocicleta() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR MOTOCICLETA");
        System.out.println("1. Buscar Moto por Id");
        System.out.println("2. Buscar Moto por Modelo");
        System.out.println("3. Buscar Moto por Número de Chassi");
        System.out.println("4. Buscar Moto por Faixa de Preço");
        System.out.println("5. Buscar Moto por Quilometragem Máxima");
        System.out.println("6. Buscar Moto por Faixa de Ano de Fabricação");
        System.out.println("7. Buscar Moto por Cor");
        System.out.println("8. Buscar Moto por Cilindrada Máxima");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe a tela especializada para busca de caminhões.
     * Interface com 11 critérios especializados para veículos pesados.
     * 
     * Critérios disponíveis:
     * 1-3: Identificação (ID, modelo, chassi)
     * 4-6: Características físicas (preço, quilometragem, ano)
     * 7-11: Especificações técnicas (cor, eixos, carga, altura, carroceria)
     */
    public static void TelaMenuBuscarCaminhao() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CAMINHÃO");
        System.out.println("1. Buscar Caminhão por Id");
        System.out.println("2. Buscar Caminhão por Modelo");
        System.out.println("3. Buscar Caminhão por Número de Chassi");
        System.out.println("4. Buscar Caminhão por Faixa de Preço");
        System.out.println("5. Buscar Caminhão por Quilometragem Máxima");
        System.out.println("6. Buscar Caminhão por Faixa de Ano de Fabricação");
        System.out.println("7. Buscar Caminhão por Cor");
        System.out.println("8. Buscar Caminhão por Número de Eixos");
        System.out.println("9. Buscar Caminhão por Capacidade de Carga");
        System.out.println("10. Buscar Caminhão por Altura");
        System.out.println("11. Buscar Caminhão por Tipo de Carroceria");
        System.out.println("0. Voltar");
    }
}
