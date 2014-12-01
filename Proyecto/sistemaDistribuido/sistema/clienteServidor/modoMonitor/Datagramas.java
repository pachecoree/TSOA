/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 5
 */

package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Datagramas {

	
	public static void send(byte[] message,DatagramSocket socketSend,InetAddress address,
						    int portReceive) {
		try {
			DatagramPacket dpSend = new DatagramPacket(message,message.length,
					 			    address, portReceive);
			socketSend.send(dpSend);
		} catch (IOException e) {
			System.out.println("Error al enviar el paquete : " + e.getMessage());
		}
	}
}
