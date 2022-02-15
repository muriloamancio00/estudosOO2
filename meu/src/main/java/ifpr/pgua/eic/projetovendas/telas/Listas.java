package ifpr.pgua.eic.projetovendas.telas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.projetovendas.App;
import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProduto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Listas {

    @FXML
    private ListView<Pessoa> lstPessoas;

    @FXML
    private ListView<Produto> lstProdutos;

    @FXML
    private Label lbListaVaziaProdutos;

    @FXML
    private Button btAtualizarPessoa;

    @FXML
    private Button btAtualizarProduto;

    @FXML
    private Button btRemoverPessoa;

    @FXML
    private Button btRemoverProduto;

    @FXML
    private TextField tfPessoas;

    @FXML
    private TextField tfProdutos;


    //mmudar para o novo formato do Root
    @FXML
    private VBox rootPane;

    
    private RepositorioProduto repositorioProduto;
    private RepositorioPessoas repositorioPessoas;

    public Listas(RepositorioPessoas repositorioPessoas, RepositorioProduto repositorioProduto){
        this.repositorioProduto = repositorioProduto;
        this.repositorioPessoas = repositorioPessoas;
    }

    public void initialize(){

        lstProdutos.setCellFactory(lista -> new ListCell<>(){
            protected void updateItem(Produto produto, boolean alterou) {
                super.updateItem(produto, alterou);
                if(produto != null){
                    setText("("+produto.getId()+") - "+produto.getNome());
                }else{
                    setText(null);
                }
            }
        });

        lstPessoas.setCellFactory(lista -> new ListCell<>(){
            protected void updateItem(Pessoa pessoa, boolean alterou) {
                super.updateItem(pessoa, alterou);
                if(pessoa != null){
                    setText("("+pessoa.getId()+") - "+pessoa.getNome());
                }else{
                    setText(null);
                }
            }
        });

        try{
            lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
            //List<Produto> produtos = repositorioProduto.listarProdutos();
            lstProdutos.getItems().addAll(repositorioProduto.listarProdutos());
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        
    }

    //atualizar
    @FXML
    private void atualizarPessoa(ActionEvent event){
        Pessoa pessoaSelecionada = lstPessoas.getSelectionModel().getSelectedItem();
        if(pessoaSelecionada != null){

                StackPane painelCentral = (StackPane) rootPane.getParent();

                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_pessoa.fxml", (o)->new CadastroPessoa(pessoaSelecionada, repositorioPessoas)));

        }
    }

    @FXML
    private void atualizarProduto(ActionEvent event){
        Produto produtoSelecionado = lstProdutos.getSelectionModel().getSelectedItem();
        if(produtoSelecionado != null){
                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", (o)->new CadastroProduto(produtoSelecionado, repositorioProduto)));
        }
    }


    //remoção
    @FXML
    private void removerProduto(ActionEvent event){
        Produto produtoSelecionado = lstProdutos.getSelectionModel().getSelectedItem();
            if(produtoSelecionado != null){
               try {
                   repositorioProduto.removerProduto(produtoSelecionado.getId());
                   lstProdutos.getItems().clear();
                   lstProdutos.getItems().addAll(repositorioProduto.listarProdutos());
               } catch (Exception e) {
                   Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                   alert.showAndWait();
               }
                
            }
        }      
        
    
    @FXML
    private void removerPessoa(ActionEvent event){
        Pessoa pessoaSelecionado = lstPessoas.getSelectionModel().getSelectedItem();

            if(pessoaSelecionado != null){
               try {
                   repositorioPessoas.removerPessoas(pessoaSelecionado.getId());
                   lstPessoas.getItems().clear();
                   lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
               } catch (Exception e) {
                   Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                   alert.showAndWait();
               }
                
            }
        }      
        
    
    //buscar
    @FXML
    private void filtraPessoas() throws Exception{
        String parte = tfPessoas.getText() ;
        Pessoa p = null;
        

        if(parte.length() >= 1 ){
            int id = Integer.parseInt(parte);

            lstPessoas.getItems().clear();
            //p = repositorioPessoas.buscar(id);
            lstPessoas.getItems().add(repositorioPessoas.buscar(id));
            //lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
        }
        else if (parte.length() == 0){
            lstPessoas.getItems().clear();
            lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
        }
    }

    @FXML
    private void filtraProdutos() throws Exception{
        String parte = tfProdutos.getText();
        ArrayList lista;
        if(parte.length() >= 2 ){

            lstProdutos.getItems().clear();
            lista = repositorioProduto.buscarProdutoNome(parte);
            lstProdutos.getItems().addAll(lista);
        }
        else if(parte.length() == 0){
            lstProdutos.getItems().clear();
            lstProdutos.getItems().addAll(repositorioProduto.listarProdutos());
        }
    }

}



