package com.example.springMVC.service.implement;

import com.example.springMVC.DTO.ClubDTO;
import com.example.springMVC.models.Club;
import com.example.springMVC.models.UserEntity;
import com.example.springMVC.repository.ClubRepository;
import com.example.springMVC.repository.UserRepository;
import com.example.springMVC.security.SecurityUtil;
import com.example.springMVC.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static com.example.springMVC.mapper.ClubMapper.mapToClub;
import static com.example.springMVC.mapper.ClubMapper.mapToClubDTO;

@AllArgsConstructor
@Component
@Service
public class ClubServiceImplement implements ClubService {
    private ClubRepository clubRepository;
    private UserRepository userRepository;

    @Autowired
    public ClubServiceImplement(UserRepository userRepository, ClubRepository clubRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public List<ClubDTO> findAllClubs() {
      List<Club> clubs = clubRepository.findAll();
      return clubs.stream().map(club -> mapToClubDTO(club)).collect(Collectors.toList());
      // створюємо з clubs потік
        // .map() використовується для перетворення кожного елемента потоку у новий об'єкт
        // та приймає в себе лямбда вираз, що вказує, як перетворити кожен елемент
        // (викликає метод mapToClubDTO, що перетворює clubs у DTO - Data Transfer Object)
        // після методу map операція collect викристовується для того щоб зібрати усі дані у List
    }
    @Override
    public Club saveClub(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();
        // SecurityUtil використовує SecurityContextHolder для отримання інформації про аутентифікованого корситувача
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDTO);
        club.setCreatedBy(user);
        // встановлюється користувач, який створив club

        return  clubRepository.save(club);
    }
    @Override
    public ClubDTO findClubById(Integer clubId) {
        Club club = clubRepository.findById(clubId).get(); //шукає об'єкти за id
        return mapToClubDTO(club); // зберігає об'єкт Club та конветує його у CLUBDTO
    }
    @Override
    public void updateCLub(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();
        // SecurityUtil використовує SecurityContextHolder для отримання інформації про аутентифікованого корситувача
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDTO); // за допомогою mapToClub об'єкт clubDTO перетворюється у об'єкт Club
        club.setCreatedBy(user);
        clubRepository.save(club); // зберігаємо об'єкт Club у ClubRepository

    }
    @Override
    public void delete(int clubId) {
        clubRepository.deleteById(clubId);
    }
    @Override
    public List<ClubDTO> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(club -> mapToClubDTO(club)).collect(Collectors.toList());
    }



}
