package sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato;
/*
 * Cacho Robledo Vega Renato
 * Práctica 4 -Llamadas a Procedimientos Remotos (RPC) “El Programa Conector”
 */
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParMaquinaProceso;

public class DatosServidor {
	private String nombreServidor; 
	private String version; 
	private ParMaquinaProceso asa; 
	
	public DatosServidor(String nombreServidor,String version,ParMaquinaProceso asa){
		this.nombreServidor = nombreServidor;
		this.version = version;
		this.asa = asa;
		
	}
	
	public String getNombreServidor() {
		return nombreServidor;
	}

	public void setNombreServidor(String nombreServidor) {
		this.nombreServidor = nombreServidor;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ParMaquinaProceso getAsa() {
		return asa;
	}

	public void setAsa(ParMaquinaProceso asa) {
		this.asa = asa;
	}
	
	
	
}
