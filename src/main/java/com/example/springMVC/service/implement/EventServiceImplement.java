package com.example.springMVC.service.implement;

import com.example.springMVC.DTO.EventDTO;
import com.example.springMVC.models.Club;
import com.example.springMVC.models.Event;
import com.example.springMVC.repository.ClubRepository;
import com.example.springMVC.repository.EventRepository;
import com.example.springMVC.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.springMVC.mapper.EventMapper.mapToEvent;
import static com.example.springMVC.mapper.EventMapper.mapToEventDTO;

@Service
public class EventServiceImplement implements EventService {
    private EventRepository eventRepository;
    private ClubRepository clubRepository;
    public EventServiceImplement(EventRepository eventRepository, ClubRepository clubRepository){
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }
    @Override
    public void createEvent(Integer clubId, EventDTO eventDTO) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDTO);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDTO(event)).collect(Collectors.toList());
    }

    @Override
    public EventDTO findByEventId(Integer eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDTO(event);
    }
    @Override
    public void updateEvent(EventDTO eventDTO) {
        Event event = mapToEvent(eventDTO);
        eventRepository.save(event);
    }
    @Override
    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }


}
