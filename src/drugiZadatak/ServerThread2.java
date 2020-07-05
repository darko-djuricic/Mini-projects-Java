package drugiZadatak;

import java.io.*;
import java.net.Socket;

public class ServerThread2 extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public ServerThread2(Socket socket) throws IOException {
        this.socket=socket;
        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        start();
    }

    @Override
    public void run() {
        try {
            String[] request=in.readLine().split("%");
            String[][] rez=new String[4][3];
            for (int i = 0; i < 4; i++) {
                rez[i]=request[i].split("#");
            }

            double[][] matrica=new double[3][3];
            double[] zbir=new double[3];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if(i==3){
                        zbir[j]=Double.parseDouble(rez[i][j]);
                    }
                    else{
                        matrica[i][j]=Double.parseDouble(rez[i][j]);
                    }

                }
            }

            double[][] matricaX={{zbir[0],matrica[0][1],matrica[0][2]},{zbir[1],matrica[1][1],matrica[1][2]},{zbir[2],matrica[2][1],matrica[2][2]}};
            double[][] matricaY={{matrica[0][0],zbir[0],matrica[0][2]},{matrica[1][0],zbir[1],matrica[1][2]},{matrica[2][0],zbir[2],matrica[2][2]}};
            double[][] matricaZ={{matrica[0][0],matrica[0][1],zbir[0]},{matrica[1][0],matrica[1][1],zbir[1]},{matrica[2][0],matrica[2][1],zbir[2]}};

            double x= racunanjeDeterminante3sa3(matricaX)/racunanjeDeterminante3sa3(matrica);
            double y= racunanjeDeterminante3sa3(matricaY)/racunanjeDeterminante3sa3(matrica);
            double z= racunanjeDeterminante3sa3(matricaZ)/racunanjeDeterminante3sa3(matrica);

            System.out.println("x: "+x+"; y: "+y+" ; z: "+z);

            out.println(x+"%"+y+"%"+z);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double racunanjeDeterminante2sa2(double[][] matrica){
        return (matrica[0][0]*matrica[1][1])-(matrica[1][0]*matrica[0][1]);
    }

    public double racunanjeDeterminante3sa3(double[][] matrica){
        double[][] det1={{matrica[1][1],matrica[1][2]},{matrica[2][1],matrica[2][2]}};
        double[][] det2={{matrica[1][0],matrica[1][2]},{matrica[2][0],matrica[2][2]}};
        double[][] det3={{matrica[1][0],matrica[1][1]},{matrica[2][0],matrica[2][1]}};
        return matrica[0][0]*racunanjeDeterminante2sa2(det1)-matrica[0][1]*racunanjeDeterminante2sa2(det2)+matrica[0][2]*racunanjeDeterminante2sa2(det3);
    }
}
