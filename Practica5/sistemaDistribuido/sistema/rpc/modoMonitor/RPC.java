/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */

package sistemaDistribuido.sistema.rpc.modoMonitor;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;   //para práctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.sistema.rpc.modoUsuario.ProgramaConector;

public class RPC{
	private static ProgramaConector conector;

	/**
	 * 
	 */
	public static void asignarConector(ProgramaConector con){
		conector=con;
		conector.inicializar();
	}

	/**
	 * Efectua la llamada de busqueda en el conector.
	 * Regresa un dest para la llamada a send(dest,message).
	 */
	public static int importarInterfaz(String name,String version){
		ParIpId asa = conector.search(name, version);
		if (asa == null) {
			return ASA_NOT_FOUND;
		}
		else {
			Nucleo.registerInterface(asa);
			return asa.dameID();
		}
	}

	/**
	 * Efectua la llamada a registro en el conector.
	 * Regresa una identificacionUnica para el deregistro.
	 */
	public static int exportarInterfaz(String name,String version,ParIpId asa){
		return conector.register(name,version,asa);
	}

	/**
	 * Efectua la llamada a deregistro en el conector.
	 * Regresa el status del deregistro, true significa llevado a cabo.
	 */
	public static boolean deregistrarInterfaz(String name,String version,int serverKey){
		if (conector.unregister(name,version,serverKey)) {
			return true;
		}
		return false;
	}
}