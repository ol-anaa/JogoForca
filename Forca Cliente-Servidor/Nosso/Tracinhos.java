public class Tracinhos implements Cloneable
{
    private char texto [];

    public Tracinhos (int qtd) throws Exception
    {
        //verifica se a quantidade de tracinhos a ser colocada é negativa
        if(qtd <= 0) 
            throw new Exception ("Valor nao encontrado!\n");
        
        //instancia o array com o tamanho qtd e percorre o vetor, colocando '_' em cada elemento
        this.texto = new char[qtd];
        for (int i=0; i<qtd; i++){ 
            this.texto[i] = '_'; 
        }
   }

    public void revele (int posicao, char letra) throws Exception
    {
        //verifica se posição se a posicao é menor que zero ou maior que o tamanho do vetor    
        if(posicao < 0 || posicao >= this.texto.length){ 
            throw new Exception("Valor não encontrado"); 
        }
        //releva a letra na posição
        this.texto[posicao] = letra; 
    }

    public boolean isAindaComTracinhos ()
    {
        //percorre o vetor para verificar se ainda tem um elemento do vetor guardando `_`
        for (int i=0; i<this.texto.length; i++){
            if(texto[i] == '_') 
                return true;
        }
        return false;
    }
        
    public String toString ()
    {
        //monta uma string dos elementos de texto, separados por ' '
        String print = "";
        if (this.texto.length > 0){
            print += this.texto[0];
            for (int i=1; i<this.texto.length; i++)
                print += " " + this.texto[i];
        }
        return print;
    }

    public boolean equals (Object obj)
    {              
        //verifica se o objeto passado de parametro não é o mesmo do chamante
        if (this == obj)
            return true;
        
        //verifica se o objeto passado de parametro nao é nulo
        if (obj == null)
            return false;

        //verifica se a classe do objeto que passamos como parâmetro é diferente da classe do objeto chamante
        if (obj.getClass() != Tracinhos.class)
            return false;

        //revela através de typecast a classe do objeto passado como parâmetro (que é a mesma do objeto chamante) e atribuimos ele a um novo objeto
        Tracinhos t = (Tracinhos)obj;

        //usa o objeto t para comparar com o objeto chamante
        //verifica se os tamanhos são diferentes
        if (this.texto.length != t.texto.length)
            return false;
        
        //percorre o array verificando se o elementos de cada posição são diferentes
        for (int i = 0; i < this.texto.length; i++){
            if(this.texto[i] != t.texto[i])
            return false;
        }    
        return true;
    }

    public int hashCode ()
    {
        //declara um int que ira retornar
        int ret = 13; 
	
        // em caso de this.texto não ser nulo, multiplicamos o ret por um primo e somamos o hascode do Array
        //if (this.texto != null) ret = 7*ret + Arrays.hashCode(texto);
        for (int i = 0; i < this.texto.length; i++){
            ret = 7*ret + new Character (this.texto[i]).hashCode();
        }
        //se o ret for negativo, é feita a transformação para positivo
	    if (ret<0) ret=-ret; 
	
	    return ret;
    }


    public Tracinhos (Tracinhos t) throws Exception // construtor de cópia
    {
        //verifica se t (o modelo para cópia) é nulo
        if (t == null)
            throw new Exception ("modelo nulo");

        //instancia a cópia com tamanho do modelo t
        this.texto = new char [t.texto.length];
       
        //percorre o array para atribuir o valor de cada elemento de t no respectivo elemento de this.texto
        for (int i=0; i<t.texto.length; i++){
        this.texto[i] = t.texto[i];
        } 
    }

    public Object clone () 
    {
        //declaramos ret para ser a cópia
        Tracinhos ret = null; 
        try {
            // Instanciamos o ret usando o construtor de cópia
            ret = new Tracinhos(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
}
