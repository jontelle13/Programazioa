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
    private static String PASS="zubiri";
    private static final String GET_ALL_PHOTOGRAPHER_NAMES = "SELECT name_ FROM Photographers";
    private static final String GET_PICTURES_BY_PHOTOGRAPHER = "SELECT * FROM Pictures WHERE photographerId = ?";
    private static final String GET_PHOTOGRAPHER_ID = "SELECT photographerId FROM Photographers WHERE name_ = ?";
    private static final String GET_PICTURES_BY_PHOTOGRAPHER_AND_DATE = "SELECT * FROM Pictures WHERE photographerId = ? AND date_ > ?";
    private static final String UPDATE_PICTURE_VISITS = "UPDATE Pictures SET visist=(visist+1) WHERE pictureId = ?";
    private static final String GET_VISITS_PHOTOGRAPHERS = "SELECT visist, photographerId FROM Pictures;";
    private static final String GET_NOT_VISITED_PICTURES="SELECT pictureID,photographerId FROM Pictures where visist=0 and photographerId = ?;";
    private static final String GET_NOT_AWARDED_PHOTOGRAPHER="SELECT photographerId from Photographers where awarded=false";
    private static final String GET_PHOTOGRAPHERS_WITH_PICTURES  = "SELECT count(photographerId) FROM Pictures WHERE photographerId = ?";
    private static final String REMOVE_PHOTOGRAPHERS_WITHOUT_PICTURES = "DELETE FROM Photographers WHERE photographerId = ?";
    private static final String REMOVE_PICTURE_BY_ID= "DELETE FROM pictures WHERE pictureId= ?";
    private static final String UPDATE_AWARD= "update photographers set awarded=true where photographerId= ?";

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
    public void updateAward(int photographerId){
        ResultSet rs=null;
        try(PreparedStatement st= conn.prepareStatement(UPDATE_AWARD)){
            st.setInt(1,photographerId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Map<Integer, Integer> createVisitsMap(){
        Map<Integer, Integer> bisitak = new HashMap<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_VISITS_PHOTOGRAPHERS)) {
            rs = st.executeQuery();
            while(rs.next()){
                int photographerId = rs.getInt("photographerId");
                int visits = rs.getInt("visist");
                if(bisitak.containsKey(photographerId)){
                    int actualValue = bisitak.get(photographerId);
                    bisitak.put(photographerId, actualValue + visits);
                }else{
                    bisitak.put(photographerId, visits);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bisitak;
    }
    public Map<Integer,Integer> bisitaGabe(int photographerId){
        Map<Integer, Integer> gabe=new HashMap<>();
        ResultSet rs=null;
        try(PreparedStatement st = conn.prepareStatement(GET_NOT_VISITED_PICTURES)) {
            st.setInt(1,photographerId);
            rs=st.executeQuery();
            while(rs.next()){
                gabe.put(rs.getInt("pictureId"),rs.getInt("photographerId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gabe;
    }

    public List<Integer> getNotAwardedPhotographerId(){
        List<Integer> ezSarituak=new ArrayList<>();
        ResultSet rs=null;
        try (PreparedStatement st=conn.prepareStatement(GET_NOT_AWARDED_PHOTOGRAPHER)){
            rs=st.executeQuery();
            while(rs.next()){
                int photographerId=rs.getInt("photographerId");
                ezSarituak.add(photographerId);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ezSarituak;
    }
    public boolean getPhotographerWithPictues(int photographerId){
        boolean egia = true;
        try(PreparedStatement st = conn.prepareStatement(GET_PHOTOGRAPHERS_WITH_PICTURES)) {
            st.setInt(1, photographerId);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0){
                    egia = false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return egia;
    }

    public void removePhotographerWithoutPictures(int photographerId){
        try(PreparedStatement st = conn.prepareStatement(REMOVE_PHOTOGRAPHERS_WITHOUT_PICTURES)) {
            st.setInt(1,photographerId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            System.out.println(updated+ " eguneratuta");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removePicture(int pictureId) {
        try(PreparedStatement st = conn.prepareStatement(REMOVE_PICTURE_BY_ID)) {
            st.setInt(1, pictureId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Gaizki itxi da: " + ex.getMessage());
            }
        }
    }
}

