package views;

import utils.Funcoes;

import java.util.ArrayList;
import java.util.List;

import controllers.CaminhaoDao;
import controllers.CarroDao;
import controllers.MotocicletaDao;
import controllers.VendaDao;
import data.Banco;
import models.Caminhao;
import models.Carro;
import models.Motocicleta;
import models.Venda;

/**
 * Classe responsável pela interface do vendedor no sistema da concessionária.
 * Gerencia todas as funcionalidades disponíveis para vendedores, incluindo:
 * - Busca e seleção de veículos para venda
 * - Gerenciamento do carrinho de compras
 * - Finalização de vendas
 * - Consulta de estoque disponível
 * 
 * Esta classe implementa um sistema completo de vendas com carrinho de compras,
 * permitindo ao vendedor buscar veículos por diversos critérios e adicionar
 * múltiplos itens antes de finalizar a venda.
 */
public class VendedorView {
    
    /**
     * Menu principal do vendedor.
     * Gerencia o fluxo principal das operações de venda, incluindo:
     * - Controle de vendas abertas/criação de novas vendas
     * - Acesso aos submenus de funcionalidades
     * - Finalização de vendas
     * 
     * @param banco Objeto Banco para operações de banco de dados
     * @param idFuncionario ID do funcionário vendedor logado
     */
    public static void menuVendedor(Banco banco, int idFuncionario) {
        int idVendaAberta;
        VendaDao vendaDao = new VendaDao();
        idVendaAberta = vendaDao.buscarVendaAbertaInt(banco, idFuncionario);
        if (idVendaAberta == -1) {
            System.out.println("Nenhuma venda aberta encontrada. Iniciando uma nova venda...");
            Funcoes.pressEnterToContinue();
            Venda venda = new Venda(0.0, idFuncionario, 1, null); // 1 - Aberta
            vendaDao.criarVenda(banco, venda);
            // Buscar novamente o ID da venda recém-criada
            idVendaAberta = vendaDao.buscarVendaAbertaInt(banco, idFuncionario);
            System.out.println("Nova venda criada com ID: " + idVendaAberta);
            Funcoes.pressEnterToContinue();
        } else {
            System.out.println("Venda aberta encontrada com ID: " + idVendaAberta);
            Funcoes.pressEnterToContinue();
        }
        
        int opcao;
        do {
            telaMenuVendedor();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    menuRealizarVenda(banco, idVendaAberta);
                    break;
                case 2:
                    menuListarVeiculosAVenda(banco);
                    break;
                case 3:
                    VendaDao.listarCarrinho(banco, idVendaAberta);
                    break;
                case 4:
                    VendaDao vendaDaoFinalizar = new VendaDao();
                    Venda venda = vendaDaoFinalizar.buscarVendaPorId(banco, idVendaAberta);
                    if (venda != null) {
                        vendaDaoFinalizar.finalizarVenda(banco, idVendaAberta);
                        idVendaAberta = vendaDao.buscarVendaAbertaInt(banco, idFuncionario);
                        if (idVendaAberta == -1) {
                            System.out.println("Criando uma nova venda...");
                            Venda novaVenda = new Venda(0.0, idFuncionario, 1, null); // 1 - Aberta
                            vendaDao.criarVenda(banco, novaVenda);
                            idVendaAberta = vendaDao.buscarVendaAbertaInt(banco, idFuncionario);
                            System.out.println("Nova venda criada com ID: " + idVendaAberta);
                        }
                    } else {
                        System.out.println("Nenhuma venda encontrada para finalizar.");
                    }
                    Funcoes.pressEnterToContinue();
                    break;
                case 0:
                    System.out.println("Saindo do menu vendedor...");
                    Funcoes.pressEnterToContinue();
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Menu para realizar vendas com busca de veículos.
     * Permite ao vendedor buscar veículos por categoria (Carro, Motocicleta, Caminhão)
     * e adicionar os veículos encontrados ao carrinho de compras.
     * 
     * Funcionalidades disponíveis:
     * - Busca de carros com múltiplos critérios
     * - Busca de motocicletas com múltiplos critérios
     * - Busca de caminhões com múltiplos critérios
     * - Adição de veículos ao carrinho
     * 
     * @param banco Objeto Banco para operações de banco de dados
     * @param idVendaAberta ID da venda aberta atual
     */
    public static void menuRealizarVenda(Banco banco, int idVendaAberta) {
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
            telaMenuRealizarVenda();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    /*
                     * SEÇÃO DE BUSCA DE CARROS
                     * 
                     * Esta seção implementa um sistema completo de busca de carros
                     * com 10 critérios diferentes:
                     * 1. Busca por ID (busca específica)
                     * 2. Busca por Modelo 
                     * 3. Busca por Número do Chassi
                     * 4. Busca por Faixa de Preço
                     * 5. Busca por Quilometragem Máxima
                     * 6. Busca por Faixa de Ano de Fabricação
                     * 7. Busca por Cor
                     * 8. Busca por Potência Máxima
                     * 9. Busca por Número de Portas
                     * 10. Busca por Tipo de Combustível
                     * 
                     * Para cada busca:
                     * - Valida entrada do usuário
                     * - Verifica se o veículo está disponível (status = 1)
                     * - Permite adicionar ao carrinho se disponível
                     * - Trata casos de múltiplos resultados
                     */
                    CarroDao carroDao = new CarroDao();
                    List<Carro> carros = new ArrayList<>();
                    Carro carro = null;
                    do {
                        telaMenuBuscarCarro();
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
                                    if (carro.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Carro encontrado: " + carro);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                System.out.println("Carro adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Carro não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out.println("Carro encontrado, mas não está disponível para venda");
                                    }
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
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
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
                                    if (carro.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Carro encontrado: " + carro);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                System.out.println("Carro adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Carro não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out.println("Carro encontrado, mas não está disponível para venda");
                                    }
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
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
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
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
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
                                    System.out.println(
                                            "Nenhum carro encontrado na faixa de ano de fabricação especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
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
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 8:
                                System.out.println("Digite a potência máxima (em cavalos):");
                                int potenciaMinima = Funcoes.lerInt();
                                if (potenciaMinima < 0) {
                                    System.out.println("Potência inválida. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorPotencia(banco, potenciaMinima);
                                if (carros.isEmpty()) {
                                    System.out.println("Nenhum carro encontrado com a potência especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
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
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                                break;
                            case 10:
                                System.out.println(
                                        "Digite o tipo de combustível (1 - Gasolina, 2 - Etanol, 3 - Diesel, 4 - Elétrico):");
                                int tipoCombustivel = Funcoes.lerInt();
                                if (tipoCombustivel < 1 || tipoCombustivel > 4) {
                                    System.out.println("Tipo de combustível inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                carros = carroDao.buscarCarrosPorCombustivel(banco, tipoCombustivel);
                                if (carros.isEmpty()) {
                                    System.out
                                            .println("Nenhum carro encontrado com o tipo de combustível especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (carros.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Carro c : carros) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        carro = carros.get(0);
                                        if (carro.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Carro encontrado: " + carro);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, carro);
                                                    System.out.println("Carro adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Carro não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println("Carro encontrado, mas não está disponível para venda");
                                        }
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
                        telaMenuBuscarMoto();
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
                                    if (motocicleta.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Motocicleta encontrado: " + motocicleta);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                System.out.println("Motocicleta adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Motocicleta não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out
                                                .println("Motocicleta encontrada, mas não está disponível para venda");
                                    }
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
                                    if (motocicletas.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Motocicleta m : motocicletas) {
                                            if (m.getIdStatus() == 1) {
                                                System.out.println(m);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        motocicleta = motocicletas.get(0);
                                        if (motocicleta.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Motocicleta encontrado: " + motocicleta);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                    System.out
                                                            .println("Motocicleta adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Motocicleta não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println(
                                                    "Motocicleta encontrado, mas não está disponível para venda");
                                        }
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
                                    if (motocicleta.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Motocicleta encontrado: " + motocicleta);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                System.out.println("Motocicleta adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Motocicleta não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out
                                                .println("Motocicleta encontrada, mas não está disponível para venda");
                                    }
                                    Funcoes.pressEnterToContinue();
                                    if (motocicleta.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Motocicleta encontrado: " + motocicleta);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                System.out.println("Motocicleta adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Motocicleta não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out
                                                .println("Motocicleta encontrada, mas não está disponível para venda");
                                    }
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
                                motocicletas = motocicletaDao.buscarMotocicletasPorFaixaPreco(banco, precoMinimo,
                                        precoMaximo);
                                if (motocicletas.isEmpty()) {
                                    System.out
                                            .println("Nenhuma motocicleta encontrada na faixa de preço especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (motocicletas.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Motocicleta m : motocicletas) {
                                            if (m.getIdStatus() == 1) {
                                                System.out.println(m);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        motocicleta = motocicletas.get(0);
                                        if (motocicleta.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Motocicleta encontrado: " + motocicleta);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                    System.out
                                                            .println("Motocicleta adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Motocicleta não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println(
                                                    "Motocicleta encontrado, mas não está disponível para venda");
                                        }
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
                                motocicletas = motocicletaDao.buscarMotocicletasPorQuilometragem(banco,
                                        quilometragemMaxima);
                                if (motocicletas.isEmpty()) {
                                    System.out.println(
                                            "Nenhuma motocicleta encontrada com a quilometragem especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (motocicletas.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Motocicleta m : motocicletas) {
                                            if (m.getIdStatus() == 1) {
                                                System.out.println(m);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        motocicleta = motocicletas.get(0);
                                        if (motocicleta.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Motocicleta encontrado: " + motocicleta);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                    System.out
                                                            .println("Motocicleta adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Motocicleta não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println(
                                                    "Motocicleta encontrado, mas não está disponível para venda");
                                        }
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
                                motocicletas = motocicletaDao.buscarMotocicletasPorFaixaAno(banco, anoMinimo,
                                        anoMaximo);
                                if (motocicletas.isEmpty()) {
                                    System.out.println(
                                            "Nenhuma motocicleta encontrada na faixa de ano de fabricação especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (motocicletas.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Motocicleta m : motocicletas) {
                                            if (m.getIdStatus() == 1) {
                                                System.out.println(m);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        motocicleta = motocicletas.get(0);
                                        if (motocicleta.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Motocicleta encontrado: " + motocicleta);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                    System.out
                                                            .println("Motocicleta adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Motocicleta não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println(
                                                    "Motocicleta encontrado, mas não está disponível para venda");
                                        }
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
                                    if (motocicletas.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Motocicleta m : motocicletas) {
                                            if (m.getIdStatus() == 1) {
                                                System.out.println(m);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        motocicleta = motocicletas.get(0);
                                        if (motocicleta.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Motocicleta encontrado: " + motocicleta);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                    System.out
                                                            .println("Motocicleta adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Motocicleta não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println(
                                                    "Motocicleta encontrado, mas não está disponível para venda");
                                        }
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
                                    if (motocicletas.size() > 1) {
                                        System.out.println("Carros encontrados:");
                                        for (Motocicleta m : motocicletas) {
                                            if (m.getIdStatus() == 1) {
                                                System.out.println(m);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        motocicleta = motocicletas.get(0);
                                        if (motocicleta.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Motocicleta encontrado: " + motocicleta);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, motocicleta);
                                                    System.out
                                                            .println("Motocicleta adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Motocicleta não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out.println(
                                                    "Motocicleta encontrado, mas não está disponível para venda");
                                        }
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
                        telaMenuBuscarCaminhao();
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
                                    if (caminhao.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Caminhão encontrado: " + caminhao);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Caminhão não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out.println("Caminhão encontrado, mas não está disponível para venda");
                                    }
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
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    if (caminhao.getIdStatus() == 1) {
                                        do {
                                            Funcoes.limpaTela();
                                            System.out.println("Caminhão encontrado: " + caminhao);
                                            telaMenuAdicionarCarrinho();
                                            int escolha = Funcoes.lerInt();
                                            if (escolha == 1) {
                                                VendaDao vendaDao = new VendaDao();
                                                vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                            } else if (escolha == 2) {
                                                System.out.println("Caminhão não adicionado ao carrinho.");
                                            } else {
                                                System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcao != 1 && opcao != 2);
                                    } else {
                                        System.out.println("Caminhão encontrado, mas não está disponível para venda");
                                    }
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
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    System.out.println(
                                            "Nenhum caminhão encontrado na faixa de ano de fabricação especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    System.out
                                            .println("Nenhum caminhão encontrado com o número de eixos especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    System.out.println(
                                            "Nenhum caminhão encontrado com a capacidade de carga especificada.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
                                    }
                                    Funcoes.pressEnterToContinue();
                                }
                            case 11:
                                System.out.println(
                                        "Digite o tipo de carroceria (1 - Baú, 2 - Sider, 3 - Graneleiro, 4 - Frigorífico):");
                                String tipoCarroceria = Funcoes.lerString();
                                if (tipoCarroceria == null || tipoCarroceria.isEmpty()) {
                                    System.out.println("Tipo de carroceria inválido. Tente novamente.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                }
                                caminhoes = caminhaoDao.buscarCaminhoesPorTipoCarroceria(banco, tipoCarroceria);
                                if (caminhoes.isEmpty()) {
                                    System.out.println(
                                            "Nenhum caminhão encontrado com o tipo de carroceria especificado.");
                                    Funcoes.pressEnterToContinue();
                                    return;
                                } else {
                                    if (caminhoes.size() > 1) {
                                        System.out.println("Caminhões encontrados:");
                                        for (Caminhao c : caminhoes) {
                                            if (c.getIdStatus() == 1) {
                                                System.out.println(c);
                                                System.out.println("\n---------------------------------\n");
                                            }
                                        }
                                    } else {
                                        caminhao = caminhoes.get(0);
                                        if (caminhao.getIdStatus() == 1) {
                                            do {
                                                Funcoes.limpaTela();
                                                System.out.println("Caminhão encontrado: " + caminhao);
                                                telaMenuAdicionarCarrinho();
                                                int escolha = Funcoes.lerInt();
                                                if (escolha == 1) {
                                                    VendaDao vendaDao = new VendaDao();
                                                    vendaDao.adicionarNoCarrinho(banco, idVendaAberta, caminhao);
                                                    System.out.println("Caminhão adicionado ao carrinho com sucesso!");
                                                } else if (escolha == 2) {
                                                    System.out.println("Caminhão não adicionado ao carrinho.");
                                                } else {
                                                    System.out.println("Opção inválida. Tente novamente.");
                                                }
                                            } while (opcao != 1 && opcao != 2);
                                        } else {
                                            System.out
                                                    .println("Caminhão encontrado, mas não está disponível para venda");
                                        }
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
     * Menu para listar veículos disponíveis para venda.
     * Permite ao vendedor visualizar todos os veículos disponíveis no estoque,
     * organizados por categoria (Carros, Motocicletas, Caminhões).
     * 
     * Funcionalidades:
     * - Listagem de todos os carros disponíveis
     * - Listagem de todas as motocicletas disponíveis
     * - Listagem de todos os caminhões disponíveis
     * - Filtro automático por status (apenas veículos disponíveis)
     * 
     * @param banco Objeto Banco para operações de banco de dados
     */
    public static void menuListarVeiculosAVenda(Banco banco) {
        List<Carro> carros;
        List<Motocicleta> motocicletas;
        List<Caminhao> caminhoes;
        int opcao;
        do {
            telaMenuListarVeiculosAVenda();
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
                            if (c.getIdStatus() == 1) { // Verifica se o carro está disponível para venda
                                System.out.println(c);
                                System.out.println("\n---------------------------------\n");
                            }
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
                            if (m.getIdStatus() == 1) { // Verifica se a moto está disponível para venda
                                System.out.println(m);
                                System.out.println("\n---------------------------------\n");
                            }
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
                            if (c.getIdStatus() == 1) { // Verifica se o caminhão está disponível para venda
                                System.out.println(c);
                                System.out.println("\n---------------------------------\n");
                            }
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

    public static void menuFinalizarVenda(Banco banco, int idVendaAberta) {
        int opcao;
        do {
            telaMenuFinalizarVenda();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    System.out.println("Continuando a compra...");
                    menuRealizarVenda(banco, idVendaAberta);
                    break;
                case 0:
                    VendaDao vendaDao = new VendaDao();
                    vendaDao.finalizarVenda(banco, idVendaAberta);
                    System.out.println("Compra finalizada com sucesso!");
                    Funcoes.pressEnterToContinue();
                    return;
                default:
                    System.out.println("Opção inválida.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Exibe o menu principal do vendedor.
     * Interface de console com opções disponíveis para o vendedor.
     */
    public static void telaMenuVendedor() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Realizar Venda");
        System.out.println("2. Listar Veiculos a Venda");
        System.out.println("3. Listar Carrinho");
        System.out.println("4. Finalizar Vendas");
        System.out.println("0. Sair");
    }

    /**
     * Exibe o menu de realização de vendas.
     * Permite escolher o tipo de veículo para busca.
     */
    public static void telaMenuRealizarVenda() {
        Funcoes.limpaTela();
        System.out.println("1. Buscar Carro");
        System.out.println("2. Buscar Moto");
        System.out.println("3. Buscar Caminhão");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de listagem de veículos à venda.
     * Permite visualizar estoque por categoria de veículo.
     */
    public static void telaMenuListarVeiculosAVenda() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("LISTAR VEÍCULOS A VENDA");
        System.out.println("1. Listar Carro");
        System.out.println("2. Listar Moto");
        System.out.println("3. Listar Caminhão");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de busca de carros.
     * Apresenta múltiplos critérios de busca disponíveis para carros.
     */
    public static void telaMenuBuscarCarro() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CARRO");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Modelo");
        System.out.println("3. Buscar por Numero de Chassi");
        System.out.println("4. Buscar por Preço");
        System.out.println("5. Buscar por Quilometragem");
        System.out.println("6. Buscar por Ano de Fabricação");
        System.out.println("7. Buscar por Cor");
        System.out.println("8. Buscar por Potência");
        System.out.println("9. Buscar por Número de Portas");
        System.out.println("10. Buscar por Tipo de Combustível");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de busca de motocicletas.
     * Apresenta múltiplos critérios de busca disponíveis para motocicletas.
     */
    public static void telaMenuBuscarMoto() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR MOTO");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Modelo");
        System.out.println("3. Buscar por Numero de Chassi");
        System.out.println("4. Buscar por Preço");
        System.out.println("5. Buscar por Quilometragem");
        System.out.println("6. Buscar por Ano de Fabricação");
        System.out.println("7. Buscar por Cor");
        System.out.println("8. Buscar por Cilindrada");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de busca de caminhões.
     * Apresenta múltiplos critérios de busca disponíveis para caminhões.
     */
    public static void telaMenuBuscarCaminhao() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CAMINHÃO");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Modelo");
        System.out.println("3. Buscar por Numero de Chassi");
        System.out.println("4. Buscar por Preço");
        System.out.println("5. Buscar por Quilometragem");
        System.out.println("6. Buscar por Ano de Fabricação");
        System.out.println("7. Buscar por Cor");
        System.out.println("8. Buscar por Número de Eixos");
        System.out.println("9. Buscar por Capacidade de Carga");
        System.out.println("10. Buscar por Altura Máxima");
        System.out.println("11. Buscar por Tipo de Carroceria");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe opções para listagem de veículos encontrados em buscas.
     * Interface para navegação em resultados de busca.
     */
    public static void telaMenuListarVeiculos() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("VEÍCULOS CADASTRADOS");
        System.out.println("1. Informe o Número da lista");
        System.out.println("2. Realizar Outra Busca");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe opções para adicionar veículo ao carrinho.
     * Interface de confirmação para adição de itens.
     */
    public static void telaMenuAdicionarCarrinho() {
        System.out.println("1. Adicionar ao Carrinho");
        System.out.println("2. Não Adicionar ao Carrinho");
    }

    /**
     * Exibe opções para finalização de venda.
     * Interface para conclusão ou continuação do processo de venda.
     */
    public static void telaMenuFinalizarVenda() {
        Funcoes.limpaTela();
        System.out.println("1. Continuar Comprando");
        System.out.println("0. Finalizar Compra");
    }
}
