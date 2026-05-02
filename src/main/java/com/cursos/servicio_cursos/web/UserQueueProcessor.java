package com.cursos.servicio_cursos.web;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.models.QueueMessageItem;
import com.cursos.servicio_cursos.dtos.UserMessage;
import com.cursos.servicio_cursos.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserQueueProcessor {
  private final QueueClient queueClient;
  private final UserService userService;

  @Scheduled(fixedDelay = 5000)
  public void proccesMessages() {
    List<QueueMessageItem> messages = queueClient.receiveMessages(10).stream().toList();
    for (QueueMessageItem msg : messages) {
      try {
        log.info("procesando mensaje: {}", msg.getBody().toString());
        UserMessage userMessage = msg.getBody().toObject(UserMessage.class);
        queueClient.deleteMessage(msg.getMessageId(), msg.getPopReceipt());
        userService.upsertUser(userMessage);
      } catch (Exception e) {
        log.error("error procesando el mensaje {}", e.getMessage());
      }
    }
  }

}
