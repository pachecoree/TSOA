/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 5
 */

package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.util.HashMap;

public class AlmacenamientoTemporal {

	HashMap<Integer,byte[]> map;
	
	public AlmacenamientoTemporal() {
		map = new HashMap<Integer,byte[]>();
	}
	
	public void addElement(int dest,byte[] message) {
		map.put(dest, message);
	}
	
	public void deleteElement(int dest) {
		if (hasElement(dest)) {
			map.remove(dest);
		}
	}
	
	public byte[] getElement(int dest) {
		return map.get(dest);
	}
	
	public boolean hasElement(int dest) {
		return map.containsKey(dest);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
}
