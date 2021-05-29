package cn.ulyer;

import cn.ulyer.socket.util.CharUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class ClientStart {


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(System.getProperty("server.host"),Integer.valueOf(System.getProperty("server.port")));
        socket.setKeepAlive(true);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CharUtil.ENCODE));
        Scanner scanner = new Scanner(System.in);
        final boolean[] status = {true};
        CompletableFuture.runAsync(()->{
            while(status[0]){
                try{
                    String s = reader.readLine();
                    if (s!=null){
                        System.out.println(s);
                    }
                }catch (Exception e){
                    status[0] =false;
                }
            }


        });
        while (true){
            String s = scanner.nextLine();
            writer.write(s+"\n");
            writer.flush();
        }

    }


}
