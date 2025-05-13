package com.ejemplo.asignacion.controller;

import com.ejemplo.asignacion.dto.AsignacionResponse;
import com.ejemplo.asignacion.dto.ClienteDTO;
import com.ejemplo.asignacion.dto.ReglaAsignacionDTO;
import com.ejemplo.asignacion.entity.Cliente;
import com.ejemplo.asignacion.entity.Comercial;
import com.ejemplo.asignacion.entity.ReglaAsignacion;
import com.ejemplo.asignacion.service.ClienteService;
import com.ejemplo.asignacion.service.MotorReglasService;
import com.ejemplo.asignacion.service.ReglaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AsignacionController {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionController.class);

    @Autowired
    private MotorReglasService motorReglasService;

    @Autowired
    private ClienteService clienteService; // Para guardar el cliente con el comercial asignado

    @PostMapping("/asignacion/evaluar")
    public ResponseEntity<AsignacionResponse> evaluarAsignacion(@Valid @RequestBody ClienteDTO clienteDTO) {
        logger.info("Recibida solicitud de asignación para cliente: {}", clienteDTO.getNombreCompleto());
        Optional<Comercial> comercialAsignadoOpt = motorReglasService.asignarComercial(clienteDTO);

        if (comercialAsignadoOpt.isPresent()) {
            Comercial comercial = comercialAsignadoOpt.get();
            
            // Opcional: Crear/actualizar el cliente en la BD con el comercial asignado
            // Para este ejemplo, vamos a crear un nuevo cliente. En un caso real, podrías buscar si ya existe.
            Cliente nuevoCliente = new Cliente(
                clienteDTO.getNombreCompleto(),
                clienteDTO.getUbicacionGeografica(),
                clienteDTO.getTipoCliente(),
                clienteDTO.getVolumenCompraAnual(),
                clienteDTO.getSectorIndustrial()
            );
            nuevoCliente.setComercialAsignado(comercial);
            Cliente clienteGuardado = clienteService.guardarCliente(nuevoCliente);
            logger.info("Cliente {} guardado con ID {} y comercial asignado ID {}", clienteGuardado.getNombreCompleto(), clienteGuardado.getId(), comercial.getId());

            AsignacionResponse response = new AsignacionResponse(
                "Comercial asignado exitosamente.",
                comercial.getId(),
                comercial.getNombre(),
                clienteGuardado.getId()
            );
            return ResponseEntity.ok(response);
        } else {
            logger.warn("No se pudo asignar un comercial para el cliente: {}", clienteDTO.getNombreCompleto());
            // Opcional: Guardar el cliente sin comercial asignado o con un estado "pendiente de asignación"
             Cliente nuevoClienteSinAsignacion = new Cliente(
                clienteDTO.getNombreCompleto(),
                clienteDTO.getUbicacionGeografica(),
                clienteDTO.getTipoCliente(),
                clienteDTO.getVolumenCompraAnual(),
                clienteDTO.getSectorIndustrial()
            );
            Cliente clienteGuardado = clienteService.guardarCliente(nuevoClienteSinAsignacion);


            AsignacionResponse response = new AsignacionResponse(
                "No se encontró un comercial adecuado según las reglas.",
                null,
                null,
                clienteGuardado.getId() // Se devuelve el ID del cliente aunque no se haya asignado comercial
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

@RestController
@RequestMapping("/api/reglas")
public class ReglaController {

    @Autowired
    private ReglaService reglaService;
    
    private static final Logger logger = LoggerFactory.getLogger(ReglaController.class);


    @GetMapping
    public List<ReglaAsignacion> obtenerTodasLasReglas() {
        return reglaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReglaAsignacion> obtenerReglaPorId(@PathVariable Long id) {
        return reglaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearRegla(@Valid @RequestBody ReglaAsignacionDTO reglaDTO) {
        try {
            ReglaAsignacion nuevaRegla = reglaService.crearRegla(reglaDTO);
            return new ResponseEntity<>(nuevaRegla, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear regla: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRegla(@PathVariable Long id, @Valid @RequestBody ReglaAsignacionDTO reglaDTO) {
         try {
            Optional<ReglaAsignacion> reglaActualizada = reglaService.actualizarRegla(id, reglaDTO);
            return reglaActualizada
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar regla {}: {}",id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegla(@PathVariable Long id) {
        if (reglaService.eliminarRegla(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

// Controlador simple para gestionar Comerciales (opcional, para poblar datos si es necesario)
@RestController
@RequestMapping("/api/comerciales")
class ComercialController {
    @Autowired
    private ComercialService comercialService;

    @PostMapping
    public Comercial crearComercial(@RequestBody Comercial comercial) {
        return comercialService.guardarComercial(comercial);
    }

    @GetMapping
    public List<Comercial> listarComerciales() {
        return comercialService.obtenerTodos();
    }
}
