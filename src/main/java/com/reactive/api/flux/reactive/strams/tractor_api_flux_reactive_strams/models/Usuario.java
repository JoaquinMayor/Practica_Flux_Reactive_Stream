package com.reactive.api.flux.reactive.strams.tractor_api_flux_reactive_strams.models;

public class Usuario {

    private String name;
    private String lastname;
    
    
    public Usuario(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    
}
