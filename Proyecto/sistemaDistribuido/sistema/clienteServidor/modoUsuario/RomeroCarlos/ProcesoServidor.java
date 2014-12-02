/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 2
 */

package sistemaDistribuido.sistema.clienteServidor.modoUsuario.RomeroCarlos;

import java.util.Random;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.OrdenamientoBytes;
import sistemaDistribuido.util.Pausador;

/**
 * 
 */
public class ProcesoServidor extends Proceso{

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
		start();
	}

	/**
	 * 
	 */
	
	private byte[] procesaCrear(byte[] solServer) {
		byte[] respServer =  new byte[BYTES_SOURCE+BYTES_DEST+C_1];
		int fileNameBytes = BYTES_SHORT;
		int bufferReadPos = BYTES_SOURCE+BYTES_DEST+BYTES_OPCOD;
		int fileNameSize = OrdenamientoBytes.buildNumber(fileNameBytes,solServer,bufferReadPos);
		String fileName = new String(solServer,bufferReadPos+fileNameBytes,fileNameSize);
		imprimeln("Operación: Crear - Nombre : "+fileName);
		int response = new Random().nextInt(2);
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		respServer[BYTES_SOURCE+BYTES_DEST] = (byte) response;
		return respServer;
	}
	
	private byte[] procesaEscribir(byte[] solServer) {
		byte[] respServer = new byte[BYTES_SOURCE+BYTES_DEST+C_1];
		int fileNameBytes = BYTES_SHORT;
		int bufferReadPos = BYTES_SOURCE+BYTES_DEST+BYTES_OPCOD;
		int fileNameSize = OrdenamientoBytes.buildNumber(fileNameBytes,solServer,bufferReadPos);
		bufferReadPos += fileNameBytes;
		String fileName = new String(solServer,bufferReadPos,fileNameSize);
		int textPos = bufferReadPos+fileNameSize;
		int textSize = OrdenamientoBytes.buildNumber(fileNameBytes,solServer,textPos);
		String text = new String(solServer,textPos+BYTES_SHORT,textSize);
		imprimeln("Operación: Escribir\nNombre : "+fileName+" Texto: "+text);
		int response = new Random().nextInt()%2;
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		respServer[BYTES_SOURCE+BYTES_DEST] = (byte) response;
		return respServer;
	}
	
	private byte[] procesaLeer(byte[] solServer) {
		byte[] respServer;
		String[] files = {"Texto del archivo que se esta leyendo actualmente.",
				   "Otra cadena que se va a modificar.",
				   "Taller de Sistemas Operativos Avanzados.",
				   "Esta es la primer practica!."};
		int readBytesSize = BYTES_INT;
		int readPosSize = BYTES_INT;
		int fileNameBytes = BYTES_SHORT;
		int position;
		StringBuffer readString;
		String string = files[new Random().nextInt(files.length)];
		int bufferReadPos = POS_OPCODE+BYTES_OPCOD;
		int readBytes=OrdenamientoBytes.buildNumber(readBytesSize,solServer,bufferReadPos);
		bufferReadPos += readBytesSize;
		int Readpos = OrdenamientoBytes.buildNumber(readPosSize,solServer,bufferReadPos);
		bufferReadPos += readPosSize;
		int tamNombre = OrdenamientoBytes.buildNumber(fileNameBytes,solServer,bufferReadPos);
		bufferReadPos += fileNameBytes;
		readString = new StringBuffer();
		String fileName = new String(solServer,bufferReadPos,tamNombre);
		imprimeln("Operación:Leer");
		imprimeln("|Nombre: "+fileName+" Bytes a leer:"+readBytes+" Posición:"+Readpos);
		if ((Readpos + readBytes) > string.length()) {
			readBytes = string.length() - Readpos;
		}
		for(int i=0,j=Readpos; i<readBytes; i++) {
			readString.append(string.charAt(j++));
		}
		respServer = new byte[BYTES_SOURCE+BYTES_DEST+BYTES_INT+readBytes];
		OrdenamientoBytes.breakNumber(readBytes,respServer,readBytesSize,BYTES_SOURCE+BYTES_DEST);
		byte[] arrCadenaLeida = readString.toString().getBytes();
		position = BYTES_SOURCE+BYTES_DEST+BYTES_INT;
		for (int i=0; i<readBytes;i++) {
			respServer[position++] = arrCadenaLeida[i];
		}
		return respServer;
	}
	
	private byte[] procesaEliminar(byte[]solServer) {
		byte[] respServer= new byte[BYTES_SOURCE+BYTES_DEST+C_1];
		int fileNameBytes = BYTES_SHORT;
		int posSize = POS_OPCODE + BYTES_OPCOD;
		int fileNameSize = OrdenamientoBytes.buildNumber(fileNameBytes,solServer,posSize);
		String fileName = new String(solServer,posSize+fileNameBytes,fileNameSize);
		imprimeln("Operación: Eliminar - Nombre : " +fileName);
		int response = new Random().nextInt(2);
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		respServer[BYTES_SOURCE+BYTES_DEST] = (byte) response;
		return respServer;
	}
	
	private byte[] processBadOpCode() {
		byte[] respServer = new byte[BYTES_SOURCE+BYTES_DEST+1];
		OrdenamientoBytes.breakNumber(BAD_OPCODE, respServer, C_1, BYTES_SOURCE+BYTES_DEST);
		imprimeln("Código de operación desconocido");
		return respServer;
	}
	
	private byte[] processApplication(byte[] solServer) {
		byte[] respServer;
		imprimeln("Procesando petición recibida del cliente");
		int opCod = OrdenamientoBytes.buildNumber(BYTES_OPCOD,solServer,POS_OPCODE);
		switch(opCod) {
			case CREATE:
				respServer = procesaCrear(solServer);
				break;
			case DELETE:
				respServer = procesaEliminar(solServer);
				break;
			case READ:
				respServer = procesaLeer(solServer);
				break;
			case WRITE:
				respServer = procesaEscribir(solServer);
				break;
			default:
				respServer = processBadOpCode();
				break;
		}
		return respServer;
	}
	
	public void run(){
		imprimeln("Inicio de proceso");
		byte[] solServer=new byte[BUFFER_SIZE];
		byte[] respServer;
		
		Nucleo.nucleo.registrarServidor(ROMERO_FILE_SERVER, dameID());

		while(continuar()){
			imprimeln("Invocando a receive()");
			Nucleo.receive(dameID(),solServer);
			respServer = processApplication(solServer);
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			int dest = OrdenamientoBytes.buildNumber(BYTES_SOURCE, solServer,POS_SOURCE);
			Pausador.pausa(1000);  //sin esta línea es posible que Servidor solicite send antes que Cliente solicite receive
			Nucleo.send(dest,respServer);
		}
		
		Nucleo.nucleo.deregistrarServidor(ROMERO_FILE_SERVER, dameID());
		
	}
	
}
