/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 5
 */
package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.util.OrdenamientoBytes;

public class MensajesRespuesta {
	
	
	public static byte[] elaborateResponse(int source,int response) {
		byte[] Package = new byte[BYTES_SOURCE+BYTES_PRO];
		OrdenamientoBytes.breakNumber(source, Package, BYTES_SOURCE, POS_SOURCE);
		OrdenamientoBytes.breakNumber(response, Package, BYTES_PRO,POS_PRO);
		return Package;
	}
	
	public static byte[] elaborateResponseLSA(int source,int dest) {
		byte[] Package = new byte[BYTES_SOURCE+BYTES_SERVER+BYTES_PRO];
		OrdenamientoBytes.breakNumber(source, Package, BYTES_SOURCE, POS_SOURCE);
		OrdenamientoBytes.breakNumber(dest, Package, BYTES_SERVER, POS_SERVER);
		OrdenamientoBytes.breakNumber(PRO_LSA, Package, BYTES_PRO,POS_PRO);
		return Package;
	}
	
	public static byte[] elaborateResponseLSAAU(int source,int dest) {
		byte[] Package = new byte[BYTES_SOURCE+BYTES_SERVER+BYTES_PRO];
		OrdenamientoBytes.breakNumber(source, Package, BYTES_SOURCE, POS_SOURCE);
		OrdenamientoBytes.breakNumber(dest, Package, BYTES_SERVER, POS_SERVER);
		OrdenamientoBytes.breakNumber(PRO_AU, Package, BYTES_PRO,POS_PRO);
		return Package;
	}
}
