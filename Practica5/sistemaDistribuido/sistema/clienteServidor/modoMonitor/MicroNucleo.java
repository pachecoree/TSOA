/**
 * Romero Pacheco Carlos Mauricio
 * Pr�ctica 5
 */
package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.MicroNucleoBase;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.OrdenamientoBytes;

/**
 * 
 */
public final class MicroNucleo extends MicroNucleoBase {
	private static MicroNucleo nucleo=new MicroNucleo();
	private Recepcion mapRecepcion;
	private Emision mapEmision;
	private AlmacenamientoTemporal tempStorageTable;

	/**
	 * 
	 */
	private MicroNucleo(){
		 mapEmision = new Emision();
		 mapRecepcion = new Recepcion();
		 tempStorageTable = new AlmacenamientoTemporal();
	}

	/**
	 * 
	 */
	
	public final static MicroNucleo obtenerMicroNucleo(){
		return nucleo;
	}
	
	public void registerInterface(ParIpId pIpId) {
		mapEmision.addElement(pIpId.dameID(), pIpId);
	}

	/*---Metodos para probar el paso de mensajes entre los procesos cliente y servidor en ausencia de datagramas.
    Esta es una forma incorrecta de programacion "por uso de variables globales" (en este caso atributos de clase)
    ya que, para empezar, no se usan ambos parametros en los metodos y fallaria si dos procesos invocaran
    simultaneamente a receiveFalso() al reescriir el atributo mensaje---*/
	byte[] mensaje;

	public void sendFalso(int dest,byte[] message){
		System.arraycopy(message,0,mensaje,0,message.length);
		notificarHilos();  //Reanuda la ejecucion del proceso que haya invocado a receiveFalso()
	}

	public void receiveFalso(int addr,byte[] message){
		mensaje=message;
		suspenderProceso();
	}
	/*---------------------------------------------------------*/

	/**
	 * 
	 */
	protected boolean iniciarModulos(){
		return true;
	}

	/**
	 * 
	 */
	protected void sendVerdadero(int dest,byte[] message){
		String ip;
		int id;
		imprimeln("Buscando en listas locales el par (m�quina,proceso)que corresponde al par�metro"
				  + "dest de la llamada a send");
		if (mapEmision.hasElement(dest)) {
			ParIpId pidip = mapEmision.getElement(dest);
			id = pidip.dameID();
			ip = pidip.dameIP();
			mapEmision.deleteElement(dest);
		}
		else {
			imprimeln("Enviando mensaje de b�squeda del servidor");
			ParMaquinaProceso pmp = dameDestinatarioDesdeInterfaz();
			id = pmp.dameID();
			ip = pmp.dameIP();
			imprimeln("Recibido mensaje que contiene la ubicaci�n (m�quina,proceso)del servidor");
		}
		imprimeln("Completando campos de encabezado del mensaje a ser enviado");
		OrdenamientoBytes.breakNumber(super.dameIdProceso(), message,BYTES_INT,POS_SOURCE);
		OrdenamientoBytes.breakNumber(id, message,BYTES_INT,POS_DEST);
		try {
			imprimeln("Enviando mensaje por la red");
			Datagramas.send(message,dameSocketEmision(),InetAddress.getByName(ip),
					        damePuertoRecepcion());
		} catch (UnknownHostException e) {
			System.out.println("Error al obtener la direcci�n del anfitri�n : "+  e.getMessage());
		}
	}

	/**
	 * 
	 */
	protected void receiveVerdadero(int addr,byte[] message){
		if (!tempStorageTable.hasElement(addr)) {
			mapRecepcion.addElement(addr, message);
			suspenderProceso();
		}
		else {
		    byte[] storedMessage = tempStorageTable.getElement(addr);
		    System.arraycopy(storedMessage, C_0, message, C_0, storedMessage.length);
		    tempStorageTable.deleteElement(addr);
		}
	}

