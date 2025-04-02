package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.repository.CityRepository;
import com.devsuperior.demo.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {

    private final EventRepository repository;

    private final CityRepository cityRepository;

    public EventService(EventRepository repository, CityRepository cityRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        Event event = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        updateEntity(event, dto);
        repository.save(event);
        return new EventDTO(event);
    }

    private void updateEntity(Event entity, EventDTO dto) {
        if (dto.getName() != null && !dto.getName().equals(entity.getName())) {
            entity.setName(dto.getName());
        }
        if (dto.getDate() != null && !dto.getDate().equals(entity.getDate())) {
            entity.setDate(dto.getDate());
        }
        if (dto.getUrl() != null && !dto.getUrl().equals(entity.getUrl())) {
            entity.setUrl(dto.getUrl());
        }
        if (entity.getCity() != null && dto.getCityId() != null && !entity.getCity().getId().equals(dto.getCityId())) {
            Optional<City> city = cityRepository.findById(dto.getCityId());
            city.ifPresent(entity::setCity);
        }
    }
}
