//Brambila López Jaime Arturo
//Práctica #1: Modelo Cliente / Servidor “Paso de Mensajes entre Procesos”

package sistemaDistribuido.visual.clienteServidor.BrambilaJaime;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.BrambilaJaime.ProcesoCliente;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;

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
	private TextField campoMensaje;
	private Button botonSolicitud;
	private String codop1,codop2,codop3,codop4;
	private final int CREAR = 0;
	private final int ELIMINAR = 1;
	private final int LEER = 2;
	private final int ESCRIBIR = 3;

	public ClienteFrame(MicroNucleoFrame frameNucleo){
		super(frameNucleo,"Cliente de Archivos");
		add("South",construirPanelSolicitud());
		validate();
		proc=new ProcesoCliente(this);
		fijarProceso(proc);
	}

	public Panel construirPanelSolicitud(){
		Panel p=new Panel();
		codigosOperacion=new Choice();
		codigosOperacion.addItemListener(new ManejadorChoice());
		codop1="Crear Archivo";
		codop2="Eliminar Archivo";
		codop3="Leer Archivo";
		codop4="Escribir en Archivo";
		codigosOperacion.add(codop1);
		codigosOperacion.add(codop2);
		codigosOperacion.add(codop3);
		codigosOperacion.add(codop4);
		campoMensaje=new TextField(20);
		campoMensaje.setText("Documento.txt");
		botonSolicitud=new Button("Solicitar");
		botonSolicitud.addActionListener(new ManejadorSolicitud());
		p.add(new Label("Operacion:"));
		p.add(codigosOperacion);
		p.add(new Label("Datos:"));
		p.add(campoMensaje);
		p.add(botonSolicitud);
		return p;
	}

	class ManejadorSolicitud implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String mensaje;
			String com=e.getActionCommand();
			if (com.equals("Solicitar")){
				botonSolicitud.setEnabled(false);
				com=codigosOperacion.getSelectedItem();
				mensaje = campoMensaje.getText();
				imprimeln("Solicitud a enviar: "+com);
				imprimeln("Mensaje a enviar: " + mensaje);
				proc.setParametros((short)codigosOperacion.getSelectedIndex(),mensaje);
				Nucleo.reanudarProceso(proc);
			}
		}
	}
	
	class ManejadorChoice implements ItemListener{
		
		public void itemStateChanged(ItemEvent e){
			int codigoOperacion = codigosOperacion.getSelectedIndex();
			switch(codigoOperacion)
			{
			
				case CREAR:
					campoMensaje.setText("Documento.txt");
					break;
				case ELIMINAR:
					campoMensaje.setText("Documento.txt");
					break;	
				case LEER:
					campoMensaje.setText("Documento.txt-3-14");
					break;	
				case ESCRIBIR:
					campoMensaje.setText("Documento.txt-0-CC320 ");
					break;
			}
		}
		
	}
	
}
