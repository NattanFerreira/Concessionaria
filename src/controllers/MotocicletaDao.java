package controllers;

import data.Banco;
import models.Motocicleta;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Motocicleta.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas às motocicletas.
 */
public class MotocicletaDao {
    private final Banco banco;

    public MotocicletaDao() {
        this.banco = new Banco();
    }

    /**
     * Insere uma nova motocicleta no banco de dados.
     *
     * @param motocicleta O objeto Motocicleta a ser persistido.
     */
    public Motocicleta cadastrarMotocicleta(Motocicleta motocicleta) {
        String sql = "INSERT INTO motocicletas (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, motocicleta.getModelo());
            pstmt.setInt(2, motocicleta.getNumChassi());
            pstmt.setDouble(3, motocicleta.getQuilometragem());
            pstmt.setDouble(4, motocicleta.getPreco());
            pstmt.setString(5, motocicleta.getCor());
            pstmt.setInt(6, motocicleta.getAnoFabricacao());
            pstmt.setInt(7, motocicleta.getIdStatus());
            pstmt.setInt(8, motocicleta.getCilindrada());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        motocicleta.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar motocicleta: " + e.getMessage());
        }
        return motocicleta;
    }

    /**
     * Busca e retorna todas as motocicletas cadastradas no banco de dados.
     *
     * @return Uma lista de objetos Motocicleta.
     */
    public List<Motocicleta> listarMotocicletas() {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar motocicletas: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Atualiza os dados de uma motocicleta existente com base no seu ID.
     *
     * @param motocicleta O objeto Motocicleta com os dados atualizados e o ID do
     *                    registro
     *                    a ser alterado.
     */
    public void atualizarMotocicleta(Motocicleta motocicleta) {
        String sql = "UPDATE motocicletas SET modelo = ?, num_chassi = ?, quilometragem = ?, preco = ?, cor = ?, ano_fabricacao = ?, id_status = ?, cilindrada = ? WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, motocicleta.getModelo());
            pstmt.setInt(2, motocicleta.getNumChassi());
            pstmt.setDouble(3, motocicleta.getQuilometragem());
            pstmt.setDouble(4, motocicleta.getPreco());
            pstmt.setString(5, motocicleta.getCor());
            pstmt.setInt(6, motocicleta.getAnoFabricacao());
            pstmt.setInt(7, motocicleta.getIdStatus());
            pstmt.setInt(8, motocicleta.getCilindrada());
            pstmt.setInt(9, motocicleta.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar motocicleta: " + e.getMessage());
        }
    }

    /**
     * Exclui uma motocicleta do banco de dados pelo seu ID.
     *
     * @param id O ID da motocicleta a ser excluída.
     */
    public void excluirMotocicleta(int id) {
        String sql = "DELETE FROM motocicletas WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir motocicleta: " + e.getMessage());
        }
    }

    /**
     * Busca uma motocicleta pelo seu ID.
     *
     * @param id O ID da motocicleta a ser buscada.
     * @return Um objeto Motocicleta ou null se não encontrado.
     */
    public Motocicleta buscarMotocicletaPorId(int id) {
        Motocicleta motocicleta = null;
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicleta por ID: " + e.getMessage());
        }
        return motocicleta;
    }

    /**
     * Busca motocicletas por modelo.
     *
     * @param modelo O modelo da motocicleta a ser buscado.
     * @return Uma lista de objetos Motocicleta que correspondem ao modelo
     *         fornecido.
     */
    public List<Motocicleta> buscarMotocicletasPorModelo(String modelo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE modelo = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, modelo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por modelo: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Busca uma motocicleta pelo número do chassi.
     *
     * @param numChassi O número do chassi da motocicleta.
     * @return O objeto Motocicleta encontrado ou null se não existir.
     */
    public Motocicleta buscarMotocicletaPorChassi(int numChassi) {
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE num_chassi = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numChassi);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));
                return motocicleta;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicleta por chassi: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca motocicletas por faixa de preço.
     *
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Motocicleta na faixa de preço especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaPreco(double precoMinimo, double precoMaximo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE preco >= ? AND preco <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, precoMinimo);
            pstmt.setDouble(2, precoMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por faixa de preço: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Busca motocicletas por faixa de quilometragem.
     *
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Motocicleta com quilometragem igual ou menor.
     */
    public List<Motocicleta> buscarMotocicletasPorQuilometragem(double quilometragemMaxima) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE quilometragem <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, quilometragemMaxima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por quilometragem: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Busca motocicletas por faixa de ano de fabricação.
     *
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Motocicleta na faixa de ano especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaAno(int anoMinimo, int anoMaximo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE ano_fabricacao >= ? AND ano_fabricacao <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, anoMinimo);
            pstmt.setInt(2, anoMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por faixa de ano: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Busca motocicletas por cor.
     *
     * @param cor A cor da motocicleta a ser buscada.
     * @return Uma lista de objetos Motocicleta que correspondem à cor fornecida.
     */
    public List<Motocicleta> buscarMotocicletasPorCor(String cor) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por cor: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Busca motocicletas por cilindrada.
     *
     * @param cilindrada A cilindrada da motocicleta desejada.
     * @return Uma lista de objetos Motocicleta que possuem a cilindrada
     *         especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorCilindrada(int cilindrada) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cilindrada = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cilindrada);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por cilindrada: " + e.getMessage());
        }
        return motocicletas;
    }

    /**
     * Busca motocicletas por faixa de cilindrada.
     *
     * @param cilindradaMinima A cilindrada mínima desejada.
     * @param cilindradaMaxima A cilindrada máxima desejada.
     * @return Uma lista de objetos Motocicleta na faixa de cilindrada especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaCilindrada(int cilindradaMinima, int cilindradaMaxima) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cilindrada >= ? AND cilindrada <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cilindradaMinima);
            pstmt.setInt(2, cilindradaMaxima);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getInt("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getInt("cilindrada"));
                motocicleta.setId(rs.getInt("id"));

                motocicletas.add(motocicleta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motocicletas por faixa de cilindrada: " + e.getMessage());
        }
        return motocicletas;
    }
}
