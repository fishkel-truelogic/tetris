package ar.com.finit.tetris.lan;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class LanConnection {

	private Socket clientSocket;
	
	private ServerSocket serverSocket;
	
	private DataOutputStream out;
	
	private BufferedReader in;
	
	public LanConnection(String ip, int port) throws IOException {
		startServer(port);
		startSocket(ip, port);
		startInOut();
	}
	
	private void startInOut() {
		try {
		out = new DataOutputStream(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void startSocket(String ip, int port) {
		boolean error = false;
		try {
			this.clientSocket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			error = true;
			e.printStackTrace();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		}
		
		if (error) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	private void startServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendLine() {
		try {
			if (clientSocket != null) {
				String str = "l;";
				byte[] buf = str.getBytes();
				out.write(buf);
				out.flush();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public int readLine() {
		String lines = "";
		try {
			if (in.ready()) {
				String s = null;
				while ((s = in.readLine()) != null) {
					lines += s;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("bla " + lines);
		int count = 0;
		if (!lines.isEmpty()) {
			count = lines.split(";").length -1;
		}
		
		return count;
	}
	

	
}
