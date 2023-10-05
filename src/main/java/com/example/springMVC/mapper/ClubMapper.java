package com.example.springMVC.mapper;

import com.example.springMVC.DTO.ClubDTO;
import com.example.springMVC.models.Club;

import java.util.stream.Collectors;

import static com.example.springMVC.mapper.EventMapper.mapToEventDTO;


public class ClubMapper {
    public static Club mapToClub(ClubDTO club) {
        Club clubDTO = Club.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photourl(club.getPhotourl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .build();
        return clubDTO;
    }
    public static ClubDTO mapToClubDTO(Club club){
        ClubDTO clubDTO = ClubDTO.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photourl(club.getPhotourl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map(event -> mapToEventDTO(event)).collect(Collectors.toList()))
                .build();
        // ClubDTO.builder() - створює об'єкт ClubDTO.Builder.
        // Об'єкти створені з використанням патерну Builder, надають зручний спосіб налаштовувати параметри перед його фактичним створенням
        //  .build(). Виклик цього методу завершує конфігурацію об'єкту ClubDTO та фактично створює новий екземпляр ClubDTO з заданими параметрами
        return  clubDTO;
    }
}
