/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.sistema.exclusion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Emision{
	
	
	public static void send(byte[] message,DatagramSocket socketSend,InetAddress address,
		    int portReceive) {
		new HiloEnvia(message,socketSend,address,portReceive).start();
		
	}
}


class HiloEnvia extends Thread {
	
	byte[] message;
	DatagramSocket socketSend;
	InetAddress address;
	int portReceive;
	
	public HiloEnvia(byte[] message,DatagramSocket socketSend,InetAddress address,
		    int portReceive) {
		
		this.message = message;
		this.socketSend = socketSend;
		this.address = address;
		this.portReceive = portReceive;
	}
	
	public void run() {
		try {
			DatagramPacket dpSend = new DatagramPacket(message,message.length,
				 			    address, portReceive);
			Thread.sleep(5000);
			socketSend.send(dpSend);
		} catch (IOException e) {
			System.out.println("Error al enviar el paquete : " + e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}