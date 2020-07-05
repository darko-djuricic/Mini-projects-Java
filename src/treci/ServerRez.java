package treci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRez {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(7000);
        System.out.println("Server3 pokrenut");
        int i=1;
        while (true){
            Socket socket=ss.accept();
            System.out.println("Prihvacen "+(i++)+". zahtev");
            ServerThread3 st3=new ServerThread3(socket);
        }
    }
}
