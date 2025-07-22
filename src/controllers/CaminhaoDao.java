package controllers;

import data.Banco;
import models.Caminhao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Funcoes;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Caminhao.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas aos caminhões.
 */
public class CaminhaoDao {

    /**
     * Insere um novo caminhão no banco de dados.
     *
     * @param banco    O objeto Banco para realizar a operação.
     * @param caminhao O objeto Caminhao a ser persistido.
     */
    public void cadastrarCaminhao(Banco banco, Caminhao caminhao) {
        String sql = String.format(
                "INSERT INTO Caminhao (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga,altura, tipo_carroceria) VALUES ('%s', '%s', %.2f, %.2f, '%s', %d, %d, %d, %.2f, %.2f, '%s')",
                caminhao.getModelo(),
                caminhao.getNumChassi(),
                caminhao.getQuilometragem(),
                caminhao.getPreco(),
                caminhao.getCor(),
                caminhao.getAnoFabricacao(),
                caminhao.getIdStatus(),
                caminhao.getEixo(),
                caminhao.getCapacidadeCarga(),
                caminhao.getAltura(),
                caminhao.getTipoCarroceria());
        banco.queryInsup(sql);
    }

    /**
     * Adiciona um novo caminhão ao estoque, solicitando os dados do usuário.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void adicionarCaminhao(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Adicionar Caminhão");

        String modelo;
        do {
            System.out.println("Digite o modelo do caminhão:");
            modelo = Funcoes.lerString();
            if (modelo == null || modelo.trim().isEmpty()) {
                System.out.println("Modelo não pode estar vazio. Tente novamente.");
            }
        } while (modelo == null || modelo.trim().isEmpty());

        String numChassi;
        do {
            System.out.println("Digite o número do chassi:");
            numChassi = Funcoes.lerString();
            if (numChassi == null) {
                System.out.println("Número do chassi não pode estar vazio. Tente novamente.");
            }
        } while (numChassi == null);

        double quilometragem;
        do {
            System.out.println("Digite a quilometragem:");
            quilometragem = Funcoes.lerDouble();
            if (quilometragem < 0) {
                System.out.println("Quilometragem não pode ser negativa. Tente novamente.");
            }
        } while (quilometragem < 0);

        double preco;
        do {
            System.out.println("Digite o preço:");
            preco = Funcoes.lerDouble();
            if (preco <= 0) {
                System.out.println("Preço deve ser um valor positivo. Tente novamente.");
            }
        } while (preco <= 0);

        String cor;
        do {
            System.out.println("Digite a cor:");
            cor = Funcoes.lerString();
            if (cor == null || cor.trim().isEmpty()) {
                System.out.println("Cor não pode estar vazia. Tente novamente.");
            }
        } while (cor == null || cor.trim().isEmpty());

        int anoFabricacao;
        do {
            System.out.println("Digite o ano de fabricação:");
            anoFabricacao = Funcoes.lerInt();
            if (anoFabricacao < 1900 || anoFabricacao > 2025) {
                System.out.println("Ano de fabricação deve estar entre 1900 e 2025. Tente novamente.");
            }
        } while (anoFabricacao < 1900 || anoFabricacao > 2025);

        int eixo;
        do {
            System.out.println("Digite o número de eixos:");
            eixo = Funcoes.lerInt();
            if (eixo < 2 || eixo > 10) {
                System.out.println("Número de eixos deve estar entre 2 e 10. Tente novamente.");
            }
        } while (eixo < 2 || eixo > 10);

        double capacidadeCarga;
        do {
            System.out.println("Digite a capacidade de carga (em toneladas):");
            capacidadeCarga = Funcoes.lerDouble();
            if (capacidadeCarga <= 0 || capacidadeCarga > 100) {
                System.out.println("Capacidade de carga deve estar entre 0.1 e 100 toneladas. Tente novamente.");
            }
        } while (capacidadeCarga <= 0 || capacidadeCarga > 100);

        double altura;
        do {
            System.out.println("Digite a altura (em metros):");
            altura = Funcoes.lerDouble();
            if (altura <= 0 || altura > 5) {
                System.out.println("Altura deve estar entre 0.1 e 5 metros. Tente novamente.");
            }
        } while (altura <= 0 || altura > 5);

        String tipoCarroceria;
        do {
            System.out.println("Digite o tipo de carroceria:");
            tipoCarroceria = Funcoes.lerString();
            if (tipoCarroceria == null || tipoCarroceria.trim().isEmpty()) {
                System.out.println("Tipo de carroceria não pode estar vazio. Tente novamente.");
            }
        } while (tipoCarroceria == null || tipoCarroceria.trim().isEmpty());

        Caminhao caminhao = new Caminhao(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, 0,
                eixo, capacidadeCarga, altura, tipoCarroceria);

        cadastrarCaminhao(banco, caminhao);
        System.out.println("Caminhão adicionado com sucesso!");
    }

    /**
     * Atualiza os dados de um caminhão existente, solicitando os novos dados do
     * usuário.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void atualizarCaminhao(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Atualizar Caminhão");
        System.out.println("Caminhões disponíveis:");
        List<Caminhao> Caminhao = listarCaminhoes(banco);
        for (Caminhao caminhao : Caminhao) {
            System.out.println("ID: " + caminhao.getId() + " - Modelo: " + caminhao.getModelo());
        }

        System.out.println("Digite o ID do caminhão a ser atualizado:");
        int id = Funcoes.lerInt();

        Caminhao caminhaoExistente = buscarCaminhaoPorId(banco, id);
        if (caminhaoExistente == null) {
            System.out.println("Caminhão não encontrado com o ID: " + id);
            return;
        }

        System.out.println("Caminhão atual: " + caminhaoExistente);

        String modelo;
        do {
            System.out.println("Digite o novo modelo do caminhão (atual: " + caminhaoExistente.getModelo() + "):");
            modelo = Funcoes.lerString();
            if (modelo == null || modelo.trim().isEmpty()) {
                System.out.println("Modelo não pode estar vazio. Tente novamente.");
            }
        } while (modelo == null || modelo.trim().isEmpty());

        String numChassi;
        do {
            System.out.println("Digite o novo número do chassi (atual: " + caminhaoExistente.getNumChassi() + "):");
            numChassi = Funcoes.lerString();
            if (numChassi == null) {
                System.out.println("Número do chassi não pode estar vazio. Tente novamente.");
            }
        } while (numChassi == null);

        double quilometragem;
        do {
            System.out.println("Digite a nova quilometragem (atual: " + caminhaoExistente.getQuilometragem() + "):");
            quilometragem = Funcoes.lerDouble();
            if (quilometragem < 0) {
                System.out.println("Quilometragem não pode ser negativa. Tente novamente.");
            }
        } while (quilometragem < 0);

        double preco;
        do {
            System.out.println("Digite o novo preço (atual: " + caminhaoExistente.getPreco() + "):");
            preco = Funcoes.lerDouble();
            if (preco <= 0) {
                System.out.println("Preço deve ser um valor positivo. Tente novamente.");
            }
        } while (preco <= 0);

        String cor;
        do {
            System.out.println("Digite a nova cor (atual: " + caminhaoExistente.getCor() + "):");
            cor = Funcoes.lerString();
            if (cor == null || cor.trim().isEmpty()) {
                System.out.println("Cor não pode estar vazia. Tente novamente.");
            }
        } while (cor == null || cor.trim().isEmpty());

        int anoFabricacao;
        do {
            System.out
                    .println("Digite o novo ano de fabricação (atual: " + caminhaoExistente.getAnoFabricacao() + "):");
            anoFabricacao = Funcoes.lerInt();
            if (anoFabricacao < 1900 || anoFabricacao > 2025) {
                System.out.println("Ano de fabricação deve estar entre 1900 e 2025. Tente novamente.");
            }
        } while (anoFabricacao < 1900 || anoFabricacao > 2025);

        int idStatus;
        do {
            System.out.println("Caminhão atual: " + caminhaoExistente.getIdStatus());
            System.out.println("Digite o novo ID do status (1 - Disponível, 2 - Vendido, 3 - Reservado):");
            idStatus = Funcoes.lerInt();
            if (idStatus < 1 || idStatus > 3) {
                System.out.println("Status deve ser 1, 2 ou 3. Tente novamente.");
            }
        } while (idStatus < 1 || idStatus > 3);

        int eixo;
        do {
            System.out.println("Digite o novo número de eixos (atual: " + caminhaoExistente.getEixo() + "):");
            eixo = Funcoes.lerInt();
            if (eixo < 2 || eixo > 10) {
                System.out.println("Número de eixos deve estar entre 2 e 10. Tente novamente.");
            }
        } while (eixo < 2 || eixo > 10);

        double capacidadeCarga;
        do {
            System.out.println("Digite a nova capacidade de carga em toneladas (atual: "
                    + caminhaoExistente.getCapacidadeCarga() + "):");
            capacidadeCarga = Funcoes.lerDouble();
            if (capacidadeCarga <= 0 || capacidadeCarga > 100) {
                System.out.println("Capacidade de carga deve estar entre 0.1 e 100 toneladas. Tente novamente.");
            }
        } while (capacidadeCarga <= 0 || capacidadeCarga > 100);

        double altura;
        do {
            System.out.println("Digite a nova altura em metros (atual: " + caminhaoExistente.getAltura() + "):");
            altura = Funcoes.lerDouble();
            if (altura <= 0 || altura > 5) {
                System.out.println("Altura deve estar entre 0.1 e 5 metros. Tente novamente.");
            }
        } while (altura <= 0 || altura > 5);

        String tipoCarroceria;
        do {
            System.out.println(
                    "Digite o novo tipo de carroceria (atual: " + caminhaoExistente.getTipoCarroceria() + "):");
            tipoCarroceria = Funcoes.lerString();
            if (tipoCarroceria == null || tipoCarroceria.trim().isEmpty()) {
                System.out.println("Tipo de carroceria não pode estar vazio. Tente novamente.");
            }
        } while (tipoCarroceria == null || tipoCarroceria.trim().isEmpty());

        Caminhao caminhao = new Caminhao(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus,
                eixo, capacidadeCarga, altura, tipoCarroceria);
        caminhao.setId(id);

        atualizarCaminhao(banco, caminhao);
        System.out.println("Caminhão atualizado com sucesso!");
    }

    /**
     * Remove um caminhão do estoque, solicitando o ID do caminhão a ser removido.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void removerCaminhao(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Remover Caminhão");

        System.out.println("Caminhões disponíveis:");
        List<Caminhao> Caminhao = listarCaminhoes(banco);
        for (Caminhao caminhao : Caminhao) {
            System.out.println("ID: " + caminhao.getId() + " - Modelo: " + caminhao.getModelo());
        }

        System.out.println("Digite o ID do caminhão a ser removido:");
        int id = Funcoes.lerInt();

        Caminhao caminhaoExistente = buscarCaminhaoPorId(banco, id);
        if (caminhaoExistente == null) {
            System.out.println("Caminhão não encontrado com o ID: " + id);
            return;
        }

        excluirCaminhao(banco, id);
        System.out.println("Caminhão removido com sucesso!");
    }

    /**
     * Busca e retorna todos os caminhões cadastrados no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Caminhao.
     */
    public List<Caminhao> listarCaminhoes(Banco banco) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao";

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null) {
                while (rs.next()) {
                    Caminhao caminhao = new Caminhao(
                            rs.getString("modelo"),
                            rs.getString("num_chassi"),
                            rs.getDouble("quilometragem"),
                            rs.getDouble("preco"),
                            rs.getString("cor"),
                            rs.getInt("ano_fabricacao"),
                            rs.getInt("id_status"),
                            rs.getInt("eixos"),
                            rs.getDouble("capacidade_carga"),
                            rs.getDouble("altura"),
                            rs.getString("tipo_carroceria"));
                    caminhao.setId(rs.getInt("id"));

                    caminhoes.add(caminhao);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar caminhões: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca um caminhão pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do caminhão a ser buscado.
     * @return Um objeto Caminhao ou null se não encontrado.
     */
    public List<Caminhao> listarCaminhoesAVenda(Banco banco) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE id_status = 1";

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null) {
                while (rs.next()) {
                    Caminhao caminhao = new Caminhao(
                            rs.getString("modelo"),
                            rs.getString("num_chassi"),
                            rs.getDouble("quilometragem"),
                            rs.getDouble("preco"),
                            rs.getString("cor"),
                            rs.getInt("ano_fabricacao"),
                            rs.getInt("id_status"),
                            rs.getInt("eixos"),
                            rs.getDouble("capacidade_carga"),
                            rs.getDouble("altura"),
                            rs.getString("tipo_carroceria"));
                    caminhao.setId(rs.getInt("id"));
                    caminhoes.add(caminhao);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar caminhões à venda: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Atualiza os dados de um caminhão existente com base no seu ID.
     *
     * @param banco    O objeto Banco para realizar a operação.
     * @param caminhao O objeto Caminhao com os dados atualizados e o ID do registro
     *                 a ser alterado.
     */
    public void atualizarCaminhao(Banco banco, Caminhao caminhao) {
        String sql = String.format(
                "UPDATE Caminhao SET modelo = '%s', num_chassi = '%s', quilometragem = %.2f, preco = %.2f, cor = '%s', ano_fabricacao = %d, id_status = %d, eixos = %d, capacidade_carga = %.2f, altura = %.2f, tipo_carroceria = '%s' WHERE id = %d",
                caminhao.getModelo(),
                caminhao.getNumChassi(),
                caminhao.getQuilometragem(),
                caminhao.getPreco(),
                caminhao.getCor(),
                caminhao.getAnoFabricacao(),
                caminhao.getIdStatus(),
                caminhao.getEixo(),
                caminhao.getCapacidadeCarga(),
                caminhao.getAltura(),
                caminhao.getTipoCarroceria(),
                caminhao.getId());
        banco.queryInsup(sql);
    }

    /**
     * Exclui um caminhão do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do caminhão a ser excluído.
     */
    public void excluirCaminhao(Banco banco, int id) {
        String sql = String.format("DELETE FROM Caminhao WHERE id = %d", id);
        banco.queryInsup(sql);
    }

    /**
     * Busca um caminhão pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do caminhão a ser buscado.
     * @return Um objeto Caminhao ou null se não encontrado.
     */
    public Caminhao buscarCaminhaoPorId(Banco banco, int id) {
        Caminhao caminhao = null;
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE id = %d",
                id);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null && rs.next()) {
                caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhão por ID: " + e.getMessage());
        }
        return caminhao;
    }

    /**
     * Busca caminhões por modelo.
     *
     * @param banco  O objeto Banco para realizar a operação.
     * @param modelo O modelo do caminhão a ser buscado.
     * @return Uma lista de objetos Caminhao que correspondem ao modelo fornecido.
     */
    public List<Caminhao> buscarCaminhoesPorModelo(Banco banco, String modelo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE modelo = '%s'",
                modelo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por modelo: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca um caminhão pelo número do chassi.
     *
     * @param banco     O objeto Banco para realizar a operação.
     * @param numChassi O número do chassi do caminhão.
     * @return O objeto Caminhao encontrado ou null se não existir.
     */
    public Caminhao buscarCaminhaoPorChassi(Banco banco, String numChassi) {
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE num_chassi = '%s'",
                numChassi);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));
                return caminhao;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhão por chassi: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca caminhões por faixa de preço.
     *
     * @param banco       O objeto Banco para realizar a operação.
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Caminhao na faixa de preço especificada.
     */
    public List<Caminhao> buscarCaminhoesPorFaixaPreco(Banco banco, double precoMinimo, double precoMaximo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE preco >= %.2f AND preco <= %.2f",
                precoMinimo, precoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por faixa de preço: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por faixa de quilometragem.
     *
     * @param banco               O objeto Banco para realizar a operação.
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Caminhao com quilometragem igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorQuilometragem(Banco banco, double quilometragemMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE quilometragem <= %.2f",
                quilometragemMaxima);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por quilometragem: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por faixa de ano de fabricação.
     *
     * @param banco     O objeto Banco para realizar a operação.
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Caminhao na faixa de ano especificada.
     */
    public List<Caminhao> buscarCaminhoesPorFaixaAno(Banco banco, int anoMinimo, int anoMaximo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE ano_fabricacao >= %d AND ano_fabricacao <= %d",
                anoMinimo, anoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por faixa de ano: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por cor.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param cor   A cor do caminhão a ser buscada.
     * @return Uma lista de objetos Caminhao que correspondem à cor fornecida.
     */
    public List<Caminhao> buscarCaminhoesPorCor(Banco banco, String cor) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE cor = '%s'",
                cor);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por cor: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por número de eixos.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param eixo  O número de eixos desejado.
     * @return Uma lista de objetos Caminhao que possuem o número de eixos
     *         especificado.
     */
    public List<Caminhao> buscarCaminhoesPorEixo(Banco banco, int eixo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE eixos = %d",
                eixo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por eixo: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por capacidade de carga máxima.
     *
     * @param banco            O objeto Banco para realizar a operação.
     * @param capacidadeMaxima A capacidade de carga máxima desejada.
     * @return Uma lista de objetos Caminhao que possuem capacidade igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorCapacidadeCarga(Banco banco, double capacidadeMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE capacidade_carga <= %.2f",
                capacidadeMaxima);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por capacidade de carga: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por altura máxima.
     *
     * @param banco        O objeto Banco para realizar a operação.
     * @param alturaMaxima A altura máxima desejada.
     * @return Uma lista de objetos Caminhao que possuem altura igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorAltura(Banco banco, double alturaMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE altura <= %.2f",
                alturaMaxima);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por altura: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Busca caminhões por tipo de carroceria.
     *
     * @param banco          O objeto Banco para realizar a operação.
     * @param tipoCarroceria O tipo de carroceria desejado.
     * @return Uma lista de objetos Caminhao que possuem o tipo de carroceria
     *         especificado.
     */
    public List<Caminhao> buscarCaminhoesPorTipoCarroceria(Banco banco, String tipoCarroceria) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM Caminhao WHERE tipo_carroceria = '%s'",
                tipoCarroceria);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("eixos"),
                        rs.getDouble("capacidade_carga"),
                        rs.getDouble("altura"),
                        rs.getString("tipo_carroceria"));
                caminhao.setId(rs.getInt("id"));

                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhões por tipo de carroceria: " + e.getMessage());
        }
        return caminhoes;
    }
}
