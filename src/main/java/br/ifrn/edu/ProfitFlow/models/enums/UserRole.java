package br.ifrn.edu.ProfitFlow.models.enums;

public enum UserRole {
    ADMIN("admin"),
    USUARIO("user"),
    GERENTE("gerente");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}