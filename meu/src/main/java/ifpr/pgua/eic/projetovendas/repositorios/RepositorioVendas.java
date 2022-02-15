package ifpr.pgua.eic.projetovendas.repositorios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.VendaDAO;
import ifpr.pgua.eic.projetovendas.models.ItemVenda;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.models.Venda;

public class RepositorioVendas {

    private VendaDAO vendaDAO;
    private PessoaDAO pessoaDAO;
    private ProdutoDAO produtoDAO;

    private Venda venda;

    public RepositorioVendas(VendaDAO vendaDAO, PessoaDAO pessoaDAO, ProdutoDAO produtoDAO){
        this.pessoaDAO = pessoaDAO;
        this.vendaDAO = vendaDAO;
        this.produtoDAO = produtoDAO;
    }

    public void iniciaVenda(Pessoa pessoa){
        LocalDateTime dataHora = LocalDateTime.now();
        venda = new Venda(pessoa, dataHora);
    }

    public void adicionaProduto(Produto produto, int quantidade){

        ItemVenda itemVenda = new ItemVenda(produto, quantidade, produto.getValor());

        venda.adicionar(itemVenda);
    }

    public void finalizaVenda() throws Exception{
        venda.calcularTotal();
        vendaDAO.salvar(venda);

        //pra garantir que nao tenha venda aberta dps de salvar
        venda = null;
    }

    public Venda getVenda(){
        return venda;
    }

    public double totalVendas() throws Exception{
       
        return vendaDAO.totalVendas();
    }


}
