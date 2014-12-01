/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 2
 */

package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.util.HashMap;

public class Emision {

	HashMap<Integer,ParIpId> map;
	
	public Emision() {
		map = new HashMap<Integer,ParIpId>();
	}
	
	public void addElement(int dest,ParIpId data) {
		map.put(dest, data);
	}
	
	public void deleteElement(int dest) {
		if (hasElement(dest)) {
			map.remove(dest);
		}
	}
	
	public ParIpId getElement(int dest) {
		return map.get(dest);
	}
	
	public boolean hasElement(int dest) {
		return map.containsKey(dest);
	}
}
