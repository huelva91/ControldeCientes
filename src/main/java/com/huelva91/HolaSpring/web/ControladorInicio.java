package com.huelva91.HolaSpring.web;

import com.huelva91.HolaSpring.domain.Persona;
import com.huelva91.HolaSpring.servicio.PersonaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.net.UnknownServiceException;

@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private PersonaService personaService;


    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {

        var personas = personaService.listaPersonas();
        log.info("El usuario que hizo login:" + user);

        model.addAttribute("personas", personas);
        var saldoTotal= 0D;
        for (var persona : personas) {
            saldoTotal += persona.getSaldo();
        }
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());

        log.info("Ejecutando el controlador Spring MVC");
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona) {
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errores){
        if (errores.hasErrors()) {
            return "modificar";
        }
        personaService.guardar(persona);
        return "redirect:/";
    }
    @GetMapping("/editar/{idPersona}")
    public String editar(Persona persona, Model model) {
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";

    }

    @GetMapping("/eliminar")
    public String eliminar(Persona persona) {
        personaService.eliminar(persona);
        return "redirect:/";
    }


}
