package sistemaDistribuido.visual.rpc.BrambilaJaime;

//Brambila López Jaime Arturo
//Práctica #3: Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor”

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime.ProcesoCliente;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import sistemaDistribuido.visual.rpc.RPCFrame;
import static sistemaDistribuido.util.Constantes.*;

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
	private TextField campoSuma1,campoSuma2,campoPromedio1,campoPromedio2,campoPromedio3,campoPromedio4,campoPromedio5,
	campoPotencia1,campoPotencia2,campoPorcentaje1,campoPorcentaje2;
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
		campoSuma1=new TextField(TAMANO_TEXTFIELD);
		campoSuma2=new TextField(TAMANO_TEXTFIELD);
		campoSuma1.setText("5");
		campoSuma2.setText("12");
		campoPromedio1=new TextField(TAMANO_TEXTFIELD);
		campoPromedio2=new TextField(TAMANO_TEXTFIELD);
		campoPromedio3=new TextField(TAMANO_TEXTFIELD);
		campoPromedio4=new TextField(TAMANO_TEXTFIELD);
		campoPromedio5=new TextField(TAMANO_TEXTFIELD);
		campoPromedio1.setText("10");
		campoPromedio2.setText("9");
		campoPromedio3.setText("8");
		campoPromedio4.setText("9");
		campoPromedio5.setText("9");
		campoPotencia1=new TextField(TAMANO_TEXTFIELD);
		campoPotencia2=new TextField(TAMANO_TEXTFIELD);
		campoPotencia1.setText("2");
		campoPotencia2.setText("3");
		campoPorcentaje1=new TextField(TAMANO_TEXTFIELD);
		campoPorcentaje2=new TextField(TAMANO_TEXTFIELD);
		campoPorcentaje1.setText("15");
		campoPorcentaje2.setText("200");
		
		pSolicitud.setLayout(new GridLayout(5,1));

		pcodop1.add(new Label("SUMA:"));
		pcodop1.add(campoSuma1);
		pcodop1.add(new Label("+"));
		pcodop1.add(campoSuma2);

		pcodop2.add(new Label("PROMEDIO"));
		pcodop2.add(campoPromedio1);
		pcodop2.add(new Label(","));
		pcodop2.add(campoPromedio2);
		pcodop2.add(new Label(","));
		pcodop2.add(campoPromedio3);
		pcodop2.add(new Label(","));
		pcodop2.add(campoPromedio4);
		pcodop2.add(new Label(","));
		pcodop2.add(campoPromedio5);

		pcodop3.add(new Label("POTENCIA:"));
		pcodop3.add(campoPotencia1);
		pcodop3.add(new Label("^"));
		pcodop3.add(campoPotencia2);

		pcodop4.add(new Label("PORCENTAJE"));
		pcodop4.add(campoPorcentaje1);
		pcodop4.add(new Label("% de"));
		pcodop4.add(campoPorcentaje2);

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
				//...
				proc.fijarParametros(campoSuma1.getText(),campoSuma2.getText(),
						campoPromedio1.getText(),campoPromedio2.getText(),campoPromedio3.getText(),campoPromedio4.getText(),campoPromedio5.getText(),
						campoPotencia1.getText(),campoPotencia2.getText(),campoPorcentaje1.getText(),campoPorcentaje2.getText());
				Nucleo.reanudarProceso(proc);
			}
		}
	}
}
