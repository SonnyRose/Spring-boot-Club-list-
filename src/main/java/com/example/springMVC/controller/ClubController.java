package com.example.springMVC.controller;

import com.example.springMVC.DTO.ClubDTO;
import com.example.springMVC.models.Club;
import com.example.springMVC.models.UserEntity;
import com.example.springMVC.security.SecurityUtil;
import com.example.springMVC.service.ClubService;
import com.example.springMVC.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller // анотація, що означає, що цей клас буде обробляти GET запити
public class ClubController {
    private ClubService clubService;
    private UserService userService;

    @Autowired
    public ClubController(UserService userService, ClubService clubController) {
        this.userService = userService;
        this.clubService = clubController;
    }

    @GetMapping("/club") // метод listClub буде обробляти GET запити за посиланням /club
    private String listClubs(Model model){ // матод, що обробляє GET запити, що приймає параметр Model model, який використовується для передачі даних
        UserEntity user = new UserEntity();
        List<ClubDTO> club = clubService.findAllClubs(); // викликає метод findAllClubs(), щоб отримати список об'єктів ClubDTO з бази даних
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("club", club); // додає список клубів у об'єкт Model з іменем club. Зроблено для тогоЮ щоб передавати дані в уявлення
        return  "clubs-list";
    }
    @GetMapping("/club/{clubId}")
    public String clubDetail(@PathVariable("clubId") int clubId, Model model){
        UserEntity user =new UserEntity();
        ClubDTO clubDTO = clubService.findClubById(clubId);
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("club", clubDTO);
        return "club-detail";
    }
    // CRUD READ
    // цей учсток коду є формою, яку треба заповнити та зберігає заповнену інформацію у базу даних
    @GetMapping("/club/new")
    public String creaetClubForm(Model model){
        Club club = new Club();
        model.addAttribute("club", club);
        return "club-create";
    }
    // CRUD DELETE
    @GetMapping("/club/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") int clubId){
    clubService.delete(clubId);
    return "redirect:/club";
    }
    //Searching clubs on a web page
    @GetMapping("/club/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model){
    List<ClubDTO> club = clubService.searchClubs(query);
    model.addAttribute("club", club);
    return  "clubs-list";
    }
    //CRUD CREATE
    // цей участок коду виконує функцію додавання нового об'єкту на головну сторінку
    @PostMapping("/club/new") // анотація що обробляє POST запити за вказаним посиланням
    public String saveClub(@Valid @ModelAttribute("club") ClubDTO clubDTO, Model model, BindingResult bindingResult){
    // BindingResult - інтерфейс який диктує, як об'єкт, що зберігає результат валідації, повинен зберігати та отримувати результат валідації (помилки, спроба зв'язування із забороненими полями тощо)
        // ModelAtribute дає зрозуміти Sping'y, що дані відправдені у post запиті мають бути прив'язані до об'єкту club
    if (bindingResult.hasErrors()) {
        model.addAttribute("club", clubDTO);
        return "club-create";
    }
    clubService.saveClub(clubDTO); // сервіс для збереження об'єкту club
    return "redirect:/club"; // після успішного збереження даних club, перенаправляє на головну сторінку /club
    }
    //CRUD UPDATE
    @GetMapping("/club/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") Integer clubId, Model model){ // PathVariable - анотація, що використовується для діставання значення змінної в URL та прив'язувати параметри до clubId
    // Наприклад : "/club/123/edit" - clubId = 123
    ClubDTO club = clubService.findClubById(clubId);
    model.addAttribute("club", club);
    return "club-edit";
    }
    @GetMapping("/clubs")
    public String redirectToClubWithContinueParam(HttpServletRequest request) {
        String continueParam = request.getParameter("continue");
        if (continueParam != null && !continueParam.isEmpty()) {
            return "redirect:/club?continue=" + continueParam;
        } else {
            return "redirect:/club";
        }
    }
    // Не знайшов помилку, у посиланні замість http://localhost:8091/club?continue виводило http://localhost:8091/clubs?continue
    // Тому вирішив зробити щось типу redirecty
    //CRUD UPDATE
    @PostMapping("/club/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Integer clubId,
                             @Valid @ModelAttribute("club") ClubDTO club, BindingResult bindingResult,
                             Model model){
        // анотація @Valid - перевіряє чи співпадають значення вписані зі значеннями задані у ClubDTO
        // анотація ModelAttribute використовується дл пов'язаних даних, відправлених у тіло POST запиту, з об'єктом club.
        // атрибут club вказує на ім'я атрибуту, під яким дані будуть доступні Model
        if (bindingResult.hasErrors()){
            model.addAttribute("club", club);
            return "club-edit";
        }
        club.setId(clubId);
        clubService.updateCLub(club);
        return "redirect:/club";
    }

}
