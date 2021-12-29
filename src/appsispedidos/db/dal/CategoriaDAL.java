package appsispedidos.db.dal;

import appsispedidos.db.entidades.Categoria;
import appsispedidos.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAL {
    public boolean gravar(Categoria c)
    {
        String sql="insert into categorias values (default,'#1','#2')";
        sql=sql.replace("#1",c.getNome());
        sql=sql.replace("#2",c.getDescricao());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Categoria c)
    {
        String sql="update categorias set cat_nome='#1',cat_desc='#2' where cat_id="+c.getId();
        sql=sql.replace("#1",c.getNome());
        sql=sql.replace("#2",c.getDescricao());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id)
    {
        String sql="delete from categorias where cat_id="+id;
        return Banco.getCon().manipular(sql);
    }
    public Categoria get(int id)
    {   Categoria aux=null;
        String sql="select * from categorias where cat_id="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           if(rs.next())
             aux=new Categoria(rs.getInt("cat_id"),
                             rs.getString("cat_nome"),
                             rs.getString("cat_desc"));
        }catch(Exception e){}
        return aux;
    }
    public List<Categoria> get(String filtro)
    {   List <Categoria> categorias=new ArrayList();
        String sql="select * from categorias";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           while(rs.next())
             categorias.add(new Categoria(rs.getInt("cat_id"),
                             rs.getString("cat_nome"),
                             rs.getString("cat_desc")));
        }catch(Exception e){}
        return categorias;
    }
}
