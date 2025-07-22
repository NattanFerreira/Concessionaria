package controllers;

import data.Banco;
import models.Venda;
import utils.Funcoes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import models.Veiculo;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Venda.
 * É responsável por todas as operações de banco de dados (CRUD) relacionadas às
 * vendas.
 */
public class VendaDao {

    /**
     * Insere uma nova venda no banco de dados.
     * 
     * @param banco O objeto Banco para realizar a operação.
     * @param venda O objeto Venda a ser persistido.
     */
    public void criarVenda(Banco banco, Venda venda) {
        String sql = String.format(Locale.US,
                "INSERT INTO Venda (valorTotal, idFuncionario, id_status) VALUES (%.2f, %d, 1)",
                venda.getValorTotal(),
                venda.getIdFuncionario());

        try {
            banco.queryInsup(sql);
        } catch (Exception e) {
            System.err.println("Erro ao criar venda: " + e.getMessage());
            Funcoes.pressEnterToContinue();
        }
    }

    /**
     * Adiciona veículos ao carrinho de uma venda.
     * 
     * @param banco    O objeto Banco para realizar a operação.
     * @param idVenda  O ID da venda à qual os veículos serão adicionados.
     * @param veiculos A lista de veículos a serem adicionados ao carrinho.
     */
    public void adicionarNoCarrinho(Banco banco, int idVenda, Veiculo veiculo) {
        int idTipoVeiculo = 0;

        if (veiculo instanceof models.Carro) {
            idTipoVeiculo = 1; // Carro
        } else if (veiculo instanceof models.Motocicleta) {
            idTipoVeiculo = 2; // Motocicleta
        } else if (veiculo instanceof models.Caminhao) {
            idTipoVeiculo = 3; // Caminhão
        } else {
            System.out.println("Erro: Tipo de veículo desconhecido.");
            return;
        }

        String sql = String.format("INSERT INTO Carrinho (idVenda, idVeiculo, idTipoVeiculo) VALUES (%d, %d, %d)",
                idVenda,
                veiculo.getId(),
                idTipoVeiculo);

        try {
            banco.queryInsup(sql);

            if (idTipoVeiculo == 1) {
                sql = String.format("UPDATE Carro SET id_status = 3 WHERE id = %d", veiculo.getId());
            } else if (idTipoVeiculo == 2) {
                sql = String.format("UPDATE Motocicleta SET id_status = 3 WHERE id = %d", veiculo.getId());
            } else if (idTipoVeiculo == 3) {
                sql = String.format("UPDATE Caminhao SET id_status = 3 WHERE id = %d", veiculo.getId());
            }

            try {
                banco.queryInsup(sql);
            } catch (Exception e) {
                System.err.println("Erro ao atualizar disponibilidade do veículo: " + e.getMessage());
                Funcoes.pressEnterToContinue();
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar veículo no carrinho: " + e.getMessage());
            Funcoes.pressEnterToContinue();
        }
    }

    public static void listarCarrinho(Banco banco, int idVenda) {
        String sql = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = %d", idVenda);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null) {
                boolean encontrouVeiculos = false;
                while (rs.next()) {
                    int idVeiculo = rs.getInt("idVeiculo");
                    int idTipoVeiculo = rs.getInt("idTipoVeiculo");

                    Veiculo veiculo = null;
                    if (idTipoVeiculo == 1) {
                        veiculo = new CarroDao().buscarCarroPorId(banco, idVeiculo);
                    } else if (idTipoVeiculo == 2) {
                        veiculo = new MotocicletaDao().buscarMotocicletaPorId(banco, idVeiculo);
                    } else if (idTipoVeiculo == 3) {
                        veiculo = new CaminhaoDao().buscarCaminhaoPorId(banco, idVeiculo);
                    }
                    if (veiculo != null) {
                        System.out.println(veiculo.toString());
                        encontrouVeiculos = true;
                    }
                }

                if (!encontrouVeiculos) {
                    System.out.println("Carrinho vazio.");
                }
            } else {
                System.out.println("Carrinho vazio.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar carrinho: " + e.getMessage());
        }
        Funcoes.pressEnterToContinue();
    }

