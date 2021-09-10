package com.egg.biblioteca.repository;

//@author Javi

import com.egg.biblioteca.entities.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, String>{
    
    @Query("select p from Prestamo p join Cliente c where c.nombre like %?1%")
    List<Prestamo> listByCliente(String name);
    
    @Query("select p from Prestamo p order by devolucion")
    List<Prestamo> findAllOrderByDevolucion();

}
