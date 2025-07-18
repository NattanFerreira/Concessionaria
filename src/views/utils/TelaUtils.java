package views.utils;

import java.io.IOException;

public class TelaUtils {

    public static void limpaTela() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao tentar limpar a tela: " + e.getMessage());

            for (int i = 0; i < 50; ++i) {
                System.out.println();
            }
        }
    }

    public static void cabecalhoMenu(String texto) {
        System.out.println("\n--- " + texto + " ---");
    }
}
