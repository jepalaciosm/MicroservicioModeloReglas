package com.bancolombia.MSModeloReglas.service.Impl;

import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.service.IComercialService;

@Service
public class ComercialServiceImpl implements IComercialService {
    @Override
    public String nombreComercial(){
        return "Comercial funcionando";
    }
    
}
