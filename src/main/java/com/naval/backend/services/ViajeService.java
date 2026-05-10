package com.naval.backend.services;

import com.naval.backend.dto.BloqueRequest;
import com.naval.backend.dto.ViajeRequest;
import com.naval.backend.dto.ViajeResponse;
import com.naval.backend.models.BloqueItinerario;
import com.naval.backend.models.Viaje;
import com.naval.backend.repositories.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    public ViajeResponse crear(ViajeRequest request, String propietarioId) {
        Viaje viaje = new Viaje();
        viaje.setTitulo(request.getTitulo());
        viaje.setDestino(request.getDestino());
        viaje.setFechaSalida(request.getFechaSalida());
        viaje.setFechaLlegada(request.getFechaLlegada());
        viaje.setPortadaUrl(request.getPortadaUrl());
        viaje.setGrupal(request.isGrupal());
        viaje.setPropietarioId(propietarioId);
        viaje.setColaboradores(request.getColaboradores() != null ? request.getColaboradores() : new ArrayList<>());
        viaje.setItinerario(new ArrayList<>());
        return toResponse(viajeRepository.save(viaje));
    }

    public List<ViajeResponse> listarPorUsuario(String usuarioId) {
        List<Viaje> propios = viajeRepository.findByPropietarioId(usuarioId);
        List<Viaje> colaborados = viajeRepository.findByColaboradoresContaining(usuarioId);
        List<Viaje> todos = new ArrayList<>(propios);
        todos.addAll(colaborados);
        return todos.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ViajeResponse obtenerPorId(String id) {
        Viaje viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado: " + id));
        return toResponse(viaje);
    }

    public ViajeResponse actualizar(String id, ViajeRequest request) {
        Viaje viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado: " + id));
        viaje.setTitulo(request.getTitulo());
        viaje.setDestino(request.getDestino());
        viaje.setFechaSalida(request.getFechaSalida());
        viaje.setFechaLlegada(request.getFechaLlegada());
        viaje.setPortadaUrl(request.getPortadaUrl());
        viaje.setGrupal(request.isGrupal());
        if (request.getColaboradores() != null) {
            viaje.setColaboradores(request.getColaboradores());
        }
        return toResponse(viajeRepository.save(viaje));
    }

    public void eliminar(String id) {
        viajeRepository.deleteById(id);
    }

    // Reemplaza todo el itinerario con la lista que manda el editor
    // El orden lo da la posición en la lista, no el campo orden del frontend
    public ViajeResponse actualizarItinerario(String viajeId, List<BloqueRequest> bloques) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado: " + viajeId));

        List<BloqueItinerario> nuevos = new ArrayList<>();
        for (int i = 0; i < bloques.size(); i++) {
            nuevos.add(toBloqueNuevo(bloques.get(i), i));
        }

        viaje.setItinerario(nuevos);
        return toResponse(viajeRepository.save(viaje));
    }

    // Añade un bloque nuevo al final del itinerario
    public ViajeResponse agregarBloque(String viajeId, BloqueRequest request) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado: " + viajeId));

        int posicion = viaje.getItinerario().size();
        viaje.getItinerario().add(toBloqueNuevo(request, posicion));
        return toResponse(viajeRepository.save(viaje));
    }

    // Edita el contenido de un bloque concreto sin tocar los demás
    public ViajeResponse actualizarBloque(String viajeId, String bloqueId, BloqueRequest request) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado: " + viajeId));

        viaje.getItinerario().stream()
                .filter(b -> bloqueId.equals(b.getId()))
                .findFirst()
                .ifPresent(b -> {
                    if (request.getTipo() != null)      b.setTipo(request.getTipo());
                    if (request.getContenido() != null) b.setContenido(request.getContenido());
                    if (request.getDato() != null)      b.setDato(request.getDato());
                });

        return toResponse(viajeRepository.save(viaje));
    }

    // Elimina un bloque y reordena los índices
    public ViajeResponse eliminarBloque(String viajeId, String bloqueId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado: " + viajeId));

        viaje.getItinerario().removeIf(b -> bloqueId.equals(b.getId()));

        for (int i = 0; i < viaje.getItinerario().size(); i++) {
            viaje.getItinerario().get(i).setOrden(i);
        }

        return toResponse(viajeRepository.save(viaje));
    }

    private BloqueItinerario toBloqueNuevo(BloqueRequest req, int orden) {
        BloqueItinerario b = new BloqueItinerario();
        b.setId(UUID.randomUUID().toString());
        b.setTipo(req.getTipo());
        b.setContenido(req.getContenido());
        b.setOrden(orden);
        b.setDato(req.getDato());
        return b;
    }

    private ViajeResponse toResponse(Viaje v) {
        return new ViajeResponse(
                v.getId(), v.getTitulo(), v.getDestino(),
                v.getFechaSalida(), v.getFechaLlegada(), v.getPortadaUrl(),
                v.getPropietarioId(), v.isGrupal(),
                v.getColaboradores(), v.getItinerario()
        );
    }
}
