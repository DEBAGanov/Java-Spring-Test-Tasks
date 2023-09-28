package com.example.numbergenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.numbergenerator.entity.NumberEntity;
import com.example.numbergenerator.repository.NumberRepository;

import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/number")
public class NumberController {

    @Autowired
    private NumberRepository numberRepository;

    private int currentIndex;

    @GetMapping("/random")
    public String getRandomNumber() {
        List<NumberEntity> numbers = numberRepository.findAll();
        if (numbers.isEmpty()) {
            return "No numbers found";
        }
        Random random = new Random();
        currentIndex = random.nextInt(numbers.size());
        NumberEntity number = numbers.get(currentIndex);
        return number.getNumber();
    }

    @GetMapping("/next")
    public String getNextNumber() {
        List<NumberEntity> numbers = numberRepository.findAll();
        if (numbers.isEmpty()) {
            return "No numbers found";
        }
        if (currentIndex >= numbers.size()) {
            currentIndex = 0;
        }
        NumberEntity number = numbers.get(currentIndex);
        currentIndex++;
        return number.getNumber();
    }
}