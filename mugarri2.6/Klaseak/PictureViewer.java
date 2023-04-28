package Klaseak;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.JXDatePicker;

public class PictureViewer extends JFrame {

    private JComboBox<String> photographerComboBox;
    private JXDatePicker datePicker;
    private JList<File> photoList;
    private DefaultListModel<File> photoListModel;
    private JLabel photoLabel;

    public PictureViewer() {
        DBConnection conn =new DBConnection();
        conn.getConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
        selectionPanel.setPreferredSize(new Dimension(800, 30));
        selectionPanel.add(new JLabel("Photographer:"));

        photographerComboBox = new JComboBox<String>();

        List<Photographer> photographers = conn.getAllPhotographers();
        for (Photographer photographer : photographers) {
            photographerComboBox.addItem(photographer.getName());
        }

        selectionPanel.add(photographerComboBox);
        selectionPanel.add(new JLabel("Photo after"));
        datePicker = new JXDatePicker();
        selectionPanel.add(datePicker);


        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setPreferredSize(new Dimension(400, 400));
        photoListModel = new DefaultListModel<File>();
        photoList = new JList<File>(photoListModel);
        photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photoList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    File selectedFile = photoList.getSelectedValue();
                    if (selectedFile != null) {
                        photoLabel.setIcon(new ImageIcon(selectedFile.getPath()));
                    }
                }
            }
        });
        JScrollPane photoListScrollPane = new JScrollPane(photoList);
        photoListScrollPane.setPreferredSize(new Dimension(200, 400));
        photoPanel.add(photoListScrollPane, BorderLayout.WEST);
        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(200, 400));
        photoPanel.add(photoLabel, BorderLayout.CENTER);

        add(selectionPanel, BorderLayout.NORTH);
        add(photoPanel, BorderLayout.CENTER);


        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new PictureViewer();
    }
}