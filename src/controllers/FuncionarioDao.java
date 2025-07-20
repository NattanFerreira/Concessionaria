package controllers;

import data.Banco;
import models.Funcionario;

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

    /**
     * Insere um novo funcionário no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param funcionario O objeto Funcionario a ser persistido.
     * @return O objeto Funcionario com o ID gerado pelo banco.
     */
    public Funcionario cadastrarFuncionario(Banco banco, Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (nome, usuario, senha, cargo) VALUES (?, ?, ?, ?)";

        int id = banco.queryInsertComRetorno(sql, 
            funcionario.getNome(),
            funcionario.getLogin(),
            funcionario.getSenha(),
            funcionario.getCargo()
        );

        if (id > 0) {
            funcionario.setId(id);
        }
        return funcionario;
    }

    /**
     * Busca e retorna todos os funcionários cadastrados no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarFuncionarios(Banco banco) {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario";

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("cargo"));
                funcionario.setId(rs.getInt("id"));

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
     * @param banco O objeto Banco para realizar a operação.
     * @param funcionario O objeto Funcionario com os dados atualizados e o ID do registro
     *                   a ser alterado.
     */
    public void atualizarFuncionario(Banco banco, Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET nome = ?, usuario = ?, senha = ?, cargo = ? WHERE id = ?";

        banco.queryUpdate(sql, 
            funcionario.getNome(),
            funcionario.getLogin(),
            funcionario.getSenha(),
            funcionario.getCargo(),
            funcionario.getId()
        );
    }

    /**
     * Exclui um funcionário do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id O ID do funcionário a ser excluído.
     */
    public void excluirFuncionario(Banco banco, int id) {
        String sql = "DELETE FROM Funcionario WHERE id = ?";
        banco.queryUpdate(sql, id);
    }

    /**
     * Busca um funcionário pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id O ID do funcionário a ser buscado.
     * @return Um objeto Funcionario ou null se não encontrado.
     */
    public Funcionario buscarFuncionarioPorId(Banco banco, int id) {
        Funcionario funcionario = null;
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario WHERE id = ?";

        try (ResultSet rs = banco.querySelect(sql, id)) {
            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("cargo"));
                funcionario.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return funcionario;
    }

    /**
     * Busca funcionários por nome.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param nome O nome do funcionário a ser buscado.
     * @return Uma lista de objetos Funcionario que correspondem ao nome fornecido.
     */
    public List<Funcionario> buscarFuncionariosPorNome(Banco banco, String nome) {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario WHERE nome LIKE ?";

        try (ResultSet rs = banco.querySelect(sql, "%" + nome + "%")) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("cargo"));
                funcionario.setId(rs.getInt("id"));

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionários por nome: " + e.getMessage());
        }
        return funcionarios;
    }

    /**
     * Busca funcionários por cargo.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param cargo O cargo do funcionário a ser buscado.
     * @return Uma lista de objetos Funcionario que possuem o cargo especificado.
     */
    public List<Funcionario> buscarFuncionariosPorCargo(Banco banco, String cargo) {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario WHERE cargo = ?";

        try (ResultSet rs = banco.querySelect(sql, cargo)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("cargo"));
                funcionario.setId(rs.getInt("id"));

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionários por cargo: " + e.getMessage());
        }
        return funcionarios;
    }

    /**
     * Busca um funcionário por login (usuário).
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param login O login do funcionário a ser buscado.
     * @return O objeto Funcionario encontrado ou null se não existir.
     */
    public Funcionario buscarFuncionarioPorLogin(Banco banco, String login) {
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario WHERE usuario = ?";

        try (ResultSet rs = banco.querySelect(sql, login)) {
            if (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("cargo"));
                funcionario.setId(rs.getInt("id"));
                return funcionario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por login: " + e.getMessage());
        }
        return null;
    }

    /**
     * Autentica um funcionário verificando login e senha.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param login O login do funcionário.
     * @param senha A senha do funcionário.
     * @return O objeto Funcionario autenticado ou null se as credenciais forem inválidas.
     */
    public Funcionario autenticarFuncionario(Banco banco, String login, String senha) {
        String sql = "SELECT id, nome, usuario, senha, cargo FROM Funcionario WHERE usuario = ? AND senha = ?";

        try (ResultSet rs = banco.querySelect(sql, login, senha)) {
            if (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("cargo"));
                funcionario.setId(rs.getInt("id"));
                return funcionario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar funcionário: " + e.getMessage());
        }
        return null;
    }
}
