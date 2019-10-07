package br.ifpb.edu.socketnfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Servidor {
    private static String HOME = "/home/kamila/";

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println(mensagem);
            String[] msgSplit = mensagem.split(" ");
            switch (msgSplit[0]){
                case "readdir":
                    String diretorios = servidor.readdir(msgSplit[1]);
                    if (diretorios.isEmpty()){
                        dos.writeUTF("Diretorio inexistente ou vazio");
                    }else {
                        dos.writeUTF(diretorios);
                    }
                    break;
                case "rename":
                    if(servidor.rename(msgSplit[1], msgSplit[2])){
                        dos.writeUTF("");
                    }else{
                        dos.writeUTF("Arquivo inexistente");
                    }
                    break;
                case "remove":
                    if (servidor.remove(msgSplit[1])){
                        dos.writeUTF("");
                    }else{
                        dos.writeUTF("Arquivo inexistente");
                    }
                    break;
                case "create":
                    if(servidor.create(msgSplit[1])){
                        dos.writeUTF("");
                    }else{
                        dos.writeUTF("Diretorio inexistente");
                    }
                    break;
                default:
                    dos.writeUTF(msgSplit[0] + "- comando nao válido");
                    break;
            }
        }
    }

    public String readdir(String diretorio) throws IOException{
        Path path = Paths.get(HOME + diretorio);
        if (Files.exists(path)){
            Stream<Path> list = Files.list(path);
            return list.map(Path::getFileName).map(Objects::toString).collect(Collectors.joining(", "));
        }else{
            return null;
        }
    }

    public boolean rename(String nome, String novo) throws IOException{
        Path arquivo = Paths.get(HOME + nome);
        Path nomeNovo = Paths.get(HOME +novo);
        if(Files.exists(arquivo)){
            Files.move(arquivo, nomeNovo);
            return true;
        }
        return false;
    }

    public boolean create(String nome) throws IOException {
        Path arquivo = Paths.get(HOME + nome);
        if (!Files.exists(arquivo)){
            Files.createFile(arquivo);
            return true;
        }
        return false;
    }

    public boolean remove(String nome) throws IOException {
        Path arquivo = Paths.get(HOME + nome);
        if(Files.exists(arquivo)){
            Files.delete(arquivo);
            return true;
        }
        return false;
    }
}
