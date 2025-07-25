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
import com.bancolombia.MSModeloReglas.model.RulesEntity.OperadorLogico;
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
    public ResponseEntity<?> DeleteRule(Long id) {
        if (rulesRepository.existsById(id)) {
            rulesRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Regla eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Regla no encontrada");
        }
    }

    @Override
    public List<RulesEntity> findAllRules() {
        return this.rulesRepository.findAll()
                .stream()
                .map(rules -> rules)
                .collect(Collectors.toList());
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
    public boolean valideCondition(Object valorCliente, String valorCondicion, OperadorLogico operador) {
        // Imprime los valores en consola
        System.out.println("val-valorCliente1: " + valorCliente);
        System.out.println("val-valorCondicion1: " + valorCondicion);
        System.out.println("val-operador1: " + operador);

        
        if(valorCliente instanceof String clienteStr){
            
            String valorCondicionStr = valorCondicion.toLowerCase();
            System.out.println("Es string cliente:"+ clienteStr+"  condicion:" +valorCondicionStr+" resultado: "+clienteStr.equalsIgnoreCase(valorCondicionStr));
            System.out.println("***validacion condicion igual=**"+clienteStr.equalsIgnoreCase(valorCondicionStr));
            switch(operador){
                case IGUAL_A: return clienteStr.equalsIgnoreCase(valorCondicionStr);
                case NO_IGUAL_A: return !clienteStr.equalsIgnoreCase(valorCondicionStr);
                case CONTIENE: return clienteStr.toLowerCase().contains(valorCondicionStr.toLowerCase());
                case NO_CONTIENE: return !clienteStr.toLowerCase().contains(valorCondicionStr.toLowerCase());
                case EN_LISTA: return Arrays.asList(valorCondicionStr.toLowerCase().split(","))
                                        .contains(clienteStr.toLowerCase());
                case NO_EN_LISTA: return !Arrays.asList(valorCondicionStr.toLowerCase().split(","))
                                        .contains(clienteStr.toLowerCase());
                default:  return false;
            }
        } else if (valorCliente instanceof Integer clienteInt) {
            int valorCondicionInt = Integer.parseInt(valorCondicion);
            System.out.println("Es numerico cliente:"+ clienteInt+"  condicion:" +valorCondicionInt+" resultado: "+clienteInt.equals(valorCondicionInt));
            
            switch (operador) {
                case IGUAL_A: return clienteInt.equals(valorCondicionInt);
                case NO_IGUAL_A: return !clienteInt.equals(valorCondicionInt);
                case MAYOR_QUE: return clienteInt > valorCondicionInt;
                case MENOR_QUE: return clienteInt < valorCondicionInt;
                case MAYOR_O_IGUAL_QUE: return clienteInt >= valorCondicionInt;
                case MENOR_O_IGUAL_QUE: return clienteInt <= valorCondicionInt;
                default: return false;
            }
            
        }

        return false;

    }    

    

    @Override
    public boolean OKRule(ClientEntity client, RulesEntity rules) {
        String valorCondicion1 = rules.getValorCondicion1();
        OperadorLogico operador1= rules.getOperador1();

        String valorCondicion2 = rules.getValorCondicion2();
        OperadorLogico operador2= rules.getOperador2();

        String valorCondicion3 = rules.getValorCondicion3();
        OperadorLogico operador3= rules.getOperador3();
        ;
        boolean condition1= false;
        if (operador1 == OperadorLogico.NO_APLICA) {
            condition1= true;
            return condition1;
        } else {
            try {            
                Field campoDeclarado = ClientEntity.class.getDeclaredField(rules.getCampoCliente1());
                campoDeclarado.setAccessible(true); // Necesario si el campo es privado
                Object valorCliente1 = campoDeclarado.get(client);
                System.out.println("condition1: " + operador1);
                condition1=valideCondition(valorCliente1, valorCondicion1, operador1);
            } catch (Exception e) {          
                System.out.println("Error al validar la condición 1: " + e.getMessage());         
            }
        }
        
        boolean condition2= false;
        if (operador2 == OperadorLogico.NO_APLICA) {
            condition2= true;
        } else {
            try {            
                Field campoDeclarado = ClientEntity.class.getDeclaredField(rules.getCampoCliente2());
                campoDeclarado.setAccessible(true); // Necesario si el campo es privado
                Object valorCliente2 = campoDeclarado.get(client);
                // Imprime los valores en consola
                System.out.println("condition2: " + operador3);

                condition2=valideCondition(valorCliente2, valorCondicion2, operador2);
            } catch (Exception e) {  
                System.out.println("Error al validar la condición 2: " + e.getMessage());                 
            
            }
        }
        
        boolean condition3= false;
        if (operador3 == OperadorLogico.NO_APLICA) {
            condition3= true;
        } else {
            try {            
                Field campoDeclarado = ClientEntity.class.getDeclaredField(rules.getCampoCliente3());
                campoDeclarado.setAccessible(true); // Necesario si el campo es privado
                Object valorCliente3 = campoDeclarado.get(client);
                // Imprime los valores en consola
                System.out.println("condition3: " + operador3);
                condition3=valideCondition(valorCliente3, valorCondicion3, operador3);
            } catch (Exception e) { 
                System.out.println("Error al validar la condición 3: " + e.getMessage());         
            
            }
        }
        
        System.out.println("ok-condition1: " + condition1 + " condition2: " + condition2 + " condition3: " + condition3);
        return condition1 && condition2  && condition3;
    }

   
}
