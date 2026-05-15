package com.naval.backend.controllers;

import com.naval.backend.dto.PresenciaMessage;
import com.naval.backend.services.PresenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

  @Autowired private PresenciaService presenciaService;
  @Autowired private SimpMessagingTemplate messagingTemplate;

  @EventListener
  public void handleDisconnect(SessionDisconnectEvent event) {
    String sessionId = event.getSessionId();
    String viajeId = presenciaService.getViajeIdPorSesion(sessionId);

    presenciaService.desconectar(sessionId);

    if (viajeId != null) {
      PresenciaMessage presencia =
          new PresenciaMessage(viajeId, presenciaService.obtenerUsuariosActivos(viajeId));
      messagingTemplate.convertAndSend("/topic/viaje/" + viajeId + "/presencia", presencia);
    }
  }
}
