package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Apuestas;
import com.mycompany.myapp.repository.ApuestasRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Apuestas.
 */
@RestController
@RequestMapping("/api")
public class ApuestasResource {

    private final Logger log = LoggerFactory.getLogger(ApuestasResource.class);

    @Inject
    private ApuestasRepository apuestasRepository;

    /**
     * POST  /apuestass -> Create a new apuestas.
     */
    @RequestMapping(value = "/apuestass",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Apuestas> createApuestas(@Valid @RequestBody Apuestas apuestas) throws URISyntaxException {
        log.debug("REST request to save Apuestas : {}", apuestas);
        if (apuestas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("apuestas", "idexists", "A new apuestas cannot already have an ID")).body(null);
        }
        Apuestas result = apuestasRepository.save(apuestas);
        return ResponseEntity.created(new URI("/api/apuestass/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("apuestas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apuestass -> Updates an existing apuestas.
     */
    @RequestMapping(value = "/apuestass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Apuestas> updateApuestas(@Valid @RequestBody Apuestas apuestas) throws URISyntaxException {
        log.debug("REST request to update Apuestas : {}", apuestas);
        if (apuestas.getId() == null) {
            return createApuestas(apuestas);
        }
        Apuestas result = apuestasRepository.save(apuestas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apuestas", apuestas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apuestass -> get all the apuestass.
     */
    @RequestMapping(value = "/apuestass",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Apuestas>> getAllApuestass(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Apuestass");
        Page<Apuestas> page = apuestasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apuestass");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /apuestass/:id -> get the "id" apuestas.
     */
    @RequestMapping(value = "/apuestass/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Apuestas> getApuestas(@PathVariable Long id) {
        log.debug("REST request to get Apuestas : {}", id);
        Apuestas apuestas = apuestasRepository.findOne(id);
        return Optional.ofNullable(apuestas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /apuestass/:id -> delete the "id" apuestas.
     */
    @RequestMapping(value = "/apuestass/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApuestas(@PathVariable Long id) {
        log.debug("REST request to delete Apuestas : {}", id);
        apuestasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("apuestas", id.toString())).build();
    }


    /** Pol y Vasil
     * GET  /apuestass -> get all the apuestass by name.
     */
    @RequestMapping(value = "/apuestass/byName/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Apuestas>> getApuestasByName(@PathVariable String name){
        log.debug("REST request to get Jugador : {}", name);
        List<Apuestas> apuestas =apuestasRepository.findByapuestaNameEquals(name);

        return Optional.ofNullable(apuestas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /** Pol y Vasil
     * GET  /apuestass -> get all the apuestass by leagueName.
     */
    @RequestMapping(value = "/apuestass/byleagueName/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Apuestas>> getApuestasByleagueName(@PathVariable String name){
        log.debug("REST request to get Jugador : {}", name);
        List<Apuestas> apuestas =apuestasRepository.findByligaNameEquals(name);

        return Optional.ofNullable(apuestas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /** Pol y Vasil
     * GET  /apuestass -> get all the apuestassMatchBet by leagueName.
     */
    @RequestMapping(value = "/apuestass/byleagueNameMatchBetting/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Apuestas>> getleagueNameMatchBetting(@PathVariable String name){
        log.debug("REST request to get Jugador : {}", name);
        List<Apuestas> apuestas = apuestasRepository.findByapuestaNameContainingAndligaNameEquals(name);

        return Optional.ofNullable(apuestas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




}
