import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.*;

public class Milestone2 extends JFrame {
    public Milestone2() {
        JPanel mende = new JPanel();
        mende.setPreferredSize(new Dimension(600,400));
        String [] lenguaimota={"","Python.txt","C.txt","java.txt"};
        JComboBox lenguaiak = new JComboBox(lenguaimota);
        lenguaiak.setSelectedIndex(0);
        lenguaiak.setPreferredSize(new Dimension(250,25));
        mende.add(lenguaiak);
        JButton garbitu=new JButton("Clear");
        mende.add(garbitu);
        add(mende, BorderLayout.WEST);

        JPanel eki=new JPanel();
        eki.setPreferredSize(new Dimension(600,400));
         JTextArea testua=new JTextArea();
        JScrollPane scrollPane = new JScrollPane(testua);
        scrollPane.setPreferredSize(new Dimension(300,200));
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        eki.add(scrollPane,BorderLayout.CENTER);
        add(eki, BorderLayout.EAST);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

