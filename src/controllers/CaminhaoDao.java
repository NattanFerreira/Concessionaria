package controllers;

import data.Banco;
import models.Caminhao;

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

    /**
     * Insere um novo caminhão no banco de dados.
     *
     * @param banco    O objeto Banco para realizar a operação.
     * @param caminhao O objeto Caminhao a ser persistido.
     */
    public void cadastrarCaminhao(Banco banco, Caminhao caminhao) {
        String sql = "INSERT INTO caminhoes (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int id = banco.queryInsertComRetorno(sql,
                caminhao.getModelo(),
                caminhao.getNumChassi(),
                caminhao.getQuilometragem(),
                caminhao.getPreco(),
                caminhao.getCor(),
                caminhao.getAnoFabricacao(),
                caminhao.getIdStatus(),
                caminhao.getEixo(),
                caminhao.getCapacidadeCarga(),
                caminhao.getAltura(),
                caminhao.getTipoCarroceria());

        if (id > 0) {
            caminhao.setId(id);
        }
    }

    /**
     * Busca e retorna todos os caminhões cadastrados no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Caminhao.
     */
    public List<Caminhao> listarCaminhoes(Banco banco) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes";

        try (ResultSet rs = banco.querySelect(sql)) {
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
     * @param banco    O objeto Banco para realizar a operação.
     * @param caminhao O objeto Caminhao com os dados atualizados e o ID do registro
     *                 a ser alterado.
     */
    public void atualizarCaminhao(Banco banco, Caminhao caminhao) {
        String sql = "UPDATE caminhoes SET modelo = ?, num_chassi = ?, quilometragem = ?, preco = ?, cor = ?, ano_fabricacao = ?, id_status = ?, eixos = ?, capacidade_carga = ?, altura = ?, tipo_carroceria = ? WHERE id = ?";

        banco.queryUpdate(sql,
                caminhao.getModelo(),
                caminhao.getNumChassi(),
                caminhao.getQuilometragem(),
                caminhao.getPreco(),
                caminhao.getCor(),
                caminhao.getAnoFabricacao(),
                caminhao.getIdStatus(),
                caminhao.getEixo(),
                caminhao.getCapacidadeCarga(),
                caminhao.getAltura(),
                caminhao.getTipoCarroceria(),
                caminhao.getId());
    }

    /**
     * Exclui um caminhão do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do caminhão a ser excluído.
     */
    public void excluirCaminhao(Banco banco, int id) {
        String sql = "DELETE FROM caminhoes WHERE id = ?";
        banco.queryUpdate(sql, id);
    }

    /**
     * Busca um caminhão pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do caminhão a ser buscado.
     * @return Um objeto Caminhao ou null se não encontrado.
     */
    public Caminhao buscarCaminhaoPorId(Banco banco, int id) {
        Caminhao caminhao = null;
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE id = ?";

        try (ResultSet rs = banco.querySelect(sql, id)) {
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
     * @param banco  O objeto Banco para realizar a operação.
     * @param modelo O modelo do caminhão a ser buscado.
     * @return Uma lista de objetos Caminhao que correspondem ao modelo fornecido.
     */
    public List<Caminhao> buscarCaminhoesPorModelo(Banco banco, String modelo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE modelo = ?";

        try (ResultSet rs = banco.querySelect(sql, modelo)) {
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
     * @param banco     O objeto Banco para realizar a operação.
     * @param numChassi O número do chassi do caminhão.
     * @return O objeto Caminhao encontrado ou null se não existir.
     */
    public Caminhao buscarCaminhaoPorChassi(Banco banco, int numChassi) {
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE num_chassi = ?";

        try (ResultSet rs = banco.querySelect(sql, numChassi)) {
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
     * @param banco       O objeto Banco para realizar a operação.
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Caminhao na faixa de preço especificada.
     */
    public List<Caminhao> buscarCaminhoesPorFaixaPreco(Banco banco, double precoMinimo, double precoMaximo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE preco >= ? AND preco <= ?";

        try (ResultSet rs = banco.querySelect(sql, precoMinimo, precoMaximo)) {
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
     * @param banco               O objeto Banco para realizar a operação.
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Caminhao com quilometragem igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorQuilometragem(Banco banco, double quilometragemMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE quilometragem <= ?";

        try (ResultSet rs = banco.querySelect(sql, quilometragemMaxima)) {
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
     * @param banco     O objeto Banco para realizar a operação.
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Caminhao na faixa de ano especificada.
     */
    public List<Caminhao> buscarCaminhoesPorFaixaAno(Banco banco, int anoMinimo, int anoMaximo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE ano_fabricacao >= ? AND ano_fabricacao <= ?";

        try (ResultSet rs = banco.querySelect(sql, anoMinimo, anoMaximo)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param cor   A cor do caminhão a ser buscada.
     * @return Uma lista de objetos Caminhao que correspondem à cor fornecida.
     */
    public List<Caminhao> buscarCaminhoesPorCor(Banco banco, String cor) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE cor = ?";

        try (ResultSet rs = banco.querySelect(sql, cor)) {
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
     * @param banco O objeto Banco para realizar a operação.
     * @param eixo  O número de eixos desejado.
     * @return Uma lista de objetos Caminhao que possuem o número de eixos
     *         especificado.
     */
    public List<Caminhao> buscarCaminhoesPorEixo(Banco banco, int eixo) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE eixos = ?";

        try (ResultSet rs = banco.querySelect(sql, eixo)) {
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
     * @param banco            O objeto Banco para realizar a operação.
     * @param capacidadeMaxima A capacidade de carga máxima desejada.
     * @return Uma lista de objetos Caminhao que possuem capacidade igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorCapacidadeCarga(Banco banco, double capacidadeMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE capacidade_carga <= ?";

        try (ResultSet rs = banco.querySelect(sql, capacidadeMaxima)) {
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
     * @param banco        O objeto Banco para realizar a operação.
     * @param alturaMaxima A altura máxima desejada.
     * @return Uma lista de objetos Caminhao que possuem altura igual ou menor.
     */
    public List<Caminhao> buscarCaminhoesPorAltura(Banco banco, double alturaMaxima) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE altura <= ?";

        try (ResultSet rs = banco.querySelect(sql, alturaMaxima)) {
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
     * @param banco          O objeto Banco para realizar a operação.
     * @param tipoCarroceria O tipo de carroceria desejado.
     * @return Uma lista de objetos Caminhao que possuem o tipo de carroceria
     *         especificado.
     */
    public List<Caminhao> buscarCaminhoesPorTipoCarroceria(Banco banco, String tipoCarroceria) {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, eixos, capacidade_carga, altura, tipo_carroceria FROM caminhoes WHERE tipo_carroceria = ?";

        try (ResultSet rs = banco.querySelect(sql, tipoCarroceria)) {
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
