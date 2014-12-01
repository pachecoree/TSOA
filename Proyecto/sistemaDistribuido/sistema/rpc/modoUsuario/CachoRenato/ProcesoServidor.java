package sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato;
/*
 * Cacho Robledo Vega Renato
 * Práctica 3 -Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor” -09-NOV-14
 * Práctica 4 -Llamadas a Procedimientos Remotos (RPC) “El Programa Conector” 16-nov-14
 * Práctica 5 -Almacenamiento de mensajes en buzones 28-11-14
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;   //para práctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.sistema.empaquetador.Empaquetador;
import static sistemaDistribuido.util.Constantes.*;


public class ProcesoServidor extends Proceso{
	private LibreriaServidor ls;   //para práctica 3
	ParIpId asa;
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

	private void evaluaCodop(byte[] solicitud,short codop,byte[] respuesta){

		int resultado = 0;
		short cantidadParametros = 0;
		int offset;
		int[] parametrosInt;
		double resultadoDouble;
		int[] offsetArreglo = new int[1];


		switch(codop){
		case CODOP_SUMA:
			imprimeln("Desordenando los parametros");
			cantidadParametros = Empaquetador.desempaquetarShort(solicitud,OFFSET_BYTES_TAMANIO);

			parametrosInt = new int[cantidadParametros];
			offset = POSICION_INICIO_DATOS;
			for(int i = 0; i<(int)cantidadParametros; i++){
				parametrosInt[i] = Empaquetador.desempaquetarInt(solicitud,offset += BYTES_INT);

			}
			imprimeln("Estableciendo los parametros en la pila");
			resultado = ls.suma(parametrosInt);

			Empaquetador.empaquetarInt(respuesta,resultado,POSICION_INICIO_RESPUESTA);
			break;

		case CODOP_POTENCIA:
			imprimeln("Desordenando los parametros");
			cantidadParametros = Empaquetador.desempaquetarShort(solicitud,OFFSET_BYTES_TAMANIO);

			parametrosInt = new int[cantidadParametros];
			offset = POSICION_INICIO_DATOS;
			for(int i = 0; i<(int)cantidadParametros; i++,offset += BYTES_INT){
				parametrosInt[i] = Empaquetador.desempaquetarInt(solicitud,offset);

			}
			imprimeln("Estableciendo los parametros en la pila");
			resultado = ls.potencia(parametrosInt);

			Empaquetador.empaquetarInt(respuesta,resultado,POSICION_INICIO_RESPUESTA);
			break;	

		case CODOP_RAIZ:
			imprimeln("Desordenando los parametros");
			cantidadParametros = Empaquetador.desempaquetarShort(solicitud,OFFSET_BYTES_TAMANIO);

			parametrosInt = new int[cantidadParametros];
			offset = POSICION_INICIO_DATOS;
			parametrosInt[0] = Empaquetador.desempaquetarInt(solicitud,offset);

			imprimeln("Estableciendo los parametros en la pila");
			resultadoDouble = ls.raiz(parametrosInt[0]);

			offsetArreglo[0] = POSICION_INICIO_RESPUESTA;
			Empaquetador.empaquetarDouble(respuesta,resultadoDouble,offsetArreglo);
			break;


		case CODOP_ORDENA:
			double[] parametrosAOrdenar;
			double[] parametrosOrdenados;
			imprimeln("Desordenando los parametros");
			cantidadParametros = Empaquetador.desempaquetarShort(solicitud,OFFSET_BYTES_TAMANIO);

			parametrosAOrdenar = new double[(int)cantidadParametros];
			offsetArreglo[0] = POSICION_INICIO_DATOS;
			for(int i = 0; i<(int)cantidadParametros; i++){
				parametrosAOrdenar[i] = Empaquetador.desempaquetarDouble(solicitud,offsetArreglo);

			}
			imprimeln("Estableciendo los parametros en la pila");
			parametrosOrdenados = ls.ordenar(parametrosAOrdenar);

			offsetArreglo[0] = POSICION_INICIO_RESPUESTA;

			Empaquetador.empaquetarShort(respuesta, cantidadParametros, offsetArreglo[0]);
			offsetArreglo[0] += 2;

			for(int i = 0; i<(int)cantidadParametros; i++){
				Empaquetador.empaquetarDouble(respuesta,parametrosOrdenados[i],offsetArreglo);
			}
			break;



		default:
			System.out.println("Error...");
			break;


		}
	}



	public void run(){

		imprimeln("Proceso servidor en ejecucion.");

		byte[] solicitud = new byte[TAMANIO_SOLICITUD];
		byte[] respuesta = new byte[TAMANIO_SOLICITUD];

		int idUnica = 0;

		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = "";
			e.printStackTrace();
		} 

		if(!ip.isEmpty()){
			imprimeln("Generando asa...");
			asa = new ParIpId(super.dameID(),ip);
			imprimeln("Exportando interfáz...");
			idUnica = RPC.exportarInterfaz("FileServer", "3.1", asa);  //para práctica 4

			while(continuar()){
				Nucleo.receive(dameID(),solicitud);

				int origen = Empaquetador.desempaquetarInt(solicitud,POS_SOURCE);

				evaluaCodop(solicitud,Empaquetador.desempaquetarShort(solicitud,POS_OPCODE),respuesta);

				Empaquetador.empaquetarInt(respuesta,super.dameID(),POS_SOURCE);
				Empaquetador.empaquetarInt(respuesta,origen,POS_DEST);
				imprimeln("Llamando al servidor");
				Nucleo.send(origen,respuesta);
			}
			imprimeln("Deregistrando interfaz...");
			if(RPC.deregistrarInterfaz("FileServer", "3.1", idUnica)){  //para práctica 4
				imprimeln("interfáz deregistrada.");
			}else{
				imprimeln("la interfáz no se pudo deregistrar...");
			}

		}

	}


}
