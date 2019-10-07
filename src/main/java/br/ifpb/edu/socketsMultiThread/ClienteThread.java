package br.ifpb.edu.socketsMultiThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static br.ifpb.edu.socketsMultiThread.Servidor.*;

public class ClienteThread extends Thread {
    private Socket cliente;

    public ClienteThread(Socket cliente){
        this.cliente = cliente;
    }

    public void run(){
        while(true) {
            try {
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                System.out.println("Cliente: " + cliente.getInetAddress());

                String mensagem = null;
                mensagem = dis.readUTF();
                System.out.println(mensagem);
                String[] msgSplit = mensagem.split(" ");

                switch (msgSplit[0]) {
                    case "addTopico":
                        if (msgSplit[1] == null) {
                            dos.writeUTF("Digite o nome do topico");
                        }

                        String nomeTopico = msgSplit[1];
                        if (isTopico(nomeTopico)) {
                            Topico topico = getTopico(nomeTopico);
                            if (topico != null) {
                                topico.addSubscritor(cliente);
                                dos.writeUTF("Você foi cadastrado no tópico " + nomeTopico);
                            }
                        } else {
                            dos.writeUTF("Topico " + nomeTopico + " não existe");
                        }
                        break;
                    case "listTopico":
                        dos.writeUTF(topicos.toString());
                        break;
                    default:
                        dos.writeUTF(msgSplit[0] + "- comando nao válido");
                        break;
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
