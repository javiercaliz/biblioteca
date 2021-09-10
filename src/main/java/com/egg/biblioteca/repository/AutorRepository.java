package com.egg.biblioteca.repository;

//@author Javi

import com.egg.biblioteca.entities.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, String>{

    List<Autor> findByNombreContainingOrderByNombre(String nombre);
    
}
