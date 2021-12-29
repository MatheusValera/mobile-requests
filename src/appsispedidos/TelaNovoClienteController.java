package appsispedidos;

import appsispedidos.db.dal.CategoriaDAL;
import appsispedidos.db.dal.ClienteDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Cliente;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class TelaNovoClienteController implements Initializable {

    @FXML
    private TextField txid;
    @FXML
    private TextField txdocumento;
    @FXML
    private TextField txnome;
    @FXML
    private TextField txendereco;
    @FXML
    private TextField txbairro;
    @FXML
    private TextField txcidade;
    @FXML
    private TextField txcep;
    @FXML
    private TextField txuf;
    @FXML
    private TextField txemail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(TelaCadClienteController.cliente!=null)
        {
            Cliente c = TelaCadClienteController.cliente;
            txid.setText(""+c.getId());
            txdocumento.setText(c.getDocumento());
            txnome.setText(c.getNome());
            txendereco.setText(c.getEndereco());
            txbairro.setText(c.getBairro());
            txcidade.setText(c.getCidade());
            txcep.setText(c.getCep());
            txuf.setText(c.getUf());
            txemail.setText(c.getEmail());
        }
        Platform.runLater(()->{txdocumento.requestFocus();});
        
    }    

    @FXML
    private void evtConfirmar(ActionEvent event) {
        Cliente c = new Cliente(txdocumento.getText(),txnome.getText(),txendereco.getText(),txbairro.getText(),
                txcidade.getText(),txcep.getText(),txuf.getText(),txemail.getText());
        if(txid.getText().isEmpty())
        {
            if(!new ClienteDAL().gravar(c))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar"+Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else
        {
            c.setId(Integer.parseInt(txid.getText()));
            if(!new ClienteDAL().alterar(c))
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
