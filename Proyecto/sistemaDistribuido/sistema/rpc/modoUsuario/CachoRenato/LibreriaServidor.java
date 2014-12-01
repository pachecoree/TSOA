package sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato;
/*
 *Cacho Robledo Vega Renato 
 *Práctica 3 -Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor” -09-NOV-14
 */
import sistemaDistribuido.sistema.rpc.modoUsuario.CachoRenato.Libreria;
import sistemaDistribuido.util.Escribano;

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
	@Override
	protected void suma(){
		super.imprimeln("Realiza el servicio Suma");
		int parametro = 0;
		int result = 0;
		int cantidadParametros = super.pila.size();
		
		while(cantidadParametros >0){
				parametro = (Integer)super.pila.pop();
				result = result + parametro;
				cantidadParametros--;		
		}

		super.pila.push(Integer.valueOf(result));
		
		}

	@Override
	protected void ordenar() {
		super.imprimeln("Realiza el servicio Ordenar");
		double[] parametros = (double[])(super.pila.pop());
		ordenaShell(parametros);
		super.pila.push(parametros);

	}
	
	
	
	private void ordenaShell(double[] arreglo){
		int intervalo;
		int j;
		int k;
		
		intervalo = arreglo.length / 2;
		
		while(intervalo > 0){
			for(int i = intervalo; i < arreglo.length; i++){
				j = i - intervalo;
				while(j >= 0){
					k = j + intervalo;
					if(Double.compare(arreglo[j], arreglo[k]) <= 0)
						j = -1;
					else{
						double aux;
						aux = arreglo[j];
						arreglo[j] = arreglo[k];
						arreglo[k] = aux;
						j -= intervalo;
					}
				}
			}
			intervalo = intervalo / 2;
		}
	}

	@Override
	protected void potencia() {
		super.imprimeln("Realiza el servicio Potencia");
		int exponente = (Integer)super.pila.pop();
		int base = (Integer)super.pila.pop();
		int resultado = (int) Math.pow(base,exponente);
		super.pila.push(Integer.valueOf(resultado));
		
	}

	@Override
	protected void raiz() {
		super.imprimeln("Realiza el servicio Raiz");
		int radicando = (Integer) pila.pop();
		Double resultado = (double) Math.sqrt(radicando);
		pila.push(resultado);	
		
		
	}
	



}
