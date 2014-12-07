/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.sistema.exclusion;

public class ParIdPuerto {
	
	private int id;
	private int port;
	
	public ParIdPuerto(int id, int port) {
		this.id = id;
		this.port = port;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPort() {
		return port;
	}
	
}
