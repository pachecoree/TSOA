package sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento;
/**
 * Romero Pacheco Carlos Mauricio
 * Cacho Robledo Vega Renato
 * Proyecto
 */


import java.util.HashMap;

public class TablaSolicitudes {

	HashMap<Integer,Solicitudes> map;
	
	public TablaSolicitudes() {
		map = new HashMap<Integer,Solicitudes>();
	}
	
	public void addElement(int addr,Solicitudes sol) {
		map.put(addr, sol);
	}
	
	public void deleteElement(int addr) {
		if (hasElement(addr)) {
			map.remove(addr);
		}
	}
	
	public Solicitudes getElement(int addr) {
		return map.get(addr);
	}
	
	public boolean hasElement(int addr) {
		return map.containsKey(addr);
	}
}
