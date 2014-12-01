package sistemaDistribuido.visual.rpc.CachoRenato;

import sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato.ProcesoServidor;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import sistemaDistribuido.visual.rpc.RPCFrame;

public class ServidorFrame extends ProcesoFrame{
  private static final long serialVersionUID=1;
  private ProcesoServidor proc;
  
  public ServidorFrame(RPCFrame frameNucleo){
    super(frameNucleo,"Servidor de Archivos");
    proc=new ProcesoServidor(this);
    fijarProceso(proc);
  }
}
