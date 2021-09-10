package com.egg.biblioteca.controladores;

import com.egg.biblioteca.Excepciones.WebException;
import com.egg.biblioteca.entities.Cliente;
import com.egg.biblioteca.services.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService CS;

    @GetMapping("/listado")
    public String listadoClientes(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("clientes", CS.findByQ(q));
        } else {
            model.addAttribute("clientes", CS.listAll());
        }
        return "clientes";
    }

    @GetMapping("/form")
    public String crearClientes(Model model, @RequestParam(required = false) String id, @RequestParam(required = true) String action) {
        if (id != null) {
            Optional<Cliente> op = CS.findById(id);
            if (op.isPresent()) {
                model.addAttribute("cliente", op.get());
            } else {
                return "redirect:/clientes/listado";
            }
        } else {
            model.addAttribute("cliente", new Cliente());
        }
        model.addAttribute("action", action);
        return "clientes_form";
    }

    @PostMapping("/save")
    public String guardarCliente(Model model, RedirectAttributes redirectAtributes, @ModelAttribute Cliente c, @RequestParam(required = false) String id, @RequestParam (required = true) String action) throws WebException {
        try {
            if (action.equals("crear")) {
                CS.save(c);
            } else {
                CS.modify(id, c.getDocumento(), c.getNombre(), c.getApellido(), c.getCorreo(), c.getTelefono());
            }
             redirectAtributes.addFlashAttribute("succes", "Lista actualizada");
        } catch (WebException e) {
            redirectAtributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes/listado";
    }
    
    @GetMapping("/delete")
    public String eliminarCliente(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {
        CS.deleteById(id);
        redirectAttributes.addFlashAttribute("deleted", "Cliente eliminado");
        return "redirect:/clientes/listado";
    }
    


}
