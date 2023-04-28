package Klaseak;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static Connection connection;
    private static String URL="jdbc:mysql://localhost:3306/mugarria_6";
    private static String USER="root";
    private static String PASS="zubiri";
    private static String QUERY_SELECT_PHOTOGRAPHERS="SELECT * FROM Photographers";
    private static String QUERY_SELECT_PICTURES="SELECT * FROM Pictures";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                System.out.println("Connecting..");
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Connected");
            } catch (SQLException ex) {
                System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
            }
        }
        return connection;
    }
    public List<Photographer> getAllPhotographers() {
        List<Photographer> photographers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_SELECT_PHOTOGRAPHERS);
            while (resultSet.next()) {
                int photographerId = resultSet.getInt("photographerId");
                String name = resultSet.getString("name_");
                boolean awarded = resultSet.getBoolean("awarded");
                Photographer photographer = new Photographer(photographerId, name, awarded);
                photographers.add(photographer);
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return photographers;
    }

    public List<Picture> getAllPictures() {
        List<Picture> pictures = new ArrayList<>();
        try{
            Statement stm=connection.createStatement();
            ResultSet resultSet = stm.executeQuery();
        }catch(){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión a la base de datos: " + ex.getMessage());
            }
        }
    }
}

