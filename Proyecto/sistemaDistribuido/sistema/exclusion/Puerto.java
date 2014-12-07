/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.sistema.exclusion;

import static sistemaDistribuido.util.exclusion.Constantes.*;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Puerto {
	DatagramSocket socketBroadcast;
	
	public Puerto() {
		initializeSocketBroadcast();
	}

	private void initializeSocketBroadcast() {
		socketBroadcast = null;
		try {
			socketBroadcast = new DatagramSocket(OUT_PORT);
		} catch (SocketException e) {
			System.out.println("Error al iniciar el puerto de salida: " + e.getMessage());
		}
	}
	
	public void closeSockets() {
		if (socketBroadcast != null) {
			socketBroadcast.close();
		}
	}
	
	public DatagramSocket getSocketBroadcast() {
		return socketBroadcast;
	}
}