    /**
     * Busca uma venda aberta pelo ID do funcionário.
     * 
     * @param banco         O objeto Banco para realizar a operação.
     * @param idFuncionario O ID do funcionário que está buscando a venda.
     * @return Um objeto Venda ou null se não houver venda aberta.
     */
    public Venda buscarVendaAbertaVenda(Banco banco, int idFuncionario) {
        Venda venda = null;
        String sql = String.format(
                "SELECT id, valorTotal, idFuncionario FROM Venda WHERE idFuncionario = %d AND id_status = 1",
                idFuncionario);
        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null && rs.next()) {
                venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setValorTotal(rs.getDouble("valorTotal"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda aberta: " + e.getMessage());
        }
        if (venda != null) {
            String sqlVeiculos = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = %d",
                    venda.getId());
            try (ResultSet rsVeiculos = banco.querySelect(sqlVeiculos)) {
                List<Veiculo> veiculos = new ArrayList<>();
                CarroDao carroDao = new CarroDao();
                CaminhaoDao caminhaoDao = new CaminhaoDao();
                MotocicletaDao motoDao = new MotocicletaDao();

                if (rsVeiculos != null) {
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
                }
                venda.setVeiculos(veiculos);
            } catch (SQLException e) {
                System.err.println("Erro ao buscar veículos da venda: " + e.getMessage());
            }
        }
        return venda;
    }

    /**
     * Busca uma venda aberta pelo ID do funcionário.
     * 
     * @param banco         O objeto Banco para realizar a operação.
     * @param idFuncionario O ID do funcionário que está buscando a venda.
     * @return Um objeto Venda ou null se não houver venda aberta.
     */
    public int buscarVendaAbertaInt(Banco banco, int idFuncionario) {
        int idVenda = -1;
        String sql = String.format("SELECT id FROM Venda WHERE idFuncionario = %d AND id_status = 1", idFuncionario);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null && rs.next()) {
                idVenda = (rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda aberta: " + e.getMessage());
        }

        return idVenda;
    }

