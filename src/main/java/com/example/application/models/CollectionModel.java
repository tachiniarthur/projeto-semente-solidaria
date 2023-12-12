package com.example.application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.application.controllers.CollectionController;

public class CollectionModel {
    private static final String url = "jdbc:sqlite:database.sqlite";

    public static void main(String[] args) {
        try (Connection c = DriverManager.getConnection(url)) {
            String createTableSql = """
                    CREATE TABLE IF NOT EXISTS collection (
                        id_collection INTEGER PRIMARY KEY AUTOINCREMENT,
                        valor_doado DOUBLE,
                        forma_doacao VARCHAR(250),
                        data DATE,
                        nome_doador VARCHAR(250),
                        id_user INTEGER,
                        FOREIGN KEY (id_user) REFERENCES user (id_user)
                    );
                    """;
            try (PreparedStatement createTableStatement = c.prepareStatement(createTableSql)) {
                createTableStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CollectionController getCollectionById(int id) {
        CollectionController arrecadacao = null;
        try (Connection c = DriverManager.getConnection(url)) {
            String selectSql = "SELECT * FROM collection WHERE id_collection=?";
            try (PreparedStatement selectStatement = c.prepareStatement(selectSql)) {
                selectStatement.setInt(1, id);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    Integer id_collection = resultSet.getInt("id_collection");
                    Double valor_doado = resultSet.getDouble("valor_doado");
                    String forma_doacao = resultSet.getString("forma_doacao");
                    Date data = resultSet.getDate("data");
                    String nome_doador = resultSet.getString("nome_doador");
                    arrecadacao = new CollectionController(id_collection, valor_doado, forma_doacao, data, nome_doador);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrecadacao;
    }

    public static boolean insertCollection(Double valor_doado, String forma_doacao, Date data, String nomeDoador, int userId) {
        String url = "jdbc:sqlite:database.sqlite";
   
        try (Connection c = DriverManager.getConnection(url)) {
            String insertSql = "INSERT INTO collection (valor_doado, forma_doacao, data, nome_doador, id_user) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = c.prepareStatement(insertSql)) {
                insertStatement.setDouble(1, valor_doado);
                insertStatement.setString(2, forma_doacao);
                java.sql.Date sqlDate = new java.sql.Date(data.getTime());
                insertStatement.setDate(3, sqlDate);
                insertStatement.setString(4, nomeDoador);
                insertStatement.setInt(5, userId);
                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
   
        return false;
    }

    public static List<CollectionController> getAll(int id_user) {
        List<CollectionController> arrecadacoes = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(url)) {
            String selectSql = "SELECT * FROM collection WHERE id_user=?";
            try (PreparedStatement selectStatement = c.prepareStatement(selectSql)) {
                selectStatement.setInt(1, id_user);
                ResultSet resultSet = selectStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id_collection = resultSet.getInt("id_collection");
                    double valor_doado = resultSet.getDouble("valor_doado");
                    String forma_doacao = resultSet.getString("forma_doacao");
                    Date data = resultSet.getDate("data");
                    String nome_doador = resultSet.getString("nome_doador");
                    arrecadacoes.add(new CollectionController(id_collection, valor_doado, forma_doacao, data, nome_doador));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrecadacoes;
    }

    public static boolean update(int id, CollectionController arrecadacao) {
        String url = "jdbc:sqlite:database.sqlite";

        try (Connection c = DriverManager.getConnection(url)) {
            String updateSql = "UPDATE collection SET valor_doado=?, forma_doacao=?, data=?, nome_doador=? WHERE id_collection=?";
            try (PreparedStatement updateStatement = c.prepareStatement(updateSql)) {
                updateStatement.setDouble(1, arrecadacao.getValorDoado());
                updateStatement.setString(2, arrecadacao.getFormaDoacao());
                updateStatement.setDate(3, new java.sql.Date(arrecadacao.getData().getTime()));                
                updateStatement.setString(4, arrecadacao.getNomeDoador());
                updateStatement.setInt(5, id);

                int rowsAffected = updateStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void delete(int id) {
        try (Connection c = DriverManager.getConnection(url)) {
            String deleteSql = "DELETE FROM collection WHERE id_collection=?";
            try (PreparedStatement deleteStatement = c.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}