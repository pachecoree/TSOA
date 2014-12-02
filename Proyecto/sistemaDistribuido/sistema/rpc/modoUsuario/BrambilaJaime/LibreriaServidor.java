package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;

//Brambila López Jaime Arturo
//Práctica #3: Llamadas a Procedimientos Remotos (RPC) “Resguardos del Cliente y del Servidor”
//import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.PaqueteParametros;
import static sistemaDistribuido.util.Constantes.*;


public class LibreriaServidor extends Libreria{

	public LibreriaServidor(Escribano esc){
		super(esc);
	}

	protected void suma(){
		PaqueteParametros suma = pila.pop();
		int sumando1 = Integer.parseInt(suma.dameParametro(PRIMER_PARAMETRO));
		int sumando2 = Integer.parseInt(suma.dameParametro(SEGUNDO_PARAMETRO));
		String resultado = Integer.toString(sumando1+sumando2);
		pila.push(new PaqueteParametros(resultado));
	}
	
	protected void promedio(){
		PaqueteParametros promedio = pila.pop();
		int promedio1 = Integer.parseInt(promedio.dameParametro(PRIMER_PARAMETRO));
		int promedio2 = Integer.parseInt(promedio.dameParametro(SEGUNDO_PARAMETRO));
		int promedio3 = Integer.parseInt(promedio.dameParametro(TERCER_PARAMETRO));
		int promedio4 = Integer.parseInt(promedio.dameParametro(CUARTO_PARAMETRO));
		int promedio5 = Integer.parseInt(promedio.dameParametro(QUINTO_PARAMETRO));
		String resultado = Integer.toString((promedio1+promedio2+promedio3+promedio4+promedio5)/ELEMENTOS_PROMEDIO);
		pila.push(new PaqueteParametros(resultado));
	}
	
	protected void potencia(){
		PaqueteParametros potencia = pila.pop();
		int potencia1 = Integer.parseInt(potencia.dameParametro(PRIMER_PARAMETRO));
		int potencia2 = Integer.parseInt(potencia.dameParametro(SEGUNDO_PARAMETRO));
		String resultado = Integer.toString((int)Math.pow(potencia1,potencia2));
		pila.push(new PaqueteParametros(resultado));
	}
	
	protected void porcentaje(){
		PaqueteParametros porcentaje = pila.pop();
		int porcentaje1 = Integer.parseInt(porcentaje.dameParametro(PRIMER_PARAMETRO));
		int porcentaje2 = Integer.parseInt(porcentaje.dameParametro(SEGUNDO_PARAMETRO));
		String resultado = Integer.toString((porcentaje1*porcentaje2)/PORCIENTO);
		pila.push(new PaqueteParametros(resultado));
	}
}