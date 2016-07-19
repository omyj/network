package echoChatting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
	public static void main(String[] args) {
		try {
			
			
			Socket sock = new Socket("220.67.115.228", 5000);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			OutputStream os = sock.getOutputStream();
			InputStream is = sock.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line = null;
			while ((line = keyboard.readLine()) != null){
				if (line.equals("quit")) break;
				
				pw.println(line);
				pw.flush();
				
				String echo = br.readLine();
				System.out.println("Server echo : " + echo);
			}
			pw.close();
			br.close();
			sock.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
