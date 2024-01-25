package com.example.sign.service;

import com.example.sign.dto.DoubleSignalDTO;
import com.example.sign.model.DoubleSignal;
import com.example.sign.repository.DoubleSignalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoubleSignalService {

    @Autowired
    private DoubleSignalRepository doubleSignalRepository;

    public List<DoubleSignalDTO> getAllLines() {
        return doubleSignalRepository.findAll().stream()
                .map(ds -> {
                    DoubleSignalDTO dto = new DoubleSignalDTO();
                    dto.setUser1XCoordinate(ds.getUser1XCoordinate());
                    dto.setUser1YCoordinate(ds.getUser1YCoordinate());
                    dto.setUser1ZCoordinate(ds.getUser1ZCoordinate());
                    dto.setUser2XCoordinate(ds.getUser2XCoordinate());
                    dto.setUser2YCoordinate(ds.getUser2YCoordinate());
                    dto.setUser2ZCoordinate(ds.getUser2ZCoordinate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}