package controllers;

import data.Banco;
import models.Caminhao;
import models.Carro;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Carro.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas aos carros.
 */
public class CarroDao {
    private final Banco banco;

    public CarroDao() {
        this.banco = new Banco();
    }

    /**
     * Insere um novo carro no banco de dados.
     *
     * @param carro O objeto Carro a ser persistido.
     */
    public Carro cadastrarCarro(Carro carro) {
        String sql = "INSERT INTO carros (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, carro.getModelo());
            pstmt.setInt(2, carro.getNumChassi());
            pstmt.setDouble(3, carro.getQuilometragem());
            pstmt.setDouble(4, carro.getPreco());
            pstmt.setString(5, carro.getCor());
            pstmt.setInt(6, carro.getAnoFabricacao());
            pstmt.setInt(7, carro.getIdStatus());
            pstmt.setDouble(8, carro.getCavaloPotencia());
            pstmt.setInt(9, carro.getNumeroPortas());
            pstmt.setString(10, carro.getTipoCombustível());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        carro.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar carro: " + e.getMessage());
        }
        return carro;
    }

    /**
     * Busca e retorna todos os carros cadastrados no banco de dados.
     *
     * @return Uma lista de objetos Carro.
     */
    public List<Carro> listarCarros() {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar carros: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Atualiza os dados de um carro existente com base no seu ID.
     *
     * @param carro O objeto Carro com os dados atualizados e o ID do registro a ser
     *              alterado.
     */
    public void atualizarCarro(Carro carro) {
        String sql = "UPDATE carros SET modelo = ?, num_chassi = ?, quilometragem = ?, preco = ?, cor = ?, ano_fabricacao = ?, id_status = ?, cavalo_potencia = ?, numero_portas = ?, tipo_combustivel = ? WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, carro.getModelo());
            pstmt.setInt(2, carro.getNumChassi());
            pstmt.setDouble(3, carro.getQuilometragem());
            pstmt.setDouble(4, carro.getPreco());
            pstmt.setString(5, carro.getCor());
            pstmt.setInt(6, carro.getAnoFabricacao());
            pstmt.setInt(7, carro.getIdStatus());
            pstmt.setDouble(8, carro.getCavaloPotencia());
            pstmt.setInt(9, carro.getNumeroPortas());
            pstmt.setString(10, carro.getTipoCombustível());
            pstmt.setInt(11, carro.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carro: " + e.getMessage());
        }
    }

    /**
     * Exclui um carro do banco de dados pelo seu ID.
     *
     * @param id O ID do carro a ser excluído.
     */
    public void excluirCarro(int id) {
        String sql = "DELETE FROM carros WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir carro: " + e.getMessage());
        }
    }

        /**
     * Busca um carro pelo seu ID.
     *
     * @param id O ID do carro a ser buscado.
     * @return Um objeto carro ou null se não encontrado.
     */
    public Carro buscarCarroPorId(int id) {
        Carro carro = null;
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caminhão por ID: " + e.getMessage());
        }
        return carro;
    }

    /**
     * Busca carros por modelo.
     *
     * @param modelo O modelo do carro a ser buscado.
     * @return Uma lista de objetos Carro que correspondem ao modelo fornecido.
     */
    public List<Carro> buscarCarrosPorModelo(String modelo) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE modelo = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, modelo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por modelo: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca um carro pelo número do chassi.
     *
     * @param numChassi O número do chassi do carro.
     * @return O objeto Carro encontrado ou null se não existir.
     */
    public Carro buscarCarroPorChassi(int numChassi) {
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE num_chassi = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numChassi);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));
                return carro;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carro por chassi: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca carros por faixa de preço.
     *
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Carro na faixa de preço especificada.
     */
    public List<Carro> buscarCarrosPorFaixaPreco(double precoMinimo, double precoMaximo) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE preco >= ? AND preco <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, precoMinimo);
            pstmt.setDouble(2, precoMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por faixa de preço: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca carros por faixa de quilometragem.
     *
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Carro com quilometragem igual ou menor.
     */
    public List<Carro> buscarCarrosPorQuilometragem(double quilometragemMaxima) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE quilometragem <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, quilometragemMaxima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por quilometragem: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca carros por faixa de ano de fabricação.
     *
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Carro na faixa de ano especificada.
     */
    public List<Carro> buscarCarrosPorFaixaAno(int anoMinimo, int anoMaximo) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE ano_fabricacao >= ? AND ano_fabricacao <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, anoMinimo);
            pstmt.setInt(2, anoMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por faixa de ano: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca carros por cor.
     *
     * @param cor A cor do carro a ser buscada.
     * @return Uma lista de objetos Carro que correspondem à cor fornecida.
     */
    public List<Carro> buscarCarrosPorCor(String cor) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE cor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por cor: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca carros por potência mínima.
     *
     * @param potenciaMinima A potência mínima desejada em cavalos.
     * @return Uma lista de objetos Carro com potência igual ou maior.
     */
    public List<Carro> buscarCarrosPorPotencia(double potenciaMinima) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE cavalo_potencia >= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, potenciaMinima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por potência: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca carros por número de portas.
     *
     * @param numeroPortas O número de portas desejado.
     * @return Uma lista de objetos Carro que possuem o número de portas
     *         especificado.
     */
    public List<Carro> buscarCarrosPorNumeroPortas(int numeroPortas) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE numero_portas = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numeroPortas);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por número de portas: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca carros por tipo de combustível.
     *
     * @param tipoCombustivel O tipo de combustível desejado.
     * @return Uma lista de objetos Carro que utilizam o tipo de combustível
     *         especificado.
     */
    public List<Carro> buscarCarrosPorCombustivel(String tipoCombustivel) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE tipo_combustivel = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoCombustivel);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por combustível: " + e.getMessage());
        }
        return carros;
    }
}