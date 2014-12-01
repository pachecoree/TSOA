package sistemaDistribuido.visual.rpc.CachoRenato;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato.ProcesoCliente;
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
		campo1=new TextField(10);
		campo2=new TextField(10);
		campo3=new TextField(10);
		campo4=new TextField(10);
		pSolicitud.setLayout(new GridLayout(5,1));

		pcodop1.add(new Label("Sumar>> "));
		pcodop1.add(new Label("Param n: "));
		campo1.setText("9 8 7 3 13");
		pcodop1.add(campo1);

		pcodop2.add(new Label("Ordenar>> "));
		pcodop2.add(new Label("Param n:"));
		campo2.setText("10.6 7.5 3.3 1.9 10.68");
		pcodop2.add(campo2);

		pcodop3.add(new Label("Potencia x^y>> "));
		pcodop3.add(new Label("Param 2:"));
		campo3.setText("5 2");
		pcodop3.add(campo3);

		pcodop4.add(new Label("Raiz cuadrada>> "));
		pcodop4.add(new Label("Param 1:"));
		campo4.setText("234");
		pcodop4.add(campo4);

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
				proc.recibirParametros(campo1.getText(), campo2.getText(),campo3.getText(),campo4.getText());
				Nucleo.reanudarProceso(proc);
			}
		}
	}
}
