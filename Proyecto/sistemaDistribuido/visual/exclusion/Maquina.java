/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.visual.exclusion;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Label;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashMap;

import sistemaDistribuido.sistema.exclusion.Emision;
import sistemaDistribuido.sistema.exclusion.ParIdPuerto;
import sistemaDistribuido.sistema.exclusion.Recepcion;
import sistemaDistribuido.util.exclusion.OrdenamientoBytes;
import sistemaDistribuido.visual.exclusion.InterfazPrincipal;
import static sistemaDistribuido.util.exclusion.Constantes.*;

public class Maquina extends Frame implements WindowListener{
	
	public static final long serialVersionUID = 1l;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private TextArea txtArea;
	private TextArea waitingListTxt;
	private InterfazPrincipal prin;
	private Button getResourceBtn;
	private Button releaseResourceBtn;
	private boolean wantResource;
	private boolean usingResource;
	private TextField manualTime;
	private Recepcion hiloReceptor;
	private int idProcess;
	private int inPort;
	private int reqTime;
	private DatagramSocket socketReception;
	private int machineCount;
	private int acceptedReqCount;
	private Queue<ParIdPuerto> machineQueue;
	
	public Maquina(InterfazPrincipal prin,int idProcess,int inPort) {
		super("Maquina");
		setBounds(400,100,WIDTH,HEIGHT);
		this.prin = prin;
		this.idProcess = idProcess;
		this.inPort = inPort;
		machineQueue = new LinkedList<ParIdPuerto>();
		initializeSocketReception();
		add("North",createNorthPanel());
		add("Center",createMainPanel());
		add("South",createSouthPanel());
		addWindowListener(this);
		wantResource = false;
		usingResource = false;
		hiloReceptor = new Recepcion(this);
		hiloReceptor.start();
	}
	
	public void initializeSocketReception() {
		socketReception = null;
		try {
			socketReception = new DatagramSocket(inPort);
		} catch (SocketException e) {
			System.out.println("Error al iniciar el puerto de entrada : " + e.getMessage());
		}
	}
	
	
	private Panel createNorthPanel() {
		Panel p = new Panel();
		manualTime = new TextField("5",5);
		TextField txtIdProcess = new TextField(Integer.toString(idProcess),5);
		txtIdProcess.setEditable(false);
		p.add(new Label("ID proceso"));
		p.add(txtIdProcess);
		p.add(new Label("Tiempo de Solicitud"));
		p.add(manualTime);
		return p;
	}
	
	private Panel createSouthPanel() {
		Panel p = new Panel();
		getResourceBtn = new Button("Pedir");
		releaseResourceBtn = new Button("Liberar");
		releaseResourceBtn.setEnabled(false);
		getResourceBtn.addActionListener(new ButtonEventHandler());
		releaseResourceBtn.addActionListener(new ButtonEventHandler());
		p.add(new Label("Control de Recursos"));
		p.add(getResourceBtn);
		p.add(releaseResourceBtn);
		return p;
	}
	
	private Panel createMainPanel() {
		Panel p = new Panel();
		txtArea = new TextArea();
		waitingListTxt = new TextArea();
		p.add(txtArea);
		p.add(waitingListTxt);
		return p;
	}
	
