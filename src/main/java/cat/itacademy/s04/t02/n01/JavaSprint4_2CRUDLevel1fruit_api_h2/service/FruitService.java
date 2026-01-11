package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitRequest;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitResponse;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;

import java.util.List;

public interface FruitService {

    Fruit createFruit(Fruit fruit);
    List<Fruit> findAllFruits();
    Fruit findFruitByName(String name);
    Fruit updateFruit(String name, Fruit fruit);
    void deleteFruitByName(String name);
}
