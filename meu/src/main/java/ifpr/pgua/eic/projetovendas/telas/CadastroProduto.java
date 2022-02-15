package ifpr.pgua.eic.projetovendas.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProduto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroProduto {
    
    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfQuantidadeEstoque;

    @FXML
    private TextField tfValor;

    @FXML
    private Button btCadastrar;

    @FXML
    private Label LabelH1;

    private RepositorioProduto repositorioProduto;

    private Produto produtoExistente = null;

    public CadastroProduto(RepositorioProduto repositorioProduto){
        this.repositorioProduto = repositorioProduto;
    }

    //novo controlador para atualização de produto
    public CadastroProduto(Produto produtoExistente, RepositorioProduto repositorioProduto){
        this.repositorioProduto = repositorioProduto;
        this.produtoExistente = produtoExistente;
    }

    public void initialize(){
        if(produtoExistente != null){
            tfNome.setText(produtoExistente.getNome());
            tfDescricao.setText(produtoExistente.getDescricao());
            tfQuantidadeEstoque.setText(String.valueOf(produtoExistente.getQuantidadeEstoque()));
            tfValor.setText(String.valueOf(produtoExistente.getValor()));

            btCadastrar.setText("Atualizar");

            LabelH1.setText("Atualizacao de Produtos");
        }
    }

    @FXML
    private void cadastrar(){
        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        String strQuantidadeEstoque = tfQuantidadeEstoque.getText();
        String strValor = tfValor.getText();

        boolean temErro = false;
        String msg="";

        int quantidadeEstoque = 0;
        double valor = 0;

        if(nome.isEmpty() || nome.isBlank()){
            temErro = true;
            msg += "Nome não pode ser vazio!\n";
        }

        if(descricao.isEmpty() || descricao.isBlank()){
            temErro = true;
            msg += "Descrição não pode ser vazio!\n";
        }

        try{
            quantidadeEstoque = Integer.parseInt(strQuantidadeEstoque);

        }catch(NumberFormatException e){
            msg += "Quantidade em estoque inválida!";
            temErro = true;
        }

        try{
            valor = Double.parseDouble(strValor);
        }catch(NumberFormatException e){
            temErro = true;
            msg += "Valor inválido!";
        }

        if(!temErro){
            boolean ret;
            try{
                if(produtoExistente != null){
                    ret = repositorioProduto.atualizarProduto(produtoExistente.getId(), nome, descricao, quantidadeEstoque, valor);
                }else{
                    ret = repositorioProduto.cadastrarProduto(nome, descricao, quantidadeEstoque, valor);
                }
                
                if(ret){
                    msg = "Produto cadastrado com sucesso!";
                    limpar();
                }else{
                    msg = "Erro ao cadastrar produto!";
                }
            }
            catch(SQLException e){
                temErro = true;
                msg = e.getMessage();
            }
        }

        Alert alert = new Alert(temErro?AlertType.ERROR:AlertType.INFORMATION,msg);
        alert.showAndWait();

    }

    @FXML
    private void limpar(){
        tfDescricao.clear();
        tfNome.clear();
        tfQuantidadeEstoque.clear();
    }



}
