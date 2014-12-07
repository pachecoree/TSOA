package sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento;
/**
 * Romero Pacheco Carlos Mauricio
 * Cacho Robledo Vega Renato
 * Proyecto
 */


import java.util.HashMap;
import java.util.LinkedList;
import static sistemaDistribuido.util.Constantes.*;

public class TablaServidoresLocales {

	HashMap<Integer,LinkedList<ServidorLocal>> map;
	
	public TablaServidoresLocales() {
		map = new HashMap<Integer,LinkedList <ServidorLocal>>();
	}
	
	public void addElement(int serviceId,ServidorLocal serv) {
		LinkedList<ServidorLocal> list;
		if (hasElement(serviceId)) {
			list = map.get(serviceId);
		}
		else {
			list = new LinkedList<ServidorLocal>();
		}
		list.add(serv);
		map.put(serviceId,list);
	}
	
	public void deleteElement(int serviceId,int serverId) {
		if (hasElement(serviceId)) {
			LinkedList<ServidorLocal> list = map.get(serviceId);
			int listSize = list.size();
			for (int i=0;i<listSize;i++) {
				ServidorLocal servidor = list.get(i);
				if (servidor.getId() == serverId) {
					if (listSize == C_1) {
						map.remove(serviceId);
					}
					else {
						list.remove(i);
						map.put(serviceId, list);
					}
					break;
				}
			}
		}
	}

	
	public ServidorLocal getElement(int serviceId) {
		LinkedList<ServidorLocal> list = map.get(serviceId);
		return list.getFirst();
	}
	
	public boolean hasElement(int serviceId) {
		return map.containsKey(serviceId);
	}
}