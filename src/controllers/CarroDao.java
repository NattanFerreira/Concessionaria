package controllers;

import data.Banco;
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

    // Instância da nossa fábrica de conexões.
    private final Banco banco;

    public CarroDao() {
        // Ao criar um CarroDao, inicializamos a referência à nossa classe Banco.
        this.banco = new Banco();
    }

    /**
     * Insere um novo carro no banco de dados.
     *
     * @param carro O objeto Carro a ser persistido.
     */
    public Carro cadastrarCarro(Carro carro) {
        String sql = "INSERT INTO carros (tipo_veiculo, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // O segundo argumento "Statement.RETURN_GENERATED_KEYS" instrui o banco a
        // retornar o ID criado.
        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, "Carro");
            pstmt.setString(2, carro.getModelo());
            pstmt.setInt(3, carro.getNumChassi());
            pstmt.setDouble(4, carro.getQuilometragem());
            pstmt.setDouble(5, carro.getPreco());
            pstmt.setString(6, carro.getCor());
            pstmt.setInt(7, carro.getAnoFabricacao());
            pstmt.setInt(8, carro.getIdStatus());
            pstmt.setDouble(9, carro.getCavaloPotencia());
            pstmt.setInt(10, carro.getNumeroPortas());
            pstmt.setString(11, carro.getTipoCombustível());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Captura o ID que o banco gerou
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Define o ID correto no objeto que acabamos de salvar
                        carro.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar carro: " + e.getMessage());
        }
        // Retorna o objeto completo, agora com o ID correto do banco
        return carro;
    }

    /**
     * Busca e retorna todos os carros cadastrados no banco de dados.
     *
     * @return Uma lista de objetos Carro.
     */
    public List<Carro> listarCarros() {
        List<Carro> carros = new ArrayList<>();
        // É uma boa prática listar os campos explicitamente em vez de usar "SELECT *".
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Para cada linha no resultado, cria um objeto Carro
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
                carro.setId(rs.getInt("id")); // Essencial para operações de update/delete

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
            pstmt.setInt(11, carro.getId()); // O ID é usado na cláusula WHERE

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
     * Busca carros por .
     *
     * @param modelo O numChassi do carro a ser buscado.
     * @return Uma lista de objetos Carro que correspondem ao numChassi fornecido.
     */
    public List<Carro> buscarCarrosPorNumChassi(int numChassi) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE numChassi = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numChassi);
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
            System.err.println("Erro ao buscar carros pelo numero do chassi: " + e.getMessage());
        }
        return carros;
    }

    /**
     * Busca um carro pelo número do preço.
     *
     * @param preco O número do chassi do carro.
     * @return O objeto Carro encontrado ou null se não existir.
     */
    public Carro buscarCarroPorPreco(double preco) {
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE preco <= ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, preco);
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
            System.err.println("Erro ao buscar carro por preço: " + e.getMessage());
        }
        return null;
    }
}
