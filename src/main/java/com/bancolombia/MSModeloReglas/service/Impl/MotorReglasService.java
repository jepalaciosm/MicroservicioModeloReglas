package com.bancolombia.MSModeloReglas.service.Impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// DTO para representar las características de un cliente en una solicitud de asignación
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    @NotBlank(message = "El nombre completo no puede estar vacío")
    private String nombreCompleto;

    @NotBlank(message = "La ubicación geográfica no puede estar vacía")
    private String ubicacionGeografica;

    @NotBlank(message = "El tipo de cliente no puede estar vacío")
    private String tipoCliente;
    
    @NotNull(message = "El volumen de compra anual no puede ser nulo")
    private Double volumenCompraAnual;
    
    @NotBlank(message = "El sector industrial no puede estar vacío")
    private String sectorIndustrial;
    // Se pueden añadir más campos según las necesidades de las reglas
}

// DTO para la respuesta de la asignación
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionResponse {
    private String mensaje;
    private Long idComercialAsignado;
    private String nombreComercialAsignado;
    private Long idClienteProcesado; // Opcional, si se crea/actualiza el cliente
}

// DTO para crear o actualizar una regla (similar a la entidad pero para entrada)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReglaAsignacionDTO {
    private Long id; // Opcional, para actualizaciones

    @NotBlank
    private String nombreRegla;
    
    @NotNull
    private Integer prioridad;
    
    @NotBlank
    private String campoCliente; // Ej: "ubicacionGeografica", "tipoCliente", "volumenCompraAnual"
    
    @NotNull
    private com.ejemplo.asignacion.entity.OperadorLogico operador; // Usando el enum de la entidad
    
    @NotBlank
    private String valorCondicion;
    
    @NotNull
    private Long idComercialAsignado;
    
    private boolean activa = true;
    private String descripcionNoTecnica;
}

// --- SERVICIOS ---
package com.ejemplo.asignacion.service;

