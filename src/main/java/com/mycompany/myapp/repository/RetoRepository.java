package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Reto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetoRepository extends JpaRepository<Reto, Long> {}
