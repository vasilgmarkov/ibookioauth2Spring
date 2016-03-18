package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Resultados;
import com.mycompany.myapp.repository.ResultadosRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Resultados.
 */
@RestController
@RequestMapping("/api")
public class ResultadosResource {

    private final Logger log = LoggerFactory.getLogger(ResultadosResource.class);
        
    @Inject
    private ResultadosRepository resultadosRepository;
    
    /**
     * POST  /resultadoss -> Create a new resultados.
     */
    @RequestMapping(value = "/resultadoss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resultados> createResultados(@RequestBody Resultados resultados) throws URISyntaxException {
        log.debug("REST request to save Resultados : {}", resultados);
        if (resultados.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resultados", "idexists", "A new resultados cannot already have an ID")).body(null);
        }
        Resultados result = resultadosRepository.save(resultados);
        return ResponseEntity.created(new URI("/api/resultadoss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resultados", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultadoss -> Updates an existing resultados.
     */
    @RequestMapping(value = "/resultadoss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resultados> updateResultados(@RequestBody Resultados resultados) throws URISyntaxException {
        log.debug("REST request to update Resultados : {}", resultados);
        if (resultados.getId() == null) {
            return createResultados(resultados);
        }
        Resultados result = resultadosRepository.save(resultados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resultados", resultados.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultadoss -> get all the resultadoss.
     */
    @RequestMapping(value = "/resultadoss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Resultados>> getAllResultadoss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Resultadoss");
        Page<Resultados> page = resultadosRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resultadoss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /resultadoss/:id -> get the "id" resultados.
     */
    @RequestMapping(value = "/resultadoss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resultados> getResultados(@PathVariable Long id) {
        log.debug("REST request to get Resultados : {}", id);
        Resultados resultados = resultadosRepository.findOne(id);
        return Optional.ofNullable(resultados)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resultadoss/:id -> delete the "id" resultados.
     */
    @RequestMapping(value = "/resultadoss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResultados(@PathVariable Long id) {
        log.debug("REST request to delete Resultados : {}", id);
        resultadosRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resultados", id.toString())).build();
    }
}
