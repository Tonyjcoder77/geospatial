package br.com.sccon.cleanach.domain.enums;

public enum AgeOutput {
    days, months, years;

    public static AgeOutput from(String s) {
        for (var v : values()) if (v.name().equalsIgnoreCase(s)) return v;
        throw new IllegalArgumentException("Parâmetro 'output' inválido para idade: use days|months|years");
    }
}

