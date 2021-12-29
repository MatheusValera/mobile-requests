package appsispedidos.db.util;

public class Banco 
{
    private static Conexao con=null;

    private Banco() {
    }    
    
    public static boolean conectarBanco()
    {
        con=new Conexao();
        return con.conectar("jdbc:postgresql://localhost:5433/", "pedidos", "postgres", "postgres123");
    }
    public static Conexao getCon()
    {
        return con;
    }    
}
