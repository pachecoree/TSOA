//Brambila L�pez Jaime Arturo
//Pr�ctica #5: Transmisi�n Rala

package sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento;

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
