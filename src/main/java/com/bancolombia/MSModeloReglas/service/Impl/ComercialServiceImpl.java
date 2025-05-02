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
}
