package ifpr.pgua.eic.projetovendas.repositorios;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.models.Produto;

public class RepositorioProduto {
    
    private ProdutoDAO produtoDAO;

    private ArrayList<Produto> produtos;

    public RepositorioProduto(ProdutoDAO produtoDAO){
        this.produtoDAO = produtoDAO;
        produtos = new ArrayList<>();
    }

    public boolean cadastrarProduto(String nome, String descricao, int quantidadeEstoque, double valor) throws SQLException{
  
            try{
                Produto p = new Produto(nome, descricao, quantidadeEstoque, valor);
                
                produtoDAO.cadastrar(p);

                this.produtos.add(p);
            
                return true;
            }catch(Exception e){
                throw new SQLException(e.getMessage());
            }
        
    }

    public boolean atualizarProduto( int id , String nome, String descricao, int quantidadeEstoque, double valor) throws SQLException{
        
        Produto produto = new Produto(nome,descricao,quantidadeEstoque,valor);

        try {
            return produtoDAO.atualizar(id, produto);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }

    }

    public boolean removerProduto(int id) throws SQLException{
        try {
            return produtoDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Produto> listarProdutos() throws Exception {
        return produtoDAO.lista();
    }

    //buscar id
    public Produto buscarProdutoId(int id) throws SQLException{
        try {
            return produtoDAO.buscarId(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    //buscar nome
    public ArrayList<Produto> buscarProdutoNome(String nome) throws SQLException{
        return produtoDAO.buscarNome(nome);
    }
    
}
