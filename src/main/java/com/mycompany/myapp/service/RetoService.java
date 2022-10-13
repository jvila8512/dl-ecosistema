package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Reto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Reto}.
 */
public interface RetoService {
    /**
     * Save a reto.
     *
     * @param reto the entity to save.
     * @return the persisted entity.
     */
    Reto save(Reto reto);

    /**
     * Updates a reto.
     *
     * @param reto the entity to update.
     * @return the persisted entity.
     */
    Reto update(Reto reto);

    /**
     * Partially updates a reto.
     *
     * @param reto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Reto> partialUpdate(Reto reto);

    /**
     * Get all the retos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reto> findAll(Pageable pageable);

    /**
     * Get the "id" reto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reto> findOne(Long id);

    /**
     * Delete the "id" reto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
