package com.egg.biblioteca.repository;

import com.egg.biblioteca.entities.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Javi
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    List<Cliente> findByNombreContainingOrApellidoContainingOrCorreoContainingOrDocumentoContainingOrTelefonoContainingOrderByApellido(String nombre, String apellido, String correo, String documento , String telefono);

    Cliente findByDocumento(String dni);
}
