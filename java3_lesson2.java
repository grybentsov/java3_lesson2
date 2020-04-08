package Lesson_2;

import java.sql.*;

public class java3_lesson2 {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void main(String[] args) throws SQLException {

        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    create();
    insert(15);
    select();
    delete(15);
    update();

    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // CREATE
    public static void create(){
        int res = 0;
        try {
            res = stmt.executeUpdate("CREATE TABLE students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "score TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        System.out.println("Database has been created.");
    }

    // INSERT
    public static void insert (int quantity) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO students (name, score)" +
                "VALUES (?, ?);");
        connection.setAutoCommit(false);
        for (int i = 0; i < quantity; i++) {
            pstmt.setString(1, "Person " + i);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        connection.setAutoCommit(true);
    }
    // SELECT
    public static void select() throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT id, name, score FROM students");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + " "
                    + resultSet.getString("name") + " "
                    + resultSet.getString("score"));
        }
    }

    // DELETE
    public static void delete(int num) {
            int res = 0;
            try {
                res = stmt.executeUpdate("DELETE FROM students WHERE id = num");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.printf("%d row(s) deleted", res);
    }

        // UPDATE
        public static void update () {
            try {
                int rows = stmt.executeUpdate("UPDATE Students SET score = ​90​ WHERE 190 > id > 200");
                System.out.printf("Updated %d rows", rows);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

