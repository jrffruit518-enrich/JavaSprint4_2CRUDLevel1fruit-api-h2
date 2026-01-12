package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.controller;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitRequest;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitResponse;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service.FruitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.List;

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

    @GetMapping()
    public List<Fruit> findAll() {
        return fruitService.findAllFruits();
    }

    @GetMapping("/{name}")
    public FruitResponse findByName(@PathVariable String name) {
        Fruit fruit = fruitService.findFruitByName(name);
        return new FruitResponse(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());

    }

    @PutMapping("/{name}")
    public FruitResponse updateFruit(
            @PathVariable String name,
            @RequestBody @Valid FruitRequest request
    ) {
        Fruit updateFruit = fruitService.updateFruit(name,mapToEntity(request));
        return new FruitResponse(updateFruit.getId(),
                updateFruit.getName(),
                updateFruit.getWeightInKilos());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteFruit(@PathVariable String name) {
        fruitService.deleteFruitByName(name);
        return ResponseEntity.noContent().build(); // 204
    }





    private Fruit mapToEntity(FruitRequest request) {
        Fruit fruit = new Fruit();
        fruit.setName(request.name());
        fruit.setWeightInKilos(request.weightInKilos());
        return fruit;
    }




}
