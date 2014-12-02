//Brambila López Jaime Arturo
//Práctica #4: Llamadas a Procedimientos Remotos (RPC) “El Programa Conector”

package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;

//import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;   //para práctica 4
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;

import sistemaDistribuido.sistema.empaquetador.Empaquetador;
import static sistemaDistribuido.util.Constantes.*;

import sistemaDistribuido.util.Escribano;

public class ProcesoServidor extends Proceso{
	private LibreriaServidor ls;   //para práctica 3
	int idProceso;
	int codigoOperacion;
	int resultado;
	private InformacionServidor informacionServidor = new InformacionServidor();

	public ProcesoServidor(Escribano esc){
		super(esc);
		ls=new LibreriaServidor(esc);   //para práctica 3
		start();
	}

	public void run(){
		byte[] solicitud = new byte[TAMANO_MENSAJE];
		byte[] respuesta = new byte[TAMANO_MENSAJE];
		imprimeln("Proceso servidor en ejecucion.");
		ParIpId asa=null;
		try {
			asa = new ParIpId(dameID(),InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int idUnico = RPC.exportarInterfaz(informacionServidor.dameNombre(), informacionServidor.dameVersion(), asa);  //para práctica 4
		
		while(continuar()){
			
			Nucleo.receive(dameID(),solicitud);
			idProceso = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_ID_EMISOR,POSICION_ID_EMISOR+BYTES_ID_EMISOR));
			codigoOperacion = Empaquetador.arregloAShort(Arrays.copyOfRange(solicitud,POSICION_CODIGO_OPERACION,POSICION_CODIGO_OPERACION+BYTES_CODIGO_OPERACION));
			imprimeln("Codop actual: "+codigoOperacion);
			switch(codigoOperacion){
				case 0: suma(solicitud,respuesta); break;
				case 1: promedio(solicitud,respuesta); break;
				case 2: potencia(solicitud,respuesta); break;
				case 3: porcentaje(solicitud,respuesta); break;
			}
			Nucleo.send(idProceso, respuesta);
		}
		if(RPC.deregistrarInterfaz(informacionServidor.dameNombre(), informacionServidor.dameVersion(), idUnico)){
			imprimeln("Servidor deregistrado correctamente");
			//para práctica 4
		}else{
			imprimeln("Imposible deregistrar");
		}
	}
	
	public void suma(byte[] solicitud,byte[] respuesta){
		imprimeln("Solicitud de suma");
		int sumando1 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE,POSICION_MENSAJE+BYTES_INT));
		int sumando2 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT));
		imprimeln("Suma: "+sumando1+" + "+sumando2);
		resultado=ls.suma(sumando1,sumando2);
		imprimeln("Resultado: "+resultado);
		imprimeln("Enviando respuesta...");
		Empaquetador.empaqueta(respuesta,POSICION_RESPUESTA,resultado);
	}
	
	public void promedio(byte[] solicitud,byte[] respuesta){
		imprimeln("Solicitud de promedio");
		int promedio1 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE,POSICION_MENSAJE+BYTES_INT));
		int promedio2 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT));
		int promedio3 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT));
		int promedio4 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT+BYTES_INT));
		int promedio5 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT+BYTES_INT+BYTES_INT));
		imprimeln("Promedio: "+promedio1+" , "+promedio2+" , "+promedio3+" , "+promedio4+" , "+promedio5);
		resultado=ls.promedio(promedio1,promedio2,promedio3,promedio4,promedio5);
		imprimeln("Resultado: "+resultado);
		imprimeln("Enviando respuesta...");
		Empaquetador.empaqueta(respuesta,POSICION_RESPUESTA,resultado);
	}
	
	public void potencia(byte[] solicitud,byte[] respuesta){
		imprimeln("Solicitud de potencia");
		int potencia1 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE,POSICION_MENSAJE+BYTES_INT));
		int potencia2 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT));
		imprimeln("Potencia: "+potencia1+" ^ "+potencia2);
		resultado=ls.potencia(potencia1,potencia2);
		imprimeln("Resultado: "+resultado);
		imprimeln("Enviando respuesta...");
		Empaquetador.empaqueta(respuesta,POSICION_RESPUESTA,resultado);
	}
	
	public void porcentaje(byte[] solicitud,byte[] respuesta){
		imprimeln("Solicitud de porcentaje");
		int porcentaje1 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE,POSICION_MENSAJE+BYTES_INT));
		int porcentaje2 = Empaquetador.arregloAInt(Arrays.copyOfRange(solicitud,POSICION_MENSAJE+BYTES_INT,POSICION_MENSAJE+BYTES_INT+BYTES_INT));
		imprimeln("Porcentaje: "+porcentaje1+"% de "+porcentaje2);
		resultado=ls.porcentaje(porcentaje1,porcentaje2);
		imprimeln("Resultado: "+resultado);
		imprimeln("Enviando respuesta...");
		Empaquetador.empaqueta(respuesta,POSICION_RESPUESTA,resultado);
	}
}