import com.ejemplo.asignacion.dto.ClienteDTO;
import com.ejemplo.asignacion.entity.Comercial;
import com.ejemplo.asignacion.entity.OperadorLogico;
import com.ejemplo.asignacion.entity.ReglaAsignacion;
import com.ejemplo.asignacion.repository.ComercialRepository;
import com.ejemplo.asignacion.repository.ReglaAsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MotorReglasService {

    private static final Logger logger = LoggerFactory.getLogger(MotorReglasService.class);

    @Autowired
    private ReglaAsignacionRepository reglaRepository;

    @Autowired
    private ComercialRepository comercialRepository;

    public Optional<Comercial> asignarComercial(ClienteDTO clienteDTO) {
        List<ReglaAsignacion> reglasActivas = reglaRepository.findByActivaTrueOrderByPrioridadAsc();
        logger.info("Evaluando {} reglas activas para el cliente: {}", reglasActivas.size(), clienteDTO.getNombreCompleto());

        for (ReglaAsignacion regla : reglasActivas) {
            logger.debug("Evaluando regla: {} (Prioridad: {})", regla.getNombreRegla(), regla.getPrioridad());
            if (cumpleRegla(clienteDTO, regla)) {
                logger.info("Cliente '{}' cumple la regla '{}'. Asignando comercial ID: {}",
                        clienteDTO.getNombreCompleto(), regla.getNombreRegla(), regla.getIdComercialAsignado());
                return comercialRepository.findById(regla.getIdComercialAsignado());
            }
        }
        logger.warn("Ninguna regla cumplida para el cliente: {}", clienteDTO.getNombreCompleto());
        return Optional.empty(); // Ninguna regla se cumplió
    }

    private boolean cumpleRegla(ClienteDTO clienteDTO, ReglaAsignacion regla) {
        Object valorCliente;
        String campo = regla.getCampoCliente().toLowerCase();

        // Obtener el valor del campo del cliente DTO.
        // Se usa toLowerCase para hacer la comparación de nombres de campo insensible a mayúsculas/minúsculas.
        switch (campo) {
            case "ubicaciongeografica":
                valorCliente = clienteDTO.getUbicacionGeografica();
                break;
            case "tipocliente":
                valorCliente = clienteDTO.getTipoCliente();
                break;
            case "volumencompraanual":
                valorCliente = clienteDTO.getVolumenCompraAnual();
                break;
            case "sectorindustrial":
                valorCliente = clienteDTO.getSectorIndustrial();
                break;
            default:
                logger.warn("Campo desconocido en regla '{}': {}", regla.getNombreRegla(), regla.getCampoCliente());
                return false; // Campo no reconocido en el DTO
        }

        if (valorCliente == null) {
            logger.debug("Valor del cliente para el campo '{}' es nulo. Regla no aplicable.", regla.getCampoCliente());
            return false; // Si el valor del cliente es nulo, la regla no puede aplicar directamente (a menos que sea una regla específica para nulos)
        }
        
        String valorCondicion = regla.getValorCondicion();
        OperadorLogico operador = regla.getOperador();

        try {
            // Comparaciones
            if (valorCliente instanceof String clienteStr) {
                switch (operador) {
                    case IGUAL_A: return clienteStr.equalsIgnoreCase(valorCondicion);
                    case NO_IGUAL_A: return !clienteStr.equalsIgnoreCase(valorCondicion);
                    case CONTIENE: return clienteStr.toLowerCase().contains(valorCondicion.toLowerCase());
                    case NO_CONTIENE: return !clienteStr.toLowerCase().contains(valorCondicion.toLowerCase());
                    case EN_LISTA: return Arrays.asList(valorCondicion.toLowerCase().split(","))
                                            .contains(clienteStr.toLowerCase());
                    case NO_EN_LISTA: return !Arrays.asList(valorCondicion.toLowerCase().split(","))
                                            .contains(clienteStr.toLowerCase());
                    default: 
                        logger.warn("Operador {} no aplicable a String para regla '{}'", operador, regla.getNombreRegla());
                        return false;
                }
            } else if (valorCliente instanceof Double clienteDouble) {
                double valorCondicionDouble = Double.parseDouble(valorCondicion);
                switch (operador) {
                    case IGUAL_A: return clienteDouble.equals(valorCondicionDouble);
                    case NO_IGUAL_A: return !clienteDouble.equals(valorCondicionDouble);
                    case MAYOR_QUE: return clienteDouble > valorCondicionDouble;
                    case MENOR_QUE: return clienteDouble < valorCondicionDouble;
                    case MAYOR_O_IGUAL_QUE: return clienteDouble >= valorCondicionDouble;
                    case MENOR_O_IGUAL_QUE: return clienteDouble <= valorCondicionDouble;
                    default:
                        logger.warn("Operador {} no aplicable a Double para regla '{}'", operador, regla.getNombreRegla());
                        return false;
                }
            } else if (valorCliente instanceof Integer clienteInt) { // Si tuvieras campos Integer
                 int valorCondicionInt = Integer.parseInt(valorCondicion);
                 switch (operador) {
                    case IGUAL_A: return clienteInt.equals(valorCondicionInt);
                    // ... otros operadores para Integer
                    default:
                        logger.warn("Operador {} no aplicable a Integer para regla '{}'", operador, regla.getNombreRegla());
                        return false;
                 }
            }
            // Añadir más tipos según sea necesario (Boolean, Date, etc.)

        } catch (NumberFormatException e) {
            logger.error("Error al convertir valorCondicion '{}' a número para regla '{}'. Error: {}", valorCondicion, regla.getNombreRegla(), e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Error inesperado evaluando regla '{}'. Error: {}", regla.getNombreRegla(), e.getMessage(), e);
            return false;
        }
        
        logger.debug("Tipo de dato no manejado '{}' para el campo '{}' en regla '{}'", valorCliente.getClass().getSimpleName(), campo, regla.getNombreRegla());
        return false; // Tipo de dato no manejado
    }
}

// Servicio para gestionar las reglas (CRUD básico)
@Service
public class ReglaService {
    @Autowired
    private ReglaAsignacionRepository reglaRepository;

    @Autowired
    private ComercialRepository comercialRepository; // Para validar que el comercial exista

    public List<ReglaAsignacion> obtenerTodas() {
        return reglaRepository.findAll();
    }

    public Optional<ReglaAsignacion> obtenerPorId(Long id) {
        return reglaRepository.findById(id);
    }

    public ReglaAsignacion crearRegla(ReglaAsignacionDTO dto) {
        if (!comercialRepository.existsById(dto.getIdComercialAsignado())) {
            throw new IllegalArgumentException("El comercial con ID " + dto.getIdComercialAsignado() + " no existe.");
        }
        ReglaAsignacion regla = new ReglaAsignacion();
        // Mapeo de DTO a Entidad
        regla.setNombreRegla(dto.getNombreRegla());
        regla.setPrioridad(dto.getPrioridad());
        regla.setCampoCliente(dto.getCampoCliente());
        regla.setOperador(dto.getOperador());
        regla.setValorCondicion(dto.getValorCondicion());
        regla.setIdComercialAsignado(dto.getIdComercialAsignado());
        regla.setActiva(dto.isActiva());
        regla.setDescripcionNoTecnica(dto.getDescripcionNoTecnica());
        return reglaRepository.save(regla);
    }

    public Optional<ReglaAsignacion> actualizarRegla(Long id, ReglaAsignacionDTO dto) {
        return reglaRepository.findById(id)
            .map(reglaExistente -> {
                if (!comercialRepository.existsById(dto.getIdComercialAsignado())) {
                    throw new IllegalArgumentException("El comercial con ID " + dto.getIdComercialAsignado() + " no existe.");
                }
                // Mapeo de DTO a Entidad existente
                reglaExistente.setNombreRegla(dto.getNombreRegla());
                reglaExistente.setPrioridad(dto.getPrioridad());
                reglaExistente.setCampoCliente(dto.getCampoCliente());
                reglaExistente.setOperador(dto.getOperador());
                reglaExistente.setValorCondicion(dto.getValorCondicion());
                reglaExistente.setIdComercialAsignado(dto.getIdComercialAsignado());
                reglaExistente.setActiva(dto.isActiva());
                reglaExistente.setDescripcionNoTecnica(dto.getDescripcionNoTecnica());
                return reglaRepository.save(reglaExistente);
            });
    }

    public boolean eliminarRegla(Long id) {
        if (reglaRepository.existsById(id)) {
            reglaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

// Servicios para Cliente y Comercial (simplificados para este ejemplo)
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
}

@Service
public class ComercialService {
    @Autowired
    private ComercialRepository comercialRepository;

    public Comercial guardarComercial(Comercial comercial) {
        return comercialRepository.save(comercial);
    }
    
    public List<Comercial> obtenerTodos() {
        return comercialRepository.findAll();
    }
}
