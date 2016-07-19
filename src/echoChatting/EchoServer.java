package echoChatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(10001);
			
			System.out.println("Clinet 접속 대기중...");
			Socket sock = server.accept();
			
//			InetAddress ip = InetAddress.getLocalHost();  
//			System.out.println(ip.getHostAddress());
			
			InetAddress inetAddress = sock.getInetAddress();
			System.out.println(inetAddress.getHostAddress()+ "이(가) 접속했습니다");
			
			OutputStream os = sock.getOutputStream();
			InputStream is = sock.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
			
			String line = null;
					
					while( (line = br.readLine()) != null){
						System.out.println(inetAddress.getHostAddress() + " : " + line);
						pw.println(line);
						pw.flush();
					}
			pw.close();
			br.close();
			sock.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("접속 대기중...");
		
		
	}
}
