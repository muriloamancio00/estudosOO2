package ifpr.pgua.eic.projetovendas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import ifpr.pgua.eic.projetovendas.daos.JDBCPessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.JDBCProdutoDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProduto;
import ifpr.pgua.eic.projetovendas.telas.Home;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

/**
 * JavaFX App
 * 
 * sub por sub
 * caso de uso - interface,cadastro,DAO..
 * 
 */
public class App extends Application {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    PessoaDAO pessoaDAO = new JDBCPessoaDAO(fabricaConexoes);
    ProdutoDAO produtoDAO = new JDBCProdutoDAO();

    RepositorioProduto repositorioProduto = new RepositorioProduto(produtoDAO);
    RepositorioPessoas repositorioPessoas = new RepositorioPessoas(pessoaDAO);


    @Override
    public void start(Stage stage) throws IOException {
        

        Scene scene = new Scene(loadTela("fxml/home.fxml", o->new Home(repositorioPessoas,repositorioProduto)), 800, 700);
        stage.setScene(scene);
        stage.show();
    }

    
    public static Parent loadTela(String fxml, Callback controller){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            loader.setControllerFactory(controller);

            root = loader.load();

        }catch (Exception e){
            System.out.println("Problema no arquivo fxml. Está correto?? "+fxml);
            e.printStackTrace();
            System.exit(0);
        }
        return root;   
    }

    public static void main(String[] args) {
        launch();
    }

}