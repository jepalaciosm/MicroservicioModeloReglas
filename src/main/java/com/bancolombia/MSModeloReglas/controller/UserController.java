package com.bancolombia.MSModeloReglas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.service.IComercialService;


@RestController
@RequestMapping("/api/asignacionclietes")
public class UserController {
    private final IComercialService comercialService;

    public UserController(IComercialService comercialService){
        this.comercialService = comercialService;
    }

    @GetMapping("/saludo")
    public String holaMundo(){
        return "Funcionando ........";
    }
    @GetMapping("/saludo-patch/{nombre}")
    public String patchSaludo(@PathVariable("nombre") String nombre){
        return "Hola " + nombre;
    }
    @PostMapping("/saludo-patch2")
    public String patchSaludo2(String nombre){
        return "Hola " + nombre;
    } 

    @GetMapping("/saludocomercial")
    public String comercial(){
        return comercialService.nombreComercial();
    }
}
