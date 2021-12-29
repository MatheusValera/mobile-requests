package appsispedidos;

import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Categoria;
import appsispedidos.db.entidades.Produto;
import appsispedidos.db.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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

public class TelaCadProdutoController implements Initializable {

    @FXML
    private TextField txfiltro;
    @FXML
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colid;
    @FXML
    private TableColumn<Produto, String> colnome;
    @FXML
    private TableColumn<Produto, Double> colpreco;
    @FXML
    private TableColumn<Produto, Integer> colestoque;
    @FXML
    private TableColumn<Produto, String> colcategoria;

    static public Produto produto=null;
    @FXML
    private Button btfechar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colpreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colestoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colcategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        carregarTabela("");
    }    

    private void carregarTabela(String filtro)
    {
        ProdutoDAL dal = new ProdutoDAL();
        List <Produto> produtos = dal.get(filtro);
        tabela.setItems(FXCollections.observableArrayList(produtos));
    }
    
    @FXML
    private void evtFiltrar(ActionEvent event) {
        carregarTabela("pro_nome ILIKE '%"+txfiltro.getText()+"%'");
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoProduto.fxml"));
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
    private void evtApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja excluir o produto?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {
            Produto p = tabela.getSelectionModel().getSelectedItem();
            new ProdutoDAL().apagar(p.getId());
            carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        produto = tabela.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoProduto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        produto = null;
        carregarTabela("");
    }
    
}
