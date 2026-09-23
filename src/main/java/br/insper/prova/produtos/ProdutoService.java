package br.insper.prova.produtos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> listarID(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto criar (Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();

        produtoRepository.delete(produto);
    }
}
