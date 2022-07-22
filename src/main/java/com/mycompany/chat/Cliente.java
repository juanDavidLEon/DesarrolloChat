/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.chat;

import java.awt.event.*;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dintev
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        MarcoCliente miMarco = new MarcoCliente();
        
        miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
}

class MarcoCliente extends JFrame{
    
    public MarcoCliente(){
        
        setBounds(600, 300, 200, 350);
        
        LaminaMarcoCliente miLamina = new LaminaMarcoCliente();
        
        add(miLamina);
        
        setVisible(true);
    }
        
}

class LaminaMarcoCliente extends JPanel{
    
    public LaminaMarcoCliente(){
        
        JLabel texto = new JLabel("Cliente");
        
        add(texto);
        
        campo1 = new JTextField(20);
         add(campo1);
         
         miBoton = new JButton("Enviar");
         
         EnviaTexto miEvento = new EnviaTexto();
         
         miBoton.addActionListener(miEvento);
         
         add(miBoton);
         
    }
    
   private class EnviaTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            //System.out.println(campo1.getText());
                            

            try {

                Socket miSocket = new Socket("10.162.25.208", 9999);
                
                DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());
                
                flujoSalida.writeUTF(campo1.getText());
                flujoSalida.close();
                
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
                
            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }
            
        }       
       
   }
    
    private JTextField campo1;
    private JButton miBoton; 
}
