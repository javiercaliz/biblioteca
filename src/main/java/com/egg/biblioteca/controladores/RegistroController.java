package com.egg.biblioteca.controladores;

//@author Javi
import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Cliente;
import com.egg.biblioteca.services.ClienteService;
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
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private ClienteService CS;

    @GetMapping("/form")
    public String registrarUsuario(Model model, @RequestParam(required = false) String documento, @RequestParam(required = true) String action) {
        if (documento != null) {
            Cliente x = CS.findByDocumento(documento);
            model.addAttribute("cliente", x);
            if (x != null) {
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("cliente", new Cliente());
        }
        model.addAttribute("action", action);
        return "registro";
    }
    
    @PostMapping("/save")
    public String guardarCliente(Model model, RedirectAttributes redirectAtributes, @RequestParam(required = true) String validation, @ModelAttribute Cliente c, @RequestParam(required = false) String id, @RequestParam (required = true) String action) throws WebException {
        try {
            if (action.equals("crear")) {
                if (c.getPassword().equals(validation)) {
                    CS.save(c);
                } else {
                    throw new WebException("Las contraseñas no coinciden");
                }
            } else {
                CS.modify(id, c.getDocumento(), c.getNombre(), c.getApellido(), c.getCorreo(), c.getTelefono());
            }
             redirectAtributes.addFlashAttribute("succes", "Usuario Registrado");
        } catch (WebException e) {
            redirectAtributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
}
