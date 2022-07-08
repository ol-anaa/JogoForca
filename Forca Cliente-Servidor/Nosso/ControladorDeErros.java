 // Parece tudo okay
 
 public class ControladorDeErros implements Cloneable
{
    private int qtdMax, qtdErr=0;

    public ControladorDeErros (int qtdMax) throws Exception
    {
        //verifica se quantidade máxima de erros é não positiva
        if (qtdMax <= 0) 
            throw new Exception("Valor invalido"); 

        //atribui qtdMax passada como parâmetro à this.qtdMax
        this.qtdMax = qtdMax;
    }

    public void registreUmErro () throws Exception
    {
        //verifica se a quantidade máxima de erros já foi atingida
        if (this.qtdErr == this.qtdMax){ 
            throw new Exception("Número de Erros máximo atingido");
        }
        //caso não, adiciona +1 a qtdErr
        else
            this.qtdErr ++; 
    } 

    public boolean isAtingidoMaximoDeErros  ()
    {
        //verifica se a quantidade de erros máxima já foi atingida
        if (this.qtdErr == this.qtdMax) 
            return true; 
        return false;
    }

    public String toString ()
    {
        return this.qtdErr + "/" + this.qtdMax;
    }

    public boolean equals (Object obj)
    {
 
        //verifica se o objeto que passamos como parâmetro não é o mesmo objeto chamante
        if (this == obj) 
            return true; 
        
        // verifica se o objeto que passamos como parâmetro é nulo
        if (obj == null) 
            return false; 

        //verifica se a classe do objeto que passamos como parâmetro é diferente da classe do objeto chamante
        if (obj.getClass() != ControladorDeErros.class) 
            return false;

        //revelamos através de typecast a classe do objeto passado como parâmetro (que é a mesma do objeto chamante) e atribuimos ele a um novo objeto
        ControladorDeErros c = (ControladorDeErros)obj;
        
        //veririca se a quantidade máxima de erros do objeto chamante é diferente do da quantidade máxima de erros do objeto passado como parâmetro
        if (this.qtdMax != c.qtdMax) 
            return false; 

        // verifica se a quantidade de erros do objeto chamante é diferente da quantidade de erros do objeto passado como parâmetro
        if (this.qtdErr != c.qtdErr) 
            return false;

        return true;
    }

    public int hashCode ()
    {
        //declaramos um int, que iremos retornar
        int ret = 13; 
	
        //multiplicamos o ret por um numero primo e somamos o hashCode do objeto que declaramos para converter a variavel em um objeto da classe Integer
        ret = 7 * ret + new Integer (this.qtdMax).hashCode(); 
        ret = 7 * ret + new Integer (this.qtdErr).hashCode(); 
	    
        //em caso do ret ser negativo, é feita a transformação para positivo 
        if (ret<0) ret=-ret; 
	
	    return ret;
    }

    public ControladorDeErros (ControladorDeErros c) throws Exception // construtor de c�pia
    {
        //verifica se c (que é o modelo para fazer a cópia) for nulo
        if (c == null) 
            throw new Exception("c ausente"); 

        // atribui o valores dos atributos do modelo (c) para os atributos do objeto chamante (a cópia)
        this.qtdMax = c.qtdMax; 
        this.qtdErr = c.qtdErr;
    }

    public Object clone ()
    {
        // declaramos ret para ser a cópia
        ControladorDeErros ret = null;
        try{
            // Instanciamos o ret usando o construtor de cópia
            ret = new ControladorDeErros(this); 
        }
        catch (Exception erro)
        {}

        return ret;
    }
}
