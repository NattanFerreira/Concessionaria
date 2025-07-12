package controllers;

import data.Banco;
import models.Funcionario;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Funcionario.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas aos funcionários.
 */
public class FuncionarioDao {

    // Instância da nossa fábrica de conexões.
    private final Banco banco;

    public FuncionarioDao() {
        // Ao criar um FuncionarioDao, inicializamos a referência à nossa classe Banco.
        this.banco = new Banco();
    }

    /**
     * Insere um novo funcionário no banco de dados.
     *
     * @param funcionario O objeto Funcionario a ser persistido, com nome, usuario, senha e cargo.
     */
    public Funcionario cadastrarFuncionario(Funcionario funcionario) {
    String sql = "INSERT INTO Funcionario (nome, usuario, senha, cargo) VALUES (?, ?, ?, ?)";

    // O segundo argumento "Statement.RETURN_GENERATED_KEYS" instrui o banco a retornar o ID criado.
    try (Connection conn = this.banco.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, funcionario.getNome());
        pstmt.setString(2, funcionario.getLogin());
        pstmt.setString(3, funcionario.getSenha());
        pstmt.setString(4, funcionario.getCargo());

        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
            // Captura o ID que o banco gerou
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Define o ID correto no objeto que acabamos de salvar
                    funcionario.setId(generatedKeys.getInt(1));
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
    }
    // Retorna o objeto completo, agora com o ID correto do banco
    return funcionario; 
}

    /**
     * Busca e retorna todos os funcionários cadastrados no banco de dados.
     *
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        // É uma boa prática listar os campos explicitamente em vez de usar "SELECT *".
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario";

        try (Connection conn = this.banco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Para cada linha no resultado, cria um objeto Funcionario
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id")); // Essencial para operações de update/delete
                funcionario.setNome(rs.getString("nome"));
                funcionario.setLogin(rs.getString("usuario"));
                funcionario.setSenha(rs.getString("senha")); // Lembre-se que esta senha virá do DB
                funcionario.setCargo(rs.getString("cargo"));

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    /**
     * Atualiza os dados de um funcionário existente com base no seu ID.
     *
     * @param funcionario O objeto Funcionario com os dados atualizados e o ID do registro a ser alterado.
     */
    public void atualizarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET nome = ?, usuario = ?, senha = ?, cargo = ? WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getLogin());
            pstmt.setString(3, funcionario.getSenha()); // Lembre-se do hash!
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setInt(5, funcionario.getId()); // O ID é usado na cláusula WHERE

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    /**
     * Exclui um funcionário do banco de dados pelo seu ID.
     *
     * @param id O ID do funcionário a ser excluído.
     */
    public void excluirFuncionario(int id) {
        String sql = "DELETE FROM Funcionario WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir funcionário: " + e.getMessage());
        }
    }
}