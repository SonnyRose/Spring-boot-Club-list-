package com.example.springMVC.controller;

import com.example.springMVC.DTO.EventDTO;
import com.example.springMVC.models.Event;
import com.example.springMVC.models.UserEntity;
import com.example.springMVC.security.SecurityUtil;
import com.example.springMVC.service.EventService;
import com.example.springMVC.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService){
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/event")
    public String eventList(Model model){
        UserEntity user = new UserEntity();
        List<EventDTO> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }
    @GetMapping("/event/{eventId}")
    public String viewEvent(@PathVariable("eventId") Integer eventId, Model model) {
        UserEntity user = new UserEntity();
        EventDTO eventDTO = eventService.findByEventId(eventId);
        List<EventDTO> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("club", eventDTO);
        if (eventDTO != null) {
            model.addAttribute("event", eventDTO);
            return "events-detail";
        } else {
            return "error";
        }
    }

    @GetMapping("/event/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Integer clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }
    @GetMapping("/event/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Integer eventId, Model model){
        EventDTO event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }
    @PostMapping("/event/{clubId}")
    public String createEvent(@PathVariable("clubId") Integer clubId, @ModelAttribute("event") EventDTO eventDTO,
                              BindingResult result,
                              Model model) {
        if(result.hasErrors()) {
            model.addAttribute("event", eventDTO);
            return "club-create";
        }
        eventService.createEvent(clubId, eventDTO);
        return "redirect:/club/" + clubId;
    }
    // eventService.createEvent(clubId, eventDTO) - якщо помилок у валідації немає, то викликається сервісний метод
    // createEvent, щоб створити новий захід, пов'язаний з вказаним clubId

    @PostMapping("/event/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Integer eventId,
                             @Valid @ModelAttribute("event") EventDTO event, BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDTO eventDTO = eventService.findByEventId(eventId);
        event.setId(eventId);
        event.setClub(eventDTO.getClub());
        eventService.updateEvent(event);
        return "redirect:/event";
    }
    // @Valid @ModelAttribute("event") EventDTO event - параметр метода означає, що дані, надіслані у формі, мають бути перетворені у об'єкт EventDTO
    // BindingResult - об'єкт, що використовується для перевірки результатів валідації
    // EventDTO eventDTO = eventService.findByEventId(eventId) - витягує наявний захід (event) із сервісу, використовуючи його ідентифікатор
    // event.setId(eventId) - встановлює ідентифікатор заходу в event, щоб оновити наявний захід
    // event.setClub(eventDTO.getClub()) - встановлює setClub для event, для збереження зв'язку між ними
    // eventService.updateEvent(event) - викликає метод updateEvent для збереження оновлених даних заходу

    @GetMapping("/event/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Integer eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/event";
    }
}
