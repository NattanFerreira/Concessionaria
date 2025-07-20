package controllers;

import data.Banco;
import models.Motocicleta;

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

    /**
     * Insere uma nova motocicleta no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param motocicleta O objeto Motocicleta a ser persistido.
     */
    public void cadastrarMotocicleta(Banco banco, Motocicleta motocicleta) {
        String sql = String.format("INSERT INTO motocicletas (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada) VALUES ('%s', %d, %.2f, %.2f, '%s', %d, %d, %d)",
            motocicleta.getModelo(),
            motocicleta.getNumChassi(),
            motocicleta.getQuilometragem(),
            motocicleta.getPreco(),
            motocicleta.getCor(),
            motocicleta.getAnoFabricacao(),
            motocicleta.getIdStatus(),
            motocicleta.getCilindrada());
        banco.queryInsup(sql);
    }

    /**
     * Busca e retorna todas as motocicletas cadastradas no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Motocicleta.
     */
    public List<Motocicleta> listarMotocicletas(Banco banco) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas";

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param motocicleta O objeto Motocicleta com os dados atualizados e o ID do registro
     *                   a ser alterado.
     */
    public void atualizarMotocicleta(Banco banco, Motocicleta motocicleta) {
        String sql = String.format("UPDATE motocicletas SET modelo = '%s', num_chassi = %d, quilometragem = %.2f, preco = %.2f, cor = '%s', ano_fabricacao = %d, id_status = %d, cilindrada = %d WHERE id = %d",
            motocicleta.getModelo(),
            motocicleta.getNumChassi(),
            motocicleta.getQuilometragem(),
            motocicleta.getPreco(),
            motocicleta.getCor(),
            motocicleta.getAnoFabricacao(),
            motocicleta.getIdStatus(),
            motocicleta.getCilindrada(),
            motocicleta.getId());
        banco.queryInsup(sql);
    }

    /**
     * Exclui uma motocicleta do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id O ID da motocicleta a ser excluída.
     */
    public void excluirMotocicleta(Banco banco, int id) {
        String sql = String.format("DELETE FROM motocicletas WHERE id = %d", id);
        banco.queryInsup(sql);
    }

    /**
     * Busca uma motocicleta pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id O ID da motocicleta a ser buscada.
     * @return Um objeto Motocicleta ou null se não encontrado.
     */
    public Motocicleta buscarMotocicletaPorId(Banco banco, int id) {
        Motocicleta motocicleta = null;
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE id = %d", id);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param modelo O modelo da motocicleta a ser buscado.
     * @return Uma lista de objetos Motocicleta que correspondem ao modelo fornecido.
     */
    public List<Motocicleta> buscarMotocicletasPorModelo(Banco banco, String modelo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE modelo = '%s'", modelo);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param numChassi O número do chassi da motocicleta.
     * @return O objeto Motocicleta encontrado ou null se não existir.
     */
    public Motocicleta buscarMotocicletaPorChassi(Banco banco, int numChassi) {
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE num_chassi = %d", numChassi);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Motocicleta na faixa de preço especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaPreco(Banco banco, double precoMinimo, double precoMaximo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE preco >= %.2f AND preco <= %.2f", precoMinimo, precoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Motocicleta com quilometragem igual ou menor.
     */
    public List<Motocicleta> buscarMotocicletasPorQuilometragem(Banco banco, double quilometragemMaxima) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE quilometragem <= %.2f", quilometragemMaxima);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Motocicleta na faixa de ano especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaAno(Banco banco, int anoMinimo, int anoMaximo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE ano_fabricacao >= %d AND ano_fabricacao <= %d", anoMinimo, anoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param cor A cor da motocicleta a ser buscada.
     * @return Uma lista de objetos Motocicleta que correspondem à cor fornecida.
     */
    public List<Motocicleta> buscarMotocicletasPorCor(Banco banco, String cor) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cor = '%s'", cor);

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param cilindrada A cilindrada desejada.
     * @return Uma lista de objetos Motocicleta que possuem a cilindrada especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorCilindrada(Banco banco, int cilindrada) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format("SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cilindrada = %d", cilindrada);

        try (ResultSet rs = banco.querySelect(sql)) {
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
}
