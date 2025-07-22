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


public class EstoqueView {

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

    public static void TelaMenuAdicionarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ADICIONAR VEÍCULO");
        System.out.println("1. Adicionar Carro");
        System.out.println("2. Adicionar Moto");
        System.out.println("3. Adicionar Caminhão");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuAtualizarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR VEÍCULO");
        System.out.println("1. Atualizar Carro");
        System.out.println("2. Atualizar Moto");
        System.out.println("3. Atualizar Caminhão");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuRemoverVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("REMOVER VEÍCULOS");
        System.out.println("1. Remover Carros");
        System.out.println("2. Remover Motos");
        System.out.println("3. Remover Caminhão");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuListarVeiculos() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("LISTAR VEÍCULOS");
        System.out.println("1. Listar Carros");
        System.out.println("2. Listar Motos");
        System.out.println("3. Listar Caminhões");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuBuscarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR VEÍCULOS");
        System.out.println("1. Buscar Carros");
        System.out.println("2. Buscar Motos");
        System.out.println("3. Buscar Caminhão");
        System.out.println("0. Voltar");
    }

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
