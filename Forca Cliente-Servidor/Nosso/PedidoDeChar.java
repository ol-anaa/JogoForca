public class PedidoDeChar extends Comunicado
{
    private char letra;

    public PedidoDeChar (char letra)
    {
        this.letra = letra;
    }

    public char getLetra()
    {
        return this.letra;
    }

    public String toString()
    {
        return (""+this.letra);

    }
}    
    

