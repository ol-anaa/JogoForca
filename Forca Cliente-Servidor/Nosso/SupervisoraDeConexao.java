import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread {
    private double valor = 0; // X
    // private Parceiro usuario;
    // private Socket conexao1;
    // private Socket conexao2;
    // private Socket conexao3;
    private ArrayList<Parceiro> trio;
    private ArrayList<ArrayList<Parceiro>> usuarios;

    public SupervisoraDeConexao(ArrayList<Parceiro> trio, ArrayList<ArrayList<Parceiro>> usuarios) // conexao --> trio
            throws Exception {
        if (trio == null) // conexao --> trio
            throw new Exception("Trio ausente");

        if (usuarios == null)
            throw new Exception("Usuarios ausentes");

        this.trio = trio;
        this.usuarios = usuarios;

        // this.conexao = conexao;
        // this.usuarios = usuarios;

        // talvez
        // for (i++) --ok
        // trio.get(i)
        // conexao1 --> i = 0 -- ok
        // conexao2 --> i = 1 --
        // conexao3 --> i = 2

        // (trio.get(1)).conexao = this.conexao1;

        /*
         * for (int l =0; l < trio.size(); l++ ){ if (l == 0) this.conexao1.add;
         * 
         * }
         */
    }

    public void run() {

        try {
            // adicionamos usuario/trio em aceitadora de conexao tambem
            synchronized (this.usuarios) {
                this.usuarios.add(this.trio); // conexao --> trio
            }

            // pedido de palavra --> enviar algo para o cliente
            Palavra palavra = null;
            Tracinhos tracinhos = null;
            ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = null;
            ControladorDeErros controladorDeErros = null;

            palavra = BancoDePalavras.getPalavraSorteada();

            try {
                tracinhos = new Tracinhos(palavra.getTamanho());
            } catch (Exception erro) {
            }

            controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();

            try {
                controladorDeErros = new ControladorDeErros((int) (palavra.getTamanho() * 0.6));
            } catch (Exception erro) {
            }

            for (;;) // FORCA
            {
                // comunicado 1, 2 e 3
                Comunicado comunicado1 = this.trio.get(1).envie();
                Comunicado comunicado2 = this.trio.get(2).envie();
                Comunicado comunicado3 = this.trio.get(3).envie();
                Comunicado comunicado = null;
                int i;
                int j;
                

                // SELECAO DA PALAVRA
                this.trio.get(1).receba(new Jogo(tracinhos, controladorDeLetrasJaDigitadas, controladorDeErros));
                this.trio.get(2).receba(new Jogo(tracinhos, controladorDeLetrasJaDigitadas, controladorDeErros));
                this.trio.get(3).receba(new Jogo(tracinhos, controladorDeLetrasJaDigitadas, controladorDeErros));

                // comunicado (1, 2 e 3) --> usuario.receba(Jogo)
                // New PedidoDePalavra palavra = (PedidoDePalavra)usuario.envie()
                // PedidoDePalavra palavra = new (PedidoDePalavra)usuario.envie()

                if (comunicado1 == null && comunicado2 == null && comunicado3 == null) // precisa verificar 1, 2 e 3
                    return;

                // ESTABELECENDO QUAL VAI SER O JOGADOR DA VEZ
                for (i = 0; i < this.trio.size(); i++){
                    j = i;

                // INDICANDO PARA O COMUNICADO QUEM FOI ESCOLHIDO
                switch (j) {
                case 0:
                    comunicado = comunicado1; // comunicadoDaVez
                    break;
                case 1:
                    comunicado = comunicado2;
                    break;
                case 2:
                    comunicado = comunicado3;
                    break;
                }

                // AVISAR QUEM FOI O ESCOLHIDO --> isso seria de quem é a vez? ou não?
                /* int vez = i */
                switch (j) {
                case 0:
                    this.trio.get(1).receba(new SuaVez());
                    this.trio.get(2).receba(new VezDoOutro());
                    this.trio.get(3).receba(new VezDoOutro());
                    // comunicado1 --> sua vez
                    // comunicado2 e comunicado3 --> nao sua vez
                    break;
                case 1:
                    this.trio.get(1).receba(new VezDoOutro());
                    this.trio.get(2).receba(new SuaVez());
                    this.trio.get(3).receba(new VezDoOutro());
                    // comunicado2 --> sua vez
                    // comunicado1 e comunicado3 --> nao sua vez
                    break;
                case 2:
                    this.trio.get(1).receba(new VezDoOutro());
                    this.trio.get(2).receba(new VezDoOutro());
                    // comunicado3 --> sua vez
                    // comunicado1 e comunicado2 --> nao sua vez
                    break;
                }

                // INICIO FORCA
                // operacoes 3 tipos de pedido --> PedidoDeOpecacao, PedidoDeResultado e
                // PedidoPraSair
                // forca: PedidoDeChar (programado precisa alteracoes / classe forca),
                // PedidoDePalavra (nao tem programado)
                // PedidoPraSair (programado pelo professor / checar)

                // REVER
                while (tracinhos.isAindaComTracinhos() && !controladorDeErros.isAtingidoMaximoDeErros()) { // se acertar
                                                                                                           // a palavra
                                                                                                           // precisa
                                                                                                           // sair desse
                                                                                                           // loop

                    // verificar se funciona (esperando uma resposta)
                    if (comunicado == null)
                        return;

                    // INICIO DA FORCA //INTERACAO DO CLIENTE
                    else if (comunicado instanceof PedidoDeChar) {
                        Character letra;
                        PedidoDeChar adivinha = (PedidoDeChar) comunicado;
                        letra = adivinha.getLetra();// pedidoDeChar para o usuario

                        try {
                            if (controladorDeLetrasJaDigitadas.isJaDigitada(letra))
                                this.trio.get(j).receba(new letraJaDigitada());
                            // comunicado de letraJaDigitada
                            else {
                                controladorDeLetrasJaDigitadas.registre(letra);

                                int qtd = palavra.getQuantidade(letra);

                                if (qtd == 0) {
                                    this.trio.get(j).receba(new naoTemLetra());
                                    // comunicado de palavra nao tem essa letra
                                    controladorDeErros.registreUmErro();
                                } else {
                                    for (int k = 0; k < qtd; k++) {
                                        int posicao = palavra.getPosicaoDaIezimaOcorrencia(k, letra);
                                        tracinhos.revele(posicao, letra);
                                    }
                                }
                            }
                        } catch (Exception erro) {
                            // comunicado de erro
                            System.err.println(erro.getMessage());
                        }

                        // comunicado de jogo
                        this.trio.get(1)
                                .receba(new Jogo(tracinhos, controladorDeLetrasJaDigitadas, controladorDeErros));
                        this.trio.get(2)
                                .receba(new Jogo(tracinhos, controladorDeLetrasJaDigitadas, controladorDeErros));
                        this.trio.get(3)
                                .receba(new Jogo(tracinhos, controladorDeLetrasJaDigitadas, controladorDeErros));

                        if (i == (this.trio.size() - 1))
                        i = 0;
                    }

                    else if (comunicado instanceof PedidoDeString) {
                        // metodo para receber a palavra digitada
                        // comunicado --> palavraDigitada (parametro passado para o comunicado)
                        String chute;
                        PedidoDeString adivinha = (PedidoDeString) comunicado;
                        chute = adivinha.getPalavra();// pedidoDeString para o usuario

                        if (palavra.equals(chute)) {

                            // AVISAR QUEM GANHOU E QUEM PERDEU
                            switch (j) {
                            case 0:
                                this.trio.get(1).receba(new Ganhou());
                                this.trio.get(2).receba(new Perdeu());
                                this.trio.get(3).receba(new Perdeu());
                                // comunicado1 --> comunicadoDeVencedor(); //venceu :)
                                // comunicaddo 2 e comunicado 3 --> comunicadoDePerdedor(); //perdeu por chute:(
                                break;
                            case 1:
                                this.trio.get(1).receba(new Perdeu());
                                this.trio.get(2).receba(new Ganhou());
                                this.trio.get(3).receba(new Perdeu());
                                // comunicado2 --> comunicadoDeVencedor()//venceu :)
                                // comunicado1 e comunicado3 --> comunicadoDePerdedor(); //perdeu perdeu por
                                // chute:(
                                break;
                            case 2:
                                this.trio.get(1).receba(new Perdeu());
                                this.trio.get(2).receba(new Perdeu());
                                this.trio.get(3).receba(new Ganhou());
                                // comunicado3 --> comunicadoDeVencedor() //venceu :)
                                // comunicado1 e comunicado2 --> comunicadoDePerdedor(); //perdeu perdeu por
                                // chute:(
                                break;
                            }
                            // desligando as pessoas
                            for (int l = 0; l < trio.size(); l++)
                                trio.get(l).adeus();

                        } else {
                            // avisar que a pessoa errou
                            switch (j) {
                            case 0:
                                this.trio.get(1).receba(new Perdeu());
                                this.trio.get(2).receba(new RecadoDePerda());
                                this.trio.get(3).receba(new RecadoDePerda());
                                break;
                            case 1:
                                this.trio.get(1).receba(new RecadoDePerda());
                                this.trio.get(2).receba(new Perdeu());
                                this.trio.get(3).receba(new RecadoDePerda());
                                break;
                            case 2:
                                this.trio.get(1).receba(new RecadoDePerda());
                                this.trio.get(2).receba(new RecadoDePerda());
                                this.trio.get(3).receba(new Perdeu());
                                break;
                            }
                            // expulsar jogador da vez
                            trio.get(j).adeus();

                            if (i == (this.trio.size() - 1))
                            i = 0;
                        }

                    }
                    
                    // else if (comunicado instanceof PedidoParaSair)
                    // {
                    // synchronized (this.usuarios)
                    // {
                    // this.usuarios.remove (this.usuario);
                    // }
                    // this.usuario.adeus();
                    // }
                }

                if (controladorDeErros.isAtingidoMaximoDeErros()) {
                    // todos perderam
                    // chamar o comunicadoDeDerrota
                    System.out.println("Que pena! Voce perdeu! A palavra era " + palavra + "\n");
                    // manda o mesmo comunicado para todos os jogadores
                    // desligando as pessoas
                    for (int l = 0; l < trio.size(); l++)
                        trio.get(l).adeus();

                } else
                    switch (j) {
                    case 0:
                        this.trio.get(1).receba(new Ganhou());
                        this.trio.get(2).receba(new Perdeu());
                        this.trio.get(3).receba(new Perdeu());
                        break;

                    case 1:
                        this.trio.get(1).receba(new Perdeu());
                        this.trio.get(2).receba(new Ganhou());
                        this.trio.get(3).receba(new Perdeu());
                        break;

                    case 2:
                        this.trio.get(1).receba(new Perdeu());
                        this.trio.get(2).receba(new Perdeu());
                        this.trio.get(3).receba(new Ganhou());
                        break;
                    }
                
                System.out.println("Parabens! Voce ganhou! A palavra era mesmo " + palavra + "\n");

                // aqui o jogo acaba //porque alguem acertou tudo ou acabou os erros
            }
            
        }
        }
        // ver dps como arrumar --> se deu no jogo erro cai aqui.Deveria excluir o trio
        catch (Exception erro) {
        /* try {
                 // excluir o trio
                 transmissor.close();
                receptor.close();
             } catch (Exception falha) {
             } // so tentando fechar antes de acabar a thread

             return;*/
        }
    }
}
