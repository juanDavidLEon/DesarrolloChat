/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chate;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author dintev
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MarcoSevidor  miMarco = new MarcoSevidor();
        
        miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}

class MarcoSevidor extends JFrame implements Runnable{
    
    public MarcoSevidor(){
        
        setBounds(200,200,280,350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel miLamina = new JPanel();
        
        miLamina.setLayout(new BorderLayout());
        
        areaTexto = new JTextArea();
        
        miLamina.add(areaTexto, BorderLayout.CENTER);
        add(miLamina);
        setVisible(true);
        
        Thread miHilo = new Thread(this);
        miHilo.start();
        
    }
    
    private JTextArea areaTexto;

    @Override
    public void run() {
        
        try {
            //System.out.println("Funciona hilo");

            ServerSocket servidor = new ServerSocket(9999);
            
            
            String nick, ip, mensaje;
            PaqueteEnvio paqueteRecibido;
            
            while (true) {    
         
            Socket miSocket = servidor.accept();
            
            ObjectInputStream paqueteDatos = new ObjectInputStream(miSocket.getInputStream());
            
            paqueteRecibido = (PaqueteEnvio) paqueteDatos.readObject();
            
            nick = paqueteRecibido.getNick();
            ip= paqueteRecibido.getIp();
            mensaje= paqueteRecibido.getMensaje();
            
            /*DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());
            
            String mensajeTexto = flujoEntrada.readUTF();
            
            areaTexto.append("\n" + mensajeTexto);*/
            
            areaTexto.append("\n" + nick + ": " + mensaje + " para " +ip);
            
            Socket enviaDestino = new Socket(ip, 9090);
           
            ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestino.getOutputStream());
              
           paqueteReenvio.writeObject(paqueteRecibido);
           
           paqueteReenvio.close();
           enviaDestino.close();
           miSocket.close();      
           
           }
            
        } catch (IOException ex) {
            
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MarcoSevidor.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}
