package com.egg.biblioteca.services;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Cliente;
import com.egg.biblioteca.entities.Libro;
import com.egg.biblioteca.entities.Prestamo;
import com.egg.biblioteca.repository.PrestamoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository PR;

    @Transactional
    public Prestamo save(Prestamo x) throws WebException {
        if (x.getCliente() == null) {
            throw new WebException("Faltó completar el Cliente");
        }
        if (x.getLibro() == null) {
            throw new WebException("Faltó completar el Libro");
        }
        x.setInicio(new Date());
        x.getLibro().setPrestados(x.getLibro().getPrestados() + 1);
        return PR.save(x);
    }

    @Transactional
    public Prestamo save(Date inicio, Date devolucion, Double multa, Libro libro, Cliente cliente) {
        Prestamo x = new Prestamo(inicio, devolucion, multa, libro, cliente);
        return PR.save(x);
    }

    @Transactional
    public Prestamo modify(String id, Libro libro, Cliente cliente) {
        Prestamo x = PR.findById(id).get();
        x.setLibro(libro);
        x.setCliente(cliente);
        return PR.save(x);
    }

    @Transactional
    public Prestamo devolverPrestamo(String id) {
        Prestamo x = PR.findById(id).get();
        x.setDevolucion(new Date());
        double tiempo = ((x.getDevolucion().getTime() - x.getInicio().getTime())/86400000);
        x.setMulta(tiempo * 10);
        x.getLibro().setPrestados(x.getLibro().getPrestados()-1);
        return PR.save(x);
    }

    @Transactional
    public void delete(Prestamo x) {
        PR.delete(x);
    }

    @Transactional
    public void deleteById(String id) {
        PR.deleteById(id);
    }

    public Optional<Prestamo> findById(String id) {
        return PR.findById(id);
    }

    public List<Prestamo> listByCliente(String name) {
        return PR.listByCliente(name);
    }

    public List<Prestamo> listAll() {
        return PR.findAllOrderByDevolucion();
    }

}
