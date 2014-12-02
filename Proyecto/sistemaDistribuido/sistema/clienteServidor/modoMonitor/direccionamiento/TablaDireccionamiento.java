package sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento;
/**
 * Romero Pacheco Carlos Mauricio
 * Cacho Robledo Vega Renato
 * Proyecto
 */
import java.util.HashMap;
import java.util.LinkedList;

public class TablaDireccionamiento {

	HashMap<Integer,LinkedList<ServidorDireccionamiento>> map;
	
	public TablaDireccionamiento() {
		map = new HashMap<Integer,LinkedList<ServidorDireccionamiento>>();
	}
	
	public void addElement(int dest,ServidorDireccionamiento servidor) {
		LinkedList<ServidorDireccionamiento> list;
		if (hasElement(dest)) {
			list = map.get(dest);
		}
		else {
			list = new LinkedList<ServidorDireccionamiento>();
		}
		list.add(servidor);
		map.put(dest, list);
	}
	
	public void deleteElement(int dest,int processId) {
		if (hasElement(dest)) {
			LinkedList<ServidorDireccionamiento> list = map.get(dest);
			int listSize = list.size();
			for (int i=0;i<listSize;i++) {
				ServidorDireccionamiento servidor = list.get(i);
				if (servidor.getProcessId() == processId) {
					list.remove(i);
					break;
				}
			}
		}
	}
	
	public ServidorDireccionamiento getElement(int dest) {
		LinkedList<ServidorDireccionamiento> list = map.get(dest);
		ServidorDireccionamiento servidor = list.getFirst();
		return servidor;
	}
	
	public boolean hasElement(int dest) {
		return map.containsKey(dest);
	}
}
