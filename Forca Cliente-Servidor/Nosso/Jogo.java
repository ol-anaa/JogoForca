public class Jogo extends Comunicado {
    private Tracinhos tracinhos;
	private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
	private ControladorDeErros controladorDeErros;
        
	public Jogo (Tracinhos tracinhos, ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas, ControladorDeErros controladorDeErros){
		this.tracinhos = tracinhos;
		this.controladorDeLetrasJaDigitadas = controladorDeLetrasJaDigitadas;
		this.controladorDeErros = controladorDeErros;
	}
		
	public String toString(){  
		return 
		"Palavra...: "+tracinhos+"\n"+
		"Digitadas.: "+controladorDeLetrasJaDigitadas+"\n"+
		"Erros.....: "+controladorDeErros;
	}  
}
