package ifpr.pgua.eic.projetovendas.telas;

import ifpr.pgua.eic.projetovendas.App;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProduto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class Home {
    
    private RepositorioProduto repositorioProduto;
    private RepositorioPessoas repositorioPessoas;
    private RepositorioVendas repositorioVendas;
    
    @FXML
    private StackPane painelCentral;


    public Home(RepositorioPessoas repositorioPessoas, RepositorioProduto repositorioProduto){
        this.repositorioProduto = repositorioProduto;
        this.repositorioPessoas = repositorioPessoas;
    }

    @FXML
    private void loadListas(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/listas.fxml", (o)->new Listas(repositorioPessoas,repositorioProduto)));
    }

    @FXML
    private void loadCadastroPessoa(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_pessoa.fxml", (o)->new CadastroPessoa(repositorioPessoas)));
    }

    @FXML
    private void loadCadastroProduto(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", (o)->new CadastroProduto(repositorioProduto)));
    }

    @FXML
    private void loadRealizarVenda(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/realiza_venda.fxml", (o)->new RealizaVenda(repositorioPessoas,repositorioProduto,repositorioVendas)));
    }
}
