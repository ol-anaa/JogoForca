public class PedidoDeString extends Comunicado{
    
    private String palavra;

    public PedidoDeString (String palavra)
    {
        this.palavra = palavra;
    }

    public String getPalavra()
    {
        return this.palavra;
    }

    public String toString()
    {
        return (this.palavra);

    }
}   
