package sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato;
/*
 *Cacho Robledo Vega Renato 
 * Práctica 3 -Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor” -09-NOV-14
 * Práctica 4 - Llamadas a Procedimientos Remotos (RPC) “El Programa Conector” 16-nov-14
 */
import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;  //para práctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato.Libreria;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.sistema.empaquetador.Empaquetador;
import static sistemaDistribuido.util.Constantes.*;


public class LibreriaCliente extends Libreria{
	byte[] solicitud;
	byte[] respuesta;


	public LibreriaCliente(Escribano esc){
		super(esc);

	}


	private void prepararBuffer(){
		solicitud = new byte[BUFFER_SIZE];
		respuesta = new byte[BUFFER_SIZE];

	}


	private void empaquetarDatos(byte[] solicitud, String operacion){

		int parametroInt;
		double[] parametroDouble;
		int posicionByte = POSICION_INICIO_DATOS;

		int cantidadParametros = super.pila.size();

		switch(operacion){

		case "suma":

			while(cantidadParametros > 0){

				parametroInt = (int)super.pila.pop();
				Empaquetador.empaquetarInt(solicitud, parametroInt, posicionByte += BYTES_INT);	

				cantidadParametros--;
			}

			break;

		case "ordenar":

			int[] offsetArreglo = {posicionByte};
			parametroDouble = (double[])super.pila.pop();

			for(int i = 0,j=parametroDouble.length;i < j; i++){
				Empaquetador.empaquetarDouble(solicitud, parametroDouble[i], offsetArreglo);
			}

			break;

		case "potencia":

			int exponente = (int)super.pila.pop();
			int base = (int)super.pila.pop();

			Empaquetador.empaquetarInt(solicitud, base, posicionByte);
			posicionByte += BYTES_INT;
			Empaquetador.empaquetarInt(solicitud, exponente, posicionByte);


			break;

		case "raiz":

			Empaquetador.empaquetarInt(solicitud, (int)super.pila.pop(), posicionByte);

			break;

		}

	}

	private void procesarRespuesta(byte[] respuesta, String operacion){
		
		super.imprimeln("Prcesando respuesta...");
		
		if(respuesta[OFFSET_RESPUESTA_SERVIDOR] < 0){
			
			super.pila.push(null);
			
			switch(respuesta[OFFSET_RESPUESTA_SERVIDOR]){
			case -1:
				super.imprimeln("AU. Dirección desconocida");
				break;
			default:
				super.imprimeln("Respuesta no disponible");
				break;
			}
		}else{
			int resultado = 0;
			int[] offsetArreglo = new int[1];
			switch(operacion)
			{
			case "suma":
				resultado = 0;
				resultado = Empaquetador.desempaquetarInt(respuesta, POSICION_INICIO_RESPUESTA);
				super.pila.push(new Integer(resultado));
				break;

			case "ordenar":

				short parametrosRespuesta = Empaquetador.desempaquetarShort(respuesta, POSICION_INICIO_RESPUESTA);
				double[] parametrosOrdenados = new double[parametrosRespuesta];
				offsetArreglo[0] = POSICION_INICIO_RESPUESTA+2; 

				for(int i = 0;i<parametrosRespuesta; i++){
					parametrosOrdenados[i] = Empaquetador.desempaquetarDouble(respuesta, offsetArreglo);
				}

				super.pila.push(parametrosOrdenados);
				break;
			case "potencia":
				resultado = 0;
				resultado = Empaquetador.desempaquetarInt(respuesta, POSICION_INICIO_RESPUESTA);
				super.pila.push(new Integer(resultado));
				break;

			case "raiz":
				double resultadoDouble = 0.0;
				offsetArreglo[0] = POSICION_INICIO_RESPUESTA;
				resultadoDouble = Empaquetador.desempaquetarDouble(respuesta, offsetArreglo);
				super.pila.push(new Double(resultadoDouble));
				break;

			}


		}

	}



	private void llenarCamposEncabezado(short operacion){

		super.imprimeln("Llenando los campos de encabezado");

		Empaquetador.empaquetarInt(solicitud, Nucleo.dameIdProceso(), POS_SOURCE);
		Empaquetador.empaquetarShort(solicitud, operacion, POS_OPCODE);

	}

