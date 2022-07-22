/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.chat;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
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

            String nick, ip, mensaje;

            InformacionEnvio informacionRecibida;

            while(true){
                Socket miSocket = servidor.accept();

                ObjectInputStream paqueteDatos = new ObjectInputStream(miSocket.getInputStream());

                informacionRecibida = (InformacionEnvio)paqueteDatos.readObject();

                nick = informacionRecibida.getNick();
                ip = informacionRecibida.getIp();
                mensaje = informacionRecibida.getMensaje();

                /*DataInputStream flujoEntrada = new DataInputStream(
                    miSocket.getInputStream());

                String mensajeTexto = flujoEntrada.readUTF();

                areaTexto.append("\n" + mensajeTexto);*/

                areaTexto.append("\n" + nick + ": " + mensaje + " para " + ip);

                Socket enviaDestinatario = new Socket(ip, 9090);

                ObjectOutputStream informacionReenvio = new ObjectOutputStream(
                        enviaDestinatario.getOutputStream());

                informacionReenvio.writeObject(informacionRecibida);

                enviaDestinatario.close();


                miSocket.close();
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private JTextArea areaTexto;
}