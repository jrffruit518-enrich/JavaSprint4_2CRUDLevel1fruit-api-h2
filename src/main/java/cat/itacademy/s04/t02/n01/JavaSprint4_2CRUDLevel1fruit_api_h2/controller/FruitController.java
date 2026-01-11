package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.controller;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitRequest;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitResponse;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service.FruitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FruitResponse createFruit(@RequestBody @Valid FruitRequest fruitRequest) {
        Fruit savedFruit = fruitService.createFruit(mapToEntity(fruitRequest));
        return new FruitResponse(savedFruit.getId(),savedFruit.getName(),savedFruit.getWeightInKilos());

    }



    private Fruit mapToEntity(FruitRequest request) {
        Fruit fruit = new Fruit();
        fruit.setName(request.name());
        fruit.setWeightInKilos(request.weightInKilos());
        return fruit;
    }


}
