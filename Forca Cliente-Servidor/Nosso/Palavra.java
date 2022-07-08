public class Palavra implements Comparable<Palavra>
{
    private String texto;

    public Palavra (String texto) throws Exception
    {
        //verifica se texto é nulo ou "", lançando exceção se for 
        if (texto == "" || texto == null)
            throw new Exception ("Valor Inválido");        

        //atributo texto passado como parâmetro para this.texto
        this.texto = texto; 
    }

    public int getQuantidade (char letra)
    {
        //declara uma variavel qtd para contar a quantidade de vezes que aparece letra
        int qtd = 0;

        //percorre o vetor procurando se em cada determinada posição acha o char letra. Caso ache soma +1 a qtd
        for (int i = 0; i < this.texto.length(); i++) {
            if (this.texto.charAt(i) == letra) {
                qtd++;
            }
        }
        return qtd;
    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {
        //declara um contador, inicando com valor 0, para contar a isezima ocorrencia (ex: contacor = 1, primeira ocorrencia)
        int contador = 0;
        
        //verifica e i é negativo
        if (i < 0)
            throw new Exception("Valor negativo para ocorência");

        //percorremos o contador procurando pela letra, toda vez que acha a letra, soma +1 ao contador
        for (int j = 0; j < this.texto.length(); j++){
            if(this.texto.charAt(j) == letra){  
                contador++;
                //retorna a posição que corresponder a izesima ocorrencia da letra (pois i=0 corresponde a primeira ocorrencia, então i+1)
                if(contador == (i + 1))
                    return j;
                }
        }
        throw new Exception("Não encontrou essa ocorrencia da letra");
    }

    public int getTamanho ()
    {
        return this.texto.length();
    }

    public String toString ()
    {
        return this.texto;
    }

    public boolean equals (Object obj)
    {
        //verifica se o objeto passado de parametro é o mesmo do chamante
        if (this == obj)
            return true;

        //verifica se o objeto passado de parametro nao é nulo
        if (obj == null)
            return false;
        
        //verifica se a classe do objeto que passamos como parâmetro é diferente da classe do objeto chamante
        if (obj.getClass() != Palavra.class)
            return false;
        //revela através de typecast a classe do objeto passado como parâmetro (que é a mesma do objeto chamante) e atribuimos ele a um novo objeto
        Palavra p = (Palavra)obj;
    
        //verifica se this.texto é igual ao texto do parâmetro
        if (this.texto != p.texto)   
            return false; 

        return true; 
    }

    public int hashCode ()
    {
        //declaramos um inteiro ret para retornar
        int ret = 13;
	
        // em caso de this.texto não ser nulo, multiplicamos o ret por um primo e somamos o hascode do texto
        if (this.texto != null) ret = 7*ret + texto.hashCode();
	    
        //se o ret for negativo, é feita a transformação para positivo
	    if (ret<0) ret=-ret;
	
	    return ret;
    }

    public int compareTo (Palavra palavra)
    {
        //verifica se o tamanho do texto do chamante é maior que o tamanho do texto do parâmetro, retornando um valor positivo se for
        if (this.texto.length() > palavra.texto.length())
            return 1;

        //verifica se o tamanho do texto do chamante é menor que o tamanho do texto do parâmetro, retornando um valor negativo se for
        if (this.texto.length() < palavra.texto.length())
            return -1;
        
        //caso não for nem maior, nem menor, então é igual o tamanho, retornando 0;
        return 0;
    }
}
