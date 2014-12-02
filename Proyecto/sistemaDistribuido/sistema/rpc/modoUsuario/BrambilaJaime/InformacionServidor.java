//Brambila López Jaime Arturo
//Práctica #4: Llamadas a Procedimientos Remotos (RPC) “El Programa Conector”

package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;

public class InformacionServidor {
	private final String NOMBRE = "FileServer";
	private final String VERSION = "3.1";

	public String dameNombre(){
		return NOMBRE;
	}

	public String dameVersion(){
		return VERSION;
	}
}