	/**
	 * Para el(la) encargad@ de direccionamiento por servidor de nombres en pr�ctica 5  
	 */
	protected void sendVerdadero(String dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en pr�ctica 5
	 */
	protected void sendNBVerdadero(int dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en pr�ctica 5
	 */
	protected void receiveNBVerdadero(int addr,byte[] message){
	}

	/**
	 * 
	 */
	public void run(){
		DatagramSocket socket = dameSocketRecepcion();
		byte[] buffer = new byte[BUFFER_SIZE];
		DatagramPacket dp = new DatagramPacket(buffer,BUFFER_SIZE);
		try {
			while(seguirEsperandoDatagramas()){
				socket.receive(dp);
				int source = OrdenamientoBytes.buildNumber(BYTES_SOURCE,buffer,POS_SOURCE);
				if (OrdenamientoBytes.buildNumber(BYTES_PRO, buffer, POS_PRO) == PRO_AU) {
					imprimeln("Direcci�n Desconocida : El proceso no fue encontrado");
					reanudarProceso(dameProcesoLocal(source));
				}
				else if (OrdenamientoBytes.buildNumber(BYTES_PRO, buffer, POS_PRO) == PRO_TA) {
					imprimeln("Intenta de nuevo : (TA) recibido");
					byte[] solicitud = mapRecepcion.getElement(source);
					imprimeln("Espera 5 segundos...");
					sleep(FIVE_SECONDS);
					imprimeln("Reenviando mensaje..");
					Datagramas.send(solicitud,dameSocketEmision(),dp.getAddress(),damePuertoRecepcion());
				}
				else {
					int dest = OrdenamientoBytes.buildNumber(BYTES_DEST,buffer,POS_DEST);
					String ip = dp.getAddress().getHostAddress();
					imprimeln("Recibido mensaje proveniente de la red");
					imprimeln("Emisor |ID : "+source+"|IP: "+ip);
					imprimeln("Buscando proceso correspondiente al campo dest del mensaje recibido");
					Proceso proc = dameProcesoLocal(dest);
					if (proc != null) {
						if (mapRecepcion.hasElement(dest)) {
							byte[] solicitud = mapRecepcion.getElement(dest);
							mapEmision.addElement(source, new ParIpId(source,ip));
							mapRecepcion.deleteElement(dest);
							imprimeln("Copiando el mensaje hacia el espacio del proceso");
							System.arraycopy(buffer, C_0, solicitud, C_0, buffer.length);
							reanudarProceso(proc);
						}
						else {
							if (!tempStorageTable.hasElement(dest)) {
								tempStorageTable.addElement(dest, buffer);
								imprimeln("Esperando a que el servidor este disponible");
								mapEmision.addElement(source,new ParIpId(source,ip));
								sleep(TEN_SECONDS);
								if (tempStorageTable.hasElement(dest)) {
									imprimeln("Proceso destinatario no disponible (TA)");
									tempStorageTable.deleteElement(dest);
									Datagramas.send(MensajesRespuesta.elaborateResponse(source,PRO_TA),
											    dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
								}
							}
							else {
								imprimeln("Proceso destinatario no disponible (TA)");
								tempStorageTable.deleteElement(dest);
								Datagramas.send(MensajesRespuesta.elaborateResponse(source,PRO_TA),
									   dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
							}
						}
					}
					else {
						if (!tempStorageTable.hasElement(dest)) {
							tempStorageTable.addElement(dest,buffer);
							imprimeln("Esperando respuesta de servidor");
							sleep(TEN_SECONDS);
							if (tempStorageTable.hasElement(dest)) {
								tempStorageTable.deleteElement(dest);
								imprimeln("Proceso destinatario no encontrado seg�n campo "
										  + "dest del mensaje recibido (AU)");
								Datagramas.send(MensajesRespuesta.elaborateResponse(source,PRO_AU),
									dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
							}
						}
						else {
							imprimeln("Proceso destinatario no encontrado seg�n campo "
									  + "dest del mensaje recibido (AU)");
							Datagramas.send(MensajesRespuesta.elaborateResponse(source,PRO_AU),
									       dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
						}
					}
				}
			}
		}catch (IOException e) {
			System.out.println("Error al recibir el paquete : " + e.getMessage());
		}catch (InterruptedException e) {
			System.out.println("Error al ejecutar el Sleep :" + e.getMessage());
		}
	}
	
}
