public class BancoDePalavras
{
    private static String[] palavras = 
    {
		"PROTAGONISTA",
		"PARCIALIDADE",
		"ESTABILIDADE",
		"DEPRECIATIVO",
		"EXTROVERTIDO",
		"PROCASTINAR",
		"CONTABILIDADE",
		"INTROVERTIDO",
		"OTORRINOLARINGOLOGISTA",
		"PRERROGATIVA",
		"DETERMINAÇÃO",
		"COMPLEXIDADE",
        "PNEUMOULTRAMICROSCOPICOSSILICOVULCANOCONIÓTICO"
    };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
            new Palavra (BancoDePalavras.palavras[
            (int)(Math.random() * BancoDePalavras.palavras.length)]);

        }
        catch (Exception e)
        {} // sei que não vai dar erro

        return palavra;
    }
}
