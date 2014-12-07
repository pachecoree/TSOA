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
	
	public synchronized void addElement(int dest,byte[] message) {
		map.put(dest, message);
	}
	
	public synchronized void deleteElement(int dest) {
		map.remove(dest);
	}
	
	public synchronized byte[] getElement(int dest) {
		return map.get(dest);
	}
	
	public boolean hasElement(int dest) {
		return map.containsKey(dest);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
}
