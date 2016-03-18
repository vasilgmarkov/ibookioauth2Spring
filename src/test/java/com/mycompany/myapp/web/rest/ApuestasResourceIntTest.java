package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Apuestas;
import com.mycompany.myapp.repository.ApuestasRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ApuestasResource REST controller.
 *
 * @see ApuestasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApuestasResourceIntTest {

    private static final String DEFAULT_LIGA_NAME = "AAAAA";
    private static final String UPDATED_LIGA_NAME = "BBBBB";

    private static final LocalDate DEFAULT_PARTIDO_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PARTIDO_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PARTIDO_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PARTIDO_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_APUESTA_NAME = "AAAAA";
    private static final String UPDATED_APUESTA_NAME = "BBBBB";

    private static final Double DEFAULT_A_APOSTAR_ODD = 1D;
    private static final Double UPDATED_A_APOSTAR_ODD = 2D;
    private static final String DEFAULT_A_APOSTAR_NAME = "AAAAA";
    private static final String UPDATED_A_APOSTAR_NAME = "BBBBB";
    private static final String DEFAULT_TIPO_DEPORTE = "AAAAA";
    private static final String UPDATED_TIPO_DEPORTE = "BBBBB";

    @Inject
    private ApuestasRepository apuestasRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApuestasMockMvc;

    private Apuestas apuestas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApuestasResource apuestasResource = new ApuestasResource();
        ReflectionTestUtils.setField(apuestasResource, "apuestasRepository", apuestasRepository);
        this.restApuestasMockMvc = MockMvcBuilders.standaloneSetup(apuestasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        apuestas = new Apuestas();
        apuestas.setLigaName(DEFAULT_LIGA_NAME);
        apuestas.setPartidoStartDate(DEFAULT_PARTIDO_START_DATE);
        apuestas.setPartidoTime(DEFAULT_PARTIDO_TIME);
        apuestas.setApuestaName(DEFAULT_APUESTA_NAME);
        apuestas.setaApostarOdd(DEFAULT_A_APOSTAR_ODD);
        apuestas.setaApostarName(DEFAULT_A_APOSTAR_NAME);
        apuestas.setTipoDeporte(DEFAULT_TIPO_DEPORTE);
    }

    @Test
    @Transactional
    public void createApuestas() throws Exception {
        int databaseSizeBeforeCreate = apuestasRepository.findAll().size();

        // Create the Apuestas

        restApuestasMockMvc.perform(post("/api/apuestass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestas)))
                .andExpect(status().isCreated());

        // Validate the Apuestas in the database
        List<Apuestas> apuestass = apuestasRepository.findAll();
        assertThat(apuestass).hasSize(databaseSizeBeforeCreate + 1);
        Apuestas testApuestas = apuestass.get(apuestass.size() - 1);
        assertThat(testApuestas.getLigaName()).isEqualTo(DEFAULT_LIGA_NAME);
        assertThat(testApuestas.getPartidoStartDate()).isEqualTo(DEFAULT_PARTIDO_START_DATE);
        assertThat(testApuestas.getPartidoTime()).isEqualTo(DEFAULT_PARTIDO_TIME);
        assertThat(testApuestas.getApuestaName()).isEqualTo(DEFAULT_APUESTA_NAME);
        assertThat(testApuestas.getaApostarOdd()).isEqualTo(DEFAULT_A_APOSTAR_ODD);
        assertThat(testApuestas.getaApostarName()).isEqualTo(DEFAULT_A_APOSTAR_NAME);
        assertThat(testApuestas.getTipoDeporte()).isEqualTo(DEFAULT_TIPO_DEPORTE);
    }

    @Test
    @Transactional
    public void checkPartidoTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apuestasRepository.findAll().size();
        // set the field null
        apuestas.setPartidoTime(null);

        // Create the Apuestas, which fails.

        restApuestasMockMvc.perform(post("/api/apuestass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestas)))
                .andExpect(status().isBadRequest());

        List<Apuestas> apuestass = apuestasRepository.findAll();
        assertThat(apuestass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApuestass() throws Exception {
        // Initialize the database
        apuestasRepository.saveAndFlush(apuestas);

        // Get all the apuestass
        restApuestasMockMvc.perform(get("/api/apuestass?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(apuestas.getId().intValue())))
                .andExpect(jsonPath("$.[*].ligaName").value(hasItem(DEFAULT_LIGA_NAME.toString())))
                .andExpect(jsonPath("$.[*].partidoStartDate").value(hasItem(DEFAULT_PARTIDO_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].partidoTime").value(hasItem(DEFAULT_PARTIDO_TIME.toString())))
                .andExpect(jsonPath("$.[*].apuestaName").value(hasItem(DEFAULT_APUESTA_NAME.toString())))
                .andExpect(jsonPath("$.[*].aApostarOdd").value(hasItem(DEFAULT_A_APOSTAR_ODD.doubleValue())))
                .andExpect(jsonPath("$.[*].aApostarName").value(hasItem(DEFAULT_A_APOSTAR_NAME.toString())))
                .andExpect(jsonPath("$.[*].tipoDeporte").value(hasItem(DEFAULT_TIPO_DEPORTE.toString())));
    }

    @Test
    @Transactional
    public void getApuestas() throws Exception {
        // Initialize the database
        apuestasRepository.saveAndFlush(apuestas);

        // Get the apuestas
        restApuestasMockMvc.perform(get("/api/apuestass/{id}", apuestas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(apuestas.getId().intValue()))
            .andExpect(jsonPath("$.ligaName").value(DEFAULT_LIGA_NAME.toString()))
            .andExpect(jsonPath("$.partidoStartDate").value(DEFAULT_PARTIDO_START_DATE.toString()))
            .andExpect(jsonPath("$.partidoTime").value(DEFAULT_PARTIDO_TIME.toString()))
            .andExpect(jsonPath("$.apuestaName").value(DEFAULT_APUESTA_NAME.toString()))
            .andExpect(jsonPath("$.aApostarOdd").value(DEFAULT_A_APOSTAR_ODD.doubleValue()))
            .andExpect(jsonPath("$.aApostarName").value(DEFAULT_A_APOSTAR_NAME.toString()))
            .andExpect(jsonPath("$.tipoDeporte").value(DEFAULT_TIPO_DEPORTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApuestas() throws Exception {
        // Get the apuestas
        restApuestasMockMvc.perform(get("/api/apuestass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApuestas() throws Exception {
        // Initialize the database
        apuestasRepository.saveAndFlush(apuestas);

		int databaseSizeBeforeUpdate = apuestasRepository.findAll().size();

        // Update the apuestas
        apuestas.setLigaName(UPDATED_LIGA_NAME);
        apuestas.setPartidoStartDate(UPDATED_PARTIDO_START_DATE);
        apuestas.setPartidoTime(UPDATED_PARTIDO_TIME);
        apuestas.setApuestaName(UPDATED_APUESTA_NAME);
        apuestas.setaApostarOdd(UPDATED_A_APOSTAR_ODD);
        apuestas.setaApostarName(UPDATED_A_APOSTAR_NAME);
        apuestas.setTipoDeporte(UPDATED_TIPO_DEPORTE);

        restApuestasMockMvc.perform(put("/api/apuestass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apuestas)))
                .andExpect(status().isOk());

        // Validate the Apuestas in the database
        List<Apuestas> apuestass = apuestasRepository.findAll();
        assertThat(apuestass).hasSize(databaseSizeBeforeUpdate);
        Apuestas testApuestas = apuestass.get(apuestass.size() - 1);
        assertThat(testApuestas.getLigaName()).isEqualTo(UPDATED_LIGA_NAME);
        assertThat(testApuestas.getPartidoStartDate()).isEqualTo(UPDATED_PARTIDO_START_DATE);
        assertThat(testApuestas.getPartidoTime()).isEqualTo(UPDATED_PARTIDO_TIME);
        assertThat(testApuestas.getApuestaName()).isEqualTo(UPDATED_APUESTA_NAME);
        assertThat(testApuestas.getaApostarOdd()).isEqualTo(UPDATED_A_APOSTAR_ODD);
        assertThat(testApuestas.getaApostarName()).isEqualTo(UPDATED_A_APOSTAR_NAME);
        assertThat(testApuestas.getTipoDeporte()).isEqualTo(UPDATED_TIPO_DEPORTE);
    }

    @Test
    @Transactional
    public void deleteApuestas() throws Exception {
        // Initialize the database
        apuestasRepository.saveAndFlush(apuestas);

		int databaseSizeBeforeDelete = apuestasRepository.findAll().size();

        // Get the apuestas
        restApuestasMockMvc.perform(delete("/api/apuestass/{id}", apuestas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Apuestas> apuestass = apuestasRepository.findAll();
        assertThat(apuestass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
