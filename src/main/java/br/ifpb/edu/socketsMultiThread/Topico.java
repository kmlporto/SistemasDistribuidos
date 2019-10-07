package br.ifpb.edu.socketsMultiThread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Topico {
    public String nome;
    public List<String> mensagens;
    public List<Socket> subscritores;

    public Topico(String nome){
        this.nome = nome;
        this.mensagens = new ArrayList<String>();
        this.subscritores = new ArrayList<Socket>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void addMensagem(String mensagem) throws IOException {
        this.mensagens.add(mensagem);
        for(Socket c: subscritores){
            DataOutputStream dos = new DataOutputStream(c.getOutputStream());
            dos.writeUTF(mensagem);
        }
    }

    public List<Socket> getSubscritores() {
        return subscritores;
    }

    public void addSubscritor(Socket subscritor) {
        this.subscritores.add(subscritor);
    }

    @Override
    public String toString() {
        return nome;
    }
}
