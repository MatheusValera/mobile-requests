package appsispedidos;

import static appsispedidos.TelaCadProdutoController.produto;
import appsispedidos.db.dal.ClienteDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Cliente;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelaCadClienteController implements Initializable {

    @FXML
    private TextField txfiltro;
    @FXML
    private Button btFechar;
    @FXML
    private TableColumn<Cliente, Integer> colid;
    @FXML
    private TableColumn<Cliente, String> coldocumento;
    @FXML
    private TableColumn<Cliente, String> colnome;
    @FXML
    private TableColumn<Cliente, String> colendereco;
    @FXML
    private TableColumn<Cliente, String> colbairro;
    @FXML
    private TableColumn<Cliente, String> colcidade;
    @FXML
    private TableColumn<Cliente, String> colcep;
    @FXML
    private TableColumn<Cliente, String> coluf;
    @FXML
    private TableColumn<Cliente, String> colemail;
    @FXML
    private TableView<Cliente> tabela;
    static public Cliente cliente = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colendereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colbairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colcidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colcep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        coluf.setCellValueFactory(new PropertyValueFactory<>("uf"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        carregarTabela("");   
    }    
    
    private void carregarTabela(String filtro)
    {
        ClienteDAL dal = new ClienteDAL();
        List <Cliente> clientes = dal.get(filtro);
        tabela.setItems(FXCollections.observableArrayList(clientes));
    }

    @FXML
    private void evtFiltrar(ActionEvent event) {
        carregarTabela("cli_nome ILIKE '%"+txfiltro.getText()+"%'");
    }

    @FXML
    private void evtCadastrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoCliente.fxml"));
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
        Stage stage = (Stage) btFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        cliente = tabela.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoCliente.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        cliente = null;
        carregarTabela("");
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja excluir o cliente?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {
            Cliente p = tabela.getSelectionModel().getSelectedItem();
            new ClienteDAL().apagar(p.getId());
            carregarTabela("");
        }
    }
    
}
