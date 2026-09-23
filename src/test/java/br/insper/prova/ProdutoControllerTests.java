package br.insper.prova;

import br.insper.prova.produtos.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ProdutoControllerTests {
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("produtos_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl
        );

        registry.add(
                "spring.datasource.username",
                postgres::getUsername
        );

        registry.add(
                "spring.datasource.password",
                postgres::getPassword
        );
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_shouldCreateCourse() throws Exception {

        Produto curso = new Produto();
        curso.setNome("Caderno");
        curso.setDescricao("Muitas folhas para escrever");

        // chamada
        MvcResult result = mockMvc.perform(
                        post("/produtos")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isCreated())
                .andReturn();

        // asserts
        Produto response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                Produto.class
        );

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals("Caderno", response.getNome());
        Assertions.assertEquals("Muitas folhas para escrever", response.getDescricao());

    }
}
