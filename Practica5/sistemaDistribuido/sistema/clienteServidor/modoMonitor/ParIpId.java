/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */

package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

public class ParIpId implements ParMaquinaProceso{

	int id;
	String ip;
	
	public ParIpId (int id,String ip) {
		this.id= id;
		this.ip= ip;
	}
	
	@Override
	public String dameIP() {
		return ip;
	}

	@Override
	public int dameID() {
		return id;
	}
}
