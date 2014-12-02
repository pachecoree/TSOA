package sistemaDistribuido.sistema.administracionDeBuzon;
/*
 * Cacho Robledo Vega Renato
 * Práctica 5 -Almacenamiento de mensajes en buzones 28-11-14
 * proyecto
 * 
 */
import java.util.LinkedList;

public class Buzon{
	private static final boolean INSERCION_EXITOSA = true;
	private static final boolean BUZON_LLENO = false;
	private static final short MAX_MENSAJES = 3;
	
	private LinkedList<byte[]> mensajes;
	
	public Buzon(){
		mensajes = new LinkedList<byte[]>();
	}
	
	
	public byte[] obtenerMensaje() {
		return mensajes.poll();
	}

	public boolean ingresarMensaje(byte[] mensaje) {
		
		if((short)mensajes.size() < MAX_MENSAJES){
			
			mensajes.offer(mensaje);
			return INSERCION_EXITOSA;
			}
		else
			return BUZON_LLENO;
		
	}
	
	public boolean estaVacio(){
		
		if(mensajes.isEmpty()){		
			return true;
		}else{
			return false;
		}
			
	}
	
	
	
}
