package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import sistemaDistribuido.sistema.administracionDeBuzon.Buzon;

/**
 * Cacho Robledo Vega Renato, 
 * 
 * Práctica 2: 22-OCT-2014
 */
public interface AdminTabla {
	public void registrar(int id, ParIpId pmp);
	public void registrar(int id, byte[] buffer);
	public void registrar(int id, Buzon buzon);
	public void eliminar(int id);
	public void imprimirTabla();
}
