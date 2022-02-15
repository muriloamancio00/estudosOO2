package ifpr.pgua.eic.projetovendas.repositorios;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.models.Pessoa;

public class RepositorioPessoas {

    private ArrayList<Pessoa> pessoas;

    private PessoaDAO pessoaDAO;

    public RepositorioPessoas(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
        pessoas = new ArrayList<>();
    }

    public boolean cadastrarPessoa(String nome, String email, String telefone) throws SQLException {
        if (buscarPessoa(email) == null) {
            Pessoa p = new Pessoa(nome, email, telefone);
            
            try{
                pessoaDAO.cadastrar(p);
                this.pessoas.add(p);
                
                return true;
            }catch(Exception e){
                throw new SQLException(e.getMessage());
            }
            
        }

        return false;
    }

    public boolean atualizarPessoa( int id , String nome, String email, String telefone) throws SQLException{
        
        Pessoa pessoa = new Pessoa(nome,email,telefone);

        try {
            return pessoaDAO.atualizar(id, pessoa);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }

    }

    public boolean removerPessoas(int id) throws SQLException{
        try {
            return pessoaDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Pessoa> listarPessoas() throws Exception {
        return pessoaDAO.listar();
    }

    //buscar id
    public Pessoa buscar(int id) throws SQLException{
        try {
            return pessoaDAO.buscar(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    //metodo busca pessoa email
    public Pessoa buscarPessoa(String email) {
        return this.pessoas.stream().filter((pessoa) -> pessoa.getEmail().equals(email)).findFirst().orElse(null);
    }
}
