//Brambila López Jaime Arturo
//Práctica #1: Modelo Cliente / Servidor “Paso de Mensajes entre Procesos”

package sistemaDistribuido.sistema.clienteServidor.modoUsuario.BrambilaJaime;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.sistema.empaquetador.Empaquetador;

public class ProcesoCliente extends Proceso{

	private String mensaje;
	private short codigoOperacion;
	

	public ProcesoCliente(Escribano esc){
		super(esc);
		start();
	}

	public void setParametros(short codigoOperacion, String mensaje){
		this.codigoOperacion = codigoOperacion;
		this.mensaje = mensaje;
	}
	
	public void run(){
		imprimeln("Proceso cliente en ejecucion.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Iniciando Proceso");
		
		byte[] codigoOperacionBytes = new byte[BYTES_CODIGO_OPERACION];
		byte[] mensajeBytes = new byte[BYTES_MENSAJE];
		int tamanoMensaje = mensaje.length();
		byte[] respuestaCliente=new byte[BYTES_RESPUESTA];
		byte[] respuesta;
		
		Empaquetador.empaqueta(codigoOperacionBytes,C_0,codigoOperacion);
			
		System.arraycopy(mensaje.getBytes(),C_0,mensajeBytes,C_0,tamanoMensaje);

		byte[] solicitudCliente=new byte[BYTES_ID_EMISOR+BYTES_ID_RECEPTOR+BYTES_CODIGO_OPERACION+tamanoMensaje];
		
		System.arraycopy(codigoOperacionBytes,C_0,solicitudCliente,BYTES_ID_EMISOR+BYTES_ID_RECEPTOR,BYTES_CODIGO_OPERACION);
		System.arraycopy(mensajeBytes,C_0,solicitudCliente,BYTES_ID_EMISOR+BYTES_ID_RECEPTOR+BYTES_CODIGO_OPERACION,tamanoMensaje);
		imprimeln("Solicitud Enviada");
		
		Nucleo.send(BRAMBILA_FILE_SERVER,solicitudCliente);
		
		Nucleo.receive(dameID(),respuestaCliente);
		
		tamanoMensaje = respuestaCliente.length-(BYTES_ID_EMISOR+BYTES_ID_RECEPTOR);
		respuesta = new byte[tamanoMensaje];
		
		System.arraycopy(respuestaCliente,BYTES_ID_EMISOR+BYTES_ID_RECEPTOR,respuesta,C_0,tamanoMensaje);
		imprimeln("Respuesta del servidor: "+ new String(respuesta));
	}
}
