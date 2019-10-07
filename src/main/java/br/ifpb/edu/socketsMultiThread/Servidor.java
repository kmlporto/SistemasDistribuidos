package br.ifpb.edu.socketsMultiThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    public static List<Topico> topicos = new ArrayList<Topico>();

    public static void main(String[] args) throws IOException {
        carregarTopicos();
        System.out.println("== Servidor ==");

        ServerSocket serverSocket = new ServerSocket(7001);

        while (true) {
            Socket cliente = serverSocket.accept();
            ClienteThread clienteThread = new ClienteThread(cliente);
            clienteThread.start();

        }

    }

    static public void carregarTopicos(){
        if (topicos.size() == 0) {
            Topico topico = new Topico("Teconolodia");
            topicos.add(topico);
            topico = new Topico("Saude");
            topicos.add(topico);
            topico = new Topico("Culinaria");
            topicos.add(topico);
            topico = new Topico("Esportes");
            topicos.add(topico);
            topico = new Topico("Cultura");
            topicos.add(topico);
            topico = new Topico("Historia");
            topicos.add(topico);
        }
    }
    static public boolean isTopico(String texto){
        for(Topico p: topicos){
            if(p.nome.equals(texto))
                return true;
        }
        return false;
    }

    public static Topico getTopico(String nome){
        for(Topico p: topicos){
            if(p.nome.equals(nome))
                return p;
        }
        return null;
    }
}
