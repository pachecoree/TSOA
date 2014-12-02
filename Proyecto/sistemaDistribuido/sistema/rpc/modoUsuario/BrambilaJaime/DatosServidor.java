//Brambila López Jaime Arturo
//Práctica #4: Llamadas a Procedimientos Remotos (RPC) “El Programa Conector”

package sistemaDistribuido.sistema.rpc.modoUsuario.BrambilaJaime;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParIpId;

public class DatosServidor {
	private String version;
	private String nombre;
	private ParIpId ASA;
		public DatosServidor() {
			// TODO Auto-generated constructor stub
		}
		
		public void ingresaVersion(String v){
			version = v;
		}
		
		public void ingresaNombre(String n){
			nombre = n;
		}
		
		public String dameVersion(){
			return version;
		}
		
		public String dameNombre(){
			return nombre;
		}

		public void ingresaASA(ParIpId asa) {
			// TODO Auto-generated method stub
			ASA = asa;
		}

		public ParIpId dameAsa() {
			// TODO Auto-generated method stub
			return ASA;
		}
}
