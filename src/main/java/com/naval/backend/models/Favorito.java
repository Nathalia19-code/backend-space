package com.naval.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "favoritos") //Nombre de la coleccion MongoDB
@Data //getters y setters
@NoArgsConstructor //constructor vacio
@AllArgsConstructor // contructor con todos los campos

public class Favorito {
    @Id
    private String id;
    private String usuarioId;          // a qué usuario pertenece
    private String tipo;               // "vuelo", "hotel" o "lugar"
    private Map<String, String> datos; // datos flexibles según el tipo
    private LocalDateTime fechaGuardado;
}
