package com.egg.biblioteca.controladores;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Prestamo;
import com.egg.biblioteca.services.ClienteService;
import com.egg.biblioteca.services.LibroService;
import com.egg.biblioteca.services.PrestamoService;
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
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService PS;

    @Autowired
    private ClienteService CS;

    @Autowired
    private LibroService LS;

    @GetMapping("/listado")
    public String listarPrestamos(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            model.addAttribute("prestamos", PS.findById(id));
        } else {
            model.addAttribute("prestamos", PS.listAll());
        }
        return "prestamos";
    }

    @GetMapping("/form")
    public String crearPrestamo(Model model, @RequestParam(required = false) String id, @RequestParam(required = true) String action) {
        if (id != null) {
            Optional<Prestamo> op = PS.findById(id);
            if (op.isPresent()) {
                model.addAttribute("prestamo", op.get());
            } else {
                return "redirect:/prestamos/listado";
            }
        } else {
            model.addAttribute("prestamo", new Prestamo());
        }
        model.addAttribute("clientes", CS.listAll());
        model.addAttribute("libros", LS.listAll());
        model.addAttribute("action", action);
        return "prestamos_form";
    }

    @PostMapping("/save")
    public String guardarPrestamo(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Prestamo p, @RequestParam(required = false) String id, @RequestParam(required = true) String action) throws WebException {
        try {
            if (action.equals("crear")) {
                PS.save(p);
            } else {
                PS.modify(id, p.getLibro(), p.getCliente());
            }
            redirectAttributes.addFlashAttribute("success", "Lista Actualizada");
        } catch (WebException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/prestamos/listado";
    }

    @GetMapping("/delete")
    public String eliminarPrestamo(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {
        PS.deleteById(id);
        redirectAttributes.addFlashAttribute("deleted", "Préstamo eliminado");
        return "redirect:/prestamos/listado";
    }

    @GetMapping("/devolver")
    public String devolverPrestamo(Model model, RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {
        PS.devolverPrestamo(id);
        redirectAttributes.addFlashAttribute("devuelto", "Libro devuelto");
        return "redirect:/prestamos/listado";
    }

}
