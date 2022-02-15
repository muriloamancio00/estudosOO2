package ifpr.pgua.eic.projetovendas.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroPessoa {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfTelefone;

    @FXML
    private Button btCadastrar;

    @FXML
    private Label labelH1;

    private RepositorioPessoas repositorioPessoas;

    private Pessoa pessoaExistente = null;

    public CadastroPessoa(RepositorioPessoas repositorio) {
        this.repositorioPessoas = repositorio;
    }

    public CadastroPessoa(Pessoa pessoaExisPessoa, RepositorioPessoas repositorioPessoas){
        this.repositorioPessoas = repositorioPessoas;
        this.pessoaExistente = pessoaExisPessoa;
    }

    public void initialize(){
        if (pessoaExistente != null){
            tfNome.setText(pessoaExistente.getNome());
            tfEmail.setText(pessoaExistente.getEmail());
            tfTelefone.setText(pessoaExistente.getTelefone());

            btCadastrar.setText("Atualizar");

            labelH1.setText("Atualizacao de Cadastro");
        }
    }

    @FXML
    private void cadastrar() {
        String nome = tfNome.getText();
        String email = tfEmail.getText();
        String telefone = tfTelefone.getText();

        boolean temErro = false;
        String msg = "";

        if (nome.isEmpty() || nome.isBlank()) {
            temErro = true;
            msg += "Nome não pode ser vazio!\n";
        }

        if (email.isEmpty() || email.isBlank()) {
            temErro = true;
            msg += "Email não pode ser vazio!\n";
        }

        if (telefone.isEmpty() || telefone.isBlank()) {
            temErro = true;
            msg += "Telefone não pode ser vazio!\n";
        }

        if (!temErro) {
            boolean ret;
            try {
                if(pessoaExistente!= null){
                    ret = repositorioPessoas.atualizarPessoa(pessoaExistente.getId(),nome,email,telefone);
                }else{
                    ret = repositorioPessoas.cadastrarPessoa(nome, email, telefone);
                }
                
                if (ret) {
                    msg = "Pessoa cadastrada com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar pessoa!";
                }

            } catch (SQLException e) {
                temErro = true;
                msg = e.getMessage();
            }
        }

        Alert alert = new Alert(temErro ? AlertType.ERROR : AlertType.INFORMATION, msg);
        alert.showAndWait();

    }

    @FXML
    private void limpar() {
        tfEmail.clear();
        tfNome.clear();
        tfTelefone.clear();
    }

}
