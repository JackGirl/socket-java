package cn.ulyer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientB {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        while (true){
            String s = scanner.nextLine();
            writer.write(s+"\n");
            writer.flush();
            String result =  reader.readLine();
            System.out.println(result);
        }

    }
}
