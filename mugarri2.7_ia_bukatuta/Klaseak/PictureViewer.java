package Klaseak;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PictureViewer extends JFrame {
    private JComboBox<String> photographerComboBox;
    private JXDatePicker datePicker;
    private JList<String> pictureList;
    private ImageIcon pictureImage;
    private  JLabel imageLabel;
    List<Picture> pictures;


    public PictureViewer(){
        datePicker = new JXDatePicker();
        photographerComboBox = new JComboBox<>();
        imageLabel = new JLabel();
        pictureList = new JList<>();

        setLayout(new GridLayout(2,2));
        setSize(1000,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel Panelnagusi = new JPanel();
        photographerComboBox.setPreferredSize(new Dimension(300,40));
        photographerComboBox.addActionListener(e ->PictureList((String) photographerComboBox.getSelectedItem()));
        photographerComboBox.addItem(null);
        this.loadPhotographersComboBox();

        JButton awarded=new JButton("Award");
        awarded.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                awardPhotographger();
            }
        });
        JButton remove=new JButton("remove");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });

        JPanel awardPanel=new JPanel();
        awardPanel.add(awarded);
        awardPanel.add(remove);

        Panelnagusi.add(new JLabel("Photographer:"));
        Panelnagusi.add(photographerComboBox);

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Photos after:"));
        datePicker.addActionListener(e ->PictureList((String) photographerComboBox.getSelectedItem()));
        datePanel.add(datePicker);

        JScrollPane pictureListPanel = new JScrollPane(pictureList);
        pictureListPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pictureList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() ==  2){
                    for(Picture p: pictures){
                        if(pictureList.getSelectedValue().equals(p.getTitle())){
                            PictureImage(p.getFile_());
                            new DBConnection(DBConnection.getConnection()).updateVisits(p.getPictureId());
                        }
                    }
                }
            }
        });

        JPanel picturePanel = new JPanel();
        picturePanel.add(imageLabel);

        add(Panelnagusi);
        add(datePanel);
        add(pictureListPanel);
        add(picturePanel);
        add(awardPanel);

        pack();
        setVisible(true);
    }
    private void awardPhotographger() {
        int gutxieneko = Integer.parseInt(JOptionPane.showInputDialog("Gutxieneko bisitak:"));
        DBConnection conn = new DBConnection(DBConnection.getConnection());

        for (Map.Entry<Integer, Integer> ikusia: conn.createVisitsMap().entrySet()){
            if(gutxieneko <=  ikusia.getValue()){
                conn.updateAward(ikusia.getKey());
            }
        }
    }
    private void remove(){
        DBConnection conn = new DBConnection(DBConnection.getConnection());
        List<Integer> kentzekoak = conn.getNotAwardedPhotographerId();

        for (Integer kendu : kentzekoak){
            for(Map.Entry<Integer,Integer>bisitaEz : conn.bisitaGabe(kendu).entrySet()){
                int egiaztatu =JOptionPane.showConfirmDialog(null,"Ziur?");
                if(egiaztatu==0 && conn.getPhotographerWithPictues(kendu)){
                    conn.removePicture(bisitaEz.getKey());
                    conn.removePhotographerWithoutPictures(kendu);
                    System.out.println(bisitaEz.getKey()+" kenduta");
                } else{
                    System.out.println("Ez da kendu");
                }
            }
        }
    }

    private void loadPhotographersComboBox(){
        DBConnection c = new DBConnection(DBConnection.getConnection());
        List<String> izenak = c.getPhotographerNames();
        for (String izena: izenak){
            photographerComboBox.addItem(izena);
        }
    }

    private void PictureList(String photographerName){
        DBConnection c = new DBConnection(DBConnection.getConnection());
        DefaultListModel<String> pictureTitles = new DefaultListModel<>();
        if(datePicker.getDate() != null){
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String newDate = formatDate.format(datePicker.getDate());
            pictures = c.getPicuresByPhotographerAndDate(photographerName, newDate);
        }else{
            pictures = c.getPicuresByPhotographer(photographerName);
        }
        for (Picture p: pictures){
            pictureTitles.addElement(p.getTitle());
        }
        this.pictureList.setModel(pictureTitles);
    }

    private void PictureImage(String imagePath){
        pictureImage = new ImageIcon("src/Images/"+imagePath);
        Image image = pictureImage.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        pictureImage = new ImageIcon(image);
        imageLabel.setIcon(pictureImage);
    }


    public static void main(String[] args) {
        new PictureViewer();

    }
}