    /**
     * Busca e retorna todas as vendas cadastradas no banco de dados.
     * 
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Venda.
     */
    public List<Venda> listarVendas(Banco banco) {
        List<Venda> vendas = new ArrayList<>();
        CarroDao carroDao = new CarroDao();
        CaminhaoDao caminhaoDao = new CaminhaoDao();
        MotocicletaDao motoDao = new MotocicletaDao();
        String sql = "SELECT id, valorTotal, idFuncionario FROM Venda";

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null) {
                while (rs.next()) {
                    Venda venda = new Venda();
                    venda.setId(rs.getInt("id"));
                    venda.setValorTotal(rs.getDouble("valorTotal"));
                    venda.setIdFuncionario(rs.getInt("idFuncionario"));
                    sql = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = %d",
                            rs.getInt("id"));
                    try (ResultSet rsVeiculos = banco.querySelect(sql)) {
                        List<Veiculo> veiculos = new ArrayList<>();
                        if (rsVeiculos != null) {
                            while (rsVeiculos.next()) {
                                if (rsVeiculos.getInt("idTipoVeiculo") == 1) {
                                    Veiculo carro = carroDao.buscarCarroPorId(banco, rsVeiculos.getInt("idVeiculo"));
                                    if (carro != null) {
                                        veiculos.add(carro);
                                    }
                                } else if (rsVeiculos.getInt("idTipoVeiculo") == 2) {
                                    Veiculo moto = motoDao.buscarMotocicletaPorId(banco,
                                            rsVeiculos.getInt("idVeiculo"));
                                    if (moto != null) {
                                        veiculos.add(moto);
                                    }
                                } else if (rsVeiculos.getInt("idTipoVeiculo") == 3) {
                                    Veiculo caminhao = caminhaoDao.buscarCaminhaoPorId(banco,
                                            rsVeiculos.getInt("idVeiculo"));
                                    if (caminhao != null) {
                                        veiculos.add(caminhao);
                                    }
                                }
                            }
                        }
                        if (!veiculos.isEmpty()) {
                            venda.setVeiculos(veiculos);
                        }
                        vendas.add(venda);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
        return vendas;
    }

    /**
     * Busca uma venda pelo seu ID.
     * 
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID da venda a ser buscada.
     * @return Um objeto Venda ou null se não for encontrado.
     */
    public Venda buscarVendaPorId(Banco banco, int id) {
        CarroDao carroDao = new CarroDao();
        CaminhaoDao caminhaoDao = new CaminhaoDao();
        MotocicletaDao motoDao = new MotocicletaDao();
        Venda venda = null;
        String sql = String.format("SELECT id, valorTotal, idFuncionario FROM Venda WHERE id = %d", id);
        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs != null && rs.next()) {
                venda = new Venda();
                venda.setId(id);
                venda.setValorTotal(rs.getDouble("valorTotal"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));

                sql = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = %d", id);
                try (ResultSet rsVeiculos = banco.querySelect(sql)) {
                    List<Veiculo> veiculos = new ArrayList<>();
                    if (rsVeiculos != null) {
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
                                Veiculo caminhao = caminhaoDao.buscarCaminhaoPorId(banco,
                                        rsVeiculos.getInt("idVeiculo"));
                                if (caminhao != null) {
                                    veiculos.add(caminhao);
                                }
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

    public void finalizarVenda(Banco banco, int idVenda) {
        String sql = String.format("UPDATE Venda SET id_status = 2 WHERE id = %d", idVenda);
        try {
            banco.queryInsup(sql);
            sql = String.format("SELECT idVeiculo, idTipoVeiculo FROM Carrinho WHERE idVenda = %d", idVenda);
            try (ResultSet rs = banco.querySelect(sql)) {
                if (rs != null) {
                    while (rs.next()) {
                        int idVeiculo = rs.getInt("idVeiculo");
                        int idTipoVeiculo = rs.getInt("idTipoVeiculo");
                        String updateStatus = "";
                        if (idTipoVeiculo == 1) {
                            updateStatus = String.format("UPDATE Carro SET id_status = 2 WHERE id = %d", idVeiculo);
                        } else if (idTipoVeiculo == 2) {
                            updateStatus = String.format("UPDATE Motocicleta SET id_status = 2 WHERE id = %d",
                                    idVeiculo);
                        } else if (idTipoVeiculo == 3) {
                            updateStatus = String.format("UPDATE Caminhao SET id_status = 2 WHERE id = %d",
                                    idVeiculo);
                        }
                        banco.queryInsup(updateStatus);
                    }
                }
                System.out.println("Venda finalizada com sucesso.");
                Funcoes.pressEnterToContinue();
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar status dos veículos: " + e.getMessage());
                Funcoes.pressEnterToContinue();
            }
        } catch (Exception e) {
            System.err.println("Erro ao finalizar venda: " + e.getMessage());
            Funcoes.pressEnterToContinue();
        }
    }

    /**
     * Exclui uma venda do banco de dados pelo seu ID.
     * 
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID da venda a ser excluída.
     */
    public void excluirVenda(Banco banco, int id) {
        Venda venda = buscarVendaPorId(banco, id);
        if (venda != null) {
            String sql = String.format("DELETE FROM Venda WHERE id = %d", id);
            try {
                banco.queryInsup(sql);
                System.out.println("Venda excluída com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao excluir venda: " + e.getMessage());
                Funcoes.pressEnterToContinue();
            }
            List<Veiculo> veiculos = venda.getVeiculos();
            for (Veiculo veiculo : veiculos) {
                String updateStatus = "";
                if (veiculo instanceof models.Carro) {
                    updateStatus = String.format("UPDATE Carro SET id_status = 1 WHERE id = %d", veiculo.getId());
                } else if (veiculo instanceof models.Motocicleta) {
                    updateStatus = String.format("UPDATE Motocicleta SET id_status = 1 WHERE id = %d",
                            veiculo.getId());
                } else if (veiculo instanceof models.Caminhao) {
                    updateStatus = String.format("UPDATE Caminhao SET id_status = 1 WHERE id = %d", veiculo.getId());
                }
                try {
                    banco.queryInsup(updateStatus);
                } catch (Exception e) {
                    System.err.println("Erro ao atualizar status do veículo: " + e.getMessage());
                    Funcoes.pressEnterToContinue();
                }
            }
        } else {
            System.out.println("Venda não encontrada.");
        }
    }
}
