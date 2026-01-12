package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitNotExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.repository.FruitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FruitServiceImp implements FruitService {
    private final FruitRepository fruitRepository;

    @Override
    public Fruit createFruit(Fruit fruit) {
        Objects.requireNonNull(fruit,"Fruit cannot be null");
        if (fruit.getName() == null || fruit.getName().isBlank()) {
            throw new IllegalArgumentException("Fruit name cannot be empty");
        }
        if (fruitRepository.existsByName(fruit.getName())) {
            throw new FruitExistsException(fruit.getName());
        }
        return fruitRepository.save(fruit);
    }

    @Override
    public List<Fruit> findAllFruits() {
        return fruitRepository.findAll();
    }

    @Override
    public Fruit findFruitByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Search name cannot be empty");
        }
        return fruitRepository.findByName(name)
                .orElseThrow(()->new FruitNotExistsException(name));
    }

    @Override
    public Fruit updateFruit(String name, Fruit fruit) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Target name cannot be empty");
        }
        if (fruit == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }
        Fruit existingFruit = fruitRepository.findByName(name)
                .orElseThrow(() -> new FruitNotExistsException(name));

        if (!existingFruit.getName().equals(fruit.getName())) {
            if (fruitRepository.findByName(fruit.getName()).isPresent()) {
                throw new FruitExistsException(fruit.getName());
            }
        }
        existingFruit.setName(fruit.getName());
        existingFruit.setWeightInKilos(fruit.getWeightInKilos());
        return fruitRepository.save(existingFruit);
    }

    @Override
    @Transactional
    public void deleteFruitByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Target name cannot be empty");
        }
        Fruit fruit = fruitRepository.findByName(name)
                .orElseThrow(()->new FruitNotExistsException(name +" does not exist."));

        fruitRepository.delete(fruit);
    }
}

