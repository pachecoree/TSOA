package sistemaDistribuido.sistema.administracionDeBuzon;
import java.util.Hashtable;
/*
 * Cacho Robledo Vega Renato
 * Práctica 5 -Almacenamiento de mensajes en buzones
 * 
 */

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.AdministracionRegistro;

public class TablaBuzones extends AdministracionRegistro{
	private Hashtable<Integer,Buzon> tabla;
	
	public TablaBuzones(){
		tabla = new Hashtable<Integer,Buzon>();
	}
	
	public void registrar(int id, Buzon buzon) {
		tabla.put(Integer.valueOf(id), buzon);
	}
	
	public void deregistrar(int id) {
		tabla.remove(id);
	}
	
	@Override
	public void eliminar(int id) {
		tabla.remove(Integer.valueOf(id));
	}

	public Buzon buscar(int id) {
		return tabla.get(Integer.valueOf(id));
	}
	
	
	
}
