package appsispedidos.db.dal;

import appsispedidos.db.entidades.Categoria;
import appsispedidos.db.entidades.Produto;
import appsispedidos.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL {
     public Produto get(int id)
    {   Produto aux=null;
        String sql="select * from produtoss where pro_id="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           if(rs.next())
             aux=new Produto(rs.getInt("pro_id"),
                             rs.getString("pro_nome"),
                             rs.getDouble("pro_desc"),
                             rs.getInt("pro_estoque"),
                             new CategoriaDAL().get(rs.getInt("cat_id")));
        }catch(Exception e){}
        return aux;
    }
     
    public List<Produto> get(String filtro)
    {   List <Produto> produtos=new ArrayList();
        String sql="select * from produtos";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           while(rs.next())
             produtos.add(new Produto(rs.getInt("pro_id"),
                             rs.getString("pro_nome"),
                             rs.getDouble("pro_preco"),
                             rs.getInt("pro_estoque"),
                             new CategoriaDAL().get(rs.getInt("cat_id"))));
        }catch(Exception e){}
        return produtos;
    }
    
    public boolean gravar(Produto p)
    {
        String sql="insert into produtos values (default,'#1','#2','#3','#4')";
        sql=sql.replace("#1",p.getNome());
        sql=sql.replace("#2",""+p.getPreco());
        sql=sql.replace("#3",""+p.getEstoque());
        sql=sql.replace("#4",""+p.getCategoria().getId());
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Produto p)
    {
        String sql="update produtos set pro_nome='#1',pro_preco=#2,pro_estoque=#3,cat_id=#4 where pro_id="+p.getId();
        sql=sql.replace("#1",p.getNome());
        sql=sql.replace("#2",""+p.getPreco());
        sql=sql.replace("#3",""+p.getEstoque());
        sql=sql.replace("#4",""+p.getCategoria().getId());
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(int id)
    {
        String sql="delete from produtos where pro_id="+id;
        return Banco.getCon().manipular(sql);
    }
}
