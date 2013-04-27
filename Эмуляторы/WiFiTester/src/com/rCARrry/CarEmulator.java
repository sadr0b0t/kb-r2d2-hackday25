package com.rCARrry;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CarEmulator {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("[*] Started.");
		
		ServerSocket ss = new ServerSocket(new Integer(args[0]));
		Socket s = ss.accept();
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		
		System.out.println("[*] Connected.");
		
		while(true) {
			System.out.println("[+] " + (char) in.read());
		}
		
	}

}
