package controllers;

import data.Banco;
import models.Carro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Funcoes;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Carro.
 * É responsável por todas as operações de banco de dados (CRUD)
 * relacionadas aos carros.
 */
public class    CarroDao {

    /**
     * Insere um novo carro no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param carro O objeto Carro a ser persistido.
     */
    public void cadastrarCarro(Banco banco, Carro carro) {
        String sql = String.format(
                "INSERT INTO carros (modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel) VALUES ('%s', %s, %.2f, %.2f, '%s', %d, %d, %.2f, %d, '%d')",
                carro.getModelo(),
                carro.getNumChassi(),
                carro.getQuilometragem(),
                carro.getPreco(),
                carro.getCor(),
                carro.getAnoFabricacao(),
                carro.getIdStatus(),
                carro.getCavaloPotencia(),
                carro.getNumeroPortas(),
                carro.getIdTipoCombustivel());
        banco.queryInsup(sql);
    }

    /**
     * Adiciona um novo carro ao estoque, solicitando os dados do usuário.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void adicionarCarro(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Adicionar Carro");

        // Leitura e validação do modelo
        String modelo;
        do {
            System.out.println("Digite o modelo do carro:");
            modelo = Funcoes.lerString();
            if (modelo == null) {
                System.out.println("Modelo não pode estar vazio. Tente novamente.");
            }
        } while (modelo == null);

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
            if (cor == null) {
                System.out.println("Cor não pode estar vazia. Tente novamente.");
            }
        } while (cor == null);

        // Leitura e validação do ano de fabricação
        int anoFabricacao;
        do {
            System.out.println("Digite o ano de fabricação:");
            anoFabricacao = Funcoes.lerInt();
            if (anoFabricacao < 1900 || anoFabricacao > 2025) {
                System.out.println("Ano de fabricação deve estar entre 1900 e 2025. Tente novamente.");
            }
        } while (anoFabricacao < 1900 || anoFabricacao > 2025);

        // Leitura e validação do status
        int idStatus;
        do {
            System.out.println("Digite o ID do status (1 - Disponível, 2 - Vendido, 3 - Reservado):");
            idStatus = Funcoes.lerInt();
            if (idStatus < 1 || idStatus > 3) {
                System.out.println("Status deve ser 1, 2 ou 3. Tente novamente.");
            }
        } while (idStatus < 1 || idStatus > 3);

        // Leitura e validação da potência
        double cavaloPotencia;
        do {
            System.out.println("Digite a potência em cavalos:");
            cavaloPotencia = Funcoes.lerDouble();
            if (cavaloPotencia <= 0) {
                System.out.println("Potência deve ser um valor positivo. Tente novamente.");
            }
        } while (cavaloPotencia <= 0);

        // Leitura e validação do número de portas
        int numeroPortas;
        do {
            System.out.println("Digite o número de portas:");
            numeroPortas = Funcoes.lerInt();
            if (numeroPortas != 2 && numeroPortas != 4 && numeroPortas != 5) {
                System.out.println("Número de portas deve ser 2, 4 ou 5. Tente novamente.");
            }
        } while (numeroPortas != 2 && numeroPortas != 4 && numeroPortas != 5);

        // Leitura e validação do tipo de combustível
        int idTipoCombustivel;
        do {
            System.out.println("Digite o tipo de combustível (0 - Gasolina, 1 - Etanol, 2 - Diesel, 3 - Elétrico):");
            idTipoCombustivel = Funcoes.lerInt();
            if (idTipoCombustivel < 0 || idTipoCombustivel > 3) {
                System.out.println("Tipo de combustível deve ser 0, 1, 2 ou 3. Tente novamente.");
            }
        } while (idTipoCombustivel < 0 || idTipoCombustivel > 3);

        Carro carro = new Carro(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus,
                cavaloPotencia, numeroPortas, idTipoCombustivel);

        cadastrarCarro(banco, carro);
        System.out.println("Carro adicionado com sucesso!");
    }

    /**
     * Atualiza os dados de um carro existente, solicitando os novos dados do usuário.
     *
     * @param banco O objeto Banco para realizar a operação.
     */
    public void atualizarCarro(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Atualizar Carro");

        System.out.println("Carros disponíveis:");
        List<Carro> carros = listarCarros(banco);
        for (Carro carro : carros) {
            System.out.println("ID: " + carro.getId() + " - Modelo: " + carro.getModelo());
        }

        System.out.println("Digite o ID do carro a ser atualizado:");
        int id = Funcoes.lerInt();

        // Buscar carro existente
        Carro carroExistente = buscarCarroPorId(banco, id);
        if (carroExistente == null) {
            System.out.println("Carro não encontrado com o ID: " + id);
            return;
        }

        System.out.println("Carro atual: " + carroExistente.getModelo());

        // Leitura e validação do modelo
        String modelo;
        do {
            System.out.println("Digite o novo modelo do carro (atual: " + carroExistente.getModelo() + "):");
            modelo = Funcoes.lerString();
            if (modelo == null || modelo.trim().isEmpty()) {
                System.out.println("Modelo não pode estar vazio. Tente novamente.");
            }
        } while (modelo == null || modelo.trim().isEmpty());

        // Leitura e validação do número do chassi
        String numChassi;
        do {
            System.out.println("Digite o novo número do chassi (atual: " + carroExistente.getNumChassi() + "):");
            numChassi = Funcoes.lerString();
            if (numChassi == null) {
                System.out.println("Número do chassi não pode estar vazio. Tente novamente.");
            }
        } while (numChassi == null);

        // Leitura e validação da quilometragem
        double quilometragem;
        do {
            System.out.println("Digite a nova quilometragem (atual: " + carroExistente.getQuilometragem() + "):");
            quilometragem = Funcoes.lerDouble();
            if (quilometragem < 0) {
                System.out.println("Quilometragem não pode ser negativa. Tente novamente.");
            }
        } while (quilometragem < 0);

        // Leitura e validação do preço
        double preco;
        do {
            System.out.println("Digite o novo preço (atual: " + carroExistente.getPreco() + "):");
            preco = Funcoes.lerDouble();
            if (preco <= 0) {
                System.out.println("Preço deve ser um valor positivo. Tente novamente.");
            }
        } while (preco <= 0);

        // Leitura e validação da cor
        String cor;
        do {
            System.out.println("Digite a nova cor (atual: " + carroExistente.getCor() + "):");
            cor = Funcoes.lerString();
            if (cor == null || cor.trim().isEmpty()) {
                System.out.println("Cor não pode estar vazia. Tente novamente.");
            }
        } while (cor == null || cor.trim().isEmpty());

        // Leitura e validação do ano de fabricação
        int anoFabricacao;
        do {
            System.out.println("Digite o novo ano de fabricação (atual: " + carroExistente.getAnoFabricacao() + "):");
            anoFabricacao = Funcoes.lerInt();
            if (anoFabricacao < 1900 || anoFabricacao > 2025) {
                System.out.println("Ano de fabricação deve estar entre 1900 e 2025. Tente novamente.");
            }
        } while (anoFabricacao < 1900 || anoFabricacao > 2025);

        // Leitura e validação do status
        int idStatus;
        do {
            System.out.println("Digite o novo ID do status (1 - Disponível, 2 - Vendido, 3 - Reservado) (atual: "
                    + carroExistente.getIdStatus() + "):");
            idStatus = Funcoes.lerInt();
            if (idStatus < 1 || idStatus > 3) {
                System.out.println("Status deve ser 1, 2 ou 3. Tente novamente.");
            }
        } while (idStatus < 1 || idStatus > 3);

        // Leitura e validação da potência
        double cavaloPotencia;
        do {
            System.out
                    .println("Digite a nova potência em cavalos (atual: " + carroExistente.getCavaloPotencia() + "):");
            cavaloPotencia = Funcoes.lerDouble();
            if (cavaloPotencia <= 0) {
                System.out.println("Potência deve ser um valor positivo. Tente novamente.");
            }
        } while (cavaloPotencia <= 0);

        // Leitura e validação do número de portas
        int numeroPortas;
        do {
            System.out.println("Digite o novo número de portas (atual: " + carroExistente.getNumeroPortas() + "):");
            numeroPortas = Funcoes.lerInt();
            if (numeroPortas != 2 && numeroPortas != 4 && numeroPortas != 5) {
                System.out.println("Número de portas deve ser 2, 4 ou 5. Tente novamente.");
            }
        } while (numeroPortas != 2 && numeroPortas != 4 && numeroPortas != 5);

        // Leitura e validação do tipo de combustível
        int idTipoCombustivel;
        do {
            System.out.println(
                    "Digite o novo tipo de combustível (0 - Gasolina, 1 - Etanol, 2 - Diesel, 3 - Elétrico) (atual: "
                            + carroExistente.getIdTipoCombustivel() + "):");
            idTipoCombustivel = Funcoes.lerInt();
            if (idTipoCombustivel < 0 || idTipoCombustivel > 3) {
                System.out.println("Tipo de combustível deve ser 0, 1, 2 ou 3. Tente novamente.");
            }
        } while (idTipoCombustivel < 0 || idTipoCombustivel > 3);

        Carro carro = new Carro(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus,
                cavaloPotencia, numeroPortas, idTipoCombustivel);
        carro.setId(id);

        atualizarCarro(banco, carro);
        System.out.println("Carro atualizado com sucesso!");
    }

