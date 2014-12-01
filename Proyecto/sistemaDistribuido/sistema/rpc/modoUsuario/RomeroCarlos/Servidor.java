/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */

package sistemaDistribuido.sistema.rpc.modoUsuario.RomeroCarlos;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;

public class Servidor {
	String name;
	String version;
	ParIpId asa;
	
	public Servidor(String name,String version,	ParIpId asa) {
		this.name = name;
		this.version = version;
		this.asa = asa;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public ParIpId getAsa() {
		return asa;
	}
}
