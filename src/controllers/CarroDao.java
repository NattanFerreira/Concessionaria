package controllers;

import data.Banco;
import models.Carro;

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

    /**
     * Insere um novo carro no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param carro O objeto Carro a ser persistido.
     * @return O objeto Carro com o ID gerado pelo banco.
     */
    public Carro cadastrarCarro(Banco banco, Carro carro) {
        String sql = "INSERT INTO carros (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int id = banco.queryInsertComRetorno(sql,
                carro.getModelo(),
                carro.getNumChassi(),
                carro.getQuilometragem(),
                carro.getPreco(),
                carro.getCor(),
                carro.getAnoFabricacao(),
                carro.getIdStatus(),
                carro.getCavaloPotencia(),
                carro.getNumeroPortas(),
                carro.getTipoCombustivel());

        if (id > 0) {
            carro.setId(id);
        }
        return carro;
    }

    /**
     * Busca e retorna todos os carros cadastrados no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Carro.
     */
    public List<Carro> listarCarros(Banco banco) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros";

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param carro O objeto Carro com os dados atualizados e o ID do registro
     *              a ser alterado.
     */
    public void atualizarCarro(Banco banco, Carro carro) {
        String sql = "UPDATE carros SET modelo = ?, num_chassi = ?, quilometragem = ?, preco = ?, cor = ?, ano_fabricacao = ?, id_status = ?, cavalo_potencia = ?, numero_portas = ?, tipo_combustivel = ? WHERE id = ?";

        banco.queryUpdate(sql,
                carro.getModelo(),
                carro.getNumChassi(),
                carro.getQuilometragem(),
                carro.getPreco(),
                carro.getCor(),
                carro.getAnoFabricacao(),
                carro.getIdStatus(),
                carro.getCavaloPotencia(),
                carro.getNumeroPortas(),
                carro.getTipoCombustivel(),
                carro.getId());
    }

    /**
     * Exclui um carro do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do carro a ser excluído.
     */
    public void excluirCarro(Banco banco, int id) {
        String sql = "DELETE FROM carros WHERE id = ?";
        banco.queryUpdate(sql, id);
    }

    /**
     * Busca um carro pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do carro a ser buscado.
     * @return Um objeto Carro ou null se não encontrado.
     */
    public Carro buscarCarroPorId(Banco banco, int id) {
        Carro carro = null;
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE id = ?";

        try (ResultSet rs = banco.querySelect(sql, id)) {
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
            System.err.println("Erro ao buscar carro por ID: " + e.getMessage());
        }
        return carro;
    }

    /**
     * Busca carros por modelo.
     *
     * @param banco  O objeto Banco para realizar a operação.
     * @param modelo O modelo do carro a ser buscado.
     * @return Uma lista de objetos Carro que correspondem ao modelo fornecido.
     */
    public List<Carro> buscarCarrosPorModelo(Banco banco, String modelo) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE modelo = ?";

        try (ResultSet rs = banco.querySelect(sql, modelo)) {
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
     * @param banco     O objeto Banco para realizar a operação.
     * @param numChassi O número do chassi do carro.
     * @return O objeto Carro encontrado ou null se não existir.
     */
    public Carro buscarCarroPorChassi(Banco banco, int numChassi) {
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE num_chassi = ?";

        try (ResultSet rs = banco.querySelect(sql, numChassi)) {
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
     * @param banco       O objeto Banco para realizar a operação.
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Carro na faixa de preço especificada.
     */
    public List<Carro> buscarCarrosPorFaixaPreco(Banco banco, double precoMinimo, double precoMaximo) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE preco >= ? AND preco <= ?";

        try (ResultSet rs = banco.querySelect(sql, precoMinimo, precoMaximo)) {
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
     * @param banco               O objeto Banco para realizar a operação.
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Carro com quilometragem igual ou menor.
     */
    public List<Carro> buscarCarrosPorQuilometragem(Banco banco, double quilometragemMaxima) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE quilometragem <= ?";

        try (ResultSet rs = banco.querySelect(sql, quilometragemMaxima)) {
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
     * @param banco     O objeto Banco para realizar a operação.
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Carro na faixa de ano especificada.
     */
    public List<Carro> buscarCarrosPorFaixaAno(Banco banco, int anoMinimo, int anoMaximo) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE ano_fabricacao >= ? AND ano_fabricacao <= ?";

        try (ResultSet rs = banco.querySelect(sql, anoMinimo, anoMaximo)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param cor   A cor do carro a ser buscada.
     * @return Uma lista de objetos Carro que correspondem à cor fornecida.
     */
    public List<Carro> buscarCarrosPorCor(Banco banco, String cor) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE cor = ?";

        try (ResultSet rs = banco.querySelect(sql, cor)) {
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
     * @param banco          O objeto Banco para realizar a operação.
     * @param potenciaMinima A potência mínima desejada.
     * @return Uma lista de objetos Carro que possuem potência igual ou maior.
     */
    public List<Carro> buscarCarrosPorPotencia(Banco banco, double potenciaMinima) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE cavalo_potencia >= ?";

        try (ResultSet rs = banco.querySelect(sql, potenciaMinima)) {
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
     * @param banco        O objeto Banco para realizar a operação.
     * @param numeroPortas O número de portas desejado.
     * @return Uma lista de objetos Carro que possuem o número de portas
     *         especificado.
     */
    public List<Carro> buscarCarrosPorNumeroPortas(Banco banco, int numeroPortas) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE numero_portas = ?";

        try (ResultSet rs = banco.querySelect(sql, numeroPortas)) {
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
     * @param banco           O objeto Banco para realizar a operação.
     * @param tipoCombustivel O tipo de combustível desejado.
     * @return Uma lista de objetos Carro que usam o combustível especificado.
     */
    public List<Carro> buscarCarrosPorCombustivel(Banco banco, String tipoCombustivel) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE tipo_combustivel = ?";

        try (ResultSet rs = banco.querySelect(sql, tipoCombustivel)) {
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
