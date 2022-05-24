package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.Animal;

@Data
@NoArgsConstructor
public class AnimalDto {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String condition;
    private String description;
    private String typeOfAnimal;

    public AnimalDto(Animal animal) {
        if(animal.getId() != null){
            this.id = animal.getId();
        }
        this.name = animal.getName();
        this.gender = animal.getGender();
        this.age = animal.getAge();
        this.condition = animal.getCondition();
        this.description = animal.getDescription();
        this.typeOfAnimal = animal.getTypeOfAnimal().getName();
    }
}