package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.IdExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.repository.FruitRepository;
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
        if (fruitRepository.findByName(fruit.getName()).isPresent()) {
            throw new FruitExistsException(fruit.getName());
        }
        return fruitRepository.save(fruit);
    }

    @Override
    public List<Fruit> findAllFruits() {
        return fruitRepository.findAll();
    }

    @Override
    public Fruit findFruitById(Long id) {
        return null;
    }

    @Override
    public Fruit updateFruit(Long id, Fruit fruit) {
        return null;
    }

    @Override
    public void deleteFruitById(Long id) {

    }
}
