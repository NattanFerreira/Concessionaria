package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {

    private static final String DATABASE_URL = "jdbc:sqlite:src/data/data.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public void inicializarBanco() {
        // <-- PONTO DE VERIFICAÇÃO 1 -->
        System.out.println("[DEBUG] - Método inicializarBanco() foi chamado.");

        String schemaSql = lerSqlDeArquivo("schema.sql");

        if (schemaSql != null && !schemaSql.isEmpty()) {
            try (Connection conn = this.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                // <-- PONTO DE VERIFICAÇÃO 2 -->
                System.out.println("[DEBUG] - Conexão estabelecida. Executando scripts do schema.sql...");
                
                String[] comandos = schemaSql.split(";");

                for (String comando : comandos) {
                    if (!comando.trim().isEmpty()) {
                        stmt.execute(comando);
                    }
                }
                
                System.out.println("[SUCESSO] - Banco de dados inicializado com sucesso!");

            } catch (SQLException e) {
                System.err.println("[ERRO FATAL] - Erro ao executar scripts do banco de dados: " + e.getMessage());
                // Imprime o stack trace para mais detalhes do erro de SQL
                e.printStackTrace();
            }
        } else {
            System.err.println("[ERRO FATAL] - O conteúdo do arquivo schema.sql está vazio ou não foi lido.");
        }
    }

    private String lerSqlDeArquivo(String nomeArquivo) {
        StringBuilder sql = new StringBuilder();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(nomeArquivo)) {
            
            if (is == null) {
                // <-- PONTO DE VERIFICAÇÃO 3 -->
                System.err.println("[ERRO FATAL] - ARQUIVO NÃO ENCONTRADO: " + nomeArquivo);
                System.err.println("Verifique se a pasta 'src/resources' está marcada como 'Resources Root' na sua IDE.");
                return null;
            }
            
            // Se encontrou o arquivo, continua a leitura
            try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(isr)) {
                
                String linha;
                while ((linha = reader.readLine()) != null) {
                    sql.append(linha).append(System.lineSeparator());
                }
            }
            // <-- PONTO DE VERIFICAÇÃO 4 -->
            System.out.println("[DEBUG] - Arquivo schema.sql lido com sucesso.");

        } catch (IOException e) {
            System.err.println("[ERRO FATAL] - Erro de IO ao ler o arquivo de schema: " + e.getMessage());
            return null;
        }
        return sql.toString();
    }
}