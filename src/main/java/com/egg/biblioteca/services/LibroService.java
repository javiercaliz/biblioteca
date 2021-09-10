package com.egg.biblioteca.services;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.entities.Editorial;
import com.egg.biblioteca.entities.Libro;
import com.egg.biblioteca.repository.LibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {
    
    @Autowired
    private LibroRepository LR;
    
    @Autowired
    private AutorService AS;
    
    @Autowired
    private EditorialService ES;
    
    @Transactional
    public Libro save(Libro x) throws WebException {
        if (x.getAutor() == null) {
            throw new WebException("El Autor no puede estar vacío");
        } else {
            x.setAutor(AS.autorById(x.getAutor().getId()));
        }
        if (x.getEditorial() == null) {
            throw new WebException("La Editorial no puede estar vacía");
        } else {
            x.setEditorial(ES.editorialById(x.getEditorial().getId()));
        }
        if (x.getAnio() == null) {
            throw new WebException("El Año no puede estar vacío");
        }
        if (x.getEjemplares() == null) {
            throw new WebException("El número de ejemplares no puede estar vacío");
        }
        if (x.getGenero() == null) {
            throw new WebException("El Género no puede estar vacío");
        }
        if (x.getIsbn() == null) {
            throw new WebException("El ISBN no puede estar vacío");
        }
        if (x.getTitulo() == null) {
            throw new WebException("El Título no puede estar vacío");
        }
        x.setPrestados(0);
        return LR.save(x);
    }
    
    @Transactional
    public Libro save(Long isbn, String titulo, Integer año, String genero, Integer ejemplares, Integer prestados, Autor a, Editorial e) throws WebException {
        Libro x = new Libro(isbn, titulo, año, genero, ejemplares, prestados, a, e);
        return LR.save(x);
    }
    
    @Transactional
    public Libro modify(Long isbn, String titulo, Integer anio, String genero, Integer ejemplares, Autor a, Editorial e) {
        Libro x = LR.findById(isbn).get();
        x.setAutor(a);
        x.setEditorial(e);
        x.setTitulo(titulo);
        x.setGenero(genero);
        x.setAnio(anio);
        x.setEjemplares(ejemplares);
        return LR.save(x);
    }
    
    @Transactional
    public void delete(Libro x) {
        LR.delete(x);
    }
    
    @Transactional
    public void deleteByIsbn(Long isbn) {
        LR.deleteById(isbn);
    }
    
    public List<Libro> listAll() {
        return LR.findAll();
    }
    
    public List<Libro> listAllByQ(String q) {
        return LR.findByTituloContainingOrAutorNombreContainingOrEditorialNombreContainingOrGeneroContaining(q, q, q, q);
    }
    
    public List<Libro> listAllByNumber(Integer q) {
        return LR.findByIsbnOrAnio(Long.valueOf(q), q);
    }
    
    public Optional<Libro> findByIsbn(Long isbn) {
        return LR.findById(isbn);
    }
    
    public List<Libro> listByTitulo(String titulo) {
        return LR.findByTituloContaining(titulo);
    }
    
    public List<Libro> ListByAutor(String nombre) {
        return LR.findByAutor(nombre);
    }
    
    public List<Libro> ListByEditorial(String nombre) {
        return LR.findByEditorial(nombre);
    }

}
