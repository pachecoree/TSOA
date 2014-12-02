/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 5
 */
package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.MicroNucleoBase;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento.ServidorLocal;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento.Solicitudes;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento.ServidorDireccionamiento;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento.TablaDireccionamiento;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento.TablaServidoresLocales;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.direccionamiento.TablaSolicitudes;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.OrdenamientoBytes;
import sistemaDistribuido.sistema.administracionDeBuzon.*;
/**
 * 
 */
public final class MicroNucleo extends MicroNucleoBase {
	private static MicroNucleo nucleo=new MicroNucleo();
	private Recepcion mapRecepcion;
	private Emision mapEmision;
	private AlmacenamientoTemporal tempStorageTable;
	private TablaDireccionamiento mapDirectory;
	private TablaBuzones tablaBuzones;
	private TablaSolicitudes mapSol;
	private TablaServidoresLocales mapLocalServer;
	/**
	 * 
	 */
	private MicroNucleo(){
		mapEmision = new Emision();
		mapRecepcion = new Recepcion();
		tempStorageTable = new AlmacenamientoTemporal();
		mapDirectory = new TablaDireccionamiento();
		tablaBuzones = new TablaBuzones();
		mapSol = new TablaSolicitudes();
		mapLocalServer = new TablaServidoresLocales();
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

	public void registrarBuzon(int idProceso){

		tablaBuzones.registrar(idProceso, new Buzon());
	}

	public void deregistrarBuzon(int idProceso){

		tablaBuzones.deregistrar(idProceso);
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
		String ip = "";
		boolean foundServer = false;
		int id = 0;
		if(dest>=248) {
		imprimeln("Registrando en tabla de solicitudes...");
		Solicitudes solicitud = new Solicitudes(message, dest);
		mapSol.addElement(super.dameIdProceso(),solicitud);
		}
		imprimeln("Buscando en listas locales el par (máquina,proceso)que corresponde al parámetro"
				+ "dest de la llamada a send");
		if (mapEmision.hasElement(dest)) {
			foundServer = true;
			ParIpId pidip = mapEmision.getElement(dest);
			id = pidip.dameID();
			ip = pidip.dameIP();
			mapEmision.deleteElement(dest);
		}
		else {
			try {
				for (int i=0; i<MAX_INTENTOS_LSA; i++) {
					if (mapDirectory.hasElement(dest)) {
						foundServer = true;
						ServidorDireccionamiento server = mapDirectory.getElement(dest);
						ip = server.getIp();
						id = server.getProcessId();
						break;
					}
					imprimeln("Elaborando mensaje (LSA) buscando al servidor");
					Datagramas.send(MensajesRespuesta.elaborateResponseLSA(super.dameIdProceso(),dest),
						dameSocketEmision(),InetAddress.getByName(BROADCAST_IP), damePuertoRecepcion());
					imprimeln("Intento "+ (i+1) +"/"+MAX_INTENTOS_LSA+" Mensaje (LSA) enviado");
					sleep(FIVE_SECONDS);
				}
			} catch (UnknownHostException e) {
				System.out.println("Error al obtener la dirección ip para rala: "+  e.getMessage());
			} catch (InterruptedException e) {
				System.out.println("Error al ejecutar el Sleep : " + e.getMessage());
			}
		}
		if (!foundServer) {
			imprimeln("Servidor no encontrado según campo "
					+ "dest del mensaje recibido (AU)");
			try {
				Datagramas.send(MensajesRespuesta.elaborateResponse(super.dameIdProceso(),PRO_AU),
						dameSocketEmision(),InetAddress.getByName(LOCALHOST), damePuertoRecepcion());
			} catch (UnknownHostException e) {
				System.out.println("Error al obtener la dirección del anfitrión : "+  e.getMessage());
			}
		}
		else {
			imprimeln("Completando campos de encabezado del mensaje a ser enviado");
			OrdenamientoBytes.breakNumber(super.dameIdProceso(), message,BYTES_INT,POS_SOURCE);
			OrdenamientoBytes.breakNumber(id, message,BYTES_INT,POS_DEST);
			try {
				imprimeln("Enviando mensaje por la red");
				Datagramas.send(message,dameSocketEmision(),InetAddress.getByName(ip),
						damePuertoRecepcion());
			} catch (UnknownHostException e) {
				System.out.println("Error al obtener la dirección del anfitrión : "+  e.getMessage());
			}
		}
	}

	/**
	 * 
	 */
	protected void receiveVerdadero(int addr,byte[] message){

		Buzon buzon = tablaBuzones.buscar(addr);

		if (!tempStorageTable.hasElement(addr) && buzon == null) {
			mapRecepcion.addElement(addr, message);
			suspenderProceso();

		}
		else if(buzon != null){

			if(buzon.estaVacio()){
				mapRecepcion.addElement(addr, message);
				suspenderProceso();
			}else{
				byte[] storedMessage = buzon.obtenerMensaje();
				System.arraycopy(storedMessage, C_0, message, C_0, storedMessage.length);			
			}
		}
		else{
			byte[] storedMessage = tempStorageTable.getElement(addr);
			System.arraycopy(storedMessage, C_0, message, C_0, storedMessage.length);
			tempStorageTable.deleteElement(addr);
		}
	}


	/**
	 * Para el(la) encargad@ de direccionamiento por servidor de nombres en prï¿½ctica 5  
	 */
	protected void sendVerdadero(String dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en prï¿½ctica 5
	 */
	protected void sendNBVerdadero(int dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en prï¿½ctica 5
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
					imprimeln("Dirección Desconocida : El proceso no fue encontrado");
					if (buffer.length == LSAAU_PACKAGE_SIZE) {
						int server = OrdenamientoBytes.buildNumber(BYTES_SERVER,buffer,POS_SERVER);
						if (mapSol.hasElement(source)) {
							Solicitudes sol = mapSol.getElement(source);
							mapDirectory.deleteElement(sol.dameServicio(), server);
						}
					}
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
				else if (OrdenamientoBytes.buildNumber(BYTES_PRO, buffer, POS_PRO) == PRO_LSA) {
					imprimeln("Recibido mensaje de busqueda de servidor");
					imprimeln("Revisando si tengo el servidor");
					int serviceId  = OrdenamientoBytes.buildNumber(BYTES_SERVER, buffer, POS_SERVER);
					if (mapLocalServer.hasElement(serviceId)) {
						imprimeln("Se encontro un servidor para ese servicio");
						ServidorLocal localServ = mapLocalServer.getElement(serviceId);
						int serverId = localServ.getId();
						imprimeln("Elaborando mensaje respuesta (FSA)");
						Datagramas.send(MensajesRespuesta.elaborateResponseFSA(source,serverId),
								dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
						imprimeln("Enviado mensaje con el ID el servidor ");
					}
					else {
						imprimeln("No se encontro el servidor para realizar ese servicio");
					}
					
				}
				else if (OrdenamientoBytes.buildNumber(BYTES_PRO, buffer, POS_PRO) == PRO_FSA) {
					//procesar mensaje FSA
					int serverId = OrdenamientoBytes.buildNumber(BYTES_SERVER, buffer, POS_SERVER);
					String ip = dp.getAddress().getHostAddress();
					imprimeln("Recibido mensaje de localizacion (FSA)");
					imprimeln("Servidor con el Id : " + serverId + " en la direccion : " + ip);
					Solicitudes sol = mapSol.getElement(source);
					mapDirectory.addElement(sol.dameServicio(), new ServidorDireccionamiento(ip,serverId));
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
							Buzon buzon = tablaBuzones.buscar(dest);
							
							if(buzon != null){
								byte[] copiaNueva = null;
								System.arraycopy(buffer, C_0, copiaNueva, C_0, buffer.length);
								if(buzon.ingresarMensaje(copiaNueva)){
									mapEmision.addElement(source,new ParIpId(source,ip));
								}else{

										imprimeln("Buzon lleno... (TA)");
										tempStorageTable.deleteElement(dest);
										Datagramas.send(MensajesRespuesta.elaborateResponse(source,PRO_TA),
												dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
									
									}
							}else{
								
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
					}
					else {
						if (!tempStorageTable.hasElement(dest)) {
							tempStorageTable.addElement(dest,buffer);
							imprimeln("Esperando respuesta de servidor");
							sleep(TEN_SECONDS);
							if (tempStorageTable.hasElement(dest)) {
								tempStorageTable.deleteElement(dest);
								imprimeln("Proceso destinatario no encontrado según campo "
										+ "dest del mensaje recibido (AU)");
								Datagramas.send(MensajesRespuesta.elaborateResponseLSAAU(source,dest),
										dameSocketEmision(),dp.getAddress(), damePuertoRecepcion());
							}
						}
						else {
							imprimeln("Proceso destinatario no encontrado según campo "
									+ "dest del mensaje recibido (AU)");
							Datagramas.send(MensajesRespuesta.elaborateResponseLSAAU(source,dest),
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
	
	public void registrarServidor(int numeroDeServicio, int idProceso ){
		mapLocalServer.addElement(numeroDeServicio,new ServidorLocal(idProceso));
	}
	
	public void deregistrarServidor(int numeroDeServicio,int idProceso){
		mapLocalServer.deleteElement(numeroDeServicio, idProceso);
	}

}
