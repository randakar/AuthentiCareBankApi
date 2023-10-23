package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.controller.model.CardModel;
import org.kraaknet.authenticarebankapi.repository.database.CardRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.CardEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.kraaknet.authenticarebankapi.service.mapper.CardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;
    private final CardMapper mapper;


    public List<CardModel> findCardsByOwner(CustomerEntity customerEntity) {
        List<CardEntity> result = repository.findAllByOwner(customerEntity);
        return mapper.toCardModels(result);
    }
}
