package br.com.alura.forum.repositories;

import br.com.alura.forum.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void deveRetornarUmUsuarioPeloEmail() {
        Usuario usuarioAluno = new Usuario();
        usuarioAluno.setNome("Aluno");
        usuarioAluno.setEmail("aluno@email.com");

        Optional<Usuario> usuario = repository.findByEmail("aluno@email.com");

        Assert.assertNotNull(usuario);
        Assert.assertEquals(usuarioAluno.getEmail(), usuario.get().getEmail());
        Assert.assertEquals(usuarioAluno.getNome(), usuario.get().getNome());
    }

}
