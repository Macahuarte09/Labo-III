package ar.edu.utn.frbb.tup.model;

public enum TipoMoneda {
    PESOS("P"),
    DOLARES("D");

    private final String descripcion;

    TipoMoneda(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMoneda fromString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("El tipo de moneda no es correcto");
        }

        switch (text.toLowerCase()) {
            case "p":
            case "pesos":
                return PESOS;
            case "d":
            case "dolares":
                return DOLARES;
            default:
                throw new IllegalArgumentException("El tipo de moneda no es correcto");
        }
    }
}