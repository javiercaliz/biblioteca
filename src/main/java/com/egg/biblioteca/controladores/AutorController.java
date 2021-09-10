package com.egg.biblioteca.controladores;

import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.services.AutorService;
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
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService AS;

    @GetMapping("/listado")
    public String listarAutores(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("autores", AS.findByNombre(q));
        } else {
            model.addAttribute("autores", AS.listAll());
        }
        return "autores";
    }

    @GetMapping("/form")
    public String crearAutor(Model model, @RequestParam(required = false) String id, @RequestParam(required = true) String action) {
        if (id != null) {
            Optional<Autor> op = AS.findById(id);
            if (op.isPresent()) {
                model.addAttribute("autor", op.get());
            } else {
                return "redirect:/autores/listado";
            }
        } else {
            model.addAttribute("autor", new Autor());
        }
        model.addAttribute("action", action);
        return "autores_form";
    }
    
        @PostMapping("/save")
    public String guardarAutor(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Autor a, @RequestParam(required = false) String id, @RequestParam(required = true) String action) throws WebException {

        try {
            if (action.equals("crear")) {
                AS.save(a);
            } else {
                AS.modify(id, a.getNombre());
            }
            redirectAttributes.addFlashAttribute("success", "Lista actualizada");
        } catch (WebException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/autores/listado";
    }

    @GetMapping("/delete")
    public String eliminarAutor(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {
        AS.deleteById(id);
        redirectAttributes.addFlashAttribute("deleted", "Autor borrado");
        return "redirect:/autores/listado";
    }

}
