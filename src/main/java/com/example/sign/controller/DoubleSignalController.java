package com.example.sign.controller;

import com.example.sign.dto.DoubleSignalDTO;
import com.example.sign.model.DoubleSignal;
import com.example.sign.service.DoubleSignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lines")
public class DoubleSignalController {

    @Autowired
    private DoubleSignalService doubleSignalService;

    @GetMapping
    public List<DoubleSignalDTO> getAllLines() {
        return doubleSignalService.getAllLines();
    }
}
