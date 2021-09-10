package com.egg.biblioteca.services;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Cliente;
import com.egg.biblioteca.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository CR;
    
     @Transactional
    public Cliente save(Cliente x) throws WebException {
        if (findByDocumento(x.getDocumento()) != null) {
            throw new WebException("El DNI ya ha sido asignado a otro usuario");
        }
        if (x.getNombre().isEmpty() || x.getNombre() == null) {
            throw new WebException("Faltó completar el nombre");
        }
        if (x.getApellido().isEmpty() || x.getApellido() == null) {
            throw new WebException("Faltó completar el apellido");
        }
        if (x.getDocumento().isEmpty() ||x.getDocumento() == null) {
            throw new WebException("Faltó completar el documento");
        }
        if (x.getCorreo().isEmpty() || x.getCorreo() == null) {
            throw new WebException("Faltó completar el correo");
        }
        if (x.getTelefono().isEmpty() || x.getTelefono() == null) {
            throw new WebException("Faltó completar el teléfono");
        }
        return CR.save(x);
    }

    @Transactional
    public Cliente save(String documento, String nombre, String apellido, String correo, String teléfono) {
        Cliente x = new Cliente(documento, nombre, apellido, correo, teléfono);
        return CR.save(x);
    }

    @Transactional
    public Cliente modify(String id, String documento, String nombre, String apellido, String correo, String teléfono) {
        Cliente x = CR.findById(id).get();
        x.setNombre(nombre);
        x.setApellido(apellido);
        x.setDocumento(documento);
        x.setCorreo(correo);
        x.setTelefono(teléfono);
        return CR.save(x);
    }

    @Transactional
    public void delete(Cliente x) {
        CR.delete(x);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Cliente> op = CR.findById(id);
        if (op.isPresent()) {
            CR.deleteById(id);
        }
    }

    public List<Cliente> listAll() {
        return CR.findAll();
    }

    public Optional<Cliente> findById(String id) {
        return CR.findById(id);
    }

    public List<Cliente> findByQ(String q) {
        return CR.findByNombreContainingOrApellidoContainingOrCorreoContainingOrDocumentoContainingOrTelefonoContainingOrderByApellido(q, q, q, q, q);
    }

    public Cliente findByDocumento(String dni) {
        return CR.findByDocumento(dni);
    }
}
