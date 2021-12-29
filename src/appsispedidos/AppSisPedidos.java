package appsispedidos;

import appsispedidos.db.entidades.Cliente;
import appsispedidos.db.entidades.Pedido;
import appsispedidos.db.util.Banco;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class AppSisPedidos extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setOnCloseRequest(e->{Banco.getCon().desconectar();});
        stage.show();
    }
    
    public static void main(String[] args) {
        if(!Banco.conectarBanco())
        {
            JOptionPane.showMessageDialog(null, "Erro ao conectar o banco\n"+Banco.getCon().getMensagemErro());
            Platform.exit();
        }
        else
           launch(args);
        
    }
    
}
