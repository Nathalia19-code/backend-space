package com.naval.backend.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.Map;

@Data //getters y setters
@NoArgsConstructor //constructor vacio
@AllArgsConstructor // contructor con todos los campos

public class BloqueItinerario {
    @Id
    private String id;
    private String tipo;// los tipos de bloques son: texto,vuelo,hotel,lugar
    private String contenido;//
    private int orden;//posición del bloque
    private Map<String,String> dato;// diccionario para vuelo y hoteles

}
