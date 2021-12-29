package appsispedidos;

import appsispedidos.db.dal.CategoriaDAL;
import appsispedidos.db.dal.ClienteDAL;
import appsispedidos.db.dal.PedidoDAL;
import appsispedidos.db.dal.ProdutoDAL;
import appsispedidos.db.entidades.Cliente;
import appsispedidos.db.entidades.Pedido;
import appsispedidos.db.entidades.Pedido.ItemProduto;
import appsispedidos.db.entidades.Produto;
import appsispedidos.db.util.Banco;
import appsispedidos.util.MaskFieldUtil;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.swing.JOptionPane;

public class TelaNovoPedidoController implements Initializable {

    @FXML
    private TextField txid;
    @FXML
    private ComboBox<Cliente> cbcliente;
    @FXML
    private DatePicker dtdata;
    @FXML
    private TextField txfrete;
    @FXML
    private TextField txtotal;
    @FXML
    private ComboBox<Produto> cbproduto;
    @FXML
    private Spinner<Integer> spquantidade;
    @FXML
    private TextField txpreco;
    @FXML
    private TableView<ItemProduto> tabela;
    @FXML
    private TableColumn<ItemProduto, Produto> colproduto;
    @FXML
    private TableColumn<ItemProduto, Integer> colquantidade;
    @FXML
    private TableColumn<ItemProduto, Double> colpreco;

    public ArrayList<ItemProduto> auxitp = new ArrayList<ItemProduto>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbcliente.setItems(FXCollections.observableArrayList(new ClienteDAL().get("")));
        cbproduto.setItems(FXCollections.observableArrayList(new ProdutoDAL().get("")));
        spquantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));
        MaskFieldUtil.monetaryField(txpreco);
        MaskFieldUtil.monetaryField(txfrete);
        MaskFieldUtil.monetaryField(txtotal);
        if(TelaGerPedidoController.pedido!=null)
        {
            Pedido p = TelaGerPedidoController.pedido;
            txid.setText(""+p.getId());
            cbcliente.getSelectionModel().select(p.getCliente());
            dtdata.setDayCellFactory((Callback<DatePicker, DateCell>) dtdata);
        }
        Platform.runLater(()->{cbcliente.requestFocus();});
        
        colproduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colquantidade.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        colpreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        carregarTabela("");
    }    

    @FXML
    private void evtAdicionar(ActionEvent event) {
        auxitp.add(new ItemProduto(cbproduto.getSelectionModel().getSelectedItem(),spquantidade.getValue(),
                Double.parseDouble(txpreco.getText().replace(".","").replace(",","."))));
        carregarTabela("");
    }
    
    private void carregarTabela(String filtro)
    {
        tabela.setItems(FXCollections.observableArrayList(auxitp));
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        Pedido p = new Pedido(cbcliente.getSelectionModel().getSelectedItem(),
                dtdata.getValue(),Double.parseDouble(txfrete.getText().replace(".","").replace(",",".")),
                Double.parseDouble(txtotal.getText().replace(".","").replace(",",".")));
        
        for (int i=1; i<auxitp.size();i++)
            p.add(auxitp.get(i));
        if(txid.getText().isEmpty())
        {
            if(!new PedidoDAL().gravar(p))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar"+Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else
        {
            p.setId(Integer.parseInt(txid.getText()));
            if(!new PedidoDAL().alterar(p))
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
