package br.com.alura.forum.controller;

import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repositories.TopicoRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.JVM)
@WithMockUser(username = "aluno@email.com", password = "123456")
public class TopicosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TopicoRepository repository;

    private Curso curso;
    private Topico topico;
    private TopicoForm form;
    private TopicoDto dto;

    private static URI uri;

    public TopicosControllerTest() {
    }

    @Before
    public void setup() throws URISyntaxException {
        uri = new URI("/topicos");

        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Aprenda Spring Boot");
        curso.setCategoria("Java Web");

        topico = new Topico();
        topico.setId(1L);
        topico.setTitulo("Como utilizar o ResponseEntity?");
        topico.setCurso(curso);
        topico.setMensagem("Não entendi essa classe.");

        form = new TopicoForm();
        form.setMensagem(topico.getMensagem());
        form.setNomeCurso(topico.getCurso().getNome());
        form.setTitulo(topico.getTitulo());

        dto = new TopicoDto(topico);

    }
    @Test
    public void listaTodosOsTopicos() throws Exception {

        String body = "{\n" +
                "    \"content\": [\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"titulo\": \"Dúvida 3\",\n" +
                "            \"mensagem\": \"Tag HTML\",\n" +
                "            \"dataCriacao\": \"2019-05-05T20:00:00\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"titulo\": \"Dúvida 2\",\n" +
                "            \"mensagem\": \"Projeto não compila\",\n" +
                "            \"dataCriacao\": \"2019-05-05T19:00:00\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"titulo\": \"Dúvida\",\n" +
                "            \"mensagem\": \"Erro ao criar projeto\",\n" +
                "            \"dataCriacao\": \"2019-05-05T18:00:00\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageable\": {\n" +
                "        \"sort\": {\n" +
                "            \"sorted\": true,\n" +
                "            \"unsorted\": false,\n" +
                "            \"empty\": false\n" +
                "        },\n" +
                "        \"pageNumber\": 0,\n" +
                "        \"pageSize\": 10,\n" +
                "        \"offset\": 0,\n" +
                "        \"paged\": true,\n" +
                "        \"unpaged\": false\n" +
                "    },\n" +
                "    \"totalPages\": 1,\n" +
                "    \"totalElements\": 3,\n" +
                "    \"last\": true,\n" +
                "    \"first\": true,\n" +
                "    \"sort\": {\n" +
                "        \"sorted\": true,\n" +
                "        \"unsorted\": false,\n" +
                "        \"empty\": false\n" +
                "    },\n" +
                "    \"size\": 10,\n" +
                "    \"number\": 0,\n" +
                "    \"numberOfElements\": 3,\n" +
                "    \"empty\": false\n" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(body))
                .andReturn();



    }


    @Test
    public void cadastraUmTopicoComSucesso() throws Exception {

        String body = "{\"titulo\":\"O que é um DTO?\",\"mensagem\":\"Estou com dificuldades para entender sobre DTO\", \"nomeCurso\":\"Spring Boot\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

    }

    @Test
    public void retornaErroSeHouverCamposNulosAoCadastrarNovoTopico() throws Exception {

        String body = "{\"titulo\":\"O que é um DTO?\",\"mensagem\":\"Estou com dificuldades para entender sobre DTO\", \"nomeCurso\":\"\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }


    @Test
    public void detalharUmTopicoComSucesso() throws Exception {

        String body = "{\n" +
                "    \"id\": 1,\n" +
                "    \"titulo\": \"Dúvida\",\n" +
                "    \"mensagem\": \"Erro ao criar projeto\",\n" +
                "    \"dataCriacao\": \"2019-05-05T18:00:00\",\n" +
                "    \"nomeAutor\": \"Aluno\",\n" +
                "    \"status\": \"NAO_RESPONDIDO\",\n" +
                "    \"respostas\": []\n" +
                "}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri + "/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }

    @Test
    public void retornarErroAoDetalharTopicoInexistente() throws Exception {

        String atualizado = "{\n" +
                "    \"titulo\": \"Dúvida atualizada\",\n" +
                "    \"mensagem\": \"Erro ao criar projeto\"\n" +
                "}";

        String json = "{\n" +
                "    \"id\": 1,\n" +
                "    \"titulo\": \"Dúvida atualizada\",\n" +
                "    \"mensagem\": \"Erro ao criar projeto\",\n" +
                "    \"dataCriacao\": \"2019-05-05T18:00:00\"\n" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri + "/1")
                        .content(atualizado)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(json))
                .andReturn();

    }

    @Test
    public void retornaErroAtualizarTopicoInexistente() throws Exception {

        String atualizado = "{\n" +
                "    \"titulo\": \"Dúvida atualizada\",\n" +
                "    \"mensagem\": \"Erro ao criar projeto\"\n" +
                "}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri + "/5")
                        .content(atualizado)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

    }

    @Test
    @WithMockUser(roles = "MODERADOR")
    public void deletarTopicoComSucesso() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }

    @Test
    @WithMockUser(roles = "MODERADOR")
    public void retornarErroAoDeletarTopicoInexistente() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri + "/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

    }
    @Test
    public void retornarErroTentativaDeletarSemSerModerador() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden()).andReturn();

    }

}
