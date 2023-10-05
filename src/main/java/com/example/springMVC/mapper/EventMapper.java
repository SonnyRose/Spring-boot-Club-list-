package com.example.springMVC.mapper;

import com.example.springMVC.DTO.EventDTO;
import com.example.springMVC.models.Event;

public class EventMapper {
    public static Event mapToEvent(EventDTO eventDTO){
        return Event.builder()
                .id(eventDTO.getId())
                .name(eventDTO.getName())
                .startTime(eventDTO.getStartTime())
                .endTime(eventDTO.getEndTime())
                .type(eventDTO.getType())
                .photourl(eventDTO.getPhotourl())
                .createdOn(eventDTO.getCreatedOn())
                .updateOn(eventDTO.getUpdateOn())
                .club(eventDTO.getClub())
                .build();
    }
    public static EventDTO mapToEventDTO(Event event){
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .type(event.getType())
                .photourl(event.getPhotourl())
                .createdOn(event.getCreatedOn())
                .updateOn(event.getUpdateOn())
                .club(event.getClub())
                .build();
    }
}
