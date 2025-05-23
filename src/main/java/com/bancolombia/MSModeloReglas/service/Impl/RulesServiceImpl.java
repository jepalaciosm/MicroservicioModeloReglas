package com.bancolombia.MSModeloReglas.service.Impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity;
import com.bancolombia.MSModeloReglas.repositories.IClientRepository;
import com.bancolombia.MSModeloReglas.repositories.IComercialRepository;
import com.bancolombia.MSModeloReglas.repositories.IRulesRepository;
import com.bancolombia.MSModeloReglas.service.IRulesService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RulesServiceImpl  implements  IRulesService{
    private final IRulesRepository rulesRepository;
    private final IComercialRepository comercialRepository;
    private final IClientRepository clientRepository;
    // private final IComercialRepository comercialRepository;
    // private final IClientRepository clientRepository;
    // private final IClientService clientService;

    @Override
    public ResponseEntity<?> saveRules(RulesEntity reglas) {
        var newReglas = this.rulesRepository.save(reglas);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReglas);
    }

    // @Override
    // public ResponseEntity<?> AssignClient(ClientEntity client) {
    //     var newClient = this.clientRepository.save(client);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    // }
    @Override
    public ResponseEntity<?> findRulesByID(Long id) {
        var rules = this.rulesRepository.findById(id);
        if (rules.isPresent()) {
            return ResponseEntity.ok(rules.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Regla no encontrada");
        }
    }

    @Override
    public List<RulesEntity> findAllRulesActive() {
        return this.rulesRepository.findByActivaTrueOrderByPrioridadAsc()
                .stream()
                .map(rules -> rules)
                .collect(Collectors.toList());
    }

    @Override
    public List<RulesEntity> findAllRulesActiveBySegment(String segment) {
        return this.rulesRepository.findActiveRulesBySegmentAsc(segment)
                .stream()
                .map(rules -> rules)
                .collect(Collectors.toList());
    }

    @Override
    public boolean OKRule(ClientEntity client, RulesEntity rules) {
        var valorCondicion = rules.getValorCondicion1();
        //Ojo porque lo estoy haciendo por oficina
        //Proximo recibir el campo de operador de la regla, condicion de la regla, campo de cliente
        //var clienteStr = client.getOffice(); 
        Object valorCliente;
        try {
            // Obtener el valor del campo del cliente DTO usando Reflection
            Field campoDeclarado = ClientEntity.class.getDeclaredField(rules.getCampoCliente1());
            campoDeclarado.setAccessible(true); // Necesario si el campo es privado
            valorCliente = campoDeclarado.get(client);

        } catch (Exception e) {          
            return false;
        }

        if (valorCliente == null) {
            return false;
        }
            
        if (client.getSegment().contains("Corporativo") || client.getSegment().contains("Empresarial")) {
            if(valorCliente instanceof String clienteStr){
                switch(rules.getOperador1()){
                case IGUAL_A: return clienteStr.equalsIgnoreCase(valorCondicion);
                case NO_IGUAL_A: return !clienteStr.equalsIgnoreCase(valorCondicion);
                case CONTIENE: return clienteStr.toLowerCase().contains(valorCondicion.toLowerCase());
                case NO_CONTIENE: return !clienteStr.toLowerCase().contains(valorCondicion.toLowerCase());
                case EN_LISTA: return Arrays.asList(valorCondicion.toLowerCase().split(","))
                                        .contains(clienteStr.toLowerCase());
                case NO_EN_LISTA: return !Arrays.asList(valorCondicion.toLowerCase().split(","))
                                        .contains(clienteStr.toLowerCase());
                default:  return false;
                }
            }           

        } else {
            if(valorCliente instanceof String clienteStr){
                switch(rules.getOperador1()){
                case IGUAL_A: return clienteStr.equalsIgnoreCase(valorCondicion);
                case NO_IGUAL_A: return !clienteStr.equalsIgnoreCase(valorCondicion);
                case CONTIENE: return clienteStr.toLowerCase().contains(valorCondicion.toLowerCase());
                case NO_CONTIENE: return !clienteStr.toLowerCase().contains(valorCondicion.toLowerCase());
                case EN_LISTA: return Arrays.asList(valorCondicion.toLowerCase().split(","))
                                        .contains(clienteStr.toLowerCase());
                case NO_EN_LISTA: return !Arrays.asList(valorCondicion.toLowerCase().split(","))
                                        .contains(clienteStr.toLowerCase());
                default:  return false;
                }
            } 

        }
        return true;
    }

   
}
