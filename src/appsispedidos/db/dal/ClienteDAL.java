package appsispedidos.db.dal;

import appsispedidos.db.entidades.Categoria;
import appsispedidos.db.entidades.Cliente;
import appsispedidos.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAL {
    public boolean gravar(Cliente c)
    {
        String sql="insert into clientes values (default,'#1','#2','#3','#4','#5','#6','#7','#8')";
        sql=sql.replace("#1",c.getDocumento());
        sql=sql.replace("#2",c.getNome());
        sql=sql.replace("#3",c.getEndereco());
        sql=sql.replace("#4",c.getBairro());
        sql=sql.replace("#5",c.getCidade());
        sql=sql.replace("#6",c.getCep());
        sql=sql.replace("#7",c.getUf());
        sql=sql.replace("#8",c.getEmail());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Cliente c)
    {
        String sql="update clientes set cli_documento='#1',cli_nome='#2',cli_endereco='#3',cli_bairro='#4',cli_cidade='#5',cli_cep='#6',cli_uf='#7',cli_email='#8' where cli_id="+c.getId();
        sql=sql.replace("#1",c.getDocumento());
        sql=sql.replace("#2",c.getNome());
        sql=sql.replace("#3",c.getEndereco());
        sql=sql.replace("#4",c.getBairro());
        sql=sql.replace("#5",c.getCidade());
        sql=sql.replace("#6",c.getCep());
        sql=sql.replace("#7",c.getUf());
        sql=sql.replace("#8",c.getEmail());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id)
    {
        String sql="delete from clientes where cli_id="+id;
        return Banco.getCon().manipular(sql);
    }
    public Cliente get(int id)
    {   Cliente aux=null;
        String sql="select * from clientes where cli_id="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           if(rs.next())
             aux=new Cliente(rs.getInt("cli_id"),
                             rs.getString("cli_documento"),
                             rs.getString("cli_nome"),
                             rs.getString("cli_endereco"),
                             rs.getString("cli_bairro"),
                             rs.getString("cli_cidade"),
                             rs.getString("cli_cep"),
                             rs.getString("cli_uf"),
                             rs.getString("cli_email"));
        }catch(Exception e){}
        return aux;
    }
    public List<Cliente> get(String filtro)
    {   List <Cliente> clientes=new ArrayList();
        String sql="select * from clientes";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           while(rs.next())
             clientes.add(new Cliente(rs.getInt("cli_id"),
                             rs.getString("cli_documento"),
                             rs.getString("cli_nome"),
                             rs.getString("cli_endereco"),
                             rs.getString("cli_bairro"),
                             rs.getString("cli_cidade"),
                             rs.getString("cli_cep"),
                             rs.getString("cli_uf"),
                             rs.getString("cli_email")));
        }catch(Exception e){}
        return clientes;
    }
    
}
