package sistemaDistribuido.sistema.empaquetador;
/*
 *Cacho Robledo Vega Renato 
 * práctica 3 -Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor” -09-NOV-14
 */
import static sistemaDistribuido.util.Constantes.*;


public class Empaquetador {
	
	public static int desempaquetarInt(byte[] contenedor, int offset){
		int numero = 0;
		int aux;
		int limite = offset +3;
		
		while(offset <= limite){
			aux = (int)((contenedor[offset++])&(0x00FF));
			numero = (numero << 8);
			numero = (numero|aux);
		}
		return numero;
	}
	
	public static short desempaquetarShort(byte[] contenedor, int offset){
		short numero = 0;
		
		short aux;		
		int limite = offset + 1;
		
		while(offset <= limite){
			aux = (short)(contenedor[offset++]&0x00FF);
			numero = (short)(numero << 8);
			numero = (short)(numero|aux);
		}
				
		return numero;
	}
	
	public static void empaquetarShort(byte[] contenedor, short numero, int offset){
		
		contenedor[offset] = (byte) (numero >>>8);
		contenedor[offset +1] = (byte) numero;
		
	}
	
	public static void empaquetarInt(byte[] contenedor, int numero, int offset){
		int limite = offset + 3;

		for(int i = limite;i >= offset; i-- ){
			contenedor[i] = (byte)(numero&0x00FF);
			numero = (int)(numero >>> 8);
			
		}
		
	}
	
	public static int empaquetarDouble(byte[] contenedor, Double numero, int[] offset){
		
		String stringNumeroDouble = Double.toString(numero);
		int stringNumeroLenght = stringNumeroDouble.length();
		
		byte[] stringBytes = stringNumeroDouble.getBytes();
		
		empaquetarInt(contenedor,stringNumeroLenght,offset[0]);
		
		offset[0]+=4;
		
		for(int i = offset[0], j = 0; j < stringNumeroLenght; i++,j++){
			contenedor[i] = stringBytes[j];
		}
		offset[0] += stringNumeroLenght+1;
		
		return stringNumeroLenght;
	}
	
	public static Double desempaquetarDouble(byte[] contenedor, int[] offset){
		
		int stringNumeroLenght = desempaquetarInt(contenedor, offset[0]);
		
		offset[0]+=4;
		
		byte[] stringBytes = new byte[stringNumeroLenght];
		
		for(int i = offset[0],j = 0; j < stringNumeroLenght; i++,j++){
			
			stringBytes[j] = contenedor[i];
		}
		
		offset[0] += stringNumeroLenght+1;
		String stringNumeroDouble = new String(stringBytes);
	
		return Double.parseDouble(stringNumeroDouble);
	}
	
	
	public static void empaqueta(byte[] arreglo,int posicion,short valor){
		ordena(arreglo,posicion,BYTES_SHORT,valor);
	}
	
	public static void empaqueta(byte[] arreglo,int posicion,int valor){
		ordena(arreglo,posicion,BYTES_INT,valor);
	}
	
	public static void empaqueta(byte[] arreglo,int posicion,long valor){
		ordena(arreglo,posicion,BYTES_LONG,valor);
	}
	
	public static void ordena(byte[] arreglo,int posicionInicio,int bytes,long valor){
		int posicionFinal = posicionInicio + bytes;
		int corrimiento = bytes;
		for(int i=posicionInicio;i<posicionFinal;i++){
			corrimiento--;
			arreglo[i]= (byte)((valor >> BITS_BYTE * corrimiento) & 0xff);
		}
	}
	
	public static int arregloAInt(byte[] arreglo) 
	{
	    int valor = 0;
	    int corrimiento = BYTES_INT;
	    for (int i=0;i<BYTES_INT;i++) {
	    	corrimiento--;
	        valor += (arreglo[i] & 0x000000FF) << corrimiento * BITS_BYTE;
	    }
	    return valor;
	}
	
	public static short arregloAShort(byte[] arreglo) 
	{
	    short valor = 0;
	    int corrimiento = BYTES_SHORT;
	    for (int i=0;i<BYTES_SHORT;i++) {
	    	corrimiento--;
	        valor += (arreglo[i] & 0x00FF) << corrimiento * BITS_BYTE;
	    }
	    return valor;
	}
	
	
	
}
