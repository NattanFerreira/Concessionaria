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
     * @param banco       O objeto Banco para realizar a operação.
     * @param funcionario O objeto Funcionario a ser persistido.
     * @return O objeto Funcionario com o ID gerado pelo banco.
     */
    public void cadastrarFuncionario(Banco banco, Funcionario funcionario) {
        String sql = String.format(
                "INSERT INTO Funcionario (nome, usuario, senha, id_cargo) VALUES ('%s', '%s', '%s', %d)",
                funcionario.getNome(),
                funcionario.getLogin(),
                funcionario.getSenha(),
                funcionario.getIdCargo());
        banco.queryInsup(sql);
    }

    /**
     * Busca e retorna todos os funcionários cadastrados no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarFuncionarios(Banco banco) {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, usuario, senha, id_cargo FROM Funcionario";

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getInt("id_cargo"));
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
     * @param banco       O objeto Banco para realizar a operação.
     * @param funcionario O objeto Funcionario com os dados atualizados e o ID do
     *                    registro
     *                    a ser alterado.
     */
    public void atualizarFuncionario(Banco banco, Funcionario funcionario) {
        String sql = String.format(
                "UPDATE Funcionario SET nome = '%s', usuario = '%s', senha = '%s', cargo = %d WHERE id = %d",
                funcionario.getNome(),
                funcionario.getLogin(),
                funcionario.getSenha(),
                funcionario.getIdCargo(),
                funcionario.getId());
        banco.queryInsup(sql);
    }

    /**
     * Exclui um funcionário do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do funcionário a ser excluído.
     */
    public void excluirFuncionario(Banco banco, int id) {
        String sql = String.format("DELETE FROM Funcionario WHERE id = %d", id);
        banco.queryInsup(sql);
    }

    /**
     * Busca um funcionário pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do funcionário a ser buscado.
     * @return Um objeto Funcionario ou null se não encontrado.
     */
    public Funcionario buscarFuncionarioPorId(Banco banco, int id) {
        Funcionario funcionario = null;
        String sql = String.format("SELECT id, nome, usuario, senha, id_cargo FROM Funcionario WHERE id = %d", id);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null && rs.next()) {
                funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getInt("id_cargo"));
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
     * @param nome  O nome do funcionário a ser buscado.
     * @return Uma lista de objetos Funcionario que correspondem ao nome fornecido.
     */
    public List<Funcionario> buscarFuncionariosPorNome(Banco banco, String nome) {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = String.format("SELECT id, nome, usuario, senha, id_cargo FROM Funcionario WHERE nome LIKE '%%s%'",
                nome);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getInt("id_cargo"));
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
        String sql = String.format("SELECT id, nome, usuario, senha, id_cargo FROM Funcionario WHERE id_cargo = '%s'",
                cargo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getInt("id_cargo"));
                funcionario.setId(rs.getInt("id"));

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionários por cargo: " + e.getMessage());
        }
        return funcionarios;
    }

    public static int getIdFuncionario(Banco banco, String login, String senha) {
        String sql = String.format("SELECT id FROM Funcionario WHERE usuario = '%s' AND senha = '%s'", login, senha);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter ID do funcionário: " + e.getMessage());
        }
        return -1; // Retorna -1 se não encontrar o funcionário ou se houver erro
    }

    /**
     * Autentica um funcionário verificando login e senha.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param login O login do funcionário.
     * @param senha A senha do funcionário.
     * @return O objeto Funcionario autenticado ou null se as credenciais forem
     *         inválidas.
     */
    public static int autenticarFuncionario(Banco banco, String login, String senha) {
        String sql = String.format("SELECT id_cargo FROM Funcionario WHERE usuario = '%s' AND senha = '%s'", login,
                senha);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                int id_cargo = rs.getInt("id_cargo");
                return id_cargo;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar funcionário: " + e.getMessage());
        }
        return -1; // Retorna -1 se não encontrar o funcionário ou se houver erro
    }
}
