import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends JFrame implements ActionListener{
    private JComboBox<String> comboBox;
    private JButton gorde;

    private JCheckBox checkBox;
    private JTextField textField;
    private JLabel imageLabel;


    private static final String rutaFolder = "images/";//ruta
    private static final String password = "damocles";//pasahitza

    public Main(){

        this.logIn();
        setPreferredSize(new Dimension(600,600));
        this.pack();
        setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JOptionPane.showMessageDialog(null,"Agur");
                dispose();
            }
        });

        getContentPane().setLayout(new BorderLayout());

        this.comboBox = new JComboBox<>();
        JPanel comboPanel = new JPanel();
        comboBox.setPreferredSize(new Dimension(200,30));
        comboPanel.add(this.comboBox);
        this.load_combo();
        this.comboBox.addActionListener(new ComboListener(this));

        String rutaImage = rutaFolder + comboBox.getSelectedItem();
        this.imageLabel = new JLabel(new ImageIcon(rutaImage));
        this.imageLabel.setPreferredSize(new Dimension(200,200));

        this.checkBox = new JCheckBox("Gorde komentarioa");
        this.checkBox.setSelected(true);

        JPanel ipar= new JPanel(new GridLayout(3,1));
        ipar.add(comboPanel);
        ipar.add(this.imageLabel);
        ipar.add(this.checkBox);

        JPanel eki = new JPanel(new BorderLayout());
        this.textField = new JTextField(20);
        eki.add(this.textField, BorderLayout.SOUTH);
        eki.setBorder(BorderFactory.createEmptyBorder(0,0,40,10));

        JPanel hego = new JPanel();
        this.gorde = new JButton("Save");
        hego.add(this.gorde);
        this.gorde.addActionListener(this);

        getContentPane().add(ipar,  BorderLayout.WEST);
        getContentPane().add(eki, BorderLayout.EAST);
        getContentPane().add(hego, BorderLayout.SOUTH);
        setVisible(true);
    }


    private void load_combo(){
        File folder = new File(rutaFolder);
        File[] listFiles = folder.listFiles();

        for (File file: listFiles){
            String fileName = file.getName();
            if(fileName.endsWith(".png")||fileName.endsWith(".jpg")){
                this.comboBox.addItem(fileName);
            }
        }
    }



    private void gorde(){
        String irudi = (String) this.comboBox.getSelectedItem();
        String comment = this.textField.getText();

        if(checkBox.isSelected()){
            try(FileWriter file = new FileWriter((rutaFolder+"comments/"+irudi+".txt"),true)){
                file.write(irudi+" "+comment+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void logIn(){
        String input = JOptionPane.showInputDialog("Input password");

        if(!input.equals(password)){
            System.exit(0);
        }
    }
    public void load_image(){
        String rutaImage = rutaFolder + comboBox.getSelectedItem();
        ImageIcon irudia = new ImageIcon(rutaImage);
        this.imageLabel.setIcon(irudia);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.gorde)){
            this.gorde();
        }
    }


    public static void main(String[] args) {
        new Main();
    }


}
