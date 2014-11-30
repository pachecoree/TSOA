/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */

package sistemaDistribuido.sistema.rpc.modoUsuario;

//import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;  //para práctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;
import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.OrdenamientoBytes;
import sistemaDistribuido.util.Escribano;

public class LibreriaCliente extends Libreria{

	private final String NAME = "Servidor de Operaciones";
	private final String VERSION = "2.0";
	/**
	 * 
	 */
	public LibreriaCliente(Escribano esc){
		super(esc);
	}
	
	protected void bubbleSort() {
		int arraySize = stack.pop();
		int dest;
		byte[] respClient = new byte[BUFFER_SIZE];
		int messageSize = BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+BYTES_INT+(arraySize*BYTES_INT);
		int arrayPos = BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+BYTES_INT;
		imprimeln("Preparando el buffer de mensajes");
		byte[] solClient = new byte[messageSize];
		int[] array= new int[arraySize];
		imprimeln("Ordenando parámetros dentro del buffer");
		for (int i=0; i<arraySize; i++) {
			array[i] = stack.pop();
			OrdenamientoBytes.breakNumber(array[i],solClient,BYTES_INT,arrayPos+(BYTES_INT*i));
		}
		OrdenamientoBytes.breakNumber(arraySize,solClient,BYTES_INT,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Llenando los campos de encabezado del mensaje");
		OrdenamientoBytes.breakNumber(BUBBLE_SORT,solClient,BYTES_OPCOD,POS_OPCODE);
		imprimeln("Señalamiento al núcleo");
		dest = RPC.importarInterfaz(NAME, VERSION);
		if (dest == ASA_NOT_FOUND) {
			imprimeln("No se pudo localizar el servidor ");
			stack.push(ASA_NOT_FOUND);
			stack.push(C_1);
		}
		else {
			Nucleo.send(dest,solClient);
			Nucleo.receive(Nucleo.dameIdProceso(), respClient);
			arrayPos = BYTES_DEST+BYTES_SOURCE;
			for (int i=0; i<arraySize; i++) {
				array[i] = OrdenamientoBytes.buildNumber(BYTES_INT,respClient,arrayPos+(BYTES_INT*i));
			}
			for (int i=arraySize-1; i>= C_0; i--) {
				stack.push(array[i]);
			}
			stack.push(arraySize);
		}
	}
	
	protected void fibonacci() {
		int number = stack.pop();
		int dest;
		byte[] respClient = new byte[BUFFER_SIZE];
		int messageSize=BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+BYTES_INT;
		imprimeln("Preparando el buffer de mensajes");
		byte[] solClient = new byte[messageSize];
		imprimeln("Ordenando parámetros dentro del buffer");
		OrdenamientoBytes.breakNumber(number,solClient,BYTES_INT,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Llenando los campos de encabezado del mensaje");
		OrdenamientoBytes.breakNumber(FIBONACCI, solClient,BYTES_OPCOD,POS_OPCODE);
		imprimeln("Señalamiento al núcleo");
		dest = RPC.importarInterfaz(NAME, VERSION);
		if (dest == ASA_NOT_FOUND) {
			imprimeln("No se pudo localizar el servidor ");
			stack.push(ASA_NOT_FOUND);
		}
		else {
			Nucleo.send(dest,solClient);
			Nucleo.receive(Nucleo.dameIdProceso(), respClient);
			number = OrdenamientoBytes.buildNumber(BYTES_INT, respClient,POS_DEST+BYTES_DEST);
			stack.push(number);
		}
	}
	
	protected void primeNumber() {
		int number = stack.pop();
		int dest;
		byte[] respClient = new byte[BUFFER_SIZE];
		int messageSize=BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+BYTES_INT;
		imprimeln("Preparando el buffer de mensajes");
		byte[] solClient = new byte[messageSize];
		imprimeln("Ordenando parámetros dentro del buffer");
		OrdenamientoBytes.breakNumber(number,solClient,BYTES_INT,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Llenando los campos de encabezado del mensaje");
		OrdenamientoBytes.breakNumber(PRIME, solClient,BYTES_OPCOD,POS_OPCODE);
		imprimeln("Señalamiento al núcleo");
		dest = RPC.importarInterfaz(NAME, VERSION);
		if (dest == ASA_NOT_FOUND) {
			imprimeln("No se pudo localizar el servidor ");
			stack.push(ASA_NOT_FOUND);
		}
		else {
			Nucleo.send(dest,solClient);
			Nucleo.receive(Nucleo.dameIdProceso(), respClient);
			number = OrdenamientoBytes.buildNumber(BYTES_BOOLEAN, respClient,POS_DEST+BYTES_DEST);
			stack.push(number);
		}
	}
	
	protected void factorial() {
		int number = stack.pop();
		int dest;
		byte[] respClient = new byte[BUFFER_SIZE];
		int messageSize=BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+BYTES_INT;
		imprimeln("Preparando el buffer de mensajes");
		byte[] solClient = new byte[messageSize];
		imprimeln("Ordenando parámetros dentro del buffer");
		OrdenamientoBytes.breakNumber(number,solClient,BYTES_INT,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Llenando los campos de encabezado del mensaje");
		OrdenamientoBytes.breakNumber(FACTORIAL, solClient,BYTES_OPCOD,POS_OPCODE);
		imprimeln("Señalamiento al núcleo");
		dest = RPC.importarInterfaz(NAME, VERSION);
		if (dest == ASA_NOT_FOUND) {
			imprimeln("No se pudo localizar el servidor ");
			stack.push(ASA_NOT_FOUND);
		}
		else {
			Nucleo.send(dest,solClient);
			Nucleo.receive(Nucleo.dameIdProceso(), respClient);
			number = OrdenamientoBytes.buildNumber(BYTES_INT, respClient,POS_DEST+BYTES_DEST);
			stack.push(number);
		}
	}

}