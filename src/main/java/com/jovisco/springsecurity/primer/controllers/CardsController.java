package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.Cards;
import com.jovisco.springsecurity.primer.repositories.CardsRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController {

  private final CardsRepository cardsRepository;

  @GetMapping("/myCards")
  public List<Cards> getCardDetails(@RequestParam long id) {
    List<Cards> cards = cardsRepository.findByCustomerId(id);
    return cards;
  }
}