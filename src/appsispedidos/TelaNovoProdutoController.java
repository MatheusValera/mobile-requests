package appsispedidos;

import appsispedidos.db.dal.CategoriaDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Categoria;
import appsispedidos.db.entidades.Produto;
import appsispedidos.db.util.Banco;
import appsispedidos.util.MaskFieldUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class TelaNovoProdutoController implements Initializable {

    @FXML
    private TextField txid;
    @FXML
    private TextField txnome;
    @FXML
    private TextField txpreco;
    @FXML
    private ComboBox<Categoria> cbcategoria;
    @FXML
    private Spinner<Integer> spestoque;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbcategoria.setItems(FXCollections.observableArrayList(new CategoriaDAL().get("")));
        spestoque.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));
        MaskFieldUtil.monetaryField(txpreco);
        
        if(TelaCadProdutoController.produto!=null)
        {
            Produto p = TelaCadProdutoController.produto;
            txid.setText(""+p.getId());
            txnome.setText(p.getNome());
            txpreco.setText((""+p.getPreco()*10));
            spestoque.getEditor().textProperty().set(""+p.getEstoque());
            cbcategoria.getSelectionModel().select(p.getCategoria());
        }
        Platform.runLater(()->{txnome.requestFocus();});
    }    

    @FXML
    private void evtConfirmar(ActionEvent event) {
        Produto p = new Produto(txnome.getText(),
                Double.parseDouble(txpreco.getText().replace(".","").replace(",",".")),
                spestoque.getValue(), cbcategoria.getSelectionModel().getSelectedItem());
        if(txid.getText().isEmpty())
        {
            if(!new ProdutoDAL().gravar(p))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar"+Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else
        {
            p.setId(Integer.parseInt(txid.getText()));
            if(!new ProdutoDAL().alterar(p))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar"+Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txid.getScene().getWindow().hide();
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txid.getScene().getWindow().hide();
    }
    
}
