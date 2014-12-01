package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import sistemaDistribuido.sistema.administracionDeBuzon.Buzon;

/**
 * Cacho Robledo Vega Renato, 
 * 
 * Práctica 2: 22-OCT-2014
 */
public class AdministracionRegistro implements AdminTabla{
	@Override
	public void registrar(int id, ParIpId pmp) {}

	@Override
	public void registrar(int id, byte[] buffer) {}
	
	@Override
	public void registrar(int id, Buzon buzon) {}
	
	@Override
	public void eliminar(int id) {}

	@Override
	public void imprimirTabla() {}
}
