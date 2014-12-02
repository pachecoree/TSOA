package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;
//Brambila López Jaime Arturo
//Práctica #3: Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor”
import java.util.Stack;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.PaqueteParametros;
import static sistemaDistribuido.util.Constantes.*;

public abstract class Libreria{
	private Escribano esc;
	protected Stack<PaqueteParametros> pila = new Stack<PaqueteParametros>();
	private PaqueteParametros resultado;
	
	public Libreria(Escribano esc){
		this.esc=esc;
	}

	protected void imprime(String s){
		esc.imprime(s);
	}

	protected void imprimeln(String s){
		esc.imprimeln(s);
	}

	public int suma(int suma1,int suma2){
		PaqueteParametros suma = new PaqueteParametros(Integer.toString(suma1),Integer.toString(suma2));
		pila.push(suma);
		suma();
		resultado = pila.pop();
		return Integer.parseInt(resultado.dameParametro(PRIMER_PARAMETRO));
	}
	
	public int promedio(int promedio1,int promedio2,int promedio3,int promedio4,int promedio5){
		PaqueteParametros promedio = new PaqueteParametros(Integer.toString(promedio1),Integer.toString(promedio2),Integer.toString(promedio3),Integer.toString(promedio4),Integer.toString(promedio5));
		pila.push(promedio);
		promedio();
		resultado = pila.pop();
		return Integer.parseInt(resultado.dameParametro(PRIMER_PARAMETRO));
	}
	
	public int potencia(int potencia1,int potencia2){
		PaqueteParametros potencia = new PaqueteParametros(Integer.toString(potencia1),Integer.toString(potencia2));
		pila.push(potencia);
		potencia();
		resultado = pila.pop();
		return Integer.parseInt(resultado.dameParametro(PRIMER_PARAMETRO));
	}
	
	public int porcentaje(int porcentaje1,int porcentaje2){
		PaqueteParametros porcentaje = new PaqueteParametros(Integer.toString(porcentaje1),Integer.toString(porcentaje2));
		pila.push(porcentaje);
		porcentaje();
		resultado = pila.pop();
		return Integer.parseInt(resultado.dameParametro(PRIMER_PARAMETRO));
	}

	protected abstract void suma();
	protected abstract void promedio();
	protected abstract void potencia();
	protected abstract void porcentaje();
}