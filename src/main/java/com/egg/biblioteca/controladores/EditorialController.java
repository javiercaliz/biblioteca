package com.egg.biblioteca.controladores;

import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Editorial;
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
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService ES;

    @GetMapping("/listado")
    public String listarEditoriales(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("editoriales", ES.findByNombre(q));
        } else {
            model.addAttribute("editoriales", ES.listAll());
        }
        return "editoriales";
    }

    @GetMapping("/form")
    public String crearEditorial(Model model, @RequestParam(required = false) String id, @RequestParam(required = true) String action) {
        if (id != null) {
            Optional<Editorial> op = ES.findById(id);
            if (op.isPresent()) {
                model.addAttribute("editorial", op.get());
            } else {
                return "redirect:/editoriales/listado";
            }
        } else {
            model.addAttribute("editorial", new Editorial());
        }
        model.addAttribute("action", action);
        return "editoriales_form";
    }

    @PostMapping("/save")
    public String guardarEditorial(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Editorial editorial, @RequestParam(required = false) String id, @RequestParam(required = true) String action) throws WebException {

        try {
            if (action.equals("crear")) {
                ES.save(editorial);
            } else {
                ES.modify(id, editorial.getNombre(), editorial.getCorreo());
            }
            redirectAttributes.addFlashAttribute("success", "Lista actualizada");
        } catch (WebException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/editoriales/listado";
    }

    @GetMapping("/delete")
    public String eliminarEditorial(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {
        ES.deleteById(id);
        redirectAttributes.addFlashAttribute("deleted", "Editorial borrada");
        return "redirect:/editoriales/listado";
    }
}
