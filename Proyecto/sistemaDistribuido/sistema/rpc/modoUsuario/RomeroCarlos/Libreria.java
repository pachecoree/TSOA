/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 3
 */
package sistemaDistribuido.sistema.rpc.modoUsuario.RomeroCarlos;

import sistemaDistribuido.util.Escribano;
import static sistemaDistribuido.util.Constantes.*;
import java.util.Stack;

public abstract class Libreria{
	private Escribano esc;
	protected Stack<Integer> stack;

	/**
	 * 
	 */
	public Libreria(Escribano esc){
		this.esc=esc;
		stack = new Stack<Integer>();
	}

	protected void imprime(String s){
		esc.imprime(s);
	}

	protected void imprimeln(String s){
		esc.imprimeln(s);
	}

	public void bubbleSort(int arraySize,int[] array) {
		for (int i=arraySize-1; i>= C_0; i--) {
			stack.push(array[i]);
		}
		stack.push(arraySize);
		bubbleSort();
		arraySize = stack.pop();
		for (int i=0; i<arraySize; i++) {
			array[i] = stack.pop();
		}
	}
	
	public int fibonacci(int number) {
		stack.push(number);
		fibonacci();
		return stack.pop();
	}
	
	public boolean primeNumber(int number) {
		stack.push(number);
		primeNumber();
		return stack.pop() == C_1 ? true:false;
	}
	
	public int factorial(int number) {
		stack.push(number);
		factorial();
		return stack.pop();
	}
	
	protected abstract void fibonacci();
	protected abstract void bubbleSort();
	protected abstract void primeNumber();
	protected abstract void factorial();
}