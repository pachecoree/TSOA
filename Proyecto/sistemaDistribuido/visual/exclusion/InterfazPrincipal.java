/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */

package sistemaDistribuido.visual.exclusion;

import java.awt.Frame;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.DatagramSocket;
import java.util.HashMap;

import sistemaDistribuido.sistema.exclusion.Puerto;

public class InterfazPrincipal extends Frame implements WindowListener{
	
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 350;
	private final int HEIGHT = 350;
	private Button machineButton;
	private int machineCounter;
	private Puerto ports;
	private int idCounter;
	private TextArea txtArea;
	private int inPort;
	private HashMap<Integer,Maquina> machineTable;
	
	public InterfazPrincipal() {
		super("Practica 7 : Exclusion Mutua");
		setBounds(100,100, WIDTH, HEIGHT);
		txtArea = new TextArea();
		txtArea.setEditable(false);
		add("South",createSouthPanel());
		add("Center",txtArea);
		addWindowListener(this);
		machineButton.addActionListener(new ButtonEventHandler());
		machineCounter = 0;
		idCounter = 1;
		inPort = 1024;
		machineTable = new HashMap<Integer,Maquina>();
		ports = new Puerto();
	}
	
	private Panel createSouthPanel() {
		Panel p = new Panel();
		machineButton = new Button("Crear Maquina");
		p.add(machineButton);
		return p;
	}
	
	public void printLn(String text) {
		txtArea.append(text+"\n");
	}
	
	public DatagramSocket getSocketBroadCast() {
		return ports.getSocketBroadcast();
	}
	
	public int getMachineCounter() {
		return machineCounter;
	}
	
	public HashMap<Integer,Maquina> getMachineTable() {
		return machineTable;
	}
	
	public void closeMachine(Maquina m) {
		printLn("Cerrando maquina con el ID : " + m.getIdProcess());
		machineTable.remove(m.getIdProcess());
		m.setVisible(false);	
		machineCounter--;
	}
	
	public void createMachine() {
		machineCounter++;
		printLn("Levantando maquina con el ID :" + idCounter);
		Maquina m = new Maquina(this,idCounter,inPort++);
		machineTable.put(idCounter++,m);
		m.setVisible(true);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {	
	}
	@Override
	public void windowClosing(WindowEvent e) {
		printLn("Cerrando de sockets de comunicacion");
		System.exit(0);
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
	
	class ButtonEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Crear Maquina")) {
				createMachine();
			}
		}
	}
	
	public static void main (String[] args) {
		InterfazPrincipal prin = new InterfazPrincipal();
		prin.setVisible(true);
	}
}
