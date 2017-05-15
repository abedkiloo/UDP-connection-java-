import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by abed on 5/12/17.
 */
public class Server {
    static int[][] matrix;
    static String[] storeArray;
    static int port = 8034;
    static DatagramPacket recv;
    static DatagramSocket datagramSocket;

    public static void main(String args[]) {
        try {
             datagramSocket = new DatagramSocket(port);
            System.out.println("Server Created");
            byte[] fromClient = new byte[1024];
            byte[] toClient = new byte[1024];
            while (true) {
                /**
                 * create a data gram packet add the data from client and the length of packet
                 */
                recv = new DatagramPacket(fromClient, fromClient.length);
                datagramSocket.receive(recv);
                fromClient = recv.getData();

                String arrayfromClient = new String(fromClient);
                storeArray = arrayfromClient.split(",");
                int len = (int) Math.sqrt(storeArray.length);
                int counter = 0;

                matrix = new int[len][len];
                for (int is = 0; is < len; is++) {
                    for (int js = 0; js < len; js++) {
                        matrix[is][js] = Integer.parseInt(storeArray[counter]);
                        counter++;
                    }
                }


                /**
                 * calcuate determinant
                 */

                System.out.println("\n--: Matrix from Client is :--");
                for (int i1 = 0; i1 < len; i1++) {
                    for (int j1 = 0; j1 < len; j1++) {
                        System.out.print(" " + matrix[i1][j1]);
                    }
                    System.out.println("");
                }
                System.out.println("-------------------------------------------");

                int det = calculateDeterminant(matrix);
                System.out.println("/n Determinant is :" + det);
                String determinant = String.valueOf(det);

                toClient = determinant.getBytes();
                InetAddress inetAddress = recv.getAddress();
                int port = recv.getPort();
                DatagramPacket datagramPacketToClient = new
                        DatagramPacket(toClient, toClient.length, inetAddress, port);
                datagramSocket.send(datagramPacketToClient);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int calculateDeterminant(int[][] matrix) {
        int det = 0;
        int sign = 1;
        int size = matrix.length;
        if (size == 1) {
            det = matrix[0][0];
        } else if (size > 1) {
            for (int j1 = 0; j1 < size; j1++) {
                int[][] m = generateSubArray(matrix, j1);
                det += sign * matrix[0][j1] * calculateDeterminant(m);
                sign = -sign;
            }
        }
        return det;
    }

    private static int[][] generateSubArray(int[][] matrix, int j1) {
        int[][] genMatr = new int[(matrix.length) - 1][(matrix.length) - 1];
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j <

                    matrix.length; j++) {
                if (j < j1) {
                    genMatr[i - 1][j] = matrix[i][j];
                } else if (j > j1) {
                    genMatr[i - 1][j - 1] = matrix[i][j];
                }

            }
        }
        return genMatr;
    }
}
//212254946
// 3331764




//0708088351