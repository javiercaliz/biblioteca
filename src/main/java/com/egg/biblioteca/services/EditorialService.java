package com.egg.biblioteca.services;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Editorial;
import com.egg.biblioteca.repository.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository ER;

    @Transactional
    public Editorial save(Editorial x) throws WebException {
        if (x.getNombre().isEmpty() || x.getNombre() == null) {
            throw new WebException("Faltó completar el Nombre");
        }
        if (x.getCorreo().isEmpty() || x.getCorreo() == null) {
            throw new WebException("Faltó completar el Correo");
        }
        return ER.save(x);
    }

    @Transactional
    public Editorial save(String nombre, String correo) throws WebException {
        Editorial x = new Editorial(nombre, correo);
        return ER.save(x);
    }

    @Transactional
    public Editorial modify(String id, String nombre, String correo) throws WebException {
        Editorial x = ER.findById(id).get();
        x.setNombre(nombre);
        x.setCorreo(correo);
        return ER.save(x);
    }

    @Transactional
    public void delete(Editorial x) {
        ER.delete(x);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Editorial> op = ER.findById(id);
        if (op.isPresent()) {
            ER.deleteById(id);
        }
    }

    public List<Editorial> listAll() {
        return ER.findAll();
    }

    public Optional<Editorial> findById(String id) {
        return ER.findById(id);
    }

    public Editorial editorialById(String id) {
        return ER.findById(id).get();
    }

    public List<Editorial> findByNombre(String nombre) {
        return ER.findByNombreContainingOrderByNombre(nombre);
    }

}
