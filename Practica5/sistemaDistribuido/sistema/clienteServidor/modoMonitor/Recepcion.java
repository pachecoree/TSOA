/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 2
 */

package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.util.HashMap;

public class Recepcion {

	HashMap<Integer,byte[]> map;
	
	public Recepcion() {
		map = new HashMap<Integer,byte[]>();
	}
	
	public void addElement(int addr,byte[] paquete) {
		map.put(addr, paquete);
	}
	
	public void deleteElement(int addr) {
		if (hasElement(addr)) {
			map.remove(addr);
		}
	}
	
	public byte[] getElement(int addr) {
		return map.get(addr);
	}
	
	public boolean hasElement(int addr) {
		return map.containsKey(addr);
	}
}
