package com.naval.backend.controllers;

import com.naval.backend.dto.CambioItinerarioMessage;
import com.naval.backend.dto.PresenciaMessage;
import com.naval.backend.services.PresenciaService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ItinerarioWebSocketController {

  @Autowired private SimpMessagingTemplate messagingTemplate;
  @Autowired private PresenciaService presenciaService;

  @MessageMapping("/viaje/{viajeId}/update")
  public void recibirCambio(
      @DestinationVariable String viajeId,
      CambioItinerarioMessage mensaje,
      SimpMessageHeaderAccessor headerAccessor) {

    Principal principal = headerAccessor.getUser();
    if (principal == null) return;

    mensaje.setViajeId(viajeId);
    mensaje.setOrigen(principal.getName());

    messagingTemplate.convertAndSend("/topic/viaje/" + viajeId, mensaje);
  }

  @MessageMapping("/viaje/{viajeId}/unirse")
  public void unirse(
      @DestinationVariable String viajeId, SimpMessageHeaderAccessor headerAccessor) {

    Principal principal = headerAccessor.getUser();
    if (principal == null) return;

    String sessionId = headerAccessor.getSessionId();
    String usuarioId = principal.getName();

    presenciaService.registrar(sessionId, usuarioId, viajeId);

    PresenciaMessage presencia =
        new PresenciaMessage(viajeId, presenciaService.obtenerUsuariosActivos(viajeId));
    messagingTemplate.convertAndSend("/topic/viaje/" + viajeId + "/presencia", presencia);
  }
}
