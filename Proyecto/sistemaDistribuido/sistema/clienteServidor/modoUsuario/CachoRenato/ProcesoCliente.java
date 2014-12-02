/**
 * Cacho Robledo Vega Renato, 
 * 
 * Práctica 1: 15-OCT-2014
 * Práctica 2: 22-OCT-2014
 */

package sistemaDistribuido.sistema.clienteServidor.modoUsuario.CachoRenato;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.Pausador;
import sistemaDistribuido.sistema.empaquetador.Empaquetador;
import static sistemaDistribuido.util.Constantes.*;


public class ProcesoCliente extends Proceso{


	private int codop = 0;
	private String msj = "";
	
	
	
	public ProcesoCliente(Escribano esc){
		super(esc);
		start();
	}

	

	private void insertaOrigen(byte[] solicitud){
		
		Empaquetador.empaquetarInt(solicitud, super.dameID(), POS_SOURCE);
		
	}

	
	private void insertaCodop(byte[] solicitud){
	
		Empaquetador.empaquetarShort(solicitud,(short)codop, POS_OPCODE);
		
	}
	
	private void insertaTam(byte[] solicitud){
		Empaquetador.empaquetarShort(solicitud, (short)msj.length(),OFFSET_BYTES_TAMANIO);
	}
	
	private void insertaMsj(byte[] solicitud){
		
		byte[] msjByteArray;
		
		msjByteArray = msj.getBytes();
		int lengthByteArray = msjByteArray.length;

		for(int i = OFFSET_MENSAJE_CLIENTE,j=0; j < lengthByteArray; i++,j++){
			solicitud[i] = msjByteArray[j];
		}
		

	}
	
	private void envia_solicitud_y_recibe_respuesta(byte[] solicitud, byte[] respuesta, String msjResp){
		
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(CACHO_FILE_SERVER,solicitud);
		
		imprimeln("Invocando metodo receive");
		Nucleo.receive(dameID(),respuesta);
		
		imprimeln("Procesando respuesta del servidor");
		msjResp = procesaRespuesta(respuesta);
		
		imprimeln("La respuesta del servidor fue: " + msjResp);
		
	}
	
	
	public void run(){
		byte[] solicitud = new byte[TAMANIO_SOLICITUD];
		byte[] respuesta = new byte[TAMANIO_SOLICITUD];
		String msjResp= "";
		
		imprimeln("Inicio de proceso Cliente...");
		imprimeln("Proceso cliente en ejecución.");
		imprimeln("Esperando datos para continuar.");
		
		Nucleo.suspenderProceso();
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");

		insertaOrigen(solicitud);
		insertaCodop(solicitud);
		insertaTam(solicitud);
		insertaMsj(solicitud);
		
		envia_solicitud_y_recibe_respuesta(solicitud,respuesta,msjResp);
		
		while(msjResp == "Intenta de nuevo"){
			Pausador.pausa(1000);
			envia_solicitud_y_recibe_respuesta(solicitud,respuesta,msjResp);
			
		}
		
		

	}
	
	public int extraeTam(byte[] respuesta){
		
		return	Empaquetador.desempaquetarShort(respuesta, BITS_BYTE);
	

	}
	
	
	private String procesaRespuesta(byte[] respuesta){

		if(respuesta[OFFSET_RESPUESTA_SERVIDOR] < 0){
			switch(respuesta[OFFSET_RESPUESTA_SERVIDOR]){
				case PRO_AU:
					return " AU Dirección desconocida";
								
				default:
					return "Respuesta no disponible";
			}
		}else{

			return new String(respuesta,10,extraeTam(respuesta));

		}
		
	}
	
	public void recibirParametros(int codop, String mensaje) {
		this.codop = codop;
		if(mensaje.length() <= (TAMANIO_SOLICITUD - OFFSET_MENSAJE_CLIENTE)){
			this.msj = mensaje;
		}else{
			this.msj = mensaje.substring(0, TAMANIO_SOLICITUD);
		}
	}
	
	
	
}
