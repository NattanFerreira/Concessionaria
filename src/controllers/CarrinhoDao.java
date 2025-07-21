package controllers;

import data.Banco;
import models.ItemCarrinho;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class CarrinhoDao {
    private final Banco banco;

    public CarrinhoDao() {
        this.banco = new Banco();
    }

    public ItemCarrinho adicionarItemCarrinho(ItemCarrinho itemCarrinho) {
        String sql = "INSERT INTO carrinho (tipo_veiculo, id_veiculo, id_vendedor) VALUES (?, ?, ?)";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, itemCarrinho.getTipoVeiculo());
            pstmt.setInt(2, itemCarrinho.getIdVeiculo());
            pstmt.setInt(3, itemCarrinho.getIdVendedor());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        itemCarrinho.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar item ao carrinho: " + e.getMessage());
        }
        return itemCarrinho;
    }

    public List<ItemCarrinho> listarItensCarrinho(int idVendedor) {
        List<ItemCarrinho> itens = new ArrayList<>();
        String sql = "SELECT id, tipo_veiculo, id_veiculo, id_vendedor, data_adicao FROM carrinho WHERE id_vendedor = ? ORDER BY data_adicao DESC";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idVendedor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ItemCarrinho item = new ItemCarrinho();
                item.setId(rs.getInt("id"));
                item.setTipoVeiculo(rs.getString("tipo_veiculo"));
                item.setIdVeiculo(rs.getInt("id_veiculo"));
                item.setIdVendedor(rs.getInt("id_vendedor"));
                
                // Converte timestamp para LocalDateTime
                Timestamp timestamp = rs.getTimestamp("data_adicao");
                if (timestamp != null) {
                    item.setDataAdicao(timestamp.toLocalDateTime());
                }

                itens.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar itens do carrinho: " + e.getMessage());
        }
        return itens;
    }

    public void removerItemCarrinho(int idItem) {
        String sql = "DELETE FROM carrinho WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idItem);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover item do carrinho: " + e.getMessage());
        }
    }

    public void removerVeiculoDoCarrinho(String tipoVeiculo, int idVeiculo, int idVendedor) {
        String sql = "DELETE FROM carrinho WHERE tipo_veiculo = ? AND id_veiculo = ? AND id_vendedor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoVeiculo);
            pstmt.setInt(2, idVeiculo);
            pstmt.setInt(3, idVendedor);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover veículo do carrinho: " + e.getMessage());
        }
    }

    public void limparCarrinho(int idVendedor) {
        String sql = "DELETE FROM carrinho WHERE id_vendedor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idVendedor);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao limpar carrinho: " + e.getMessage());
        }
    }

    public boolean veiculoJaNoCarrinho(String tipoVeiculo, int idVeiculo, int idVendedor) {
        String sql = "SELECT COUNT(*) FROM carrinho WHERE tipo_veiculo = ? AND id_veiculo = ? AND id_vendedor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoVeiculo);
            pstmt.setInt(2, idVeiculo);
            pstmt.setInt(3, idVendedor);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar veículo no carrinho: " + e.getMessage());
        }
        return false;
    }

    public int contarItensCarrinho(int idVendedor) {
        String sql = "SELECT COUNT(*) FROM carrinho WHERE id_vendedor = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idVendedor);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar itens do carrinho: " + e.getMessage());
        }
        return 0;
    }

    public ItemCarrinho buscarItemPorId(int idItem) {
        String sql = "SELECT id, tipo_veiculo, id_veiculo, id_vendedor, data_adicao FROM carrinho WHERE id = ?";

        try (Connection conn = this.banco.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idItem);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ItemCarrinho item = new ItemCarrinho();
                item.setId(rs.getInt("id"));
                item.setTipoVeiculo(rs.getString("tipo_veiculo"));
                item.setIdVeiculo(rs.getInt("id_veiculo"));
                item.setIdVendedor(rs.getInt("id_vendedor"));
                
                Timestamp timestamp = rs.getTimestamp("data_adicao");
                if (timestamp != null) {
                    item.setDataAdicao(timestamp.toLocalDateTime());
                }

                return item;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar item do carrinho: " + e.getMessage());
        }
        return null;
    }
}
