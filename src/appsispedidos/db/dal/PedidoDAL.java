package appsispedidos.db.dal;

import java.sql.ResultSet;
import appsispedidos.db.entidades.Pedido;
import appsispedidos.db.entidades.Produto;
import appsispedidos.db.entidades.Pedido.ItemProduto;
import appsispedidos.db.util.Banco;
import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;

public class PedidoDAL {
    public boolean gravar(Pedido p)
    {
        boolean flag=false;
        String sql="insert into pedidos values (default,'#1',#2,#3,#4)";
        sql=sql.replace("#1",p.getData().toString());
        sql=sql.replace("#2",""+p.getFrete());
        sql=sql.replace("#3",""+p.getTotal());
        sql=sql.replace("#4",""+p.getCliente().getId());
        
        if(Banco.getCon().manipular(sql))
        {
            int pedido=Banco.getCon().getMaxPK("pedidos", "ped_id");
            for (Pedido.ItemProduto ip : p.getItens())
            {
                sql="insert into itens_pedidos values (default,#1,#2,#3,#4)";
                sql=sql.replace("#1",""+ip.getQtde());
                sql=sql.replace("#2",""+ip.getPreco());
                sql=sql.replace("#3",""+ip.getProduto().getId());
                sql=sql.replace("#4", ""+pedido);
                Banco.getCon().manipular(sql);                
            }
            flag=true;
        }
        return flag;        
    }
    public boolean alterar(Pedido p)
    {
        // update na tabela pedido
        String sql="update pedidos set ped_data='#1',ped_frete=#2,ped_total=#3,cli_id=#4 where ped_id = "+p.getId();
        sql=sql.replace("#1",""+p.getData().toString());
        sql=sql.replace("#2",""+p.getFrete());
        sql=sql.replace("#3",""+p.getTotal());
        sql=sql.replace("#4",""+p.getCliente().getId());
        // apagar todos os itens do pedido
        if (Banco.getCon().manipular(sql))
        {
             sql="delete from itens_pedido where ped_id="+p.getId();
            // gravar novamente todos os itens
            Banco.getCon().manipular(sql);
        
            //int pedido=Banco.getCon().getMaxPK("pedidos", "ped_id");
            for (Pedido.ItemProduto ip : p.getItens())
            {
                sql="insert into itens_pedidos values (default,#1,#2,#3,#4)";
                sql=sql.replace("#1",""+ip.getQtde());
                sql=sql.replace("#2",""+ip.getPreco());
                sql=sql.replace("#3",""+ip.getProduto().getId());
                sql=sql.replace("#4", ""+p.getId());
                Banco.getCon().manipular(sql);                
            }
            return true;
        }
        return false;
    }
    
    public boolean apagar(int id)
    {
        String sql="delete from itens_pedido where ped_id="+id;
        Banco.getCon().manipular(sql);
        sql="delete from pedidos where ped_id="+id;
        if(Banco.getCon().manipular(sql))
            return true;
        return false;
    }
    public Pedido get(int id)
    {   Pedido aux=null;
        String sql="select * from pedidos where ped_id="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           if(rs.next())
             aux=new Pedido(rs.getInt("ped_id"),new ClienteDAL().get(rs.getInt("cli_id")),
                             rs.getDate("ped_data").toLocalDate(),rs.getDouble("ped_frete"),
                             rs.getDouble("ped_total"));
           sql="select * from itens_pedido where ped_id="+id;
           while(rs.next())
               aux.add(new ProdutoDAL().get(rs.getInt("pro_id")), rs.getInt("itp_quant"),
                       rs.getDouble("itp_preco"));
        }catch(Exception e){}
        return aux;
    }
    
    public List<Pedido> get(String filtro)
    {   List <Pedido> pedidos=new ArrayList();
        String sql="select * from pedidos";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           while(rs.next())
             pedidos.add(new Pedido(rs.getInt("ped_id"),
                             new ClienteDAL().get(rs.getInt("cli_id")),
                             rs.getDate("ped_data").toLocalDate(),
                             rs.getDouble("ped_frete"),
                             rs.getDouble("ped_total")));
        }catch(Exception e){}
        return pedidos;
    }
    
    /*public List<ItemProduto> getItp(String filtro)
    {   List <ItemProduto> itp=new ArrayList();
        String sql="select * from itens_pedidos";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           while(rs.next())
              itp.add(new ItemProduto(new ProdutoDAL().get(rs.getInt("pro_id")), rs.getInt("itp_quant"),
                       rs.getDouble("itp_preco")));
        }catch(Exception e){}
        return itp;
    }*/
}