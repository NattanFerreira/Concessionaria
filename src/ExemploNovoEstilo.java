import data.Banco;
import controllers.CarroDao;
import controllers.CaminhaoDao;
import controllers.MotocicletaDao;
import controllers.FuncionarioDao;
import models.Carro;
import models.Caminhao;
import models.Motocicleta;
import models.Funcionario;

import java.util.List;

/**
 * Classe de exemplo demonstrando o novo estilo de uso das classes DAO.
 * Agora você cria um objeto Banco no Main e passa como parâmetro para todos os
 * métodos DAO.
 */
public class ExemploNovoEstilo {

    public static void main(String[] args) {
        // Criar um objeto Banco - agora você tem controle total sobre a instância
        // O banco será automaticamente inicializado no construtor
        Banco banco = new Banco();

        // Criar instâncias das classes DAO (não precisam mais de parâmetros no
        // construtor)
        CarroDao carroDao = new CarroDao();
        CaminhaoDao caminhaoDao = new CaminhaoDao();
        MotocicletaDao motocicletaDao = new MotocicletaDao();
        FuncionarioDao funcionarioDao = new FuncionarioDao();

        // Exemplos de uso - sempre passando o objeto banco como primeiro parâmetro

        // 1. Cadastrar um carro (usando timestamp para evitar duplicatas)
        int chassiUnico = (int) (System.currentTimeMillis() % 1000000);
        Carro novoCarro = new Carro("Civic", chassiUnico, 5000.0, 85000.0, "Preto", 2023, 1, 150.0, 4, "Flex");
        Carro carroSalvo = carroDao.cadastrarCarro(banco, novoCarro);
        System.out.println("Carro cadastrado com ID: " + carroSalvo.getId() + " e chassi: " + chassiUnico);

        // 2. Listar todos os carros
        List<Carro> carros = carroDao.listarCarros(banco);
        System.out.println("Total de carros cadastrados: " + carros.size());

        // 3. Buscar carros por modelo
        List<Carro> civics = carroDao.buscarCarrosPorModelo(banco, "Civic");
        System.out.println("Carros modelo Civic encontrados: " + civics.size());

        // 4. Cadastrar um caminhão (usando chassi único)
        int chassiCaminhao = (int) (System.currentTimeMillis() % 1000000) + 1000;
        Caminhao novoCaminhao = new Caminhao("Volvo FH", chassiCaminhao, 80000.0, 250000.0, "Branco", 2022, 1, 3,
                25000.0, 4.2,
                "Baú");
        caminhaoDao.cadastrarCaminhao(banco, novoCaminhao);
        System.out.println("Caminhão cadastrado com ID: " + novoCaminhao.getId() + " e chassi: " + chassiCaminhao);

        // 5. Buscar caminhões por faixa de preço
        List<Caminhao> caminhoesFaixaPreco = caminhaoDao.buscarCaminhoesPorFaixaPreco(banco, 200000.0, 300000.0);
        System.out.println("Caminhões na faixa de preço R$200k-300k: " + caminhoesFaixaPreco.size());

        // 6. Cadastrar uma motocicleta (usando chassi único)
        int chassiMoto = (int) (System.currentTimeMillis() % 1000000) + 2000;
        Motocicleta novaMotocicleta = new Motocicleta("Honda CB 600", chassiMoto, 15000.0, 25000.0, "Azul", 2021, 1,
                600);
        motocicletaDao.cadastrarMotocicleta(banco, novaMotocicleta);
        System.out.println("Motocicleta cadastrada com ID: " + novaMotocicleta.getId() + " e chassi: " + chassiMoto);

        // 7. Buscar motocicletas por cilindrada
        List<Motocicleta> motos600cc = motocicletaDao.buscarMotocicletasPorCilindrada(banco, 600);
        System.out.println("Motocicletas 600cc encontradas: " + motos600cc.size());

        // 8. Cadastrar um funcionário (usando login único)
        String loginUnico = "joao.silva." + (System.currentTimeMillis() % 10000);
        Funcionario novoFuncionario = new Funcionario("João Silva", loginUnico, "senha123", "Vendedor");
        funcionarioDao.cadastrarFuncionario(banco, novoFuncionario);
        System.out.println("Funcionário cadastrado com ID: " + novoFuncionario.getId() + " e login: " + loginUnico);

        // 9. Buscar funcionários por cargo
        List<Funcionario> vendedores = funcionarioDao.buscarFuncionariosPorCargo(banco, "Vendedor");
        System.out.println("Vendedores encontrados: " + vendedores.size());

        // 10. Autenticar funcionário
        Funcionario funcionarioAutenticado = funcionarioDao.autenticarFuncionario(banco, loginUnico, "senha123");
        if (funcionarioAutenticado != null) {
            System.out.println("Funcionário autenticado: " + funcionarioAutenticado.getNome());
        }

        // Exemplos de operações de atualização e exclusão

        // 11. Atualizar dados do carro
        carroSalvo.setPreco(90000.0);
        carroDao.atualizarCarro(banco, carroSalvo);
        System.out.println("Preço do carro atualizado para: R$" + carroSalvo.getPreco());

        // 12. Buscar por ID específico
        Carro carroEncontrado = carroDao.buscarCarroPorId(banco, carroSalvo.getId());
        if (carroEncontrado != null) {
            System.out.println("Carro encontrado: " + carroEncontrado.getModelo());
        }

        // 13. Outros exemplos de busca
        List<Carro> carrosAzuis = carroDao.buscarCarrosPorCor(banco, "Azul");
        List<Carro> carrosNovos = carroDao.buscarCarrosPorFaixaAno(banco, 2020, 2024);
        List<Carro> carrosPotentes = carroDao.buscarCarrosPorPotencia(banco, 200.0);

        System.out.println("Carros azuis: " + carrosAzuis.size());
        System.out.println("Carros novos (2020-2024): " + carrosNovos.size());
        System.out.println("Carros potentes (>200cv): " + carrosPotentes.size());

        // Lembrete importante: com este novo estilo, você tem controle total sobre
        // quando
        // criar, usar e desconectar o banco de dados
        banco.disconnect();
        System.out.println("Conexão com banco encerrada.");
    }
}
