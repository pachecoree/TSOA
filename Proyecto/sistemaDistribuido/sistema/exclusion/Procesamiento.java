/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.sistema.exclusion;

import java.net.InetAddress;
import java.net.UnknownHostException;

import sistemaDistribuido.util.exclusion.OrdenamientoBytes;
import sistemaDistribuido.visual.exclusion.Maquina;
import static sistemaDistribuido.util.exclusion.Constantes.*;

public class Procesamiento extends Thread{
	
	Maquina machine;
	byte[] message;
	
	public Procesamiento(Maquina machine,byte[] message) {
		this.machine = machine;
		this.message = message;
	}
	
	private void sendOk(int sourcePort) {
		byte[] message = new byte[SOURCE_SIZE+PORT_SIZE+SOL_SIZE];
		OrdenamientoBytes.breakNumber(machine.getIdProcess(), message, BYTES_INT,SOURCE_POS);
		OrdenamientoBytes.breakNumber(machine.getInPort(), message, BYTES_INT,PORT_POS);
		OrdenamientoBytes.breakNumber(OK_RESPONSE, message, BYTES_INT,SOL_POS);
		try {
			Emision.send(message,machine.getSocketBroadCast(),InetAddress.getByName(BROADCAST_IP),sourcePort);
		} catch (UnknownHostException e) {
			System.out.println("Error al obtener la dirección del anfitrión : "+  e.getMessage());
		}
	}
	
	public void run() {
		int sourceId = OrdenamientoBytes.buildNumber(SOURCE_SIZE, message,SOURCE_POS);
		int sourcePort = OrdenamientoBytes.buildNumber(PORT_SIZE, message,PORT_POS);
		int sol = OrdenamientoBytes.buildNumber(SOL_SIZE, message,SOL_POS);
		machine.printLn("Id : " +sourceId + " solicitud : " + sol);
		if (sourceId == machine.getIdProcess()) {
			machine.setAcceptedReqCount(machine.getAcceptedReqCount() + 1);
			if (machine.getAcceptedReqCount() == machine.getMachineCount()) {
				machine.printLn("Todas las maquinas me contestaron, ahora uso el recurso");
				machine.setUsingResource(true);
			}
		}
		else if (sol == OK_RESPONSE && machine.wantsResource()) {
			machine.printLn("Me respondio ok la maquina " + sourceId +"\n");
			machine.setAcceptedReqCount(machine.getAcceptedReqCount() + 1);
			if (machine.getAcceptedReqCount() == machine.getMachineCount()) {
				machine.printLn("Todas las maquinas me contestaron, ahora uso el recurso");
				machine.setUsingResource(true);
			}
		}
		else if (sol == GET_RESOURCE) {
			if (machine.getUsingResource()) {
				machine.printLn("Estoy usando el recurso, pongo en la cola al proceso");
				machine.addMachineToQueue(new ParIdPuerto(sourceId,sourcePort));
			}
			else if (machine.wantsResource()) {
				int sourceReqTime = OrdenamientoBytes.buildNumber(REQ_TIME_SIZE, message,REQ_TIME_POS);
				if (sourceReqTime < machine.getReqTime()) {
					machine.printLn("Envio una respuesta de 'OK'");
					sendOk(sourcePort);
				}
				else {
					machine.printLn("Yo tambien quiero el recurso Poniendo en la cola solicitud");
					machine.addMachineToQueue(new ParIdPuerto(sourceId,sourcePort));
				}
			}
			else {
				machine.printLn("Envio una respuesta de 'OK'");
				sendOk(sourcePort);
			}
		}
	}
}
