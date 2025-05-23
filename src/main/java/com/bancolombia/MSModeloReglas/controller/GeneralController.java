package com.bancolombia.MSModeloReglas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.model.ComercialEntity;
import com.bancolombia.MSModeloReglas.service.IComercialService;



@RestController
@RequestMapping("/api/asignacionclientes")
public class GeneralController {
    private final IComercialService comercialService;    

    public GeneralController(IComercialService comercialService){
        this.comercialService = comercialService;        
    }

    @GetMapping("/saludo")
    public String holaMundo(){
        return "Funcionando ........";
    }
    // @GetMapping("/saludo-patch/{nombre}")
    // public String patchSaludo(@PathVariable("nombre") String nombre){
    //     return "Hola " + nombre;
    // }
    // @PostMapping("/saludo-patch2")
    // public String patchSaludo2(String nombre){
    //     return "Hola " + nombre;
    // } 

    // @GetMapping("/saludocomercial")
    // public String comercial(){
    //     return comercialService.nombreComercial();
    // }

    // @GetMapping("/encontrarcomerciales")
    // public List<ComercialEntity> getMethodName() {
    //     return comercialService.imprimirComerciales();
    // }

    // @PostMapping("/crearcomercial")
    // // public ResponseEntity<ComercialEntity> crearUsuario(@RequestBody ComercialEntity comercial) {
    // public ComercialEntity crearComercial(String nombre, long cedula, String lider){
    //     return comercialService.crearComercial(nombre, cedula, lider);
    //     // ComercialEntity nuevoComercial = comercialService.crearComercial(comercial);
    //     // return ResponseEntity.ok(nuevoComercial);
    // }
    //     // ...existing code...
    
    // @PutMapping("/actualizarcomercial/{cedula}")
    // public ResponseEntity<ComercialEntity> actualizarComercial(@PathVariable("cedula") long cedula, 
    //                                                          @RequestBody ComercialEntity comercial) {
    //     ComercialEntity comercialActualizado = comercialService.actualizarComercial(cedula, comercial);
        
    //     if (comercialActualizado != null) {
    //         return ResponseEntity.ok(comercialActualizado);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
    
    // @DeleteMapping("/eliminarcomercial/{cedula}")
    // public ResponseEntity<Void> eliminarComercial(@PathVariable("cedula") long cedula) {
    //     boolean eliminado = comercialService.eliminarComercial(cedula);
        
    //     if (eliminado) {
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
    // ...existing code...
}
