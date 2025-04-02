package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.exceptions.DatabaseException;
import com.devsuperior.demo.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        return repository.findAllByOrderByNameAsc().stream().map(CityDTO::new).toList();
    }

    @Transactional
    public CityDTO save(CityDTO dto) {
        City newEntity = new City();
        BeanUtils.copyProperties(dto, newEntity);
        newEntity = repository.save(newEntity);
        return new CityDTO(newEntity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        City city = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        try {
            repository.delete(city);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
