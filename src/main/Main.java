package main;
import controllers.CaminhaoDao;
import controllers.CarroDao;
import controllers.MotocicletaDao;
import data.Banco;
import models.Caminhao;
import models.Carro;
import models.Motocicleta;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // --- CONFIGURAÇÃO ---
        // Instancia o objeto de acesso ao banco de dados
        Banco banco = new Banco(); // Assumindo que a conexão é gerenciada dentro da classe Banco

        // Instancia os DAOs
        CarroDao carroDao = new CarroDao();
        MotocicletaDao motocicletaDao = new MotocicletaDao();
        CaminhaoDao caminhaoDao = new CaminhaoDao();

        // --- TESTES PARA CARRO ---
        System.out.println("--- INICIANDO TESTES PARA CARROS ---");
        testarCarroDao(banco, carroDao);
        System.out.println("--- FIM DOS TESTES PARA CARROS ---\n");

        // --- TESTES PARA MOTOCICLETA ---
        System.out.println("--- INICIANDO TESTES PARA MOTOCICLETAS ---");
        testarMotocicletaDao(banco, motocicletaDao);
        System.out.println("--- FIM DOS TESTES PARA MOTOCICLETAS ---\n");

        // --- TESTES PARA CAMINHÃO ---
        System.out.println("--- INICIANDO TESTES PARA CAMINHÕES ---");
        testarCaminhaoDao(banco, caminhaoDao);
        System.out.println("--- FIM DOS TESTES PARA CAMINHÕES ---\n");

        // É uma boa prática fechar a conexão com o banco ao final
        // banco.fecharConexao(); // Se você tiver um método para isso
    }

    public static void testarCarroDao(Banco banco, CarroDao carroDao) {
        // 1. Cadastrar um novo carro
        System.out.println("1. Testando cadastro de carro...");
        Carro novoCarro = new Carro("Fusca", 123456, 50000.0, 25000.00, "Azul", 1975, 1, 60.0, 2, "Gasolina");
        carroDao.cadastrarCarro(banco, novoCarro);
        System.out.println("Carro cadastrado com sucesso!");

        // 2. Listar todos os carros
        System.out.println("\n2. Testando listagem de carros...");
        List<Carro> carros = carroDao.listarCarros(banco);
        if (carros.isEmpty()) {
            System.out.println("Nenhum carro encontrado.");
        } else {
            System.out.println("Carros encontrados:");
            carros.forEach(c -> System.out.println("  - ID: " + c.getId() + ", Modelo: " + c.getModelo()));
        }

        // Pega o ID do primeiro carro da lista para os próximos testes
        if (carros.isEmpty()) {
            System.out.println("\nNão há carros para continuar os testes de atualização e exclusão.");
            return;
        }
        int carroIdParaTeste = carros.get(0).getId();


        // 3. Buscar carro por ID
        System.out.println("\n3. Testando busca por ID (" + carroIdParaTeste + ")...");
        Carro carroEncontrado = carroDao.buscarCarroPorId(banco, carroIdParaTeste);
        if (carroEncontrado != null) {
            System.out.println("Carro encontrado: " + carroEncontrado.getModelo() + " - " + carroEncontrado.getCor());
        } else {
            System.out.println("Carro não encontrado com o ID: " + carroIdParaTeste);
        }

        // 4. Atualizar carro
        System.out.println("\n4. Testando atualização do carro ID " + carroIdParaTeste + "...");
        if (carroEncontrado != null) {
            carroEncontrado.setPreco(28500.50);
            carroEncontrado.setCor("Branco");
            carroDao.atualizarCarro(banco, carroEncontrado);
            System.out.println("Carro atualizado! Verificando...");
            Carro carroAtualizado = carroDao.buscarCarroPorId(banco, carroIdParaTeste);
            System.out.println("  - Novo Preço: " + carroAtualizado.getPreco());
            System.out.println("  - Nova Cor: " + carroAtualizado.getCor());
        }

        // 5. Testar outras buscas
        System.out.println("\n5. Testando outras buscas...");
        System.out.println("  - Buscando por modelo 'Fusca': " + carroDao.buscarCarrosPorModelo(banco, "Fusca").size() + " encontrados.");
        System.out.println("  - Buscando por cor 'Branco': " + carroDao.buscarCarrosPorCor(banco, "Branco").size() + " encontrados.");
        System.out.println("  - Buscando por preço entre 20000 e 30000: " + carroDao.buscarCarrosPorFaixaPreco(banco, 20000, 30000).size() + " encontrados.");
        System.out.println("  - Buscando por ano entre 1970 e 1980: " + carroDao.buscarCarrosPorFaixaAno(banco, 1970, 1980).size() + " encontrados.");


        // 6. Excluir carro
        System.out.println("\n6. Testando exclusão do carro ID " + carroIdParaTeste + "...");
        carroDao.excluirCarro(banco, carroIdParaTeste);
        Carro carroExcluido = carroDao.buscarCarroPorId(banco, carroIdParaTeste);
        if (carroExcluido == null) {
            System.out.println("Carro excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir o carro.");
        }
    }

    public static void testarMotocicletaDao(Banco banco, MotocicletaDao motocicletaDao) {
        // 1. Cadastrar uma nova motocicleta
        System.out.println("1. Testando cadastro de motocicleta...");
        Motocicleta novaMoto = new Motocicleta("Honda CG 160", 987654, 0.0, 15000.00, "Vermelha", 2024, 1, 160);
        motocicletaDao.cadastrarMotocicleta(banco, novaMoto);
        System.out.println("Motocicleta cadastrada com sucesso!");

        // 2. Listar todas as motocicletas
        System.out.println("\n2. Testando listagem de motocicletas...");
        List<Motocicleta> motos = motocicletaDao.listarMotocicletas(banco);
        if (motos.isEmpty()) {
            System.out.println("Nenhuma motocicleta encontrada.");
        } else {
            System.out.println("Motocicletas encontradas:");
            motos.forEach(m -> System.out.println("  - ID: " + m.getId() + ", Modelo: " + m.getModelo()));
        }

        if (motos.isEmpty()) {
            System.out.println("\nNão há motocicletas para continuar os testes de atualização e exclusão.");
            return;
        }
        int motoIdParaTeste = motos.get(0).getId();

        // 3. Buscar motocicleta por ID
        System.out.println("\n3. Testando busca por ID (" + motoIdParaTeste + ")...");
        Motocicleta motoEncontrada = motocicletaDao.buscarMotocicletaPorId(banco, motoIdParaTeste);
        if (motoEncontrada != null) {
            System.out.println("Motocicleta encontrada: " + motoEncontrada.getModelo() + " - " + motoEncontrada.getCilindrada() + "cc");
        } else {
            System.out.println("Motocicleta não encontrada com o ID: " + motoIdParaTeste);
        }

        // 4. Atualizar motocicleta
        System.out.println("\n4. Testando atualização da motocicleta ID " + motoIdParaTeste + "...");
        if (motoEncontrada != null) {
            motoEncontrada.setPreco(14500.00);
            motoEncontrada.setQuilometragem(100.0);
            motocicletaDao.atualizarMotocicleta(banco, motoEncontrada);
            System.out.println("Motocicleta atualizada! Verificando...");
            Motocicleta motoAtualizada = motocicletaDao.buscarMotocicletaPorId(banco, motoIdParaTeste);
            System.out.println("  - Novo Preço: " + motoAtualizada.getPreco());
            System.out.println("  - Nova Quilometragem: " + motoAtualizada.getQuilometragem());
        }
        
        // 5. Excluir motocicleta
        System.out.println("\n5. Testando exclusão da motocicleta ID " + motoIdParaTeste + "...");
        motocicletaDao.excluirMotocicleta(banco, motoIdParaTeste);
        Motocicleta motoExcluida = motocicletaDao.buscarMotocicletaPorId(banco, motoIdParaTeste);
        if (motoExcluida == null) {
            System.out.println("Motocicleta excluída com sucesso!");
        } else {
            System.out.println("Falha ao excluir a motocicleta.");
        }
    }

    public static void testarCaminhaoDao(Banco banco, CaminhaoDao caminhaoDao) {
        // 1. Cadastrar um novo caminhão
        System.out.println("1. Testando cadastro de caminhão...");
        Caminhao novoCaminhao = new Caminhao("Volvo FH 540", 555444, 150000.0, 750000.00, "Prata", 2020, 1, 6, 29000.0, 4.0, "Baú");
        caminhaoDao.cadastrarCaminhao(banco, novoCaminhao);
        System.out.println("Caminhão cadastrado com sucesso!");

        // 2. Listar todos os caminhões
        System.out.println("\n2. Testando listagem de caminhões...");
        List<Caminhao> caminhoes = caminhaoDao.listarCaminhoes(banco);
        if (caminhoes.isEmpty()) {
            System.out.println("Nenhum caminhão encontrado.");
        } else {
            System.out.println("Caminhões encontrados:");
            caminhoes.forEach(c -> System.out.println("  - ID: " + c.getId() + ", Modelo: " + c.getModelo()));
        }

        if (caminhoes.isEmpty()) {
            System.out.println("\nNão há caminhões para continuar os testes de atualização e exclusão.");
            return;
        }
        int caminhaoIdParaTeste = caminhoes.get(0).getId();

        // 3. Buscar caminhão por ID
        System.out.println("\n3. Testando busca por ID (" + caminhaoIdParaTeste + ")...");
        Caminhao caminhaoEncontrado = caminhaoDao.buscarCaminhaoPorId(banco, caminhaoIdParaTeste);
        if (caminhaoEncontrado != null) {
            System.out.println("Caminhão encontrado: " + caminhaoEncontrado.getModelo() + " - " + caminhaoEncontrado.getTipoCarroceria());
        } else {
            System.out.println("Caminhão não encontrado com o ID: " + caminhaoIdParaTeste);
        }

        // 4. Atualizar caminhão
        System.out.println("\n4. Testando atualização do caminhão ID " + caminhaoIdParaTeste + "...");
        if (caminhaoEncontrado != null) {
            caminhaoEncontrado.setPreco(740000.00);
            caminhaoEncontrado.setQuilometragem(165000.0);
            caminhaoDao.atualizarCaminhao(banco, caminhaoEncontrado);
            System.out.println("Caminhão atualizado! Verificando...");
            Caminhao caminhaoAtualizado = caminhaoDao.buscarCaminhaoPorId(banco, caminhaoIdParaTeste);
            System.out.println("  - Novo Preço: " + caminhaoAtualizado.getPreco());
            System.out.println("  - Nova Quilometragem: " + caminhaoAtualizado.getQuilometragem());
        }
        
        // 5. Excluir caminhão
        System.out.println("\n5. Testando exclusão do caminhão ID " + caminhaoIdParaTeste + "...");
        caminhaoDao.excluirCaminhao(banco, caminhaoIdParaTeste);
        Caminhao caminhaoExcluido = caminhaoDao.buscarCaminhaoPorId(banco, caminhaoIdParaTeste);
        if (caminhaoExcluido == null) {
            System.out.println("Caminhão excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir o caminhão.");
        }
    }
}