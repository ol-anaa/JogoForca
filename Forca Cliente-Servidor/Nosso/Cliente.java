import java.net.*;
import java.io.*;

public class Cliente 
{
	public static final String HOST_PADRAO = "localhost";
	public static final int PORTA_PADRAO = 3000;

	public static void main (String[] args)
	{
        if (args.length>2)
        {
            System.err.println ("Uso esperado: java Cliente [HOST [PORTA]]\n");
            return;
        }

        Socket conexao=null;
        try
        {
            String host = Cliente.HOST_PADRAO;
            int    porta= Cliente.PORTA_PADRAO;

            if (args.length>0)
                host = args[0];

            if (args.length==2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket (host, porta);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor=null;
        try
        {
            transmissor =
            new ObjectOutputStream(
            conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor =
            new ObjectInputStream(
            conexao.getInputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor=null;
        try
        {
            servidor =
            new Parceiro (conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try
        {
			tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento (servidor);
		}
		catch (Exception erro)
		{} // sei que servidor foi instanciado
		
        tratadoraDeComunicadoDeDesligamento.start();

        //FORCA
		//declarar e instanciar
		Byte opcao;
		//pedidoDeJogo
		Jogo inicioJogo;
		Comunicado comunicado = null;
		try {
			do
				comunicado = (Comunicado)servidor.espie();
			while (!(comunicado instanceof Jogo));

			inicioJogo = (Jogo)servidor.envie();
			System.out.println(inicioJogo);
		} 
		catch (Exception erro) 
		{}
		
		do
     	{
			do
			{
				try {
					comunicado = (Comunicado)servidor.espie ();
				} 
				catch (Exception erro) 
				{}
			}
			while(!(comunicado instanceof SuaVez) || !(comunicado instanceof VezDoOutro) || !(comunicado instanceof fimDoJogo));

			//acho que podemos comentar 115-120
			/*try
			{
				SuaVez suaVez = (SuaVez)servidor.envie();
				VezDoOutro vezDoOutro = (VezDoOutro)servidor.envie();
			}
			catch (Exception erro){}
			*/
			
			if (comunicado instanceof SuaVez){
				System.out.println("É a sua vez");

				//perguntar se quer digitar uma legtra ou uma palavra
				System.out.print ("Deseja chutar uma letra? [1]\n Deseja chutar a palavra? [2] \n");

				try
				{
					opcao = (Teclado.getUmByte());
				}
				catch (Exception erro)
				{
					System.err.println ("Opcao invalida! Digite somente os números já especificados.\n");
					continue;
				}

				if (opcao != 1 || opcao != 2)
				{
					System.err.println ("Opcao invalida!\n");
					continue;
				}

				try
				{
					char letra;
					if (opcao == 1)
					{
						System.out.print ("Digite uma letra: ");
						try
						{
							letra = Teclado.getUmChar();
							System.out.println();
							
						}
						catch (Exception erro)
						{
							System.err.println ("Letra invalida!\n");
							continue;
						}


						servidor.receba (new PedidoDeChar(letra));

						comunicado = null;
						do
						{
							comunicado = (Comunicado)servidor.espie ();
						}
						//enquanto o comunicado nao for o comunicado esperado
						//comunicados esperados 
							//Jogo, letraJaDigitada, naoTemLetra
						while (!(comunicado instanceof Jogo) || !(comunicado instanceof letraJaDigitada) || !(comunicado instanceof naoTemLetra));
						
						if (comunicado instanceof letraJaDigitada)
							System.out.println("Essa letra já foi digitada");

						if (comunicado instanceof naoTemLetra)
							System.out.println("A palavra não tem essa letra");
						
						
						Jogo continuacaoJogo = (Jogo)servidor.envie();
						System.out.println(continuacaoJogo);
					}
					
					else if (opcao == 2)
					{
						String palavra;
						System.out.print ("Digite uma palavra: ");
						try
						{
							palavra = Teclado.getUmString();
							System.out.println();
							
						}
						catch (Exception erro)
						{
							System.err.println ("Palavra invalida!\n");
							continue;
						}
						//pedido de string está sem parametro, por isso está dando erro.
						servidor.receba (new PedidoDeString(palavra));

						comunicado = null;
						do
						{
							comunicado = (Comunicado)servidor.espie ();
						}
						//enquanto o comunicado nao for o comunicado esperado
						//comunicados esperados 
							//Ganhou e Perdeu
						while (!(comunicado instanceof Ganhou) || !(comunicado instanceof Perdeu));
						
						if (comunicado instanceof Ganhou)
							System.out.println("Você acertou a palavra! Parabéns\nVocê ganhou!");

						else
							System.out.println("Você errou a palavra!\nAgora você sera retirado do jogo");
						}
				}
				catch (Exception erro)
				{
					System.err.println ("Erro de comunicacao com o servidor;");
					System.err.println ("Tente novamente!");
					System.err.println ("Caso o erro persista, termine o programa");
					System.err.println ("e volte a tentar mais tarde!\n");
				}
			}
			
			else{
				System.out.println("Não é sua vez");
				comunicado = null;
					try{
						do
						{
							comunicado = (Comunicado)servidor.espie();
						}
						while (!(comunicado instanceof Perdeu) || !(comunicado instanceof RecadoDePerda) || !(comunicado instanceof SuaVez));
						
						if (comunicado instanceof Perdeu)
							System.out.println("Voce perdeu :(");
						
						if (comunicado instanceof RecadoDePerda)
							System.out.println("O outro jogador errou a palavra e foi retirado da sala");
						
						if (comunicado instanceof Jogo);
					}
					catch (Exception erro){}
				//espiar um comunicado caso a pessoa que chutou a palavra ganhe
				//espiar um comunicado caso a pessoa que chutou a letra ganhe
				//espiar um comunicado que é a sua vez
			}
		}
		while(!(comunicado instanceof fimDoJogo));

	// try
	// 	{
	// 		servidor.receba (new PedidoParaSair ());
	// 	}
	// 	catch (Exception erro)
	// 	{}
		
		System.out.println ("Obrigado por usar este programa!");
		System.exit(0);
	}
}