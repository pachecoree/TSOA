/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 4
 */
package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{
	private Libreria lib;
	private String param1,param2,param3,param4;
	private String param5,param6,param7,param8;
	private final int ARRAY_SIZE = 5;

	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);
		//lib=new LibreriaServidor(esc);  //primero debe funcionar con esta para subrutina servidor local
		lib=new LibreriaCliente(esc);  //luego con esta comentando la anterior, para subrutina servidor remota
		start();
	}
	
	public void setParam1(String param) {
		param1 = param;
	}
	public void setParam2(String param) {
		param2 = param;
	}
	public void setParam3(String param) {
		param3 = param;
	}
	public void setParam4(String param) {
		param4 = param;
	}
	public void setParam5(String param) {
		param5 = param;
	}
	public void setParam6(String param) {
		param6 = param;
	}
	public void setParam7(String param) {
		param7 = param;
	}
	public void setParam8(String param) {
		param8 = param;
	}
	

	/**
	 * Programa Cliente
	 */
   private int[] createArray(String param1,String param2,String param3,String param4,String param5)
	{
		int[] array = new int[ARRAY_SIZE];
		int i=0;
		try {
			array[i++] = Integer.parseInt(param1);
			array[i++] = Integer.parseInt(param2);
			array[i++] = Integer.parseInt(param3);
			array[i++] = Integer.parseInt(param4);
			array[i++] = Integer.parseInt(param5);
		} catch(NumberFormatException e) {
			System.out.println("Error en el parametro : "+ e.getMessage());
		}
		return array;
	}
	
	private int stringToInt(String stringNumber) {
		int number;
		try {
			number = Integer.parseInt(stringNumber);
		}catch(NumberFormatException e) {
			System.out.println("Error en el parametro : "+ e.getMessage());
			number = 0;
		}
		return number;
	}
	
	private String arrayToString(int[] array,int arraySize) {
		StringBuffer str = new StringBuffer();
		for (int i=0;i<arraySize;i++) {
			str.append(array[i]+" | ");
		}
		return str.toString();
	}
	
	public void run(){
		int[] array;
		int fiboNumber;
		int factorialNumber;
		int primeNumber;
		int result;
		boolean isPrime;
		imprimeln("Proceso cliente en ejecución.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Salió de suspenderProceso");
		array = createArray(param1,param2,param3,param4,param5);
		fiboNumber = stringToInt(param6);
		factorialNumber = stringToInt(param7);
		primeNumber = stringToInt(param8);
		imprimeln("Arreglo Original : " +arrayToString(array,ARRAY_SIZE));
		imprimeln("Llamando a Burbuja");
		lib.bubbleSort(ARRAY_SIZE, array);
		if (array[C_0] == ASA_NOT_FOUND) {
			imprimeln("No se realizo la operación");
		}
		else {
			imprimeln("Arreglo Ordenado: " +arrayToString(array,ARRAY_SIZE));
		}
		imprimeln("Llamando a Fibonacci");
		result=lib.fibonacci(fiboNumber);
		if (result == ASA_NOT_FOUND) {
			imprimeln("No se realizo la operación");
		}
		else {
			imprimeln("Numero "+fiboNumber+" de la serie de Fibonacci : "+result);
		}
		imprimeln("Llamando a Factorial");
		result = lib.factorial(factorialNumber);
		if (result == ASA_NOT_FOUND) {
			imprimeln("No se realizo la operación");
		}
		else {
			imprimeln("Factorial de "+factorialNumber +" : " + result);
		}
		imprimeln("Llamando a Prime");
		isPrime = lib.primeNumber(primeNumber);
		if (result == ASA_NOT_FOUND) {
			imprimeln("No se realizo la operación");
		}
		else {
		imprimeln("Numero primo "+primeNumber + "? : " +isPrime);
		}
		imprimeln("Fin del cliente.");
	}
	
}
