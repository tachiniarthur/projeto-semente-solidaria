package com.example.application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.application.controllers.ProductController;

public class ProductModel {
    private static final String url = "jdbc:sqlite:database.sqlite";

    public static void main(String[] args) {
        try (Connection c = DriverManager.getConnection(url)) {
             String createTableSql = """
                    CREATE TABLE IF NOT EXISTS product (
                        id_product INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome VARCHAR(250) NOT NULL,
                        tipo_produto VARCHAR(250),
                        quantidade INTEGER,
                        data DATE,
                        doador_nome VARCHAR(250),
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

    public static boolean insertProduct(String nome, String tipo_produto, Integer quantidade, Date data, String doador_nome, int userId) {
        String url = "jdbc:sqlite:database.sqlite";

        try (Connection c = DriverManager.getConnection(url)) {
            String insertSql = "INSERT INTO product (nome, tipo_produto, quantidade, data, doador_nome, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = c.prepareStatement(insertSql)) {
                insertStatement.setString(1, nome);
                insertStatement.setString(2, tipo_produto);
                insertStatement.setInt(3, quantidade);
                java.sql.Date sqlDate = new java.sql.Date(data.getTime());
                insertStatement.setDate(4, sqlDate);
                insertStatement.setString(5, doador_nome);
                insertStatement.setInt(6, userId);
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

    public static ProductController getProductById(int id) {
        ProductController produto = null;
        try (Connection c = DriverManager.getConnection(url)) {
            String selectSql = "SELECT * FROM product WHERE id_product=?";
            try (PreparedStatement selectStatement = c.prepareStatement(selectSql)) {
                selectStatement.setInt(1, id);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    Integer id_product = resultSet.getInt("id_product");
                    String nome = resultSet.getString("nome");
                    String tipo_produto = resultSet.getString("tipo_produto");
                    Integer quantidade = resultSet.getInt("quantidade");
                    Date data = resultSet.getDate("data");
                    String doador_nome = resultSet.getString("doador_nome");
                    Integer id_user = resultSet.getInt("id_user");

                    produto = new ProductController(id_product, nome, tipo_produto, quantidade, data, doador_nome, id_user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    public static List<ProductController> getAll(int userId) {
        List<ProductController> produtos = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(url)) {
            String selectSql = "SELECT * FROM product WHERE id_user=?";
            try (PreparedStatement selectStatement = c.prepareStatement(selectSql)) {
                selectStatement.setInt(1, userId);
                ResultSet resultSet = selectStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id_product = resultSet.getInt("id_product");
                    String nome = resultSet.getString("nome");
                    String tipo_produto = resultSet.getString("tipo_produto");
                    Integer quantidade = resultSet.getInt("quantidade");
                    Date data = resultSet.getDate("data");
                    String doador_nome = resultSet.getString("doador_nome");
                    Integer id_user = resultSet.getInt("id_user");

                    produtos.add(new ProductController(id_product, nome, tipo_produto, quantidade, data, doador_nome, id_user));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public static boolean update(int id, ProductController novoProduto) {
        String url = "jdbc:sqlite:database.sqlite";

        try (Connection c = DriverManager.getConnection(url)) {
            String updateSql = "UPDATE product SET nome=?, tipo_produto=?, quantidade=?, data=?, doador_nome=? WHERE id_product=?";
            try (PreparedStatement updateStatement = c.prepareStatement(updateSql)) {
                updateStatement.setString(1, novoProduto.getNome());
                updateStatement.setString(2, novoProduto.getTipoProduto());
                updateStatement.setInt(3, novoProduto.getQuantidade());
                updateStatement.setDate(4, new java.sql.Date(novoProduto.getData().getTime()));
                updateStatement.setString(5, novoProduto.getDoadorNome());
                updateStatement.setInt(6, id);

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
            String deleteSql = "DELETE FROM product WHERE id_product=?";
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