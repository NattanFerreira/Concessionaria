package views;

import data.Banco;
import utils.Funcoes;
import controllers.FuncionarioDao;

public class LoginView {
    public static void telaLogin(Banco banco) {
        int id_cargo;
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Tela de Login");
        System.out.println("Digite seu login:");
        String login = Funcoes.lerLogin();
        if (login == null) {
            System.out.println("Login inválido. Tente novamente.");
            return;
        }
        System.out.println("Digite sua senha:");
        String senha = Funcoes.lerSenha();
        if (senha == null) {
            System.out.println("Senha inválida. Tente novamente.");
            return;
        }
        // Aqui você pode adicionar a lógica para verificar o login e senha no banco de dados
        id_cargo = FuncionarioDao.autenticarFuncionario(banco, login, senha);
        if (id_cargo == -1) {
            System.out.println("Login ou senha incorretos. Tente novamente.");
            return;
        } else if (id_cargo == 0) {
            System.out.println("Login realizado com sucesso! Você é um vendedor.");
            // Aqui você pode redirecionar para a tela de vendedor 
        } else if (id_cargo == 1) {
            System.out.println("Login realizado com sucesso! Você é um estoquista.");
            // Aqui você pode redirecionar para a tela de estoquista
        } else if (id_cargo == 2) {
            System.out.println("Login realizado com sucesso! Você é um erente de funcionário.");
            // Aqui você pode redirecionar para a tela de erente de funcionário
        } else if (id_cargo == 3) {
            System.out.println("Login realizado com sucesso! Você é um administrador.");
            // Aqui você pode redirecionar para a tela de administrador 
        } else {
            System.out.println("Cargo não reconhecido. Acesso negado.");
        }
    }
}