    /**
     * Remove um carro do banco de dados pelo seu ID.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @param id    O ID do carro a ser removido.
     */
    public void removerCarro(Banco banco) {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Remover Carro");

        System.out.println("Carros disponíveis:");
        List<Carro> carros = listarCarros(banco);
        for (Carro carro : carros) {
            System.out.println("ID: " + carro.getId() + " - Modelo: " + carro.getModelo());
        }

        System.out.println("Digite o ID do carro a ser removido:");
        int id = Funcoes.lerInt();

        // Verifica se o carro existe
        Carro carroExistente = buscarCarroPorId(banco, id);
        if (carroExistente == null) {
            System.out.println("Carro não encontrado com o ID: " + id);
            return;
        }

        excluirCarro(banco, id);
        System.out.println("Carro removido com sucesso!");
    }

    /**
     * Busca e retorna todos os carros cadastrados no banco de dados.
     *
     * @param banco O objeto Banco para realizar a operação.
     * @return Uma lista de objetos Carro.
     */
    public List<Carro> listarCarros(Banco banco) {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros";

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "UPDATE carros SET modelo = '%s', num_chassi = %s, quilometragem = %.2f, preco = %.2f, cor = '%s', ano_fabricacao = %d, id_status = %d, cavalo_potencia = %.2f, numero_portas = %d, id_tipo_combustivel = '%d' WHERE id = %d",
                carro.getModelo(),
                carro.getNumChassi(),
                carro.getQuilometragem(),
                carro.getPreco(),
                carro.getCor(),
                carro.getAnoFabricacao(),
                carro.getIdStatus(),
                carro.getCavaloPotencia(),
                carro.getNumeroPortas(),
                carro.getIdTipoCombustivel(),
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE id = %d",
                id);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE modelo = '%s'",
                modelo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
    public Carro buscarCarroPorChassi(Banco banco, String numChassi) {
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE num_chassi = %s", numChassi);

        try (ResultSet rs = banco.querySelect(sql)) {
            if (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE preco >= %.2f AND preco <= %.2f",
                precoMinimo, precoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE quilometragem <= %.2f",
                quilometragemMaxima);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE ano_fabricacao >= %d AND ano_fabricacao <= %d",
                anoMinimo, anoMaximo);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE cor = '%s'",
                cor);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE cavalo_potencia >= %.2f",
                potenciaMinima);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE numero_portas = %d",
                numeroPortas);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
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
    public List<Carro> buscarCarrosPorCombustivel(Banco banco, int idTipoCombustivel) {
        List<Carro> carros = new ArrayList<>();
        String sql = String.format(
                "SELECT id, modelo, num_chassi, quilometragem, preco, cor, ano_fabricacao, id_status, cavalo_potencia, numero_portas, id_tipo_combustivel FROM carros WHERE id_tipo_combustivel = '%d'",
                idTipoCombustivel);

        try (ResultSet rs = banco.querySelect(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("num_chassi"),
                        rs.getDouble("quilometragem"),
                        rs.getDouble("preco"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getInt("id_status"),
                        rs.getDouble("cavalo_potencia"),
                        rs.getInt("numero_portas"),
                        rs.getInt("id_tipo_combustivel"));
                carro.setId(rs.getInt("id"));

                carros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por combustível: " + e.getMessage());
        }
        return carros;
    }
}
