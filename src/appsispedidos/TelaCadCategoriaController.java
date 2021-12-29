package appsispedidos;

import static appsispedidos.TelaCadProdutoController.produto;
import appsispedidos.db.dal.CategoriaDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Categoria;
import appsispedidos.db.entidades.Produto;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelaCadCategoriaController implements Initializable {

    @FXML
    private TextField txfiltro;
    @FXML
    private Button btfechar;
    @FXML
    private TableColumn<Categoria, Integer> colid;
    @FXML
    private TableColumn<Categoria, String> colnome;
    @FXML
    private TableColumn<Categoria, String> coldescricao;
    @FXML
    private TableView<Categoria> tabela; 
    static public Categoria categoria;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coldescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        carregarTabela(""); 
    }
    
    private void carregarTabela(String filtro)
    {
        CategoriaDAL dal = new CategoriaDAL();
        List <Categoria> categorias = dal.get(filtro);
        tabela.setItems(FXCollections.observableArrayList(categorias));
    }

    @FXML
    private void evtFiltrar(ActionEvent event) {
        carregarTabela("cat_nome ILIKE '%"+txfiltro.getText()+"%'");
    }

    @FXML
    private void evtCadastrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovaCategoria.fxml"));
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
        categoria = tabela.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovaCategoria.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        categoria = null;
        carregarTabela("");
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja excluir a categoria?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {
            
            Categoria c = tabela.getSelectionModel().getSelectedItem();
            new ProdutoDAL().apagar(c.getId());
            carregarTabela("");
        }
    }
    
}
