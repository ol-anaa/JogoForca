public class ControladorDeLetrasJaDigitadas implements Cloneable
{
    private String letrasJaDigitadas;
    
    public ControladorDeLetrasJaDigitadas ()
    {
        // Atribui uma string vazia
        this.letrasJaDigitadas = ""; 
    }

    public boolean isJaDigitada (char letra)
    {
        //percorre o String verificando se o char em cada posição da String corresponde à letra do parâmetro
        for(int i =0; i < this.letrasJaDigitadas.length(); i++){ 
            if (this.letrasJaDigitadas.charAt(i) == letra)
                return true;
        }
        return false;
    }

    public void registre (char letra) throws Exception
    {
        //verifica se a letra já foi digitada
        if ( this.isJaDigitada (letra) ==  true )
           throw new Exception ("Letra já digitada");
        
        //adiciona a letra à String de letrasJaDigitas
        this.letrasJaDigitadas+= letra;
    }

    public String toString ()
    {
        //declara uma string para retornar
        String ret = "";

        //caso o tamanho de letras já digitadas for maior que 0, adicionamos à ret cada catacter de letrasJaDigitadas, separadas por vírgula.
        if (this.letrasJaDigitadas.length()>0){
            ret += this.letrasJaDigitadas.charAt(0);
            for (int i=1;i<this.letrasJaDigitadas.length();i++)
                ret += ", " + this.letrasJaDigitadas.charAt(i);
        }
		return ret;

    }

    public boolean equals (Object obj)
    {
        //verifica se o objeto que passamos como parâmetro não é o mesmo objeto chamante
        if (this == obj)
            return true;
        
        //verifica se o objeto que passamos como parâmetro é nulo
        if (obj == null)
            return false;

        //verifica se a classe do objeto que passamos como parâmetro é diferente da classe do objeto chamante
        if (obj.getClass() != ControladorDeLetrasJaDigitadas.class)
            return false;

        //revela através de typecast a classe do objeto passado como parâmetro (que é a mesma do objeto chamante) e atribuimos ele a um novo objeto
        ControladorDeLetrasJaDigitadas c = (ControladorDeLetrasJaDigitadas)obj;

        //verifica se o tamanho de letrasJaDigitadas é igual à letrasJaDigitadas do objeto passado como parâmetro
        if (this.letrasJaDigitadas.length() != c.letrasJaDigitadas.length())
            return false;
        
        //verifica se cada letra na String do objeto chamante esta presente na String do objeto passado como parametro
        for (int i = 0; i<this.letrasJaDigitadas.length(); i++){
            if (!c.isJaDigitada(this.letrasJaDigitadas.charAt(i)))
                return false;
        }
    
        return true; 
    }

    public int hashCode ()
    {   
        //declara um inteiro ret e atribui a ele um valor positivo
        int ret = 33;
        // em caso de this.letrasJaDigitadas não ser nulo, multiplicamos o ret por um primo e somamos o hascode do Array
        if (this.letrasJaDigitadas != null) ret = 13 * ret + letrasJaDigitadas.hashCode();
    
        if (ret < 0) ret=-ret;

        return ret;
    }

    public ControladorDeLetrasJaDigitadas(
    ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas)
    throws Exception // construtor de c�pia
    {
        //verifica se controladorDeLetrasJáDigitadas esta vazio
        if( controladorDeLetrasJaDigitadas == null)
           throw new Exception ("Modelo nulo");

        //atribui a String de letrasJaDigitadas do modelo para letrasJaDigitadas dessa instância
        this.letrasJaDigitadas = controladorDeLetrasJaDigitadas.letrasJaDigitadas;  
    }

    public Object clone () 
    {
        // declaramos ret para ser a cópia
       ControladorDeLetrasJaDigitadas ret = null;
       try{
            // Instanciamos o ret usando o construtor de cópia
            ret = new ControladorDeLetrasJaDigitadas(this);
       }
       catch (Exception erro) 
       {} 

       return ret;  
    }
}
