/*
 * Romero Pacheco Carlos Mauricio
 * Práctica 3
 */
package sistemaDistribuido.sistema.rpc.modoUsuario.RomeroCarlos;

import sistemaDistribuido.util.Escribano;
import static sistemaDistribuido.util.Constantes.*;

public class LibreriaServidor extends Libreria{

	/**
	 * 
	 */
	public LibreriaServidor(Escribano esc){
		super(esc);
	}

	/**
	 * Ejemplo de servidor suma verdadera
	 */
	
	protected void bubbleSort() {
		imprimeln("Realizando servicio");
		int arraySize = stack.pop();
		int[] array = new int[arraySize];
		int temp;
		for (int i=0; i<arraySize; i++) {
			array[i] = stack.pop();
		}
        for(int i=0; i < arraySize-C_1; i++) {
 
            for(int j=1; j < arraySize-i; j++) {
                if(array[j-1] > array[j]){
                    temp=array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
            }
        }
		for (int i=arraySize-1; i>=C_0; i--) {
			stack.push(array[i]);
		}
		stack.push(arraySize);
	}
	
	protected void fibonacci() {
		imprimeln("Realizando servicio");
		int number = stack.pop();
		int fiboNumber=0;
		int prev1, prev2; 
		prev1 = prev2 = 1; 
		if ((number == C_0) || (number == C_1)) { 
			fiboNumber = 1; 
		}
		else {
			for (int i=2; i<=number; i++) {
				fiboNumber = prev1 + prev2; 
				prev2 = prev1; 
				prev1 = fiboNumber; 
			}
		}
		stack.push(fiboNumber);
	}
	
	protected void primeNumber() {
		imprimeln("Realizando servicio");
		int number = stack.pop();
		int isPrime = 1;
		for (int i=2; i<number; i++) {
			if (number%2 == C_0) {
				isPrime = 0;
				break;
			}
		}
		stack.push(isPrime);
	}
	
	protected void factorial() {
		imprimeln("Realizando servicio");
		int number = stack.pop();
		int factorial = 1;
		for (int i=2; i<=number; i++) {
			factorial *= i;
		}
		stack.push(factorial);
		return;
	}

}