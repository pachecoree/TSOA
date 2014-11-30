/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 2
 */

package sistemaDistribuido.sistema.clienteServidor.modoUsuario;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.OrdenamientoBytes;

/**
 * 
 */
public class ProcesoCliente extends Proceso{

	private final int BYTES_SIZE_DATA = 2;
	byte[] solClient;
	int opCode;
	String param1,param2,param3;
	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);
		start();
	}
	
	public void setSolClient(byte[] solClient) {
		this.solClient = solClient;
	}
	
	public void setOpCod(int opCode) {
		this.opCode = opCode;
	}
	
	public void setParam1(String param) {
		this.param1 = param;
	}
	
	public void setParam2(String param) {
		this.param2 = param;
	}
	
	public void setParam3(String param) {
		this.param3 = param;
	}
	
	/**
	 * 
	 */

	
	private void doApplication() {
		switch(opCode) {
		case CREATE:
			generateCreateApplication();
			break;
		case DELETE:
			generateDeleteApplication();
			break;
		case READ:
			generateReadApplication();
			break;
		case WRITE:
			generateWriteApplication();
			break;
		}
	}
	
	public void generateDeleteApplication() {
		String fileName = param1;
		int fileNameSize= fileName.length();
		int applicationSize = BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+fileNameSize+BYTES_SIZE_DATA;
		int bufferReadPos;
		byte[] solClient = new byte[applicationSize];
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		bufferReadPos = BYTES_SOURCE+BYTES_DEST;
		OrdenamientoBytes.breakNumber(DELETE,solClient,BYTES_OPCOD,bufferReadPos);
		bufferReadPos += BYTES_OPCOD;
		OrdenamientoBytes.breakNumber(fileNameSize,solClient,BYTES_SIZE_DATA,bufferReadPos);
		bufferReadPos += BYTES_SIZE_DATA;
		byte[] fileNameArr = fileName.getBytes();
		for (int i=0; i<fileNameSize; i++) {
			solClient[bufferReadPos++] = fileNameArr[i];
		}
		setSolClient(solClient);
	}
	
	public void generateReadApplication() {
		String fileName = param1;
		int readBytesSize = BYTES_INT;
		int readPosSize = BYTES_INT;
		int bytes;
		int pos;
		int bufferReadPos;
		try {
			bytes = Integer.parseInt(param2);
			pos = Integer.parseInt(param3);
		}
		catch(NumberFormatException e) {
			bytes = 0;
			pos = 0;
			System.out.println("Error en el parametro bytes o pos : " + e.getMessage());
		}
		int fileNameSize = fileName.length();
		int applicationSize = BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+fileNameSize+BYTES_SIZE_DATA
				           +readBytesSize+readPosSize;
		byte[] solClient = new byte[applicationSize];
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		bufferReadPos = BYTES_DEST+BYTES_SOURCE;
		OrdenamientoBytes.breakNumber(READ,solClient,BYTES_OPCOD,bufferReadPos);
		bufferReadPos += BYTES_OPCOD;
		OrdenamientoBytes.breakNumber(bytes,solClient,readBytesSize,bufferReadPos);
		bufferReadPos += readBytesSize;
		OrdenamientoBytes.breakNumber(pos,solClient,readPosSize,bufferReadPos);
		bufferReadPos += readPosSize;
		OrdenamientoBytes.breakNumber(fileNameSize,solClient,BYTES_SIZE_DATA,bufferReadPos);
		bufferReadPos += BYTES_SIZE_DATA;
		byte[] fileNameArr = fileName.getBytes();
		for (int i=0; i<fileNameSize; i++) {
			solClient[bufferReadPos++] = fileNameArr[i];
		}
		setSolClient(solClient);
	}
	
	public void generateCreateApplication() {
		String fileName = param1;
		int fileNameSize = fileName.length();
		int applicationSize = BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+fileNameSize+BYTES_SIZE_DATA;
		int bufferReadPos;
		byte[] solClient = new byte[applicationSize];
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		bufferReadPos = BYTES_SOURCE+BYTES_DEST;
		OrdenamientoBytes.breakNumber(CREATE,solClient,BYTES_OPCOD,bufferReadPos);
		bufferReadPos += BYTES_OPCOD;
		OrdenamientoBytes.breakNumber(fileNameSize,solClient,BYTES_SIZE_DATA,bufferReadPos);
		bufferReadPos += BYTES_SIZE_DATA;
		byte[] fileNameArr=fileName.getBytes();
		for (int i=0; i<fileNameSize; i++) {
			solClient[bufferReadPos++] = fileNameArr[i];
		}
		setSolClient(solClient);
	}
	
	public void generateWriteApplication() {
		String fileName = param1;
		String text = param2;
		int fileNameSize = fileName.length();
		int textSize = text.length();
		int applicationSize = BYTES_DEST+BYTES_SOURCE+BYTES_OPCOD+fileNameSize+BYTES_SIZE_DATA
				                                           +textSize+BYTES_SIZE_DATA;
		int bufferReadPos;
		byte[] solClient = new byte[applicationSize];
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		bufferReadPos = BYTES_SOURCE+BYTES_DEST;
		OrdenamientoBytes.breakNumber(WRITE,solClient,BYTES_OPCOD,bufferReadPos);
		bufferReadPos += BYTES_OPCOD;
		OrdenamientoBytes.breakNumber(fileNameSize,solClient,BYTES_SIZE_DATA,bufferReadPos);
		bufferReadPos += BYTES_SIZE_DATA;
		byte[] fileNameArr = fileName.getBytes();
		for (int i=0; i<fileNameSize;i++) {
			solClient[bufferReadPos++] = fileNameArr[i];
		}
		OrdenamientoBytes.breakNumber(textSize,solClient,BYTES_SIZE_DATA,bufferReadPos);
		byte[] textArr = text.getBytes();
		bufferReadPos += BYTES_SIZE_DATA;
		for (int i=0; i<textSize; i++) {
			solClient[bufferReadPos++] = textArr[i];
		}
		setSolClient(solClient);
	}

	private void processCreateResponse(byte[] respClient) {
		if (respClient[BYTES_SOURCE+BYTES_DEST] == C_0) {
			imprimeln("Archivo no pudo ser creado");
		}
		else {
			imprimeln("Archivo creado exitosamente");
		}
	}
	
	private void processReadResponse(byte[] respClient) {
		int numBytesRead = OrdenamientoBytes.buildNumber(4,respClient,POS_DEST+BYTES_DEST);
		if (numBytesRead == C_0) {
			imprimeln("Archivo no pude ser leído");
		}
		else {
			imprimeln("Se leyeron "+numBytesRead+" bytes : " 
		               + new String(respClient,BYTES_SOURCE+BYTES_DEST+BYTES_INT,numBytesRead));
		}
	}
	
	private void processWriteResponse(byte[] respClient) {
		int response_pos = BYTES_SOURCE+BYTES_DEST;
		if (respClient[response_pos] == C_0) {
			imprimeln("No se pudo escribir en el archivo");
		}
		else {
			imprimeln("Archivo modificado exitosamente");
		}
	}
	
	private void processDeleteResponse(byte[] respClient) {
		int response_pos = BYTES_SOURCE+BYTES_DEST;
		if (respClient[response_pos] == C_0) {
			imprimeln("Archivo no pudo ser eliminado");
		}
		else {
			imprimeln("Archivo eliminado exitosamente");
		}
	}
	
	private void processResponse(byte[] respCliente) {
		imprimeln("Procesando respuesta recibida del servidor");
		switch(opCode) {
			case CREATE:
				processCreateResponse(respCliente);
				break;
			case DELETE:
				processDeleteResponse(respCliente);
				break;
			case READ:
				processReadResponse(respCliente);
				break;
			case WRITE:
				processWriteResponse(respCliente);
				break;
		}
	}
	
	public void run(){
		imprimeln("Inicio de proceso");
		Nucleo.suspenderProceso();
		byte[] respClient=new byte[BUFFER_SIZE];
		doApplication();
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(FILE_SERVER,solClient);
		imprimeln("Invocando a receive()");
		Nucleo.receive(dameID(),respClient);
		processResponse(respClient);
	}

}
