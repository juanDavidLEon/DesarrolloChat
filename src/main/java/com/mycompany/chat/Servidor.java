/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.chat;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author dintev
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        MarcoServidor miMarco = new MarcoServidor();
        miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
}


class MarcoServidor extends JFrame implements Runnable{
    
    public MarcoServidor(){
        
        setBounds(1200, 300, 280, 350);
        
        JPanel miLamina = new JPanel();
        
        miLamina.setLayout(new BorderLayout());
        
        areaTexto= new JTextArea();
        
        miLamina.add(areaTexto, BorderLayout.CENTER);
        
        add(miLamina);
        
        setVisible(true);
        
        Thread miHilo = new Thread(this);
        
        miHilo.start();
    }
    

    @Override
    public void run() {
        try {
            //System.out.println("Estoy a la escucha");

            ServerSocket servidor = new ServerSocket(9999);
            
            while(true){
                Socket miSocket = servidor.accept();
            
                DataInputStream flujoEntrada = new DataInputStream(
                    miSocket.getInputStream());
            
                String mensajeTexto = flujoEntrada.readUTF();
            
                areaTexto.append("\n" + mensajeTexto);
            
                miSocket.close();  
            }
            
        } catch (IOException ex) {
            
            ex.printStackTrace();
        }
                   
    }
    
    private JTextArea areaTexto;
}
