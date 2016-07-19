package kr.ac.sungkyul.echoChatting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	public static void main(String[] args) {
		try {
			
			Scanner scanner = new Scanner(System.in);
			
			// 1. 서버연결
			Socket sock = new Socket("192.168.56.1", 10001);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			
			// 2. IOEStream 받아오기
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			String line = null;
//			while ((line = keyboard.readLine()) != null){
//				if (line.equals("quit")) break;
//				
//				pw.println(line);
//				pw.flush();
//				
//				String echo = br.readLine();
//				System.out.println("Server echo : " + echo);
//			}
			while (true) {
				System.out.println(">>");
				String message = scanner.nextLine();
				
				if ("exit".equals(message)){
					break;
				}
				//메세지 보내기
				pw.println(message);
				pw.flush();
				
				// 메세지 받기
				String messageEcho = br.readLine();
				System.out.println("Sever echo : " + messageEcho);
			}
			
			pw.close();
			br.close();
			sock.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
