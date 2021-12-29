/*package appsispedidos;

import appsispedidos.db.dal.PedidoDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Pedido.ItemProduto;
import appsispedidos.db.entidades.Produto;
import appsispedidos.TelaGerPedidoController;
import appsispedidos.db.entidades.Pedido;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaVerProdutosController implements Initializable {

    @FXML
    private TableView<ItemProduto> tabela;
    @FXML
    private TableColumn<ItemProduto, Integer> colid;
    @FXML
    private TableColumn<ItemProduto, Double> colpreco;
    @FXML
    private TableColumn<ItemProduto, Integer> colqtd;
    @FXML
    private TableColumn<ItemProduto, Produto> colproduto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colpreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colqtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colproduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        carregarTabela();
    }    
    
    private void carregarTabela()
    {
        Pedido ped = appsispedidos.TelaGerPedidoController.tabela.getSelectionModel().getSelectedItem();
        tabela.setItems(FXCollections.observableArrayList(ped.getItens()));
    }
}*/
