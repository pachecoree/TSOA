//Brambila López Jaime Arturo
//Práctica #1: Modelo Cliente / Servidor “Paso de Mensajes entre Procesos”

package sistemaDistribuido.sistema.clienteServidor.modoUsuario.BrambilaJaime;

import java.util.Random;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.Pausador;
import static sistemaDistribuido.util.Constantes.*;


public class ProcesoServidor extends Proceso{
	
	public ProcesoServidor(Escribano esc){
		super(esc);
		start();
	}

	/**
	 * 
	 */
	public void run(){
		
		short codigoOperacion;
		int tamanoMensaje;
		byte[] solicitudServidor = new byte[BYTES_RESPUESTA];
		byte[] mensajeBytes;
		byte[] respuestaBytes;
		String mensaje;
		String respuestaServidor;
		int tamanoRespuesta;
		int inicio;
		int fin;
		
		imprimeln("Proceso servidor en ejecucion.");
		
		while(continuar()){
			Nucleo.receive(dameID(),solicitudServidor);
			
			codigoOperacion = solicitudServidor[BYTES_ID_EMISOR+BYTES_ID_RECEPTOR];
			codigoOperacion = (short)((codigoOperacion << BITS_BYTE) | solicitudServidor[BYTES_ID_EMISOR+BYTES_ID_RECEPTOR+1]);
			
			tamanoMensaje = solicitudServidor.length-(BYTES_ID_EMISOR+BYTES_ID_RECEPTOR+BYTES_CODIGO_OPERACION);
			mensajeBytes = new byte[tamanoMensaje];
			
			System.arraycopy(solicitudServidor,BYTES_ID_EMISOR+BYTES_ID_RECEPTOR+BYTES_CODIGO_OPERACION,mensajeBytes,C_0,tamanoMensaje);
			mensaje = new String(mensajeBytes);
		
			imprimeln("Solicitud Recibida");
			
			Random random = new Random();
			int tipoRespuesta = random.nextInt(POSIBLES_RESPUESTAS);
			String[] parametrosMensaje;
			
			switch(codigoOperacion)
			{
			
				case CREAR:
					imprimeln("Solicutud: Crear el Archivo: " + mensaje);
					respuestaServidor = String.valueOf(exito(tipoRespuesta));
					break;
				case ELIMINAR:
					imprimeln("Solicutud: Eliminar el Archivo: " + mensaje);
					respuestaServidor = String.valueOf(exito(tipoRespuesta));
					break;	
				case LEER:
					String[] textoArchivo = {"Taller de Sistemas Operativos Avanzados",
											 "Taller de Compiladores",
											 "Taller de Bases de Datos",
											 "Taller de Estructura de Datos",
											 "Taller de Estructura de Archivos",
											 "Taller de Programación Estructurada",
											 "Taller de Programación Orientada a Objetos",
											 "Taller de Introducción a la Computación",
											 "Taller de Redes de Computadoras",
											 "Taller de Sistemas operativos"
											};
					parametrosMensaje = mensaje.split(SEPARADOR,PARAMETROS_LEER);
					imprimeln("Solicutud:");
					imprimeln("Leer el Archivo: " + parametrosMensaje[POSICION_PRIMER_PARAMETRO]);
					imprimeln("Desde la posición: " + parametrosMensaje[POSICION_SEGUNDO_PARAMETRO]);
					imprimeln("Hasta la posición: " + parametrosMensaje[POSICION_TERCER_PARAMETRO]);
					inicio = Integer.parseInt(parametrosMensaje[POSICION_SEGUNDO_PARAMETRO].trim());
					fin = Integer.parseInt(parametrosMensaje[POSICION_TERCER_PARAMETRO].trim());
					respuestaServidor = textoArchivo[tipoRespuesta].substring(inicio,inicio+fin);
					break;	
				case ESCRIBIR:
					parametrosMensaje = mensaje.split(SEPARADOR,PARAMETROS_ESCRIBIR);
					imprimeln("Solicutud:");
					imprimeln("Escribir el Archivo: " + parametrosMensaje[POSICION_PRIMER_PARAMETRO]);
					imprimeln("En la posición: " + parametrosMensaje[POSICION_SEGUNDO_PARAMETRO]);
					imprimeln("El texto: " + parametrosMensaje[POSICION_TERCER_PARAMETRO]);
					respuestaServidor = String.valueOf(exito(tipoRespuesta));
					break;
				default:
					String errorCodigo = "Codigo de Operación Desconocido";
					System.out.println(errorCodigo);
					respuestaServidor = errorCodigo;
					break;
			}
			
			respuestaBytes = respuestaServidor.getBytes();
			tamanoRespuesta = respuestaBytes.length;
			
			tamanoMensaje = BYTES_ID_EMISOR+BYTES_ID_RECEPTOR+tamanoRespuesta;
			mensajeBytes = new byte[tamanoMensaje];
			
			System.arraycopy(respuestaBytes,C_0,mensajeBytes,BYTES_ID_EMISOR+BYTES_ID_RECEPTOR,tamanoRespuesta);
			
			Pausador.pausa(PAUSA_MILISEGUNDOS);  //sin esta línea es posible que Servidor solicite send antes que Cliente solicite receive
			Nucleo.send((int) solicitudServidor[POSICION_ID_EMISOR],mensajeBytes);
			imprimeln("\nRespuesta Enviada");
		}
	}
	
	public int exito(int tipoRespuesta){
		return tipoRespuesta%2==0?1:0;
	}
}
