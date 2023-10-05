package com.example.springMVC.service;

import com.example.springMVC.DTO.EventDTO;

import java.util.List;

public interface EventService {
    void createEvent(Integer clubId, EventDTO eventDTO);
    List<EventDTO> findAllEvents();

    EventDTO findByEventId(Integer eventId);

    void updateEvent(EventDTO eventDTO);

    void deleteEvent(Integer eventId);
}
