package appsispedidos.db.entidades;

import appsispedidos.db.dal.ClienteDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.util.Banco;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    static public class ItemProduto
    {
        private int id;
        private Produto produto;
        private int qtde;
        private double preco;

        public ItemProduto() {
            this(0,new Produto(),0,0);
        }

        public ItemProduto(Produto produto, int qtde, double preco) {
            this(0,produto,qtde,preco);
        }

        public ItemProduto(int id, Produto produto, int qtde, double preco) {
            this.id = id;
            this.produto = produto;
            this.qtde = qtde;
            this.preco = preco;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        public int getQtde() {
            return qtde;
        }

        public void setQtde(int qtde) {
            this.qtde = qtde;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }
    }
    
    private int id;
    private Cliente cliente;
    private LocalDate data;
    private double frete;
    private double total;
    private List <ItemProduto> itens;

    public Pedido() {
        this(0,new Cliente(), LocalDate.now(),0,0);
    }

    public Pedido(Cliente cliente, LocalDate data, double frete, double total) {
        this(0,cliente,data,frete,total);
    }

    public Pedido(int id, Cliente cliente, LocalDate data, double frete, double total) {
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.frete = frete;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public boolean add(Produto p, int qtde, double preco)
    {
        return itens.add(new ItemProduto(p,qtde,preco));
    }
    
    public boolean add(ItemProduto prod)
    {
        return itens.add(prod);
    }
    
    public List<ItemProduto> getItens()
    {
        List <ItemProduto> itp=new ArrayList();
        String sql="select * from itens_pedidos where pro_id = "+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
           while(rs.next())
             itp.add(new ItemProduto(new ProdutoDAL().get(rs.getInt("pro_id")),
                             rs.getInt("itp_quant"),
                             rs.getDouble("itp_preco")));
        }catch(Exception e){}
        return itp;
    }
}
