package appsispedidos;

import appsispedidos.db.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipalController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void evtCadProduto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadProduto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtfechar(ActionEvent event) {     
        Banco.getCon().desconectar();
        Platform.exit(); 
    }

    private void evtPedidos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoPedido.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtCadCliente(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadCliente.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtCadCategoria(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadCategoria.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtGerPedido(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerPedido.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtCadPedidos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNovoPedido.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void gerarRelatorio(String sql,String relatorio,String titulo)
   {
    try
    { //sql para obter os dados para o relatorio
      ResultSet rs = Banco.getCon().consultar(sql);
      //implementação da interface JRDataSource para DataSource ResultSet
      JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
      //chamando o relatório
      String jasperPrint = JasperFillManager.fillReportToFile(relatorio,null,jrRS);
      JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
      viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
      viewer.setTitle("Relatório");//titulo do relatório
      viewer.setVisible(true);
    } catch (JRException erro)
    {  erro.printStackTrace(); }

   }
    
    @FXML
    private void evtRelProdutos(ActionEvent event) {
        gerarRelatorio("SELECT PRO.PRO_NOME, PRO.PRO_PRECO, PRO.PRO_ESTOQUE, CAT.CAT_NOME FROM PRODUTOS PRO INNER JOIN CATEGORIAS CAT ON PRO.CAT_ID = CAT.CAT_ID ORDER BY PRO.PRO_NOME","MyReports/MyReports/Produtos.jasper","Relatório de Produtos");
    }
    
    @FXML
    private void evtRelClientes(ActionEvent event) {
        gerarRelatorio("SELECT C.CLI_ID,C.CLI_NOME,C.CLI_DOCUMENTO,C.CLI_CIDADE,C.CLI_ENDERECO,C.CLI_CEP,C.CLI_EMAIL FROM CLIENTES C ORDER BY C.CLI_NOME","MyReports/MyReports/Cliente.jasper","Relatório de Clientes");
    }

    @FXML
    private void evtRelData(ActionEvent event) {
    }

}
