package com.naval.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "viajes") //Nombre de la collecion MongoDB
@Data //getters y setters
@NoArgsConstructor //constructor vacio
@AllArgsConstructor // contructor con todos los campos

public class Viaje{
    @Id
    private String id;
    private String titulo;
    private String destino;
    private LocalDate fechaSalida;
    private LocalDate fechaLlegada;
    private String portadaUrl;
    private String propietarioId;
    private boolean grupal;
    private List<String> colaboradores;
    private  List<BloqueItinerario> itinerario;

}
