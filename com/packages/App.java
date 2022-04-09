package com.packages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JButton panelApp;
    private JPanel panel1;

    public App() {
        panelApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"hello world");
            }
        });
    }

    public static void main(String[] args) {
        JFrame Frame=new JFrame("App");
    Frame.setContentPane(new App().panel1);
    Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Frame.pack();
    Frame.setVisible(true);

    }
}
