package br.com.alura.forum.repositories;

import br.com.alura.forum.model.Topico;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository repository;

    @Test
    public void deveRetornarUmaPaginaDeTopicosPeloNomeDoCurso() {
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Topico> topicos = repository.findByCursoNome("HTML 5", paginacao);
        Assert.assertNotNull(topicos);
    }

    @Test
    public void deveRetornarUmaPaginaDeTopicosMesmoSeONomeNaoExistir() {
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Topico> topicos = repository.findByCursoNome("JPA", paginacao);
        Assert.assertNotNull(topicos);
    }

    @Test
    public void deveRetornarUmaPaginaDeTopicosMesmoSeONomeEstiverVazio() {
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Topico> topicos = repository.findByCursoNome("", paginacao);
        Assert.assertNotNull(topicos);
    }
}
