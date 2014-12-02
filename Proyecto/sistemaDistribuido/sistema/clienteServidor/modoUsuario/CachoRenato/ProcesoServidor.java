/**
 * Cacho Robledo Vega Renato, 
 * 
 * Pr�ctica 1: 15-OCT-2014
 * Pr�ctica 2: 22-OCT-2014
 */

package sistemaDistribuido.sistema.clienteServidor.modoUsuario.CachoRenato;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.Pausador;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.sistema.empaquetador.Empaquetador;

public class ProcesoServidor extends Proceso{


	public ProcesoServidor(Escribano esc){
		super(esc);
		start();
	}

	/**
	 * 
	 */

	private short extraeCodop(byte[] solicitud){
	
		return Empaquetador.desempaquetarShort(solicitud, POS_OPCODE);

	}


	private short extraeTam(byte[] solicitud){

		return Empaquetador.desempaquetarShort(solicitud, OFFSET_BYTES_TAMANIO);
	}

	private int extraeOrigen(byte[] solicitud) {
		
		return Empaquetador.desempaquetarInt(solicitud, POS_SOURCE);
	

	}
	
	private void insertaOrigen(byte[] solicitud) {
	
		Empaquetador.empaquetarInt(solicitud, super.dameID(), POS_SOURCE);
		
	}
	
	private void insertaDestino(byte[] solicitud,int destino) {
		
		Empaquetador.empaquetarInt(solicitud, destino, POS_DEST);
		
	}
	
	private void insertaTam(byte[] solicitud, short tam){
		
		Empaquetador.empaquetarShort(solicitud, tam, BITS_BYTE);

	}

	private String procesaRespuesta(Short codop, String msj, byte[] msjByteArray){
		
		switch(codop){
		case CREATE:
			imprimeln("solicitud CREAR recibida. mensaje: " + msj);
			return "petici�n CREAR recibida correctamente";

		case DELETE:
			imprimeln("solicitud ELIMINAR recibida. mensaje: " + msj);
			return "petici�n ELIMINAR recibida correctamente";


		case READ:
			imprimeln("Solicitud LEER recibida. mensaje: " + msj);
			return "petici�n LEER recibida correctamente";


		case WRITE:
			imprimeln("El cliente desea ESCRIBIR. Envio el mensaje: " + msj);
			return "petici�n ESCRIBIR recibida correctamente";


		default:
			imprimeln("Error, petici�n no v�lida... mensaje: " + msj);
			return "Error, petici�n No v�lida";

		}
		

	}
	
	
	public void run(){
		imprimeln("Inicio de proceso Servidor...");
		imprimeln("Proceso servidor en ejecuci�n.");

		byte[] solicitud = new byte[TAMANIO_SOLICITUD];
		byte[] msjByteArray = null;
		short codop;
		short tam;
		int origen = 0;
		String msj;
		String msjRespuesta = "";
		
		imprimeln("Registrando buz�n");
		Nucleo.nucleo.registrarBuzon(dameID());
		
		Nucleo.nucleo.registrarServidor(CACHO_FILE_SERVER, dameID());
		
		while(continuar()){
			imprimeln("Invocando a receive()");
			Nucleo.receive(dameID(),solicitud);

			codop = extraeCodop(solicitud);
			tam = extraeTam(solicitud);
			origen = extraeOrigen(solicitud);
			
			if(tam>0){
				msjByteArray = new byte[tam];
				System.arraycopy(solicitud, OFFSET_MENSAJE_CLIENTE, msjByteArray, 0, tam);
				msj = new String(msjByteArray,0,msjByteArray.length);

			}else{
				msj = "";
			}

			imprimeln("Procesando peticion recibida del cliente");
			msjRespuesta = procesaRespuesta(codop, msj, msjByteArray);

			Pausador.pausa(1000);  //sin esta l�nea es posible que Servidor solicite send antes que Cliente solicite receive
			imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
				
			insertaOrigen(solicitud);
			insertaDestino(solicitud,origen);
			insertaTam(solicitud, (short)msjRespuesta.getBytes().length);
			System.arraycopy(msjRespuesta.getBytes(), 0, solicitud, 10, msjRespuesta.getBytes().length);
			
			imprimeln("Se�alamiento al nucleo para env�o de mensaje");
			imprimeln("Enviando respuesta...");
			Nucleo.send(origen,solicitud);

		}
		System.out.println("Deregistrando buz�n de proceso: "+dameID());
		Nucleo.nucleo.deregistrarBuzon(dameID());
		
		Nucleo.nucleo.deregistrarServidor(CACHO_FILE_SERVER, dameID());
		
	}
}
