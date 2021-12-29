package appsispedidos.db.entidades;

public class Produto 
{
    private int id;
    private String nome;
    private double preco;
    private int estoque;
    private Categoria categoria;

    public Produto() {
        this(0,"",0,0,new Categoria());
    }

    public Produto(String nome, double preco, int estoque,Categoria categoria) {
        this(0,nome,preco,estoque,categoria);
    }

    public Produto(int id, String nome, double preco, int estoque,Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
    
    
}


