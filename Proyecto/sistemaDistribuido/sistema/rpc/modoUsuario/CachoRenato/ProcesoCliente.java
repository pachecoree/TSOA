package sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato;
/*
 *Cacho Robledo Vega Renato 
 * práctica 3, 09/11/2014
 */
import java.util.Arrays;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{
	private Libreria lib;
	private int[] parametrosSuma;
	private double[] parametrosOrdenar;
	private int[] parametrosPotencia;
	private int parametroRaiz;
	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);
		//lib=new LibreriaServidor(esc);  //primero debe funcionar con esta para subrutina servidor local
		lib=new LibreriaCliente(esc);  //luego con esta comentando la anterior, para subrutina servidor remota
		start();
	}

	public void recibirParametros(String campo1, String campo2, String campo3, String campo4){
		
		parametrosSuma = stringAIntArreglo(campo1.split("\\s+"));
		parametrosOrdenar = stringADoubleArreglo(campo2.split("\\s+"));
		parametrosPotencia = stringAIntArreglo(campo3.split("\\s+"));
		parametroRaiz = Integer.parseInt(campo4);
	}
	
	private int[] stringAIntArreglo(String[] arreglo){
		int[] arregloInt;
		
		if(arreglo.length > 0){
			arregloInt = new int[arreglo.length];
			for(int i=0; i<arreglo.length; i++){
				arregloInt[i] = Integer.parseInt(arreglo[i]);
			}
		}else{
			arregloInt = new int[1];
		}
		
		return arregloInt;
	}
	
	private double[] stringADoubleArreglo(String[] arreglo){
		double[] arregloDouble;
		
		if(arreglo.length >= 2){
			arregloDouble = new double[arreglo.length];
			for(int i=0; i<arreglo.length; i++){
				arregloDouble[i] = Double.parseDouble((arreglo[i]));
			}
		}else{
			arregloDouble = new double[1]; 
		}
		
		return arregloDouble;
	}
	
	
	/**
	 * Programa Cliente
	 */
	public void run(){

		imprimeln("Proceso cliente en ejecucion.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Salio de suspenderProceso");
		
		imprimeln("El resultado de la Suma = "+lib.suma(parametrosSuma));
		
		imprimeln("El resultado del Ordenamiento: "+Arrays.toString(lib.ordenar(parametrosOrdenar)));
		
		imprimeln("El resultado de la Potencia: "+lib.potencia(parametrosPotencia));
		
		imprimeln("El resultado de la Raiz: "+lib.raiz(parametroRaiz));
		
		imprimeln("Fin del cliente.");
	}
}
