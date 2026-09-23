package br.insper.prova;

import br.insper.prova.produtos.Produto;
import br.insper.prova.produtos.ProdutoRepository;
import br.insper.prova.produtos.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    public void test_shouldReturnTwoProdutsWhenListarWithoutFilter() {

        Produto produto1 = new Produto();
        produto1.setNome("Papel");

        Produto produto2 = new Produto();
        produto2.setNome("Caneta");

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);

        // mocks
        Mockito.when(produtoRepository.findAll())
                .thenReturn(produtos);

        // chamada
        List<Produto> response = produtoService.listar();

        // asserts
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Papel", response.get(0).getNome());
        Assertions.assertEquals("Caneta", response.get(1).getNome());
    }

    @Test
    public void test_shouldReturnOneProdutWhenListarID() {

        Produto produto1 = new Produto();
        produto1.setNome("Papel");

        Produto produto2 = new Produto();
        produto2.setNome("Caneta");


        // mocks
        Mockito.when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto1));

        // chamada
        Optional<Produto> response = produtoService.listarID(1L);

        // asserts
        Assertions.assertEquals("Papel", response.get().getNome());
        Assertions.assertEquals(0L, response.get().getId());
    }

    @Test
    public void test_shouldCreateProduto() {

        Produto produto = new Produto();
        produto.setNome("Caderno");
        produto.setDescricao("Muitas folhas para escrever");


        // mocks
        Mockito.when(produtoRepository.save(Mockito.any()))
                .thenReturn(produto);

        // chamada
        Produto response = produtoService.criar(produto);

        // asserts
        Assertions.assertEquals("Caderno", response.getNome());
        Assertions.assertEquals("Muitas folhas para escrever", response.getDescricao());

    }

    @Test
    public void test_shouldDeleteProduto() {

        Produto produto = new Produto();
        produto.setNome("Caderno");
        produto.setDescricao("Muitas folhas para escrever");

        Produto produto2 = new Produto();
        produto2.setNome("Caneta");

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        produtos.add(produto2);



        // mocks

        Mockito.when(produtoRepository.findById(0L))
                .thenReturn(Optional.of(produto));

        // chamada
        produtoService.deletar(0L);

    }



}
