package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.RetoRepository;
import com.mycompany.myapp.service.RetoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Reto}.
 */
@Service
@Transactional
public class RetoServiceImpl implements RetoService {

    private final Logger log = LoggerFactory.getLogger(RetoServiceImpl.class);

    private final RetoRepository retoRepository;

    public RetoServiceImpl(RetoRepository retoRepository) {
        this.retoRepository = retoRepository;
    }

    @Override
    public Reto save(Reto reto) {
        log.debug("Request to save Reto : {}", reto);
        return retoRepository.save(reto);
    }

    @Override
    public Reto update(Reto reto) {
        log.debug("Request to save Reto : {}", reto);
        return retoRepository.save(reto);
    }

    @Override
    public Optional<Reto> partialUpdate(Reto reto) {
        log.debug("Request to partially update Reto : {}", reto);

        return retoRepository
            .findById(reto.getId())
            .map(existingReto -> {
                if (reto.getReto() != null) {
                    existingReto.setReto(reto.getReto());
                }
                if (reto.getDescripcion() != null) {
                    existingReto.setDescripcion(reto.getDescripcion());
                }

                return existingReto;
            })
            .map(retoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reto> findAll(Pageable pageable) {
        log.debug("Request to get all Retos");
        return retoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reto> findOne(Long id) {
        log.debug("Request to get Reto : {}", id);
        return retoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reto : {}", id);
        retoRepository.deleteById(id);
    }
}
