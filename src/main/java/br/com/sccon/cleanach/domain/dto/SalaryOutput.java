package br.com.sccon.cleanach.domain.dto;

public enum SalaryOutput {
    min, full;

    public static SalaryOutput from(String s) {
        for (var v : values()) if (v.name().equalsIgnoreCase(s)) return v;
        throw new IllegalArgumentException("Parâmetro 'output' inválido para salário: use min|full");
    }
}

