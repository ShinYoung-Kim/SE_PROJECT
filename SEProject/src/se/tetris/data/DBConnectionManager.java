package se.tetris.data;

import java.sql.*;
import java.util.ArrayList;

class DBCreate {

    public static void createNewDatabase() {
        String path = System.getProperty("user.dir");

        String url = "jdbc:sqlite:./SEProject/lib/tetris.db";

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("The Path name is " + path);
//                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        String path = System.getProperty("user.dir");

        String url = "jdbc:sqlite:./SEProject/lib/tetris.db";

        String sql = "CREATE TABLE IF NOT EXISTS StInit (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " code text NOT NULL DEFAULT '',\n" // window, Color, Level, Key
                + " type integer NOT NULL DEFAULT 0\n" + ");";

        String initsql = "INSERT INTO StInit(code) VALUES('Window'), ('Color'), ('Level'), ('Key');";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();
            stmt.execute(sql);


            ResultSet rs = stmt.executeQuery("select count(*) from StInit;");
            int count = 0;

            while(rs.next()) {
                count = rs.getInt(1);
            }


            if (count == 0) {
                stmt.executeUpdate(initsql);
            } else {

            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("0"+e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS Score (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " mode integer NOT NULL DEFAULT 0,\n"
                + " type integer NOT NULL DEFAULT 0,\n"
                + " name text NOT NULL DEFAULT '',\n"
                + " score integer NOT NULL DEFAULT 0,  \n"
                + " date text NOT NULL DEFAULT (datetime('now','localtime'))  \n"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            conn.close();
        } catch (SQLException e) {
            System.out.println("2"+e.getMessage());
        }
    }
}

public class DBConnectionManager extends DBCreate {

    public static Connection connect() {
        Connection conn = null;
        String DB_JDBC_DRIVER = "org.sqlite.JDBC";
        String DB_PATH = System.getProperty("user.dir");
        String DB_URL = "jdbc:sqlite:./SEProject/lib/tetris.db";

        try {
            conn = DriverManager.getConnection(DB_URL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

}