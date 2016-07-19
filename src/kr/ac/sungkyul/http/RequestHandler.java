package kr.ac.sungkyul.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.sound.midi.Patch;

public class RequestHandler extends Thread {
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// get IOStream
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			OutputStream outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());

			String line = null;
			String request = null;
			while (true) {
				line = br.readLine();
				if (line == null || line.equals("")) {
					break;
				}
				if (request == null) {
					request = line; // GET /favicon.ico HTTP/1.1
				}
			}
				consoleLog("request:" + request);
				String[] tokens = request.split(" ");

				if ("GET".equals(tokens[0])) {
					responseStaticResource(outputStream, tokens[1], tokens[2]);

				} else {
					// POST, PUT, DELETE 지원 안함 ( 400 Bad Request )
					resopns400Error(outputStream, tokens[2]);
				}
			
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.

			// outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));// 헤더
			// outputStream.write("Content-Type:text/html;charset=utf-8\r\n".getBytes("UTF-8")); //헤더
			// outputStream.write("\r\n".getBytes());
			// outputStream.write("<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할준비가 된 것입니다.</h1>".getBytes("UTF-8")); 

		} catch (Exception ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	private void resopns400Error(OutputStream outputStream, String protocol) throws IOException {
		// TODO Auto-generated method stub
		File file = new File( "./webapp/error/400.html");
		byte[] body = Files.readAllBytes( file.toPath() );
		// 응답 헤더
		outputStream.write( ( protocol + " 200 OK\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ( "Content-Type:text/html; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes( "UTF-8" ) );
		
		// 응답 바디
		outputStream.write( body );
	}
	private void resopns404Error(OutputStream outputStream, String protocol) throws IOException {
		// TODO Auto-generated method stub
		File file = new File( "./webapp/error/404.html");
		byte[] body = Files.readAllBytes( file.toPath() );
		// 응답 헤더
		outputStream.write( ( protocol + " 200 OK\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ( "Content-Type:text/html; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes( "UTF-8" ) );
		
		// 응답 바디
		outputStream.write( body );
	}

	private void responseStaticResource( 
			OutputStream outputStream, String url, String protocol ) throws IOException {
			File file = new File( "./webapp" + url );
			if( file.exists() == false ) {
					resopns404Error(outputStream, protocol);
				return;
			}
			
//			byte[] body = Files.readAllBytes( file.toPath() );
			
			Path path = file.toPath();
			String mime = Files.probeContentType(path);
			byte[] body = Files.readAllBytes( path );
			// 응답 헤더
			outputStream.write( ( protocol + " 200 OK\r\n").getBytes( "UTF-8" ) );
			outputStream.write( ( "Content-Type:" + mime + "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
			
			outputStream.write( "\r\n".getBytes( "UTF-8" ) );
			
			// 응답 바디
			outputStream.write( body );
		}

	public void consoleLog(String message) {
		System.out.println("[RequestHandler#" + getId() + "] " + message);
	}
}
