package com.example.springMVC.service;

import com.example.springMVC.DTO.ClubDTO;
import com.example.springMVC.models.Club;


import java.util.List;

public interface ClubService { // цей інтерфейся потрібен лише для імплементації
    List<ClubDTO> findAllClubs();
    Club saveClub(ClubDTO clubDTO);
    ClubDTO findClubById(Integer clubId);
    void updateCLub(ClubDTO club);
    void delete(int clubId);
    List<ClubDTO> searchClubs(String query);
}
