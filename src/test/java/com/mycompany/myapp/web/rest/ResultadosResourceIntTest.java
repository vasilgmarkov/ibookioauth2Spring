package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Resultados;
import com.mycompany.myapp.repository.ResultadosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ResultadosResource REST controller.
 *
 * @see ResultadosResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResultadosResourceIntTest {

    private static final String DEFAULT_EVENTO = "AAAAA";
    private static final String UPDATED_EVENTO = "BBBBB";
    private static final String DEFAULT_GANADOR = "AAAAA";
    private static final String UPDATED_GANADOR = "BBBBB";

    @Inject
    private ResultadosRepository resultadosRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResultadosMockMvc;

    private Resultados resultados;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResultadosResource resultadosResource = new ResultadosResource();
        ReflectionTestUtils.setField(resultadosResource, "resultadosRepository", resultadosRepository);
        this.restResultadosMockMvc = MockMvcBuilders.standaloneSetup(resultadosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resultados = new Resultados();
        resultados.setEvento(DEFAULT_EVENTO);
        resultados.setGanador(DEFAULT_GANADOR);
    }

    @Test
    @Transactional
    public void createResultados() throws Exception {
        int databaseSizeBeforeCreate = resultadosRepository.findAll().size();

        // Create the Resultados

        restResultadosMockMvc.perform(post("/api/resultadoss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resultados)))
                .andExpect(status().isCreated());

        // Validate the Resultados in the database
        List<Resultados> resultadoss = resultadosRepository.findAll();
        assertThat(resultadoss).hasSize(databaseSizeBeforeCreate + 1);
        Resultados testResultados = resultadoss.get(resultadoss.size() - 1);
        assertThat(testResultados.getEvento()).isEqualTo(DEFAULT_EVENTO);
        assertThat(testResultados.getGanador()).isEqualTo(DEFAULT_GANADOR);
    }

    @Test
    @Transactional
    public void getAllResultadoss() throws Exception {
        // Initialize the database
        resultadosRepository.saveAndFlush(resultados);

        // Get all the resultadoss
        restResultadosMockMvc.perform(get("/api/resultadoss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resultados.getId().intValue())))
                .andExpect(jsonPath("$.[*].evento").value(hasItem(DEFAULT_EVENTO.toString())))
                .andExpect(jsonPath("$.[*].ganador").value(hasItem(DEFAULT_GANADOR.toString())));
    }

    @Test
    @Transactional
    public void getResultados() throws Exception {
        // Initialize the database
        resultadosRepository.saveAndFlush(resultados);

        // Get the resultados
        restResultadosMockMvc.perform(get("/api/resultadoss/{id}", resultados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resultados.getId().intValue()))
            .andExpect(jsonPath("$.evento").value(DEFAULT_EVENTO.toString()))
            .andExpect(jsonPath("$.ganador").value(DEFAULT_GANADOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResultados() throws Exception {
        // Get the resultados
        restResultadosMockMvc.perform(get("/api/resultadoss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultados() throws Exception {
        // Initialize the database
        resultadosRepository.saveAndFlush(resultados);

		int databaseSizeBeforeUpdate = resultadosRepository.findAll().size();

        // Update the resultados
        resultados.setEvento(UPDATED_EVENTO);
        resultados.setGanador(UPDATED_GANADOR);

        restResultadosMockMvc.perform(put("/api/resultadoss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resultados)))
                .andExpect(status().isOk());

        // Validate the Resultados in the database
        List<Resultados> resultadoss = resultadosRepository.findAll();
        assertThat(resultadoss).hasSize(databaseSizeBeforeUpdate);
        Resultados testResultados = resultadoss.get(resultadoss.size() - 1);
        assertThat(testResultados.getEvento()).isEqualTo(UPDATED_EVENTO);
        assertThat(testResultados.getGanador()).isEqualTo(UPDATED_GANADOR);
    }

    @Test
    @Transactional
    public void deleteResultados() throws Exception {
        // Initialize the database
        resultadosRepository.saveAndFlush(resultados);

		int databaseSizeBeforeDelete = resultadosRepository.findAll().size();

        // Get the resultados
        restResultadosMockMvc.perform(delete("/api/resultadoss/{id}", resultados.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Resultados> resultadoss = resultadosRepository.findAll();
        assertThat(resultadoss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
