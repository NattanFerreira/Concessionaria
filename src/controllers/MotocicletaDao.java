package controllers;

import data.Banco;
import models.Motocicleta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Funcoes;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Motocicleta.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas às motocicletas.
 */
public class MotocicletaDao {

    /**
     * Insere uma nova motocicleta no banco de dados.
     *
     * @param banco       O objeto Banco para realizar a operação.
     * @param motocicleta O objeto Motocicleta a ser persistido.
     */
    public void cadastrarMotocicleta(Banco banco, Motocicleta motocicleta) {
        String sql = String.format(
                "INSERT INTO motocicletas (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada) VALUES ('%s', '%s', '%.2f', '%.2f', '%s', '%d', '%d', '%d')",
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
     * Adiciona uma nova motocicleta ao estoque, solicitando os dados do usuário.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void adicionarMotocicleta(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Adicionar Motocicleta");

        // Leitura e validação do modelo
        String modelo;
        do {
            System.out.println("Digite o modelo da motocicleta:");
            modelo = Funcoes.lerString();
            if (modelo == null || modelo.trim().isEmpty()) {
                System.out.println("Modelo não pode estar vazio. Tente novamente.");
            }
        } while (modelo == null || modelo.trim().isEmpty());

        // Leitura e validação do número do chassi
        String numChassi;
        do {
            System.out.println("Digite o número do chassi:");
            numChassi = Funcoes.lerString();
            if (numChassi == null) {
                System.out.println("Número do chassi não pode estar vazio. Tente novamente.");
            }
        } while (numChassi == null);

        // Leitura e validação da quilometragem
        double quilometragem;
        do {
            System.out.println("Digite a quilometragem:");
            quilometragem = Funcoes.lerDouble();
            if (quilometragem < 0) {
                System.out.println("Quilometragem não pode ser negativa. Tente novamente.");
            }
        } while (quilometragem < 0);

        // Leitura e validação do preço
        double preco;
        do {
            System.out.println("Digite o preço:");
            preco = Funcoes.lerDouble();
            if (preco <= 0) {
                System.out.println("Preço deve ser um valor positivo. Tente novamente.");
            }
        } while (preco <= 0);

        // Leitura e validação da cor
        String cor;
        do {
            System.out.println("Digite a cor:");
            cor = Funcoes.lerString();
            if (cor == null || cor.trim().isEmpty()) {
                System.out.println("Cor não pode estar vazia. Tente novamente.");
            }
        } while (cor == null || cor.trim().isEmpty());

        // Leitura e validação do ano de fabricação
        int anoFabricacao;
        do {
            System.out.println("Digite o ano de fabricação:");
            anoFabricacao = Funcoes.lerInt();
            if (anoFabricacao < 1900 || anoFabricacao > 2025) {
                System.out.println("Ano de fabricação deve estar entre 1900 e 2025. Tente novamente.");
            }
        } while (anoFabricacao < 1900 || anoFabricacao > 2025);

        // Leitura e validação da cilindrada
        int cilindrada;
        do {
            System.out.println("Digite a cilindrada:");
            cilindrada = Funcoes.lerInt();
            if (cilindrada < 50 || cilindrada > 2500) {
                System.out.println("Cilindrada deve estar entre 50cc e 2500cc. Tente novamente.");
            }
        } while (cilindrada < 50 || cilindrada > 2500);

        Motocicleta motocicleta = new Motocicleta(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, 0,
                cilindrada);

        cadastrarMotocicleta(banco, motocicleta);
        System.out.println("Motocicleta adicionada com sucesso!");
    }

    /**
     * Atualiza os dados de uma motocicleta existente, solicitando os novos dados do usuário.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void atualizarMotocicleta(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Atualizar Motocicleta");

        System.out.println("Motocicletas disponíveis:");
        List<Motocicleta> motocicletas = listarMotocicletas(banco);
        for (Motocicleta motocicleta : motocicletas) {
            System.out.println("ID: " + motocicleta.getId() + " - Modelo: " + motocicleta.getModelo());
        }

        System.out.println("Digite o ID da motocicleta a ser atualizada:");
        int id = Funcoes.lerInt();

        // Buscar motocicleta existente
        Motocicleta motocicletaExistente = buscarMotocicletaPorId(banco, id);
        if (motocicletaExistente == null) {
            System.out.println("Motocicleta não encontrada com o ID: " + id);
            return;
        }

        System.out.println("Motocicleta atual: " + motocicletaExistente.getModelo());

        // Leitura e validação do modelo
        String modelo;
        do {
            System.out
                    .println("Digite o novo modelo da motocicleta (atual: " + motocicletaExistente.getModelo() + "):");
            modelo = Funcoes.lerString();
            if (modelo == null || modelo.trim().isEmpty()) {
                System.out.println("Modelo não pode estar vazio. Tente novamente.");
            }
        } while (modelo == null || modelo.trim().isEmpty());

        // Leitura e validação do número do chassi
        String numChassi;
        do {
            System.out.println("Digite o novo número do chassi (atual: " + motocicletaExistente.getNumChassi() + "):");
            numChassi = Funcoes.lerString();
            if (numChassi == null) {
                System.out.println("Número do chassi não pode estar vazio. Tente novamente.");
            }
        } while (numChassi == null);

        // Leitura e validação da quilometragem
        double quilometragem;
        do {
            System.out.println("Digite a nova quilometragem (atual: " + motocicletaExistente.getQuilometragem() + "):");
            quilometragem = Funcoes.lerDouble();
            if (quilometragem < 0) {
                System.out.println("Quilometragem não pode ser negativa. Tente novamente.");
            }
        } while (quilometragem < 0);

        // Leitura e validação do preço
        double preco;
        do {
            System.out.println("Digite o novo preço (atual: " + motocicletaExistente.getPreco() + "):");
            preco = Funcoes.lerDouble();
            if (preco <= 0) {
                System.out.println("Preço deve ser um valor positivo. Tente novamente.");
            }
        } while (preco <= 0);

        // Leitura e validação da cor
        String cor;
        do {
            System.out.println("Digite a nova cor (atual: " + motocicletaExistente.getCor() + "):");
            cor = Funcoes.lerString();
            if (cor == null || cor.trim().isEmpty()) {
                System.out.println("Cor não pode estar vazia. Tente novamente.");
            }
        } while (cor == null || cor.trim().isEmpty());

        // Leitura e validação do ano de fabricação
        int anoFabricacao;
        do {
            System.out.println(
                    "Digite o novo ano de fabricação (atual: " + motocicletaExistente.getAnoFabricacao() + "):");
            anoFabricacao = Funcoes.lerInt();
            if (anoFabricacao < 1900 || anoFabricacao > 2025) {
                System.out.println("Ano de fabricação deve estar entre 1900 e 2025. Tente novamente.");
            }
        } while (anoFabricacao < 1900 || anoFabricacao > 2025);

        // Leitura e validação do status
        int idStatus;
        do {
            System.out.println("Motocicleta atual: " + motocicletaExistente.getIdStatus());
            System.out.println("Digite o novo ID do status (1 - Disponível, 2 - Vendido, 3 - Reservado):");
            idStatus = Funcoes.lerInt();
            if (idStatus < 1 || idStatus > 3) {
                System.out.println("Status deve ser 1, 2 ou 3. Tente novamente.");
            }
        } while (idStatus < 1 || idStatus > 3);

        // Leitura e validação da cilindrada
        int cilindrada;
        do {
            System.out.println("Digite a nova cilindrada (atual: " + motocicletaExistente.getCilindrada() + "):");
            cilindrada = Funcoes.lerInt();
            if (cilindrada < 50 || cilindrada > 2500) {
                System.out.println("Cilindrada deve estar entre 50cc e 2500cc. Tente novamente.");
            }
        } while (cilindrada < 50 || cilindrada > 2500);

        Motocicleta motocicleta = new Motocicleta(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus,
                cilindrada);
        motocicleta.setId(id);

        atualizarMotocicleta(banco, motocicleta);
        System.out.println("Motocicleta atualizada com sucesso!");
    }

    /**
 * Remove uma motocicleta do banco de dados pelo seu ID.
 *
 * @param banco O objeto Banco para realizar a operação.
 */
public void removerMotocicleta(Banco banco) {
    Funcoes.limpaTela();
    Funcoes.cabecalhoMenu("Remover Motocicleta");

    System.out.println("Motocicletas disponíveis:");
    List<Motocicleta> motocicletas = listarMotocicletas(banco);
    for (Motocicleta motocicleta : motocicletas) {
        System.out.println("ID: " + motocicleta.getId() + " - Modelo: " + motocicleta.getModelo());
    }

    System.out.println("Digite o ID da motocicleta a ser removida:");
    int id = Funcoes.lerInt();

    // Verifica se a motocicleta existe
    Motocicleta motocicletaExistente = buscarMotocicletaPorId(banco, id);
    if (motocicletaExistente == null) {
        System.out.println("Motocicleta não encontrada com o ID: " + id);
        return;
    }

    excluirMotocicleta(banco, id);
    System.out.println("Motocicleta removida com sucesso!");
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
                        rs.getString("num_chassi"),
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
     * @param banco       O objeto Banco para realizar a operação.
     * @param motocicleta O objeto Motocicleta com os dados atualizados e o ID do
     *                    registro
     *                    a ser alterado.
     */
    public void atualizarMotocicleta(Banco banco, Motocicleta motocicleta) {
        String sql = String.format(
                "UPDATE motocicletas SET modelo = '%s', num_chassi = '%s', quilometragem = '%.2f', preco = '%.2f', cor = '%s', ano_fabricacao = '%d', id_status = '%d', cilindrada = '%d' WHERE id = '%d'",
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
     * @param id    O ID da motocicleta a ser excluída.
     */
    public void excluirMotocicleta(Banco banco, int id) {
        String sql = String.format("DELETE FROM motocicletas WHERE id = '%d'", id);
        banco.queryInsup(sql);
    }

    /**
     * Busca uma motocicleta pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID da motocicleta a ser buscada.
     * @return Um objeto Motocicleta ou null se não encontrado.
     */
    public Motocicleta buscarMotocicletaPorId(Banco banco, int id) {
        Motocicleta motocicleta = null;
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE id = '%d'",
                id);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param banco  O objeto Banco para realizar a operação.
     * @param modelo O modelo da motocicleta a ser buscado.
     * @return Uma lista de objetos Motocicleta que correspondem ao modelo
     *         fornecido.
     */
    public List<Motocicleta> buscarMotocicletasPorModelo(Banco banco, String modelo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE modelo = '%s'",
                modelo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param banco     O objeto Banco para realizar a operação.
     * @param numChassi O número do chassi da motocicleta.
     * @return O objeto Motocicleta encontrado ou null se não existir.
     */
    public Motocicleta buscarMotocicletaPorChassi(Banco banco, String numChassi) {
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE num_chassi = '%s'",
                numChassi);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param banco       O objeto Banco para realizar a operação.
     * @param precoMinimo O preço mínimo desejado.
     * @param precoMaximo O preço máximo desejado.
     * @return Uma lista de objetos Motocicleta na faixa de preço especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaPreco(Banco banco, double precoMinimo, double precoMaximo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE preco >= '%.2f' AND preco <= '%.2f'",
                precoMinimo, precoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param banco               O objeto Banco para realizar a operação.
     * @param quilometragemMaxima A quilometragem máxima desejada.
     * @return Uma lista de objetos Motocicleta com quilometragem igual ou menor.
     */
    public List<Motocicleta> buscarMotocicletasPorQuilometragem(Banco banco, double quilometragemMaxima) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE quilometragem <= '%.2f'",
                quilometragemMaxima);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param banco     O objeto Banco para realizar a operação.
     * @param anoMinimo O ano mínimo desejado.
     * @param anoMaximo O ano máximo desejado.
     * @return Uma lista de objetos Motocicleta na faixa de ano especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorFaixaAno(Banco banco, int anoMinimo, int anoMaximo) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE ano_fabricacao >= '%d' AND ano_fabricacao <= '%d'",
                anoMinimo, anoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param cor   A cor da motocicleta a ser buscada.
     * @return Uma lista de objetos Motocicleta que correspondem à cor fornecida.
     */
    public List<Motocicleta> buscarMotocicletasPorCor(Banco banco, String cor) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cor = '%s'",
                cor);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
     * @param banco      O objeto Banco para realizar a operação.
     * @param cilindrada A cilindrada desejada.
     * @return Uma lista de objetos Motocicleta que possuem a cilindrada
     *         especificada.
     */
    public List<Motocicleta> buscarMotocicletasPorCilindrada(Banco banco, int cilindrada) {
        List<Motocicleta> motocicletas = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cilindrada FROM motocicletas WHERE cilindrada = '%d'",
                cilindrada);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Motocicleta motocicleta = new Motocicleta(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
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
