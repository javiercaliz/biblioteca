package com.egg.biblioteca.services;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.repository.AutorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository AR;

    @Transactional
    public Autor save(Autor x) throws WebException {
        if (x.getNombre().isEmpty() || x.getNombre() == null) {
            throw new WebException("Faltó completar el Nombre");
        }

        return AR.save(x);
    }

    @Transactional
    public Autor save(String nombre) throws WebException {
        Autor x = new Autor();
        x.setNombre(nombre);
        return AR.save(x);
    }

    @Transactional
    public Autor modify(String id, String nombre) {
        Autor x = AR.findById(id).get();
        x.setNombre(nombre);
        return AR.save(x);
    }

    @Transactional
    public void delete(Autor x) {
        AR.delete(x);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Autor> op = AR.findById(id);
        if (op.isPresent()) {
            AR.deleteById(id);
        }
    }

    public List<Autor> listAll() {
        return AR.findAll();
    }

    public Optional<Autor> findById(String id) {
        return AR.findById(id);
    }

    public Autor autorById(String id) {
        return AR.findById(id).get();
    }

    public List<Autor> findByNombre(String name) {
        return AR.findByNombreContainingOrderByNombre(name);
    }

}
