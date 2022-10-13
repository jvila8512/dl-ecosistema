package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.RetoRepository;
import com.mycompany.myapp.service.RetoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Reto}.
 */
@RestController
@RequestMapping("/api")
public class RetoResource {

    private final Logger log = LoggerFactory.getLogger(RetoResource.class);

    private static final String ENTITY_NAME = "reto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetoService retoService;

    private final RetoRepository retoRepository;

    public RetoResource(RetoService retoService, RetoRepository retoRepository) {
        this.retoService = retoService;
        this.retoRepository = retoRepository;
    }

    /**
     * {@code POST  /retos} : Create a new reto.
     *
     * @param reto the reto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reto, or with status {@code 400 (Bad Request)} if the reto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/retos")
    public ResponseEntity<Reto> createReto(@Valid @RequestBody Reto reto) throws URISyntaxException {
        log.debug("REST request to save Reto : {}", reto);
        if (reto.getId() != null) {
            throw new BadRequestAlertException("A new reto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reto result = retoService.save(reto);
        return ResponseEntity
            .created(new URI("/api/retos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /retos/:id} : Updates an existing reto.
     *
     * @param id the id of the reto to save.
     * @param reto the reto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reto,
     * or with status {@code 400 (Bad Request)} if the reto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/retos/{id}")
    public ResponseEntity<Reto> updateReto(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Reto reto)
        throws URISyntaxException {
        log.debug("REST request to update Reto : {}, {}", id, reto);
        if (reto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!retoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Reto result = retoService.update(reto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /retos/:id} : Partial updates given fields of an existing reto, field will ignore if it is null
     *
     * @param id the id of the reto to save.
     * @param reto the reto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reto,
     * or with status {@code 400 (Bad Request)} if the reto is not valid,
     * or with status {@code 404 (Not Found)} if the reto is not found,
     * or with status {@code 500 (Internal Server Error)} if the reto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/retos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reto> partialUpdateReto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Reto reto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reto partially : {}, {}", id, reto);
        if (reto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!retoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reto> result = retoService.partialUpdate(reto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reto.getId().toString())
        );
    }

    /**
     * {@code GET  /retos} : get all the retos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of retos in body.
     */
    @GetMapping("/retos")
    public ResponseEntity<List<Reto>> getAllRetos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Retos");
        Page<Reto> page = retoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /retos/:id} : get the "id" reto.
     *
     * @param id the id of the reto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/retos/{id}")
    public ResponseEntity<Reto> getReto(@PathVariable Long id) {
        log.debug("REST request to get Reto : {}", id);
        Optional<Reto> reto = retoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reto);
    }

    /**
     * {@code DELETE  /retos/:id} : delete the "id" reto.
     *
     * @param id the id of the reto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/retos/{id}")
    public ResponseEntity<Void> deleteReto(@PathVariable Long id) {
        log.debug("REST request to delete Reto : {}", id);
        retoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
