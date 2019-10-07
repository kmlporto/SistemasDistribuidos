package br.ifpb.edu.socketsMultiThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("== Cliente ==");

        Socket socket = new Socket("127.0.0.1", 7001);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while (true) {
            Scanner teclado = new Scanner(System.in);
            System.out.print("cmmd: ");
            dos.writeUTF(teclado.nextLine());

            Thread.sleep(2000);
            int teste = dis.available();
            while(teste != 0) {
                String mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
                teste = dis.available();
            }
        }

    }
}
