package sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento;
/**
 *   
 * Romero Pacheco Carlos Mauricio
 * Cacho Robledo Vega Renato
 * Proyecto
 * 
 * Brambila L�pez Jaime Arturo
 * pr�ctica #5: Transmisi�n Rala
 */

public class Solicitudes {

	private byte[] mensaje;
	private int serv;
	public Solicitudes(byte[] m, int s) {
		serv = s;
		mensaje = m;
	}
	
	public byte[] dameMensaje(){
		return mensaje;
	}
	
	public int dameServicio(){
		return serv;
	}

}
