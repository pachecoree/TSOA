/**
 * Romero Pacheco Carlos Mauricio
 * Práctica 5
 */
package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import static sistemaDistribuido.util.Constantes.*;
import sistemaDistribuido.util.OrdenamientoBytes;

public class MensajesRespuesta {
	
	
	public static byte[] elaborateResponse(int source,int response) {
		byte[] auPackage = new byte[BYTES_SOURCE+BYTES_PRO];
		OrdenamientoBytes.breakNumber(source, auPackage, BYTES_SOURCE, POS_SOURCE);
		OrdenamientoBytes.breakNumber(response, auPackage, BYTES_PRO,POS_PRO);
		return auPackage;
	}
}
