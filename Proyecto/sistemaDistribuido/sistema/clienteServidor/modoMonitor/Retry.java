package sistemaDistribuido.sistema.clienteServidor.modoMonitor;


public class Retry extends Thread{
	
	int dest;
	byte[] message;
	int idProceso;
	
	public Retry (int dest, byte[] message,int idProceso) {
		this.dest = dest;
		this.message = message;
		this.idProceso = idProceso;
	}
	
	public void run() {
		Nucleo.retrySend(dest, message,idProceso);
	}
	
}
