package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UsuarioEcosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UsuarioEcosistema entity.
 */
@Repository
public interface UsuarioEcosistemaRepository extends JpaRepository<UsuarioEcosistema, Long> {
    default Optional<UsuarioEcosistema> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UsuarioEcosistema> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UsuarioEcosistema> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user left join fetch usuarioEcosistema.ecosistema",
        countQuery = "select count(distinct usuarioEcosistema) from UsuarioEcosistema usuarioEcosistema"
    )
    Page<UsuarioEcosistema> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user left join fetch usuarioEcosistema.ecosistema"
    )
    List<UsuarioEcosistema> findAllWithToOneRelationships();

    @Query(
        "select usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user left join fetch usuarioEcosistema.ecosistema where usuarioEcosistema.id =:id"
    )
    Optional<UsuarioEcosistema> findOneWithToOneRelationships(@Param("id") Long id);
}
