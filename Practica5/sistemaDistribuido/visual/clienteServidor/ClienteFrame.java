/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 2
 */

package sistemaDistribuido.visual.clienteServidor;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.ProcesoCliente;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import static sistemaDistribuido.util.Constantes.*;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Choice;
import java.awt.Button;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ClienteFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;
	private Choice codigosOperacion;
	private TextField campoMensaje1,campoMensaje2,campoMensaje3;
	private Button botonSolicitud;
	private String codop1,codop2,codop3,codop4;

	public ClienteFrame(MicroNucleoFrame frameNucleo){
		super(frameNucleo,"Cliente de Archivos");
		add("South",construirPanelSolicitud(0));
		validate();
		proc=new ProcesoCliente(this);
		fijarProceso(proc);
	}

	public Panel construirPanelSolicitud(int codOperacion){
		Panel p=new Panel();
		codigosOperacion=new Choice();
		codop1="Crear";
		codop2="Eliminar";
		codop3="Leer";
		codop4="Escribir";
		codigosOperacion.add(codop1);
		codigosOperacion.add(codop2);
		codigosOperacion.add(codop3);
		codigosOperacion.add(codop4);
		codigosOperacion.select(codOperacion);
		campoMensaje1=new TextField("hola.txt",10);
		botonSolicitud=new Button("Solicitar");
		botonSolicitud.addActionListener(new ManejadorSolicitud());
		codigosOperacion.addItemListener(new ManejadorCodOp());
		p.add(new Label("Operacion:"));
		p.add(codigosOperacion);
		p.add(new Label("Nombre:"));
		p.add(campoMensaje1);
		switch(codOperacion) {
			case READ:
				campoMensaje2 = new TextField("9",5);
				p.add(new Label("Bytes"));
				p.add(campoMensaje2);
				campoMensaje3 = new TextField("0",10);
				p.add(new Label("Posicion"));
				p.add(campoMensaje3);
				break;
			case WRITE:
				campoMensaje2 = new TextField("texto!",10);
				p.add(new Label("texto"));
				p.add(campoMensaje2);
				break;
			default:
				break;
		}
		p.add(botonSolicitud);
		return p;
	}
	
	private String getTextField1() {
		return campoMensaje1.getText();
	}
	
	private String getTextField2() {
		return campoMensaje2.getText();
	}
	
	private String getTextField3() {
		return campoMensaje3.getText();
	}
	
	class ManejadorCodOp implements ItemListener {
	   public void itemStateChanged(ItemEvent e) {
		  BorderLayout layout = (BorderLayout) getLayout();
	      remove(layout.getLayoutComponent(BorderLayout.SOUTH));
		  add("South",construirPanelSolicitud(codigosOperacion.getSelectedIndex()));
		  validate();
	   }
	}

	class ManejadorSolicitud implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String com=e.getActionCommand();
			if (com.equals("Solicitar")){
				botonSolicitud.setEnabled(false);
				com=codigosOperacion.getSelectedItem();
				int opCod = codigosOperacion.getSelectedIndex();
				switch(opCod) {
					case CREATE:
						proc.setParam1(getTextField1());
						break;
					case DELETE:
						proc.setParam1(getTextField1());
						break;
					case READ:
						proc.setParam1(getTextField1());
						proc.setParam2(getTextField2());
						proc.setParam3(getTextField3());
						break;
					case WRITE:
						proc.setParam1(getTextField1());
						proc.setParam2(getTextField2());
						break;
				}
				proc.setOpCod(opCod);
				Nucleo.reanudarProceso(proc);
			}
		}
	}
}
