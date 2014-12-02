package sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento;
/**
 * Romero Pacheco Carlos Mauricio
 * Cacho Robledo Vega Renato
 * Proyecto
 * 
 * 
 */

public class ServidorDireccionamiento {
	String ip;
	int processId;
	
	public ServidorDireccionamiento(String ip, int processId) {
		this.ip = ip;
		this.processId = processId;
	}
	
	public String getIp() {
		return ip;
	}
	public int getProcessId() {
		return processId;
	}
}
