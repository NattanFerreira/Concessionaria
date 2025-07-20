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
     */
    public void cadastrarCarro(Banco banco, Carro carro) {
        String sql = String.format(
                "INSERT INTO carros (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel) VALUES ('%s', %d, %.2f, %.2f, '%s', %d, %d, %.2f, %d, '%s')",
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
        banco.queryInsup(sql);
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
        String sql = String.format(
                "UPDATE carros SET modelo = '%s', num_chassi = %d, quilometragem = %.2f, preco = %.2f, cor = '%s', ano_fabricacao = %d, id_status = %d, cavalo_potencia = %.2f, numero_portas = %d, tipo_combustivel = '%s' WHERE id = %d",
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
        banco.queryInsup(sql);
    }

    /**
     * Exclui um carro do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do carro a ser excluído.
     */
    public void excluirCarro(Banco banco, int id) {
        String sql = String.format("DELETE FROM carros WHERE id = %d", id);
        banco.queryInsup(sql);
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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE id = %d",
                id);

        try (ResultSet rs = banco.querySelect(sql)) {
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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE modelo = '%s'",
                modelo);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE num_chassi = %d",
                numChassi);

        try (ResultSet rs = banco.querySelect(sql)) {
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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE preco >= %.2f AND preco <= %.2f",
                precoMinimo, precoMaximo);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE quilometragem <= %.2f",
                quilometragemMaxima);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE ano_fabricacao >= %d AND ano_fabricacao <= %d",
                anoMinimo, anoMaximo);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE cor = '%s'",
                cor);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE cavalo_potencia >= %.2f",
                potenciaMinima);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE numero_portas = %d",
                numeroPortas);

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
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, tipo_combustivel FROM carros WHERE tipo_combustivel = '%s'",
                tipoCombustivel);

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
            System.err.println("Erro ao buscar carros por combustível: " + e.getMessage());
        }
        return carros;
    }
}
