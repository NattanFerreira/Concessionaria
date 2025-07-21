import data.Banco;
import utils.Funcoes;
import controllers.*;
import models.*;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        FuncionarioDao funcionarioDao = new FuncionarioDao();
        Funcionario funcionario = new Funcionario("pedro", "pedro123", "pedro123", 1);
        funcionarioDao.cadastrarFuncionario(banco, funcionario);
        
        views.LoginView.telaLogin(banco);
        Funcoes.fechaScanner();
        banco.disconnect();
    }
}
