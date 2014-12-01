package sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato;
/*
 *Cacho Robledo Vega Renato 
 * Práctica 3 -Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor” -09-NOV-14
 * Práctica 4 -Llamadas a Procedimientos Remotos (RPC) “El Programa Conector” 16-nov-14
 */
import sistemaDistribuido.util.Escribano;
import java.util.*;


public abstract class Libreria{
	private Escribano esc;
	protected Stack<Object> pila;
	
	
	/**
	 * 
	 */
	public Libreria(Escribano esc){
		this.esc=esc;
		pila = new Stack<Object>();
	}

	/**
	 * 
	 */
	protected void imprime(String s){
		esc.imprime(s);
	}

	/**
	 * 
	 */
	protected void imprimeln(String s){
		esc.imprimeln(s);
	}

	/**
	 * Ejemplo para el paso intermedio de parametros en pila.
	 * Esto es lo que esta disponible como interfaz al usuario programador
	 */

	public int suma(int[] parametros){


		for(int i=0; i<parametros.length; i++){
			pila.push(new Integer(parametros[i]));
		} 
		suma();
		
		if (pila.peek() != null){
			return (Integer)pila.pop();
		}else{
			pila.clear();
			return 0;
		}
		
		
	}


	public int potencia(int[] parametros){

		for(int i=0; i<parametros.length; i++){
			pila.push(new Integer(parametros[i]));
		} 
		potencia();
		
		if (pila.peek() != null){
			return (Integer)pila.pop();
		}else{
			pila.clear();
			return 0;
		}
		
	}

	public double raiz(int parametro){

		pila.push(new Integer(parametro)); 
		raiz();
		if (pila.peek() != null){
			return (Double)pila.pop();
		}else{
			pila.clear();
			return 0.0;
		}
	}


	public double[] ordenar( double[] parametros){

		double[] listaOrdenada;

		pila.push(parametros);

		ordenar();

		try{
			listaOrdenada = (double[])pila.pop();
		}catch(Exception e){
			e.printStackTrace(System.out);
			listaOrdenada = parametros;
		}
		pila.clear();
		return listaOrdenada;
	}


	/**
	 * Servidor suma verdadera generable por un compilador estandar
	 * o resguardo de la misma por un compilador de resguardos.
	 */
	protected abstract void suma();

	protected abstract void ordenar();

	protected abstract void potencia();

	protected abstract void raiz();
}