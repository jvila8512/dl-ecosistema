package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Idea;
import com.mycompany.myapp.repository.IdeaRepository;
import com.mycompany.myapp.service.IdeaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Idea}.
 */
@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);

    private final IdeaRepository ideaRepository;

    public IdeaServiceImpl(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    @Override
    public Idea save(Idea idea) {
        log.debug("Request to save Idea : {}", idea);
        return ideaRepository.save(idea);
    }

    @Override
    public Idea update(Idea idea) {
        log.debug("Request to save Idea : {}", idea);
        return ideaRepository.save(idea);
    }

    @Override
    public Optional<Idea> partialUpdate(Idea idea) {
        log.debug("Request to partially update Idea : {}", idea);

        return ideaRepository
            .findById(idea.getId())
            .map(existingIdea -> {
                if (idea.getNumeroRegistro() != null) {
                    existingIdea.setNumeroRegistro(idea.getNumeroRegistro());
                }
                if (idea.getEntidad() != null) {
                    existingIdea.setEntidad(idea.getEntidad());
                }
                if (idea.getTitulo() != null) {
                    existingIdea.setTitulo(idea.getTitulo());
                }
                if (idea.getDescripcion() != null) {
                    existingIdea.setDescripcion(idea.getDescripcion());
                }
                if (idea.getAutor() != null) {
                    existingIdea.setAutor(idea.getAutor());
                }
                if (idea.getFechaIncripcion() != null) {
                    existingIdea.setFechaIncripcion(idea.getFechaIncripcion());
                }

                return existingIdea;
            })
            .map(ideaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Idea> findAll(Pageable pageable) {
        log.debug("Request to get all Ideas");
        return ideaRepository.findAll(pageable);
    }

    public Page<Idea> findAllWithEagerRelationships(Pageable pageable) {
        return ideaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Idea> findOne(Long id) {
        log.debug("Request to get Idea : {}", id);
        return ideaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Idea : {}", id);
        ideaRepository.deleteById(id);
    }
}
