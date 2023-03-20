import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static javax.swing.ScrollPaneConstants.*;

public class Milestone2 extends JFrame {
    public Milestone2() {
        JPanel mende = new JPanel();
        mende.setPreferredSize(new Dimension(400,400));
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
        eki.setLayout(new BoxLayout(eki, BoxLayout.Y_AXIS));
        JTextArea testua=new JTextArea();
         testua.setPreferredSize(new Dimension(400,300));
         testua.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(testua);
        scrollPane.setPreferredSize(new Dimension(400,300));
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        eki.add(scrollPane,BorderLayout.CENTER);
        add(eki, BorderLayout.EAST);

        lenguaiak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = (String) lenguaiak.getSelectedItem();
                if (!fileName.isEmpty()) {
                    try {
                        File file = new File(fileName);
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bf = new BufferedReader(fileReader);
                        String idatzi;
                        testua.setText("");
                        while ((idatzi = bf.readLine()) != null) {
                            testua.append(idatzi + "\n");
                        }
                        bf.close();
                        fileReader.close();
                    } catch (FileNotFoundException ex) {
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Milestone2.this,
                                "Ez da ondo irakurri");
                    }
                }
            }
        });

        garbitu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testua.setText("");
            }
        });

        JButton itxi=new JButton("Itxi");
        eki.add(itxi,BorderLayout.SOUTH);
        itxi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

