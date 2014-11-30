/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */

package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;
import sistemaDistribuido.visual.rpc.DespleganteConexiones;
import static sistemaDistribuido.util.Constantes.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class ProgramaConector{
	private DespleganteConexiones desplegante;
	private HashMap<Integer,Servidor> conexiones;   //las llaves que provee DespleganteConexiones

	/**
	 * 
	 */
	public ProgramaConector(DespleganteConexiones desplegante){
		this.desplegante=desplegante;
	}

	/**
	 * Inicializar tablas en programa conector
	 */
	public void inicializar(){
		conexiones=new HashMap<Integer,Servidor>();
	}

	/**
	 * Remueve tuplas visualizadas en la interfaz grï¿½fica registradas en tabla conexiones
	 */
	
	public int register(String name,String version, ParIpId asa) {
		int serverKey=desplegante.agregarServidor(name,version,asa.dameIP(),
				                                  Integer.toString(asa.dameID())); 
		conexiones.put(serverKey,new Servidor(name,version,asa));
		return serverKey;
	}
	
	public boolean unregister(String name,String version,int serverKey) {
		if (conexiones.containsKey(serverKey)) {
			Servidor server = conexiones.get(serverKey);
			if (server.getName().equals(name) && server.getVersion().equals(version)) {
				conexiones.remove(serverKey);
				desplegante.removerServidor(serverKey);
				return true;
			}
		}
		return false;
	}
	
	public ParIpId search(String name,String version) {
		Set<Integer> keySet = conexiones.keySet();
		Iterator<Integer> iterator = keySet.iterator();
		ArrayList<ParIpId> asaArray = new ArrayList<ParIpId>();
		int counter = 0;
		while (iterator.hasNext()) {
			int serverKey = iterator.next();
			Servidor server = conexiones.get(serverKey);
			if (server.getName().equals(name)) {
				if (server.getVersion().equals(version)) {
					asaArray.add(server.getAsa());
					counter++;
				}
			}
		}
		if (counter == C_0) {
			return null;
		}
		else {
			return asaArray.get(C_0);
		}
	}
	
	private void removerConexiones(){
		Set<Integer> s=conexiones.keySet();
		Iterator<Integer> i=s.iterator();
		while(i.hasNext()){
			desplegante.removerServidor(((Integer)i.next()).intValue());
			i.remove();
		}
	}

	/**
	 * Al solicitar que se termine el proceso, por si se implementa como tal
	 */
	public void terminar() {
		removerConexiones();
		desplegante.finalizar();
	}
}
