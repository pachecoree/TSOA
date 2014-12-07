/**
 * Cacho Robledo Vega Renato
 * Romero Pacheco Carlos Mauricio
 * 
 * Proyecto
 **/

package sistemaDistribuido.visual.rpc;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.rpc.PanelClienteServidorConector;

import static sistemaDistribuido.util.Constantes.*;

import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RPCFrame extends MicroNucleoFrame{
	private static final long serialVersionUID=1;
	private PanelClienteServidorConector panelBotones;
	private ConectorFrame conector;

	public RPCFrame(){
		setTitle("Practicas 3 y 4 - Llamadas a Procedimientos Remotos (RPC)");
		conector=new ConectorFrame(this);
	}

	protected Panel construirPanelSur(){
		panelBotones=new PanelClienteServidorConector();
		panelBotones.agregarActionListener(new ManejadorBotones());
		return panelBotones;
	}

	class ManejadorBotones implements ActionListener{

		public void actionPerformed(ActionEvent e){
			String com=e.getActionCommand();
			if (com.equals("Cliente")){
				int selectedChoice = panelBotones.getSelectedChoice();
				if (selectedChoice == CACHO) {
					levantarProcesoFrame(new 
						   sistemaDistribuido.visual.rpc.CachoRenato.ClienteFrame(RPCFrame.this));
				}
				else if (selectedChoice == ROMERO){
					levantarProcesoFrame(new 
						   sistemaDistribuido.visual.rpc.RomeroCarlos.ClienteFrame(RPCFrame.this));
				}
			}
			else if (com.equals("Servidor")){
				int selectedChoice = panelBotones.getSelectedChoice();
				if (selectedChoice == CACHO) {
					levantarProcesoFrame(new
						   sistemaDistribuido.visual.rpc.CachoRenato.ServidorFrame (RPCFrame.this));
				}
				else if (selectedChoice == ROMERO) {
					levantarProcesoFrame(new 
						   sistemaDistribuido.visual.rpc.RomeroCarlos.ServidorFrame(RPCFrame.this));
				}
			}
			else if (com.equals("Conector")){
				conector.setVisible(true);
				panelBotones.dameBotonConector().setEnabled(false);
			}
		}
	}

	public void cerrarFrameConector(){
		conector.setVisible(false);
		panelBotones.dameBotonConector().setEnabled(true);
	}

	public static void main(String args[]){
		RPCFrame rpcf=new RPCFrame();
		rpcf.setVisible(true);
		rpcf.imprimeln("Ventana del micronucleo iniciada.");
		Nucleo.iniciarSistema(rpcf,2001,2002,rpcf);
	}
}