	class ButtonEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Pedir")) {
				getResourceBtn.setEnabled(false);
				getResource();
			}
			else if (command.equals("Liberar")) {
				printLn("Liberando el recurso");
				while (!machineQueue.isEmpty()) {
					ParIdPuerto pip = removeMachineFromQueue();
					byte[] message = new byte[SOURCE_SIZE+PORT_SIZE+SOL_SIZE];
					OrdenamientoBytes.breakNumber(idProcess, message, SOURCE_SIZE,SOURCE_POS);
					OrdenamientoBytes.breakNumber(inPort, message, PORT_SIZE,PORT_POS);
					OrdenamientoBytes.breakNumber(OK_RESPONSE, message, SOL_SIZE,SOL_POS);
					try {
						Emision.send(message,prin.getSocketBroadCast(),
								     InetAddress.getByName(BROADCAST_IP),pip.getPort());
					} catch (UnknownHostException e1) {
						System.out.println("Error al obtener la dirección del anfitrión : "
					                       +  e1.getMessage());
					}
				}
				releaseResourceBtn.setEnabled(false);
				usingResource = false;
				wantResource = false;
				getResourceBtn.setEnabled(true);
			}
		}
	}
	
	private void refreshQueueList() {
		String text = "";
		int queueSize = machineQueue.size();
		for (int i=0; i<queueSize; i++) {
			ParIdPuerto pip = machineQueue.poll();
			text += "ID Maquina : " + pip.getId() + "\n";
			machineQueue.add(pip);
		}
		waitingListTxt.setText(text);
	}
	
	public int getInPort() {
		return inPort;
	}
	
	public void addMachineToQueue(ParIdPuerto pip) {
		machineQueue.add(pip);
		refreshQueueList();
	}
	
	public ParIdPuerto removeMachineFromQueue() {
		ParIdPuerto pip = machineQueue.poll();
		refreshQueueList();
		return pip;
	}
	
	public DatagramSocket getSocketReception() {
		return socketReception;
	}
	
	public int getIdProcess() {
		return idProcess;
	}
	
	public boolean wantsResource() {
		return wantResource;
	}
	
	public int getAcceptedReqCount() {
		return acceptedReqCount;
	}
	
	public void setAcceptedReqCount(int acceptedCount) {
		acceptedReqCount = acceptedCount;
	}
	
	public int getMachineCount() {
		return machineCount;
	}
	
	public int getReqTime() {
		return reqTime;
	}
	
	public void setUsingResource(boolean usingResource) {
		releaseResourceBtn.setEnabled(true);
		this.usingResource = usingResource;
	}
	
	public boolean getUsingResource() {
		return usingResource;
	}
	
	private void getResource() {
	   wantResource = true;
	   reqTime = Integer.parseInt(manualTime.getText());
	   byte[] message = new byte[SOURCE_SIZE+PORT_SIZE+SOL_SIZE+REQ_TIME_SIZE];
	   OrdenamientoBytes.breakNumber(idProcess, message, SOURCE_SIZE,SOURCE_POS);
	   OrdenamientoBytes.breakNumber(inPort, message, PORT_SIZE,PORT_POS);
	   OrdenamientoBytes.breakNumber(GET_RESOURCE, message, SOL_SIZE,SOL_POS);
	   OrdenamientoBytes.breakNumber(reqTime, message, REQ_TIME_SIZE,REQ_TIME_POS);
	   try {
		   	HashMap<Integer,Maquina> machineTable = prin.getMachineTable();
			Set<Integer> keySet = machineTable.keySet();
			Iterator<Integer> iterator = keySet.iterator();
			   acceptedReqCount = 0;
			   machineCount = machineTable.size();
			while (iterator.hasNext()) {
				int serverKey = iterator.next();
				Maquina m = machineTable.get(serverKey);
				Emision.send(message,prin.getSocketBroadCast(),InetAddress.getByName(BROADCAST_IP),m.getInPort());
			}
		} catch (UnknownHostException e) {
			System.out.println("Error al obtener la dirección del anfitrión : "+  e.getMessage());
		}
	}
	
	public void printLn(String text) {
		txtArea.append(text + "\n");
	}
	
	@Override
	public void windowOpened(WindowEvent e) {	
	}
	@Override
	public void windowClosing(WindowEvent e) {
		hiloReceptor.setWaitingForPackages(false);
		prin.closeMachine(this);
	}
	@Override
	public void windowClosed(WindowEvent e) {	
	}
	@Override
	public void windowIconified(WindowEvent e) {	
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowDeactivated(WindowEvent e) {		
	}

	public DatagramSocket getSocketBroadCast() {
		return prin.getSocketBroadCast();
	}
}
