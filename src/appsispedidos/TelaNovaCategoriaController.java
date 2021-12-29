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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class TelaNovaCategoriaController implements Initializable {

    @FXML
    private TextField txid;
    @FXML
    private TextField txnome;
    @FXML
    private TextField txdescricao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(TelaCadCategoriaController.categoria!=null)
        {
            Categoria c = TelaCadCategoriaController.categoria;
            txid.setText(""+c.getId());
            txnome.setText(c.getNome());
            txdescricao.setText((""+c.getDescricao()));
        }
        Platform.runLater(()->{txnome.requestFocus();});
    }    

    @FXML
    private void evtConfirmar(ActionEvent event) {
        Categoria c = new Categoria(txnome.getText(),txdescricao.getText());
        if(txid.getText().isEmpty())
        {
            if(!new CategoriaDAL().gravar(c))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar"+Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else
        {
            c.setId(Integer.parseInt(txid.getText()));
            if(!new CategoriaDAL().alterar(c))
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
