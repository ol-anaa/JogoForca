import java.net.*;
import java.util.*;
import java.io.*;
import java.util.Collections;

public class AceitadoraDeConexao extends Thread 
{
    private ServerSocket        pedido;
    private ArrayList<ArrayList<Parceiro>> usuarios;
    private ArrayList<Parceiro> trio = new ArrayList<Parceiro>();

    public AceitadoraDeConexao
    (String porta, ArrayList<ArrayList<Parceiro>> usuarios)
    throws Exception
    {
        if (porta==null)
            throw new Exception ("Porta ausente");

        try
        {
            this.pedido =
            new ServerSocket (Integer.parseInt(porta));
        }
        catch (Exception  erro)
        {
            throw new Exception ("Porta invalida");
        }

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.usuarios = usuarios;
    }

    public void run ()
    {
        
        for(;;)
        {
            Socket conexao=null;
            try
            {
                conexao = this.pedido.accept();
            }
            catch (Exception erro)
            {
                continue;
            }

            //logica de 3 usuarios
            SupervisoraDeConexao supervisoraDeConexao=null;
            try
            {
                incluir(conexao);
                if (trio.size() == 3){
                    supervisoraDeConexao =
                    new SupervisoraDeConexao (trio, usuarios); //conexao --> trio
                    supervisoraDeConexao.start();

                    //fazer um clone de trio e adicionar esse array no array de vetores
                    //adicionamos usuario/trio em supervisora de conexao tambem
                    usuarios.add((ArrayList<Parceiro>)trio.clone());
                    
                    //zerar o trio --> trio = new ArrayList<Parceiro>();
                    trio = new ArrayList<Parceiro>();
                }
                

            }
            catch (Exception erro)
            {} 
            supervisoraDeConexao.start();
        }
    }

    public void incluir(Socket conexao)
    {
        //if conexao != null --> conexao ausente
        Parceiro usuario = null;
        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }
        
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            conexao.getInputStream());
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread
            
            return;
        }

        try
        {
            usuario =
            new Parceiro (conexao,
                          receptor,
                          transmissor);
        }
        catch(Exception erro)
        {}

        if (usuario!=null)
            trio.add(usuario);
        
        //incluir esse usuario dentro do trio
    }
}
