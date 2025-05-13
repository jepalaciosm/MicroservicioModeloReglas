package com.bancolombia.MSModeloReglas.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.entities.ComercialEntity;
import com.bancolombia.MSModeloReglas.repositories.ComercialRepository;
import com.bancolombia.MSModeloReglas.service.IComercialService;

@Service
public class ComercialServiceImpl implements IComercialService {
    @Autowired
    private ComercialRepository repository;

    @Override
    public String nombreComercial(){
        return "Comercial funcionando";
    }
    
    @Override
    public List<ComercialEntity> imprimirComerciales(){
        return (List<ComercialEntity>) repository.findAll();
    }

    @Override
    public ComercialEntity crearComercial(String name, long cedula, String lider) {
        ComercialEntity comercial = new ComercialEntity(  name,  cedula,  lider);       

        return repository.save(comercial);
    }

        // In ComercialServiceImpl.java or similar
    @Override
    public ComercialEntity actualizarComercial(long cedula, ComercialEntity comercial) {
        ComercialEntity existente = repository.findById(cedula).orElse(null);
        if (existente != null) {
            existente.setName(comercial.getName());
            existente.setLider(comercial.getLider());
            return repository.save(existente);
        }
        return null;
        // Buscar el comercial existente por cédula
        // Si existe, actualizar sus datos y guardarlo
        // Si no existe, retornar null
    }
    
    @Override
    public boolean eliminarComercial(long cedula) {
        // Buscar el comercial existente por cédula
        // Si existe, eliminarlo y retornar true
        // Si no existe, retornar false
        ComercialEntity existente = repository.findById(cedula).orElse(null);
        if (existente != null) {
            repository.delete(existente);
            return true;
        }
        return false;
    }


}
