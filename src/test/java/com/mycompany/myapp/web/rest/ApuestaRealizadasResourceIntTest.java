package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ApuestaRealizadas;
import com.mycompany.myapp.repository.ApuestaRealizadasRepository;

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
 * Test class for the ApuestaRealizadasResource REST controller.
 *
 * @see ApuestaRealizadasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApuestaRealizadasResourceIntTest {


    private static final Double DEFAULT_CANTIDAD_APOSTADA = 0D;
    private static final Double UPDATED_CANTIDAD_APOSTADA = 1D;

    private static final Double DEFAULT_CUOTA = 1D;
    private static final Double UPDATED_CUOTA = 2D;
    private static final String DEFAULT_EVENTO_APOSTADO = "AAAAA";
    private static final String UPDATED_EVENTO_APOSTADO = "BBBBB";
    private static final String DEFAULT_GANADOR_APUESTA = "AAAAA";
    private static final String UPDATED_GANADOR_APUESTA = "BBBBB";

    @Inject
    private ApuestaRealizadasRepository apuestaRealizadasRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApuestaRealizadasMockMvc;

    private ApuestaRealizadas apuestaRealizadas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApuestaRealizadasResource apuestaRealizadasResource = new ApuestaRealizadasResource();
        ReflectionTestUtils.setField(apuestaRealizadasResource, "apuestaRealizadasRepository", apuestaRealizadasRepository);
        this.restApuestaRealizadasMockMvc = MockMvcBuilders.standaloneSetup(apuestaRealizadasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        apuestaRealizadas = new ApuestaRealizadas();
        apuestaRealizadas.setCantidadApostada(DEFAULT_CANTIDAD_APOSTADA);
        apuestaRealizadas.setCuota(DEFAULT_CUOTA);
        apuestaRealizadas.setEventoApostado(DEFAULT_EVENTO_APOSTADO);
        apuestaRealizadas.setGanadorApuesta(DEFAULT_GANADOR_APUESTA);
    }

    @Test
    @Transactional
    public void createApuestaRealizadas() throws Exception {
        int databaseSizeBeforeCreate = apuestaRealizadasRepository.findAll().size();

        // Create the ApuestaRealizadas

        restApuestaRealizadasMockMvc.perform(post("/api/apuestaRealizadass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestaRealizadas)))
                .andExpect(status().isCreated());

        // Validate the ApuestaRealizadas in the database
        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeCreate + 1);
        ApuestaRealizadas testApuestaRealizadas = apuestaRealizadass.get(apuestaRealizadass.size() - 1);
        assertThat(testApuestaRealizadas.getCantidadApostada()).isEqualTo(DEFAULT_CANTIDAD_APOSTADA);
        assertThat(testApuestaRealizadas.getCuota()).isEqualTo(DEFAULT_CUOTA);
        assertThat(testApuestaRealizadas.getEventoApostado()).isEqualTo(DEFAULT_EVENTO_APOSTADO);
        assertThat(testApuestaRealizadas.getGanadorApuesta()).isEqualTo(DEFAULT_GANADOR_APUESTA);
    }

    @Test
    @Transactional
    public void checkCantidadApostadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = apuestaRealizadasRepository.findAll().size();
        // set the field null
        apuestaRealizadas.setCantidadApostada(null);

        // Create the ApuestaRealizadas, which fails.

        restApuestaRealizadasMockMvc.perform(post("/api/apuestaRealizadass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestaRealizadas)))
                .andExpect(status().isBadRequest());

        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCuotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = apuestaRealizadasRepository.findAll().size();
        // set the field null
        apuestaRealizadas.setCuota(null);

        // Create the ApuestaRealizadas, which fails.

        restApuestaRealizadasMockMvc.perform(post("/api/apuestaRealizadass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestaRealizadas)))
                .andExpect(status().isBadRequest());

        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEventoApostadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = apuestaRealizadasRepository.findAll().size();
        // set the field null
        apuestaRealizadas.setEventoApostado(null);

        // Create the ApuestaRealizadas, which fails.

        restApuestaRealizadasMockMvc.perform(post("/api/apuestaRealizadass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestaRealizadas)))
                .andExpect(status().isBadRequest());

        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGanadorApuestaIsRequired() throws Exception {
        int databaseSizeBeforeTest = apuestaRealizadasRepository.findAll().size();
        // set the field null
        apuestaRealizadas.setGanadorApuesta(null);

        // Create the ApuestaRealizadas, which fails.

        restApuestaRealizadasMockMvc.perform(post("/api/apuestaRealizadass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestaRealizadas)))
                .andExpect(status().isBadRequest());

        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApuestaRealizadass() throws Exception {
        // Initialize the database
        apuestaRealizadasRepository.saveAndFlush(apuestaRealizadas);

        // Get all the apuestaRealizadass
        restApuestaRealizadasMockMvc.perform(get("/api/apuestaRealizadass?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(apuestaRealizadas.getId().intValue())))
                .andExpect(jsonPath("$.[*].cantidadApostada").value(hasItem(DEFAULT_CANTIDAD_APOSTADA.doubleValue())))
                .andExpect(jsonPath("$.[*].cuota").value(hasItem(DEFAULT_CUOTA.doubleValue())))
                .andExpect(jsonPath("$.[*].eventoApostado").value(hasItem(DEFAULT_EVENTO_APOSTADO.toString())))
                .andExpect(jsonPath("$.[*].ganadorApuesta").value(hasItem(DEFAULT_GANADOR_APUESTA.toString())));
    }

    @Test
    @Transactional
    public void getApuestaRealizadas() throws Exception {
        // Initialize the database
        apuestaRealizadasRepository.saveAndFlush(apuestaRealizadas);

        // Get the apuestaRealizadas
        restApuestaRealizadasMockMvc.perform(get("/api/apuestaRealizadass/{id}", apuestaRealizadas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(apuestaRealizadas.getId().intValue()))
            .andExpect(jsonPath("$.cantidadApostada").value(DEFAULT_CANTIDAD_APOSTADA.doubleValue()))
            .andExpect(jsonPath("$.cuota").value(DEFAULT_CUOTA.doubleValue()))
            .andExpect(jsonPath("$.eventoApostado").value(DEFAULT_EVENTO_APOSTADO.toString()))
            .andExpect(jsonPath("$.ganadorApuesta").value(DEFAULT_GANADOR_APUESTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApuestaRealizadas() throws Exception {
        // Get the apuestaRealizadas
        restApuestaRealizadasMockMvc.perform(get("/api/apuestaRealizadass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApuestaRealizadas() throws Exception {
        // Initialize the database
        apuestaRealizadasRepository.saveAndFlush(apuestaRealizadas);

		int databaseSizeBeforeUpdate = apuestaRealizadasRepository.findAll().size();

        // Update the apuestaRealizadas
        apuestaRealizadas.setCantidadApostada(UPDATED_CANTIDAD_APOSTADA);
        apuestaRealizadas.setCuota(UPDATED_CUOTA);
        apuestaRealizadas.setEventoApostado(UPDATED_EVENTO_APOSTADO);
        apuestaRealizadas.setGanadorApuesta(UPDATED_GANADOR_APUESTA);

        restApuestaRealizadasMockMvc.perform(put("/api/apuestaRealizadass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestaRealizadas)))
                .andExpect(status().isOk());

        // Validate the ApuestaRealizadas in the database
        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeUpdate);
        ApuestaRealizadas testApuestaRealizadas = apuestaRealizadass.get(apuestaRealizadass.size() - 1);
        assertThat(testApuestaRealizadas.getCantidadApostada()).isEqualTo(UPDATED_CANTIDAD_APOSTADA);
        assertThat(testApuestaRealizadas.getCuota()).isEqualTo(UPDATED_CUOTA);
        assertThat(testApuestaRealizadas.getEventoApostado()).isEqualTo(UPDATED_EVENTO_APOSTADO);
        assertThat(testApuestaRealizadas.getGanadorApuesta()).isEqualTo(UPDATED_GANADOR_APUESTA);
    }

    @Test
    @Transactional
    public void deleteApuestaRealizadas() throws Exception {
        // Initialize the database
        apuestaRealizadasRepository.saveAndFlush(apuestaRealizadas);

		int databaseSizeBeforeDelete = apuestaRealizadasRepository.findAll().size();

        // Get the apuestaRealizadas
        restApuestaRealizadasMockMvc.perform(delete("/api/apuestaRealizadass/{id}", apuestaRealizadas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ApuestaRealizadas> apuestaRealizadass = apuestaRealizadasRepository.findAll();
        assertThat(apuestaRealizadass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
