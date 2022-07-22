/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.chat;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.util.*;

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

class LaminaMarcoCliente extends JPanel implements Runnable{

    public LaminaMarcoCliente(){

        nick = new JTextField(5);
        add(nick);

        JLabel texto = new JLabel("Cliente");
        add(texto);

        ip = new JTextField(5);
        add(ip);

        campoChat = new JTextArea(10, 16);
        add(campoChat);

        campo1 = new JTextField(16);
         add(campo1);

         miBoton = new JButton("-Chat-");

         EnviaTexto miEvento = new EnviaTexto();

         miBoton.addActionListener(miEvento);

         add(miBoton);

         Thread miHilo = new Thread(this);

         miHilo.start();

    }

    @Override
    public void run() {

        try{

            ServerSocket servidorCliente = new ServerSocket(9090);

            Socket cliente;

            InformacionEnvio informacionRecibida;

            while(true){

                cliente = servidorCliente.accept();

                ObjectInputStream flujoEntrada = new ObjectInputStream(
                        cliente.getInputStream());

                informacionRecibida = (
                        InformacioEnvio)flujoEntrada.readObject();

                campoChat.append("\n" + informacionRecibida.getNick() + " : "
                + informacionRecibida.getMensaje());
            }

        } catch(Exception e){

            System.out.println(e.getMessage());
        }
    }

   private class EnviaTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //System.out.println(campo1.getText());


            try {

                Socket miSocket = new Socket("10.162.25.208", 9999);

                InformacionEnvio datos = new InformacionEnvio();

                datos.setNick(nick.getText());
                datos.setIp(ip.getText());
                datos.setMensaje(campo1.getText());

                ObjectOutputStream paqueteDatos = new ObjectOutputStream(
                        miSocket.getOutputStream());

                paqueteDatos.writeObject(datos);
                paqueteDatos.close();

                /*DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());

                flujoSalida.writeUTF(campo1.getText());
                flujoSalida.close();
                */

            } catch (UnknownHostException ex) {
                ex.printStackTrace();

            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }

        }

   }

    private JTextField campo1, nick, ip;
    private JButton miBoton;
    private JTextArea campoChat;
}

class InformacionEnvio implements Serializable{

    private String nick, ip, mensaje;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}