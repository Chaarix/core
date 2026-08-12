package edu.cfp.core.dominio;

import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Cliente {
    // Definimos los 11 campos que tu proyecto General conoce y espera parsear
    private Long id;
    private String nombreEntidad;
    private String tipoIdentificacion;
    private String telefonoEntidad;
    private String cuitcuilEntidad;
    private String condicionIva;
    private String dniEntidad;
    private String pais;
    private String emailEntidad;
    private String ciudad;
    private String provincia;
}
