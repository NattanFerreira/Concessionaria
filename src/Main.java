import data.Banco;
import utils.Funcoes;
import controllers.*;
import models.*;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        FuncionarioDao funcionarioDao = new FuncionarioDao();
        Funcionario funcionario = new Funcionario("pedro", "pedro123", "pedro123", 1);
        //funcionarioDao.cadastrarFuncionario(banco, funcionario);
        
        CarroDao carroDao = new CarroDao();
        // ...existing code...
        Carro carro = new Carro("Fusca", "ABC1234", 15000.0, 25.4, "Azul",
     2000, 1, 80, 4, 0);
        Carro carro2 = new Carro("Civic", "XYZ5678", 30000.0, 35.0, "Branco",
     2018, 1, 150, 4, 0);
        Carro carro3 = new Carro("Onix", "LMN9101", 20000.0, 30.0, "Branco",
     2019, 1, 120, 4, 0);
        Carro carro4 = new Carro("Palio", "QRS2345", 18000.0, 28.0, "Vermelho",
     2017, 1, 90, 4, 0);
        carroDao.cadastrarCarro(banco, carro);
        carroDao.cadastrarCarro(banco, carro2);
        carroDao.cadastrarCarro(banco, carro3);
        carroDao.cadastrarCarro(banco, carro4);

        views.LoginView.menuInicial(banco);
        Funcoes.fechaScanner();
        banco.disconnect();
    }
}
