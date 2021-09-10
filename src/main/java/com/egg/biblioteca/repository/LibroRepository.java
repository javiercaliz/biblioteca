package com.egg.biblioteca.repository;

//@author Javi

import com.egg.biblioteca.entities.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{

    List<Libro> findByTituloContaining(String x);
    
    List<Libro> findByTituloContainingOrAutorNombreContainingOrEditorialNombreContainingOrGeneroContaining(String a, String b, String c, String f);
    
    List<Libro> findByIsbnOrAnio(Long d, Integer e);
    
    @Query("select l from Libro l join Autor a where a.nombre like %?1%")
    List<Libro> findByAutor(String x);
    
    @Query("select l from Libro l join Editorial e where e.nombre like %?1%")
    List<Libro> findByEditorial(String x);
    
}
