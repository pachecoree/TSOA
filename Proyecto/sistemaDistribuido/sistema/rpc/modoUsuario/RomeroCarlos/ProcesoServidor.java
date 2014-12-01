/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */
package sistemaDistribuido.sistema.rpc.modoUsuario.RomeroCarlos;

import java.net.InetAddress;
import java.net.UnknownHostException;

import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.util.OrdenamientoBytes;

/**
 * 
 */
public class ProcesoServidor extends Proceso{
	private LibreriaServidor ls;   //para práctica 3
	private final String NAME = "Servidor de Operaciones";
	private final String VERSION = "2.0";

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
		ls=new LibreriaServidor(esc);   //para práctica 3
		start();
	}

	/**
	 * Resguardo del servidor
	 */
	private byte[] processFactorial(byte[] solClient) {
		imprimeln("Desordenando los parámetros");
		int number = OrdenamientoBytes.buildNumber(BYTES_INT,solClient,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Operación : Número Factorial|Parámetro: "+number);
		imprimeln("Llamando al servidor");
		int factorialNumber = ls.factorial(number);
		int respSize = BYTES_SOURCE+BYTES_DEST+BYTES_INT;
		byte[] respServer = new byte[respSize];
		OrdenamientoBytes.breakNumber(factorialNumber, respServer,BYTES_INT,POS_DEST+BYTES_DEST);
		return respServer;
	}
	
	private byte[] processFibonacci(byte[] solClient) {
		imprimeln("Desordenando los parámetros");
		int number = OrdenamientoBytes.buildNumber(BYTES_INT,solClient,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Operación : Número Fibonacci|Parámetro: "+number);
		imprimeln("Llamando al servidor");
		int fibonacciNumber = ls.fibonacci(number);
		int respSize = BYTES_SOURCE+BYTES_DEST+BYTES_INT;
		byte[] respServer = new byte[respSize];
		OrdenamientoBytes.breakNumber(fibonacciNumber,respServer,BYTES_INT,POS_DEST+BYTES_DEST);
		return respServer;
	}
	
	private byte[] processBubbleSort(byte[] solClient) {
		imprimeln("Desordenando los parámetros");
		int arraySize = OrdenamientoBytes.buildNumber(BYTES_INT,solClient, POS_OPCODE+BYTES_OPCOD);
		int arrayPos = POS_OPCODE+BYTES_OPCOD+BYTES_INT;
		StringBuffer strArray = new StringBuffer();
		int[] array = new int[arraySize];
		int respSize = BYTES_SOURCE+BYTES_DEST+(arraySize*BYTES_INT);
		byte[] respServer = new byte[respSize];
		for(int i=0; i<arraySize;i++) {
			array[i] = OrdenamientoBytes.buildNumber(BYTES_INT,solClient,arrayPos+(BYTES_INT*i));
			strArray.append(array[i]+ " | ");
		}
		imprimeln("Operación : Ordenamiento Burbuja : "+strArray.toString()) ;
		imprimeln("Llamando al servidor");
		ls.bubbleSort(arraySize, array);
		arrayPos = BYTES_SOURCE+BYTES_DEST;
		for (int i=0; i<arraySize; i++) {
			OrdenamientoBytes.breakNumber(array[i],respServer,BYTES_INT,arrayPos+(BYTES_INT*i));
		}
		return respServer;
		
	}
	
	private byte[] processBadOpCode() {
		byte[] respServer = new byte[BYTES_SOURCE+BYTES_DEST+C_1];
		OrdenamientoBytes.breakNumber(BAD_OPCODE, respServer, C_1, BYTES_SOURCE+BYTES_DEST);
		imprimeln("Código de operación desconocido");
		return respServer;
	}
	
	
	private byte[] processPrime(byte[] solClient) {
		imprimeln("Desordenando los parámetros");
		int number = OrdenamientoBytes.buildNumber(BYTES_INT,solClient,POS_OPCODE+BYTES_OPCOD);
		imprimeln("Operación : Número Primo|Parámetro: "+number);
		imprimeln("Llamando al servidor");
		boolean isPrime = ls.primeNumber(number);
		int respSize = BYTES_SOURCE+BYTES_DEST+BYTES_BOOLEAN;
		byte[] respServer = new byte[respSize];
		int respValue = isPrime?1:0;
		OrdenamientoBytes.breakNumber(respValue,respServer,BYTES_BOOLEAN,POS_DEST+BYTES_DEST);
		return respServer;
	}
	
	public void run(){
		byte[] solClient = new byte[BUFFER_SIZE];
		ParIpId asa;
		try {
			asa = new ParIpId(dameID(),InetAddress.getLocalHost().getHostAddress());
			imprimeln("Proceso servidor en ejecucion.");
			int serverKey=RPC.exportarInterfaz(NAME, VERSION, asa);
			while(continuar()){
				Nucleo.receive(dameID(),solClient);
				int opCod = OrdenamientoBytes.buildNumber(BYTES_OPCOD, solClient,POS_OPCODE);
				byte[] respServer=null;
				switch(opCod) {
					case FACTORIAL:
						respServer = processFactorial(solClient);
						break;
					case FIBONACCI:
						respServer = processFibonacci(solClient);
						break;
					case BUBBLE_SORT:
						respServer = processBubbleSort(solClient);
						break;
					case PRIME:
						respServer = processPrime(solClient);
						break;
					default:
						respServer = processBadOpCode();
						break;
				}
				int dest = OrdenamientoBytes.buildNumber(BYTES_SOURCE, solClient,POS_SOURCE);
				Nucleo.send(dest, respServer);
			}
			RPC.deregistrarInterfaz(NAME,VERSION, serverKey);
		}catch (UnknownHostException e) {
			System.out.println("No se pudo obtener la direccion IP : " + e.getMessage());
		}
	}
	
}