	protected void ordenar(){

		super.imprimeln("Importando interfáz..");
		int idServidor = RPC.importarInterfaz("FileServer", "3.1");

		if (idServidor != INTERFAZ_NO_ENCONTRADA){

			super.imprimeln("Preparando el buffer de mensajes.");
			prepararBuffer();

			super.imprimeln("Ordenando parametros...");

			Empaquetador.empaquetarShort(solicitud, (short)((double[])pila.peek()).length, OFFSET_BYTES_TAMANIO);
			empaquetarDatos(solicitud,"ordenar");

			llenarCamposEncabezado(CODOP_ORDENA);

			super.imprimeln("Realizando señalamiento al nucleo");
			Nucleo.send(idServidor, solicitud);

			Nucleo.receive(Nucleo.dameIdProceso(), respuesta);
			procesarRespuesta(respuesta,"ordenar");
		}else{
			
			prepararBuffer();
			respuesta[OFFSET_RESPUESTA_SERVIDOR] = (byte)INTERFAZ_NO_ENCONTRADA;
			procesarRespuesta(respuesta,"");
			
			super.imprimeln("Interfáz no disponible, el servidor no fue encontrado...");
		}


	}



	protected void suma(){

		super.imprimeln("Importando interfáz..");
		int idServidor = RPC.importarInterfaz("FileServer", "3.1");

		if (idServidor != INTERFAZ_NO_ENCONTRADA){

			super.imprimeln("Preparando el buffer de mensajes.");
			prepararBuffer();

			super.imprimeln("Ordenando parametros...");

			Empaquetador.empaquetarShort(solicitud, (short)(super.pila.size()), OFFSET_BYTES_TAMANIO);
			empaquetarDatos(solicitud,"suma");

			llenarCamposEncabezado(CODOP_SUMA);

			super.imprimeln("Realizando señalamiento al nucleo");
			Nucleo.send(idServidor, solicitud);

			Nucleo.receive(Nucleo.dameIdProceso(), respuesta);
			procesarRespuesta(respuesta,"suma");
		}else{
			super.imprimeln("Interfáz no disponible, el servidor no fue encontrado...");
		}
	}


	@Override
	protected void potencia() {
		super.imprimeln("Importando interfáz..");
		int idServidor = RPC.importarInterfaz("FileServer", "3.1");

		if (idServidor != INTERFAZ_NO_ENCONTRADA){
			super.imprimeln("Preparando el buffer de mensajes.");
			prepararBuffer();

			super.imprimeln("Ordenando parametros...");

			Empaquetador.empaquetarShort(solicitud, (short)(super.pila.size()), OFFSET_BYTES_TAMANIO);
			empaquetarDatos(solicitud,"potencia");


			llenarCamposEncabezado(CODOP_POTENCIA);

			super.imprimeln("Realizando señalamiento al nucleo");
			Nucleo.send(idServidor, solicitud);

			Nucleo.receive(Nucleo.dameIdProceso(), respuesta);
			procesarRespuesta(respuesta,"potencia");
		}else{
			super.imprimeln("Interfáz no disponible, el servidor no fue encontrado...");
		}
	}


	@Override
	protected void raiz() {

		super.imprimeln("Importando interfáz..");
		int idServidor = RPC.importarInterfaz("FileServer", "3.1");

		if (idServidor != INTERFAZ_NO_ENCONTRADA){

			super.imprimeln("Preparando el buffer de mensajes.");
			prepararBuffer();

			super.imprimeln("Ordenando parametros...");

			Empaquetador.empaquetarShort(solicitud, (short)(super.pila.size()), OFFSET_BYTES_TAMANIO);
			empaquetarDatos(solicitud,"raiz");

			llenarCamposEncabezado(CODOP_RAIZ);

			super.imprimeln("Realizando señalamiento al nucleo");
			Nucleo.send(idServidor, solicitud);

			Nucleo.receive(Nucleo.dameIdProceso(), respuesta);
			procesarRespuesta(respuesta,"raiz");

		}else{
			super.imprimeln("Interfáz no disponible, el servidor no fue encontrado...");
		}
	}



	/**
	 * Ejemplo de resguardo del cliente suma
	 */
	/*protected void suma(){
		int asaDest=0;
		//...

		//asaDest=RPC.importarInterfaz(nombreServidor, version)  //para práctica 4
		Nucleo.send(asaDest,null);
		//...
	}*/



}