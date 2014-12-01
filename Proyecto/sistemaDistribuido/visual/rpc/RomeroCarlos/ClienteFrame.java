/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 3
 */
package sistemaDistribuido.visual.rpc.RomeroCarlos;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.RomeroCarlos.ProcesoCliente;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import sistemaDistribuido.visual.rpc.RPCFrame;

import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Label;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;
	private TextField campo1,campo2,campo3,campo4;
	private TextField campo5,campo6,campo7,campo8;
	private Button botonSolicitud;

	public ClienteFrame(RPCFrame frameNucleo){
		super(frameNucleo,"Cliente de Archivos");
		add("South",construirPanelSolicitud());
		validate();
		proc=new ProcesoCliente(this);
		fijarProceso(proc);
	}

	public Panel construirPanelSolicitud(){
		Panel pSolicitud,pcodop1,pcodop2,pcodop3,pcodop4,pboton;
		pSolicitud=new Panel();
		pcodop1=new Panel();
		pcodop2=new Panel();
		pcodop3=new Panel();
		pcodop4=new Panel();
		pboton=new Panel();
		campo1=new TextField("121",5);
		campo2=new TextField("310",5);
		campo3=new TextField("1",5);
		campo4=new TextField("12",5);
		campo5=new TextField("122",5);
		campo6=new TextField("8",5);
		campo7=new TextField("6",5);
		campo8=new TextField("13",5);
		pSolicitud.setLayout(new GridLayout(5,1));

		pcodop1.add(new Label("QuickSort >> "));
		pcodop1.add(new Label("[0]"));
		pcodop1.add(campo1);
		pcodop1.add(new Label("[1]"));
		pcodop1.add(campo2);
		pcodop1.add(new Label("[2]"));
		pcodop1.add(campo3);
		pcodop1.add(new Label("[3]"));
		pcodop1.add(campo4);
		pcodop1.add(new Label("[4]"));
		pcodop1.add(campo5);

		pcodop2.add(new Label("Fibonacci >> "));
		pcodop2.add(new Label("Number :"));
		pcodop2.add(campo6);

		pcodop3.add(new Label("Factorial >> "));
		pcodop3.add(new Label("Number ::"));
		pcodop3.add(campo7);

		pcodop4.add(new Label("Prime >> "));
		pcodop4.add(new Label("Numero :"));
		pcodop4.add(campo8);

		botonSolicitud=new Button("Solicitar");
		pboton.add(botonSolicitud);
		botonSolicitud.addActionListener(new ManejadorSolicitud());

		pSolicitud.add(pcodop1);
		pSolicitud.add(pcodop2);
		pSolicitud.add(pcodop3);
		pSolicitud.add(pcodop4);
		pSolicitud.add(pboton);

		return pSolicitud;
	}

	class ManejadorSolicitud implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String com=e.getActionCommand();
			if (com.equals("Solicitar")){
				botonSolicitud.setEnabled(false);
				proc.setParam1(campo1.getText());
				proc.setParam2(campo2.getText());
				proc.setParam3(campo3.getText());
				proc.setParam4(campo4.getText());
				proc.setParam5(campo5.getText());
				proc.setParam6(campo6.getText());
				proc.setParam7(campo7.getText());
				proc.setParam8(campo8.getText());
				Nucleo.reanudarProceso(proc);
			}
		}
	}
}
