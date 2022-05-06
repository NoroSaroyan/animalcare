package ru.animalcare.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.models.Animal;
import ru.animalcare.repositories.AnimalRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public List<Animal> findAll(){
        return animalRepository.findAll();
    }

    public Animal findAnimalById(Long id){
        return animalRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Animal for ID: " + id + " not found"));
    }
}
