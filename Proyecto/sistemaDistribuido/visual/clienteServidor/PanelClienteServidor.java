/**
 * Romero Pacheco Carlos Mauricio
 * Proyecto
 */

package sistemaDistribuido.visual.clienteServidor;

import java.awt.Panel;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.Choice;

public class PanelClienteServidor extends Panel{
  private static final long serialVersionUID=1;
  private Button botonCliente,botonServidor;
  private Choice choiceIndividual;

  public PanelClienteServidor(){
     botonCliente=new Button("Cliente");
     botonServidor=new Button("Servidor");
     choiceIndividual = new Choice();
     choiceIndividual.add("Cacho");
     choiceIndividual.add("Romero");
     add(choiceIndividual);
     add(botonCliente);
     add(botonServidor);
  }
  
  public Button dameBotonCliente(){
    return botonCliente;
  }
  
  public int getSelectedChoice() {
	  return choiceIndividual.getSelectedIndex();
  }
  
  public Button dameBotonServidor(){
    return botonServidor;
  }
  
  public void agregarActionListener(ActionListener al){
    botonCliente.addActionListener(al);
    botonServidor.addActionListener(al);
  }
}