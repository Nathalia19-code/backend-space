package com.naval.backend.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BloqueItinerario {
    @Id
    private String id;
    private String tipo;
    private String contenido;
    private int orden;
    private Map<String,String> dato;

}
