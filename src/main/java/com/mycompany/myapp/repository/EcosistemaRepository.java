package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ecosistema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ecosistema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcosistemaRepository extends JpaRepository<Ecosistema, Long> {}
