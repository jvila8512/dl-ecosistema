package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Idea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Idea entity.
 */
@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    default Optional<Idea> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Idea> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Idea> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct idea from Idea idea left join fetch idea.tipoIdea left join fetch idea.reto",
        countQuery = "select count(distinct idea) from Idea idea"
    )
    Page<Idea> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct idea from Idea idea left join fetch idea.tipoIdea left join fetch idea.reto")
    List<Idea> findAllWithToOneRelationships();

    @Query("select idea from Idea idea left join fetch idea.tipoIdea left join fetch idea.reto where idea.id =:id")
    Optional<Idea> findOneWithToOneRelationships(@Param("id") Long id);
}
