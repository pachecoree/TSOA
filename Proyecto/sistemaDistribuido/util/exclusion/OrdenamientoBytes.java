/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 7
 */


package sistemaDistribuido.util.exclusion;
import static sistemaDistribuido.util.exclusion.Constantes.*;

public class OrdenamientoBytes {
	
	public static void breakNumber(long number,byte[] array,int bytesNumber,int position) {
		for (int i=0; i<bytesNumber; i++) {
			array[position++] = (byte) (number >> (BITS_BYTE*((bytesNumber-C_1)-i)));
		}
	}
	
	public static int buildNumber(int bytesNumber,byte[] array,int position) {
		int number = 0;
		for (int i=0; i<bytesNumber; i++) {
			number |=((array[position++]& 0xFF)) << (BITS_BYTE*((bytesNumber-C_1)-i));
		}
		return number;
	}
}
