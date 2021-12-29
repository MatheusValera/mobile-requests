
package appsispedidos;

import static appsispedidos.TelaCadProdutoController.produto;
import appsispedidos.db.dal.PedidoDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Cliente;
import appsispedidos.db.entidades.Pedido;
import appsispedidos.db.entidades.Pedido.ItemProduto;
import appsispedidos.db.entidades.Produto;
import appsispedidos.db.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class TelaGerPedidoController implements Initializable {

    @FXML
    private TextField txfiltro;
    @FXML
    private Button btfechar;
    @FXML
    private TableColumn<Pedido, Integer> colid;
    @FXML
    private TableColumn<Pedido, LocalDate> coldata;
    @FXML
    private TableColumn<Pedido, Double> colfrete;
    @FXML
    private TableColumn<Pedido, Double> coltotal;
    @FXML
    private TableColumn<Pedido, Cliente> colcliente;
    @FXML
    public TableView<Pedido> tabela;
    static Pedido pedido = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldata.setCellValueFactory(new PropertyValueFactory<>("data"));
        colfrete.setCellValueFactory(new PropertyValueFactory<>("frete"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colcliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        carregarTabela("");
    }    

    private void carregarTabela(String filtro)
    {
        PedidoDAL dal = new PedidoDAL();
        List <Pedido> pedidos = dal.get(filtro);
        tabela.setItems(FXCollections.observableArrayList(pedidos));
    }
    
    @FXML
    private void evtFiltrar(ActionEvent event) {
        /*if(txfiltro.getText() = "")
            carregarTabela("");
        else*/
            carregarTabela("ped_id ="+txfiltro.getText());
    }

    @FXML
    private void evtCadastrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoPedido.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregarTabela("");
    }

    @FXML
    private void evtFechar(ActionEvent event) {
        Stage stage = (Stage) btfechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        pedido = tabela.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoPedido.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        produto = null;
        carregarTabela("");
        
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja excluir o pedido?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {
            Pedido p = tabela.getSelectionModel().getSelectedItem();
            new PedidoDAL().apagar(p.getId());
            carregarTabela("");
        }
    }

    @FXML
    private void evtFinalizaPedido(ActionEvent event) {
        Pedido p = tabela.getSelectionModel().getSelectedItem();
        List<ItemProduto> auxitp = new ArrayList<ItemProduto>();
        auxitp = p.getItens();
        for (int i=1; i<auxitp.size();i++)
        {
            Produto pro = new ProdutoDAL().get(auxitp.get(i).getProduto().getId());
            int est = pro.getEstoque()-auxitp.get(i).getQtde();
            String sql="update produtos set pro_nome='#1',pro_preco=#2,pro_estoque=#3,cat_id=#4 where pro_id="+pro.getId();
            sql=sql.replace("#1",pro.getNome());
            sql=sql.replace("#2",""+pro.getPreco());
            sql=sql.replace("#3",""+est);
            sql=sql.replace("#4",""+pro.getCategoria().getId());
            Banco.getCon().manipular(sql);
        }
          
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja fechar o pedido?");
        if(alert.showAndWait().get()==ButtonType.OK)
            new PedidoDAL().apagar(p.getId());
        carregarTabela("");
    }

    @FXML
    private void evtVerProduto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaVerProdutos.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
}