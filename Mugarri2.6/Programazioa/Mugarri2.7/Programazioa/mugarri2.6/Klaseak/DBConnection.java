package Klaseak;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    private static Connection conn;
    private static String URL="jdbc:mysql://localhost:3306/mugarria_6";
    private static String USER="root";
    private static String PASS="Zubiri13";
    private static final String GET_ALL_PHOTOGRAPHER_NAMES = "SELECT name_ FROM Photographers";
    private static final String GET_PICTURES_BY_PHOTOGRAPHER = "SELECT * FROM Pictures WHERE photographerId = ?";
    private static final String GET_PHOTOGRAPHER_ID = "SELECT photographerId FROM Photographers WHERE name_ = ?";
    private static final String GET_PICTURES_BY_PHOTOGRAPHER_AND_DATE = "SELECT * FROM Pictures WHERE photographerId = ? AND date_ > ?";
    private static final String UPDATE_PICTURE_VISITS = "UPDATE Pictures SET visist=(visist+1) WHERE pictureId = ?";
    private static final String GET_VISITS_PHOTOGRAPHERS = "SELECT visist, photographerId FROM Pictures;";
    public DBConnection(Connection conn){
        this.conn = conn;
    }
    public static Connection getConnection(){
        try{
            System.out.println("Connecting");
            conn = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connected");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    public Map<Integer, Integer> createVisitsMap(){
        Map<Integer, Integer> visitsMap = new HashMap<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_VISITS_PHOTOGRAPHERS)) {
            rs = st.executeQuery();

            while(rs.next()){
                int photographerId = rs.getInt("photographerId");
                int visits = rs.getInt("visits");
                if(visitsMap.containsKey(photographerId)){
                    int actualValue = visitsMap.get(photographerId);
                    visitsMap.put(photographerId, actualValue + visits);
                }else{
                    visitsMap.put(photographerId, visits);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visitsMap;
    }

    public List<String> getPhotographerNames(){
        List<String> photographerNames = new ArrayList<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_ALL_PHOTOGRAPHER_NAMES)){
            rs = st.executeQuery();
            while(rs.next()){
                photographerNames.add(rs.getString("name_"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return photographerNames;
    }
    private Picture sortuPicture(ResultSet rs) throws SQLException {
        int pictureId = rs.getInt("pictureId");
        String title = rs.getString("title");
        LocalDate date_ = rs.getDate("date_").toLocalDate();
        String file = rs.getString("file_");
        int bisitak = rs.getInt("visist");
        int photographerId = rs.getInt("photographerId");
        return new Picture(pictureId,title,date_,file,bisitak,photographerId);
    }
    public List<Picture> getPicuresByPhotographer(String photographerName){
        List<Picture> pictures = new ArrayList<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_PICTURES_BY_PHOTOGRAPHER)) {
            st.setInt(1, getPhotographerId(photographerName));
            rs = st.executeQuery();
            while(rs.next()){
                pictures.add(sortuPicture(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pictures;
    }
    public List<Picture> getPicuresByPhotographerAndDate(String photographerName, String date){
        List<Picture> pictures = new ArrayList<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_PICTURES_BY_PHOTOGRAPHER_AND_DATE)) {
            st.setInt(1, getPhotographerId(photographerName));
            st.setString(2,date);
            rs = st.executeQuery();
            while(rs.next()){
                pictures.add(sortuPicture(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pictures;
    }

    public int getPhotographerId(String photographerName){
        ResultSet rs = null;
        int photographerId = 0;

        try(PreparedStatement st = conn.prepareStatement(GET_PHOTOGRAPHER_ID)){
            st.setString(1, photographerName);
            rs = st.executeQuery();
            if(rs.next()){
                photographerId = rs.getInt("photographerId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return photographerId;
    }

    public void updateVisits(int pictureId){
        try(PreparedStatement st = conn.prepareStatement(UPDATE_PICTURE_VISITS)) {
            st.setInt(1, pictureId);
            int updated = st.executeUpdate();
            System.out.println(updated+ " column updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión a la base de datos: " + ex.getMessage());
            }
        }
    }
}

