package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe responsável pelo gerenciamento da conexão com o banco de dados SQLite.
 * Implementa o padrão Singleton para garantir uma única instância da conexão.
 * 
 * Esta classe gerencia:
 * - Conexão com o banco de dados SQLite
 * - Inicialização das tabelas através do schema.sql
 * - Execução de consultas SQL
 * - Fechamento da conexão
 */
public class Banco {
    private Connection db = null;
    private Statement statement = null;

    private static final String DATABASE_URL = "jdbc:sqlite:src/data/data.db";

    /**
     * Construtor da classe Banco.
     * Estabelece conexão com o banco SQLite e inicializa as tabelas.
     */
    public Banco() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.db = DriverManager.getConnection(DATABASE_URL);
            this.statement = this.db.createStatement();
            this.statement.setQueryTimeout(5);

            if (this.db != null) {
                inicializarBanco();
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }

        } catch (SQLException e) {
            System.out.println("Erro na conexão");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite não encontrado");
            System.out.println(e);
        }
    }

    /**
     * Método privado para inicializar o banco de dados criando as tabelas
     * necessárias caso elas não existam.
     * Lê o arquivo schema.sql e executa os comandos de criação de tabelas.
     */
    private void inicializarBanco() {
        System.out.println("=== Verificando e inicializando banco de dados ===");

        try {
            // Lendo o arquivo schema.sql
            String schemaPath = "src/resources/schema.sql";
            String schema = Files.readString(Paths.get(schemaPath));

            String[] comandos = schema.split(";");

            for (String comando : comandos) {
                comando = comando.trim();
                if (!comando.isEmpty()) {
                    try {
                        this.queryInsup(comando);
                    } catch (Exception e) {
                        if (!e.getMessage().contains("already exists")) {
                            System.err.println("Aviso: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("✓ Banco de dados inicializado com sucesso!");

        } catch (IOException e) {
            System.err.println("❌ Erro ao ler schema.sql: " + e.getMessage());
            System.err.println("Continuando sem inicializar tabelas...");
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar banco: " + e.getMessage());
        }
    }

    /**
     * Fecha a conexão com o banco de dados.
     * Deve ser chamado ao final da aplicação para liberar recursos.
     */
    public void disconnect() {
        try {
            if (db != null) {
                db.close();
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro na hora de fechar conexão");
            System.out.println(e);
        }
    }

    /**
     * Retorna uma nova conexão com o banco de dados.
     * Utilizado pelos DAOs para operações específicas.
     * 
     * @return Connection para o banco de dados
     * @throws SQLException se houver erro na conexão
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite não encontrado", e);
        }
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Executa queries de inserção, atualização ou exclusão (INSERT, UPDATE, DELETE).
     * Gerencia commits e rollbacks automaticamente.
     * 
     * @param query Comando SQL a ser executado
     */
    public void queryInsup(String query) {
        try {
            statement.execute(query);
            if (db.getAutoCommit() == false) {
                db.commit();
            }
        } catch (SQLException e) {
            System.out.println("Erro na query: " + query);
            System.out.println("Detalhes do erro: " + e);

            try {
                if (db.getAutoCommit() == false) {
                    db.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Erro no rollback: " + rollbackEx);
            }
        }
    }

    /**
     * Executa queries de seleção (SELECT).
     * 
     * @param query Comando SQL SELECT a ser executado
     * @return ResultSet com os dados retornados pela consulta, ou null em caso de erro
     */
    public ResultSet querySelect(String query) {
        try {
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            System.out.println("Erro na query: " + query);
            System.out.println(e);
            return null;
        }
    }
}