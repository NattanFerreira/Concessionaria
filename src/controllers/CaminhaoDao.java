package controllers;

import data.Banco;
import models.Caminhao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Caminhao.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas aos caminhões.
 */
public class CaminhaoDao {
    private final Banco banco;

    public CaminhaoDao() {
        this.banco = new Banco();
    }

    /**
     * Insere um novo caminhão no banco de dados.
     *
     * @param caminhao O objeto Caminhao a ser persistido.
     */
    public Caminhao cadastrarCaminhao(Caminhao caminhao) {
        String sql = "INSERT INTO caminhoes (tipo_veiculo, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, "Caminhao");
            pstmt.setString(2, caminhao.getModelo());
            pstmt.setInt(3, caminhao.getNumChassi());
            pstmt.setDouble(4, caminhao.getQuilometragem());
            pstmt.setDouble(5, caminhao.getPreco());
            pstmt.setString(6, caminhao.getCor());
            pstmt.setInt(7, caminhao.getAnoFabricacao());
            pstmt.setInt(8, caminhao.getIdStatus());
            pstmt.setInt(9, caminhao.getEixo());
            pstmt.setDouble(10, caminhao.getCapacidadeCarga());
            pstmt.setDouble(11, caminhao.getAltura());
            pstmt.setString(12, caminhao.getTipoCarroceria());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        caminhao.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar caminhão: " + e.getMessage());
        }
        return caminhao;
    }

    /**
     * Busca e retorna todos os caminhões cadastrados no banco de dados.
     *
     * @return Uma lista de objetos Caminhao.
     */
    public List<Caminhao> listarCaminhoes() {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
            System.err.println("Erro ao listar caminhões: " + e.getMessage());
        }
        return caminhoes;
    }

    /**
     * Atualiza os dados de um caminhão existente com base no seu ID.
     *
     * @param caminhao O objeto Caminhao com os dados atualizados e o ID do registro
     *                 a ser alterado.
     */
    public void atualizarCaminhao(Caminhao caminhao) {
        String sql = "UPDATE caminhoes SET modelo = ?, num_chassi = ?, quilometragem = ?, preco = ?, cor = ?, ano_fabricacao = ?, id_status = ?, eixos = ?, capacidade_carga = ?, altura = ?, tipo_carroceria = ? WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, caminhao.getModelo());
            pstmt.setInt(2, caminhao.getNumChassi());
            pstmt.setDouble(3, caminhao.getQuilometragem());
            pstmt.setDouble(4, caminhao.getPreco());
            pstmt.setString(5, caminhao.getCor());
            pstmt.setInt(6, caminhao.getAnoFabricacao());
            pstmt.setInt(7, caminhao.getIdStatus());
            pstmt.setInt(8, caminhao.getEixo());
            pstmt.setDouble(9, caminhao.getCapacidadeCarga());
            pstmt.setDouble(10, caminhao.getAltura());
            pstmt.setString(11, caminhao.getTipoCarroceria());
            pstmt.setInt(12, caminhao.getId()); 

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar caminhão: " + e.getMessage());
        }
    }

    /**
     * Exclui um caminhão do banco de dados pelo seu ID.
     *
     * @param id O ID do caminhão a ser excluído.
     */
    public void excluirCaminhao(int id) {
        String sql = "DELETE FROM caminhoes WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir caminhão: " + e.getMessage());
        }
    }

    /**
     * Busca um caminhão pelo seu ID.
     *
     * @param id O ID do caminhão a ser buscado.
     * @return Um objeto Caminhao ou null se não encontrado.
     */
    public Caminhao buscarCaminhaoPorId(int id) {
        Caminhao caminhao = null;
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param modelo O modelo do caminhão a ser buscado.
     * @return Uma lista de objetos Caminhao que correspondem ao modelo fornecido.
     */
    public List<Caminhao> buscarCaminhoesPorModelo(String modelo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE modelo = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, modelo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param numChassi O número do chassi do caminhão.
     * @return O objeto Caminhao encontrado ou null se não existir.
     */
    public Caminhao buscarCaminhaoPorChassi(int numChassi) {
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE num_chassi = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numChassi);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Caminhao na faixa de preço especificada.
     */
    public List<Caminhao> buscarCaminhoesPorFaixaPreco(double precoMinimo, double precoMaximo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE preco >= ? AND preco <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, precoMinimo);
            pstmt.setDouble(2, precoMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Caminhao com quilometragem igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorQuilometragem(double quilometragemMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE quilometragem <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, quilometragemMaxima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Caminhao na faixa de ano especificada.
     */
    public List<Caminhao> buscarCaminhoesPorFaixaAno(int anoMinimo, int anoMaximo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE ano_fabricacao >= ? AND ano_fabricacao <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, anoMinimo);
            pstmt.setInt(2, anoMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param cor A cor do caminhão a ser buscada.
     * @return Uma lista de objetos Caminhao que correspondem à cor fornecida.
     */
    public List<Caminhao> buscarCaminhoesPorCor(String cor) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE cor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param eixo O número de eixos desejado.
     * @return Uma lista de objetos Caminhao que possuem o número de eixos
     *         especificado.
     */
    public List<Caminhao> buscarCaminhoesPorEixo(int eixo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE eixos = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eixo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param capacidadeMaxima A capacidade de carga máxima desejada.
     * @return Uma lista de objetos Caminhao que possuem capacidade igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorCapacidadeCarga(double capacidadeMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE capacidade_carga <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, capacidadeMaxima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param alturaMaxima A altura máxima desejada.
     * @return Uma lista de objetos Caminhao que possuem altura igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorAltura(double alturaMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE altura <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, alturaMaxima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
     * @param tipoCarroceria O tipo de carroceria desejado.
     * @return Uma lista de objetos Caminhao que possuem o tipo de carroceria
     *         especificado.
     */
    public List<Caminhao> buscarCaminhoesPorTipoCarroceria(String tipoCarroceria) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE tipo_carroceria = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoCarroceria);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Caminhao caminhao = new Caminhao(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
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
