package com.egg.biblioteca.repository;

//@author Javi

import com.egg.biblioteca.entities.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{

    List<Editorial> findByNombreContainingOrderByNombre(String nombre);
}
