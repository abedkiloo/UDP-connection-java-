import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

/**
 * Created by abed on 5/12/17.
 */
public class Client {
    static byte[] toServe = new byte[1024];
    static byte[] fromServer = new byte[1024];
    static DatagramPacket datagramPacket;
    static DatagramSocket datagramSocket;
    static int sizeOfArray = 0;
    static int elm[][];
    static int array;
    static InetAddress inetAddress = null;
    static int port = 8034;

    public static void main(String args[]) {
        readFromServer();
    }

    private static void readFromServer() {


        /**
         * create a socket for client
         */

        try {
            datagramSocket = new DatagramSocket();
            inetAddress = InetAddress.getByName("localhost");
            System.out.print("Enter Size of Array :-");
            Scanner scanner = new Scanner(System.in);
            sizeOfArray = scanner.nextInt();



            elm = new int[sizeOfArray][sizeOfArray];
            System.out.println("--: Enter the values of array :--");
            for (int i = 0; i < sizeOfArray; i++) {
                for (int j = 0; j < sizeOfArray; j++) {
                    System.out.print("[" + i + "]" + "[" + j + "]=");
                    elm[i][j] = scanner.nextInt();
                }
            }

            /*
            print the array
             */

            String arrayData = "";
            System.out.println("\n--: Matrix  is :--");
            for (int i1 = 0; i1 < sizeOfArray; i1++) {
                for (int j1 = 0; j1 < sizeOfArray; j1++) {
                    System.out.print(" " + elm[i1][j1]);
                    arrayData = arrayData.concat(elm[i1][j1] + ",");

                }
                System.out.println("");
            }
            toServe = arrayData.getBytes();
            sendtoServe(toServe, inetAddress, datagramSocket);
            System.out.println("-------------------------------------------");

            toServe = arrayData.getBytes();

            datagramPacket = new DatagramPacket(fromServer, fromServer.length);
            datagramSocket.receive(datagramPacket);

            String det = new String(datagramPacket.getData());
            System.out.println("Determinant is :-" + det);
            datagramSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {

            //fails to initialize inetaddress
            e.printStackTrace();
        } catch (IOException e) {

            //catch exception for sending the data packet
            e.printStackTrace();
        }
    }

    private static void sendtoServe(byte[] toServe, InetAddress inetAddress, DatagramSocket datagramSocket) throws IOException {
        //send to serve
        datagramPacket = new DatagramPacket(toServe, toServe.length, inetAddress, port);
        datagramSocket.send(datagramPacket);
    }
}
