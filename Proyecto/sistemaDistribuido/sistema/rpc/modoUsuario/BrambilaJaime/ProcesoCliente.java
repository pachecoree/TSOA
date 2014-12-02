//Brambila López Jaime Arturo
//Práctica #3: Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor”

package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{
	private Libreria lib;
	private int suma1;
	private int suma2;
	private int promedio1;
	private int promedio2;
	private int promedio3;
	private int promedio4;
	private int promedio5;
	private int potencia1;
	private int potencia2;
	private int porcentaje1;
	private int porcentaje2;
	
	public ProcesoCliente(Escribano esc){
		super(esc);
		//lib=new LibreriaServidor(esc);  //primero debe funcionar con esta para subrutina servidor local
		lib=new LibreriaCliente(esc);  //luego con esta comentando la anterior, para subrutina servidor remota
		start();
	}
	
	public void run(){
		int resultado;

		imprimeln("Proceso cliente en ejecucion.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Salio de suspenderProceso");
		
		resultado=lib.suma(suma1,suma2);
		imprimeln("Resultado de suma: "+resultado);
		resultado=lib.promedio(promedio1,promedio2,promedio3,promedio4,promedio5);
		imprimeln("Resultado de promedio: "+resultado);
		resultado=lib.potencia(potencia1,potencia2);
		imprimeln("Resultado de potencia: "+resultado);
		resultado=lib.porcentaje(porcentaje1,porcentaje2);
		imprimeln("Resultado de porcentaje: "+resultado);

		imprimeln("Fin del cliente.");
	}

	public void fijarParametros(String suma1,String suma2,
			String promedio1,String promedio2,String promedio3,String promedio4,String promedio5,
			String potencia1,String potencia2,String porcentaje1,String porcentaje2) {
		this.suma1 = Integer.parseInt(suma1);
		this.suma2 = Integer.parseInt(suma2);
		this.promedio1 = Integer.parseInt(promedio1);
		this.promedio2 = Integer.parseInt(promedio2);
		this.promedio3 = Integer.parseInt(promedio3);
		this.promedio4 = Integer.parseInt(promedio4);
		this.promedio5 = Integer.parseInt(promedio5);
		this.potencia1 = Integer.parseInt(potencia1);
		this.potencia2 = Integer.parseInt(potencia2);
		this.porcentaje1 = Integer.parseInt(porcentaje1);
		this.porcentaje2 = Integer.parseInt(porcentaje2);
	}
}
