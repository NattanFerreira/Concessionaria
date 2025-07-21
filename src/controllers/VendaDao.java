package controllers;

import data.Banco;
import models.Venda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import models.Veiculo;
import controllers.CaminhaoDao;
import controllers.CarroDao;
import controllers.MotocicletaDao;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Venda.
 * É responsável por todas as operações de banco de dados (CRUD) relacionadas às vendas.
 */
public class VendaDao {

    /**
     * Insere uma nova venda no banco de dados.
     * @param banco O objeto Banco para realizar a operação.
     * @param venda O objeto Venda a ser persistido.
     */
    public void registrarVenda(Banco banco, Venda venda) {
        // Usar Locale.US para garantir que o separador decimal do valor seja '.'
        String sql = String.format(Locale.US, "INSERT INTO Venda (valorTotal, dataVenda, idVeiculo, idFuncionario) VALUES ('%.2f', '%s', '%d')",
                venda.getValorTotal(),
                venda.getDataVenda(), // Armazena a data como um número longo (timestamp)
                venda.getIdFuncionario());
        banco.queryInsup(sql);
    }

    /**
     * Busca e retorna todas as vendas cadastradas no banco de dados.
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Venda.
     */
    public List<Venda> listarVendas(Banco banco) {
        List<Venda> vendas = new ArrayList<>();
        CarroDao carroDao = new CarroDao();
        CaminhaoDao caminhaoDao = new CaminhaoDao();
        MotocicletaDao motoDao = new MotocicletaDao();
        String sql = "SELECT id, valorTotal, dataVenda, idFuncionario FROM Venda";

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setValorTotal(rs.getDouble("valorTotal"));
                venda.setDataVenda(rs.getString("dataVenda"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
                sql = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = '%d'", rs.getInt("id"));
                try (ResultSet rsVeiculos = banco.querySelect(sql)) {
                    List<Veiculo> veiculos = new ArrayList<>();
                    while (rsVeiculos.next()) {
                        if (rsVeiculos.getInt("idTipoVeiculo") == 1) {
                            Veiculo carro = carroDao.buscarCarroPorId(banco, rsVeiculos.getInt("idVeiculo"));
                            if (carro != null) {
                                veiculos.add(carro);
                            }
                        } else if (rsVeiculos.getInt("idTipoVeiculo") == 2) {
                            Veiculo moto = motoDao.buscarMotocicletaPorId(banco, rsVeiculos.getInt("idVeiculo"));
                            if (moto != null) {
                                veiculos.add(moto);
                            }
                        } else if (rsVeiculos.getInt("idTipoVeiculo") == 3) {
                            Veiculo caminhao = caminhaoDao.buscarCaminhaoPorId(banco, rsVeiculos.getInt("idVeiculo"));
                            if (caminhao != null) {
                                veiculos.add(caminhao);
                            }
                        }
                    }
                    if (!veiculos.isEmpty()) {
                        venda.setVeiculos(veiculos);
                    }
                    vendas.add(venda);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
        return vendas;
    }

    /**
     * Busca uma venda pelo seu ID.
     * @param banco O objeto Banco para realizar a operação.
     * @param id O ID da venda a ser buscada.
     * @return Um objeto Venda ou null se não for encontrado.
     */
    public Venda buscarVendaPorId(Banco banco, int id) {
        CarroDao carroDao = new CarroDao();
        CaminhaoDao caminhaoDao = new CaminhaoDao();
        MotocicletaDao motoDao = new MotocicletaDao();
        Venda venda = null;
        String sql = String.format("SELECT id, valorTotal, dataVenda, idFuncionario FROM Venda WHERE id = '%d'", id);
        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                venda = new Venda();
                venda.setId(id);
                venda.setValorTotal(rs.getDouble("valorTotal"));
                venda.setDataVenda(rs.getString("dataVenda"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));

                sql = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = '%d'", id);
                try (ResultSet rsVeiculos = banco.querySelect(sql)) {
                    List<Veiculo> veiculos = new ArrayList<>();
                    while (rsVeiculos.next()) {
                        if (rsVeiculos.getInt("idTipoVeiculo") == 1) {
                            Veiculo carro = carroDao.buscarCarroPorId(banco, rsVeiculos.getInt("idVeiculo"));
                            if (carro != null) {
                                veiculos.add(carro);
                            }
                        } else if (rsVeiculos.getInt("idTipoVeiculo") == 2) {
                            Veiculo moto = motoDao.buscarMotocicletaPorId(banco, rsVeiculos.getInt("idVeiculo"));
                            if (moto != null) {
                                veiculos.add(moto);
                            }
                        } else if (rsVeiculos.getInt("idTipoVeiculo") == 3) {
                            Veiculo caminhao = caminhaoDao.buscarCaminhaoPorId(banco, rsVeiculos.getInt("idVeiculo"));
                            if (caminhao != null) {
                                veiculos.add(caminhao);
                            }
                        }
                    }
                    if (!veiculos.isEmpty()) {
                        venda.setVeiculos(veiculos);
                    } else {
                        venda.setVeiculos(new ArrayList<>()); // Garante que a lista não seja nula
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda por ID: " + e.getMessage());
        }
        return venda;
    }

    /**
     * Exclui uma venda do banco de dados pelo seu ID.
     * @param banco O objeto Banco para realizar a operação.
     * @param id O ID da venda a ser excluída.
     */
    public void excluirVenda(Banco banco, int id) {
        String sql = String.format("DELETE FROM Venda WHERE id = '%d'", id);
        banco.queryInsup(sql);
    }
}