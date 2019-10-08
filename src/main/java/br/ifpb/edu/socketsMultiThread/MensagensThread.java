package br.ifpb.edu.socketsMultiThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import static br.ifpb.edu.socketsMultiThread.Servidor.getTopico;
import static br.ifpb.edu.socketsMultiThread.Servidor.isTopico;

public class MensagensThread extends Thread{
    private ServerSocket servidor;

    public MensagensThread(ServerSocket servidor){
        this.servidor = servidor;
    }

    public void run(){
        Scanner mensagem = new Scanner(System.in);
        System.out.print("cmmd: ");
        String[] msgSplit = mensagem.toString().split(" ");

        try{
            switch (msgSplit[0]) {
                case "addMensagem":
                    if (msgSplit[1] == null) {
                        System.out.print("Digite o nome do topico");
                    }

                    String nomeTopico = msgSplit[1];
                    if (isTopico(nomeTopico)) {
                        Topico topico = getTopico(nomeTopico);
                        if (topico != null) {
                            topico.addMensagem(msgSplit[2]);
                        }
                }
                break;
            }
        }catch (
        IOException e) {
            e.printStackTrace();
        }
    }
}
