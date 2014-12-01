package sistemaDistribuido.sistema.administracionDeBuzon;
/*
 * Cacho Robledo Vega Renato
 * Práctica 5 -Almacenamiento de mensajes en buzones 28-11-14
 * 
 * 
 */
public class MensajesaRetransmitir {
	private int destino;
	private byte[] mensaje;
	
	public MensajesaRetransmitir(int destino, byte[] mensaje ){
		this.destino = destino;
		this.mensaje = mensaje;
	}
	
	public int getDestino() {
		return destino;
	}
	
	public byte[] getMensaje() {
		return mensaje;
	}
	

}
