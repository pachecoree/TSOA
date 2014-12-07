/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.sistema.exclusion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import sistemaDistribuido.visual.exclusion.Maquina;
import static sistemaDistribuido.util.exclusion.Constantes.*;

public class Recepcion extends Thread{
	
	private Maquina machine;
	private boolean waitingForPackages;
	
	public Recepcion(Maquina machine) {
		this.machine = machine;
		waitingForPackages = true;
	}
	
	public void setWaitingForPackages(boolean waitingForPackages) {
		this.waitingForPackages = waitingForPackages;
	}
	
	public void run() {
		try {
			DatagramSocket socket = machine.getSocketReception();
			byte[] buffer = new byte[BUFFER_SIZE];
			DatagramPacket message = new DatagramPacket(buffer,BUFFER_SIZE);
			while (waitingForPackages) {
				machine.printLn("Esperando Paquete de la red");
				socket.receive(message);
				machine.printLn("Mensaje recibido de la red");
				machine.printLn("Procesando mensaje");
				Procesamiento p = new Procesamiento(machine,buffer.clone());
				p.start();
			}
		} catch (IOException e) {
			System.out.println("Error al recibir el paquete : " + e.getMessage());
		}
	}
	
}
