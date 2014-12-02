package sistemaDistribuido.util;

public class PaqueteParametros {
	private String parametro1;
	private String parametro2;
	private String parametro3;
	private String parametro4;
	private String parametro5;
	
	public PaqueteParametros(String parametro1){
		this.parametro1 = parametro1;
	}
	
	public PaqueteParametros(String parametro1,String parametro2){
		this.parametro1 = parametro1;
		this.parametro2 = parametro2;
	}
	
	public PaqueteParametros(String parametro1,String parametro2,String parametro3){
		this.parametro1 = parametro1;
		this.parametro2 = parametro2;
		this.parametro3 = parametro3;
	}
	
	public PaqueteParametros(String parametro1,String parametro2,String parametro3,String parametro4){
		this.parametro1 = parametro1;
		this.parametro2 = parametro2;
		this.parametro3 = parametro3;
		this.parametro4 = parametro4;
	}
	
	public PaqueteParametros(String parametro1,String parametro2,String parametro3,String parametro4,String parametro5){
		this.parametro1 = parametro1;
		this.parametro2 = parametro2;
		this.parametro3 = parametro3;
		this.parametro4 = parametro4;
		this.parametro5 = parametro5;
	}
	
	public String dameParametro(int indice){
		switch(indice){
			case 1: return this.parametro1;
			case 2: return this.parametro2;
			case 3: return this.parametro3;
			case 4: return this.parametro4;
			case 5: return this.parametro5;
			default: return "Error";
		}
		
	}
}
