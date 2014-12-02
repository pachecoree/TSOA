package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;
//Brambila López Jaime Arturo
//Práctica #4: Llamadas a Procedimientos Remotos (RPC) “El Programa Conector”
//import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;  //para práctica 4
import java.util.Arrays;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;

import sistemaDistribuido.sistema.empaquetador.Empaquetador;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.PaqueteParametros;
import sistemaDistribuido.util.Pausador;

import static sistemaDistribuido.util.Constantes.*;


public class LibreriaCliente extends Libreria{
	byte[] solicitud = new byte[TAMANO_MENSAJE];
	byte[] respuesta = new byte[TAMANO_MENSAJE];
	private int asaDest;
	InformacionServidor informacionServidor = new InformacionServidor();
	
	public LibreriaCliente(Escribano esc){
		super(esc);
	}

	protected void suma(){
		asaDest=RPC.importarInterfaz(informacionServidor.dameNombre(), informacionServidor.dameVersion());
		int resultado;
		PaqueteParametros suma = pila.pop();
		int sumando1 = Integer.parseInt(suma.dameParametro(PRIMER_PARAMETRO));
		int sumando2 = Integer.parseInt(suma.dameParametro(SEGUNDO_PARAMETRO));
		Empaquetador.empaqueta(solicitud,POSICION_CODIGO_OPERACION,SUMA);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE,sumando1);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT,sumando2);
		Nucleo.send(asaDest,solicitud);
		Pausador.pausa(1000);
		Nucleo.receive(Nucleo.dameIdProceso(),respuesta);
		//resultado solo se usa una vez pero se puso se declaro para facilitar el entendimiento del codigo
		resultado = Empaquetador.arregloAInt(Arrays.copyOfRange(respuesta,POSICION_RESPUESTA,POSICION_RESPUESTA+BYTES_INT));
		pila.push(new PaqueteParametros(Integer.toString(resultado)));
		//asaDest=RPC.importarInterfaz(nombreServidor, version)  //para práctica 4
		//Nucleo.send(asaDest,null);
		//...
	}
	
	protected void promedio(){
		asaDest=RPC.importarInterfaz(informacionServidor.dameNombre(), informacionServidor.dameVersion());
		int resultado;
		PaqueteParametros promedio = pila.pop();
		int promedio1 = Integer.parseInt(promedio.dameParametro(PRIMER_PARAMETRO));
		int promedio2 = Integer.parseInt(promedio.dameParametro(SEGUNDO_PARAMETRO));
		int promedio3 = Integer.parseInt(promedio.dameParametro(TERCER_PARAMETRO));
		int promedio4 = Integer.parseInt(promedio.dameParametro(CUARTO_PARAMETRO));
		int promedio5 = Integer.parseInt(promedio.dameParametro(QUINTO_PARAMETRO));
		Empaquetador.empaqueta(solicitud,POSICION_CODIGO_OPERACION,PROMEDIO);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE,promedio1);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT,promedio2);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT+BYTES_INT,promedio3);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT,promedio4);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT+BYTES_INT+BYTES_INT+BYTES_INT,promedio5);
		Nucleo.send(asaDest,solicitud);
		Pausador.pausa(1000);
		Nucleo.receive(Nucleo.dameIdProceso(),respuesta);
		//resultado solo se usa una vez pero se puso se declaro para facilitar el entendimiento del codigo
		resultado = Empaquetador.arregloAInt(Arrays.copyOfRange(respuesta,POSICION_RESPUESTA,POSICION_RESPUESTA+BYTES_INT));
		pila.push(new PaqueteParametros(Integer.toString(resultado)));
		//asaDest=RPC.importarInterfaz(nombreServidor, version)  //para práctica 4
		//Nucleo.send(asaDest,null);
		//...
	}
	
	protected void potencia(){
		asaDest=RPC.importarInterfaz(informacionServidor.dameNombre(), informacionServidor.dameVersion());
		int resultado;
		PaqueteParametros potencia = pila.pop();
		int potencia1 = Integer.parseInt(potencia.dameParametro(PRIMER_PARAMETRO));
		int potencia2 = Integer.parseInt(potencia.dameParametro(SEGUNDO_PARAMETRO));
		Empaquetador.empaqueta(solicitud,POSICION_CODIGO_OPERACION,POTENCIA);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE,potencia1);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT,potencia2);
		Nucleo.send(asaDest,solicitud);
		Pausador.pausa(1000);
		Nucleo.receive(Nucleo.dameIdProceso(),respuesta);
		//resultado solo se usa una vez pero se puso se declaro para facilitar el entendimiento del codigo
		resultado = Empaquetador.arregloAInt(Arrays.copyOfRange(respuesta,POSICION_RESPUESTA,POSICION_RESPUESTA+BYTES_INT));
		pila.push(new PaqueteParametros(Integer.toString(resultado)));
		//asaDest=RPC.importarInterfaz(nombreServidor, version)  //para práctica 4
		//Nucleo.send(asaDest,null);
		//...
	}
	
	protected void porcentaje(){
		asaDest=RPC.importarInterfaz(informacionServidor.dameNombre(), informacionServidor.dameVersion());
		int resultado;
		PaqueteParametros porcentaje = pila.pop();
		int porcentaje1 = Integer.parseInt(porcentaje.dameParametro(PRIMER_PARAMETRO));
		int porcentaje2 = Integer.parseInt(porcentaje.dameParametro(SEGUNDO_PARAMETRO));
		Empaquetador.empaqueta(solicitud,POSICION_CODIGO_OPERACION,PORCENTAJE);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE,porcentaje1);
		Empaquetador.empaqueta(solicitud,POSICION_MENSAJE+BYTES_INT,porcentaje2);
		Nucleo.send(asaDest,solicitud);
		Pausador.pausa(1000);
		Nucleo.receive(Nucleo.dameIdProceso(),respuesta);
		//resultado solo se usa una vez pero se puso se declaro para facilitar el entendimiento del codigo
		resultado = Empaquetador.arregloAInt(Arrays.copyOfRange(respuesta,POSICION_RESPUESTA,POSICION_RESPUESTA+BYTES_INT));
		pila.push(new PaqueteParametros(Integer.toString(resultado)));
		//asaDest=RPC.importarInterfaz(nombreServidor, version)  //para práctica 4
		//Nucleo.send(asaDest,null);
		//...
	}

}