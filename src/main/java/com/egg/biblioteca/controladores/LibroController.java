package com.egg.biblioteca.controladores;

import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Libro;
import com.egg.biblioteca.services.AutorService;
import com.egg.biblioteca.services.LibroService;
import com.egg.biblioteca.services.EditorialService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService LS;

    @Autowired
    private EditorialService ES;

    @Autowired
    private AutorService AS;

    @GetMapping("/listado")
    public String listarLibros(Model model, @RequestParam(required = false) String q) {
        boolean res;
        try {
            Integer.parseInt(q);
            res = true;
        } catch (Exception e) {
            res = false;
        }
        if (q != null) {
            if (res == true) {
                model.addAttribute("libros", LS.listAllByNumber(Integer.valueOf(q)));
            } else {
                model.addAttribute("libros", LS.listAllByQ(q));
            }

        } else {
            model.addAttribute("libros", LS.listAll());
        }
        return "libros";
    }

    @GetMapping("/form")
    public String crearLibro(Model model, @RequestParam(required = false) Long isbn, @RequestParam(required = true) String action) {
        if (isbn != null) {
            Optional<Libro> op = LS.findByIsbn(isbn);
            if (op.isPresent()) {
                model.addAttribute("libro", op.get());
            } else {
                return "redirect:/libros/listado";
            }
        } else {
            model.addAttribute("libro", new Libro());
        }
        model.addAttribute("editoriales", ES.listAll());
        model.addAttribute("autores", AS.listAll());
        model.addAttribute("action", action);
        return "libros_form";
    }

    @PostMapping("/save")
    public String guardarLibro(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Libro libro, @RequestParam(required = false) String isbn, @RequestParam(required = true) String action) throws WebException {
        try {
            if (action.equals("crear")) {
                LS.save(libro);
            } else {
                LS.modify(libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getGenero(), libro.getEjemplares(), libro.getAutor(), libro.getEditorial());
            }
            redirectAttributes.addFlashAttribute("success", "Lista actualizada");
        } catch (WebException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/libros/listado";
    }

    @GetMapping("/delete")
    public String eliminarLibro(RedirectAttributes redirectAttributes, @RequestParam(required = true) Long isbn) {
        LS.deleteByIsbn(isbn);
        redirectAttributes.addFlashAttribute("deleted", "Libro eliminado");
        return "redirect:/libros/listado";
    }

}
