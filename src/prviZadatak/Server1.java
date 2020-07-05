package prviZadatak;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(9000);
        int i=0;
        System.out.println("Server1 pokrenut...");
        while (true){
            Socket socket=ss.accept();
            System.out.println("Prihvacen zahtev "+(++i));
            ServerThread1 serverThread=new ServerThread1(socket);
        }
    }
}
