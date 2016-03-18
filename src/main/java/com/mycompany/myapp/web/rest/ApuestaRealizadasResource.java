package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.config.JHipsterProperties;
import com.mycompany.myapp.domain.ApuestaRealizadas;
import com.mycompany.myapp.repository.ApuestaRealizadasRepository;
import com.mycompany.myapp.security.SecurityUtils;
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
import com.mycompany.myapp.repository.UserRepository;
import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApuestaRealizadas.
 */
@RestController
@RequestMapping("/api")
public class ApuestaRealizadasResource {

    private final Logger log = LoggerFactory.getLogger(ApuestaRealizadasResource.class);

    @Inject
    private ApuestaRealizadasRepository apuestaRealizadasRepository;
   @Inject
   private UserRepository userRepository;
    /**
     * POST  /apuestaRealizadass -> Create a new apuestaRealizadas.
     */
    @RequestMapping(value = "/apuestaRealizadass",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApuestaRealizadas> createApuestaRealizadas(@Valid @RequestBody ApuestaRealizadas apuestaRealizadas) throws URISyntaxException {
        log.debug("REST request to save ApuestaRealizadas : {}", apuestaRealizadas);

        if (apuestaRealizadas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("apuestaRealizadas", "idexists", "A new apuestaRealizadas cannot already have an ID")).body(null);
        }
        apuestaRealizadas.setAApostador(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ApuestaRealizadas result = apuestaRealizadasRepository.save(apuestaRealizadas);
        return ResponseEntity.created(new URI("/api/apuestaRealizadass/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("apuestaRealizadas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apuestaRealizadass -> Updates an existing apuestaRealizadas.
     */
    @RequestMapping(value = "/apuestaRealizadass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApuestaRealizadas> updateApuestaRealizadas(@Valid @RequestBody ApuestaRealizadas apuestaRealizadas) throws URISyntaxException {
        log.debug("REST request to update ApuestaRealizadas : {}", apuestaRealizadas);
        if (apuestaRealizadas.getId() == null) {
            return createApuestaRealizadas(apuestaRealizadas);
        }
        ApuestaRealizadas result = apuestaRealizadasRepository.save(apuestaRealizadas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apuestaRealizadas", apuestaRealizadas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apuestaRealizadass -> get all the apuestaRealizadass.
     */
    @RequestMapping(value = "/apuestaRealizadass",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ApuestaRealizadas>> getAllApuestaRealizadass(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ApuestaRealizadass");
        Page<ApuestaRealizadas> page = apuestaRealizadasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apuestaRealizadass");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /apuestaRealizadass/:id -> get the "id" apuestaRealizadas.
     */
    @RequestMapping(value = "/apuestaRealizadass/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApuestaRealizadas> getApuestaRealizadas(@PathVariable Long id) {
        log.debug("REST request to get ApuestaRealizadas : {}", id);
        ApuestaRealizadas apuestaRealizadas = apuestaRealizadasRepository.findOne(id);
        return Optional.ofNullable(apuestaRealizadas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /apuestaRealizadass/:id -> delete the "id" apuestaRealizadas.
     */
    @RequestMapping(value = "/apuestaRealizadass/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApuestaRealizadas(@PathVariable Long id) {
        log.debug("REST request to delete ApuestaRealizadas : {}", id);
        apuestaRealizadasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("apuestaRealizadas", id.toString())).build();
    }
}
