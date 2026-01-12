package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitNotExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceImpTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImp fruitService;




    @Test
    void create_whenNotExists_shouldCreateFruit() {
        Fruit fruit = new Fruit(null, "apple", 15);

        when(fruitRepository.existsByName("apple")).thenReturn(false);
        when(fruitRepository.save(any(Fruit.class))).thenReturn(new Fruit(1L, "apple", 15));

        Fruit savedFruit = fruitService.createFruit(fruit);

        assertNotNull(savedFruit);
        verify(fruitRepository).existsByName("apple"); // 验证调用了新方法
        verify(fruitRepository).save(fruit);
    }

    @Test
    void create_whenExists_shouldThrowException() {
        Fruit fruit = new Fruit(null, "apple", 15);

        when(fruitRepository.existsByName("apple")).thenReturn(true);

        assertThrows(FruitExistsException.class, () -> fruitService.createFruit(fruit));

        verify(fruitRepository).existsByName("apple");
        verify(fruitRepository, never()).save(any()); // 确保没有调用 save
    }


    @Test
    void updateFruit_shouldThrowException_whenNameIsBlank() {
        Fruit fruit = new Fruit(null, "apple", 10);

        assertThrows(IllegalArgumentException.class, () ->
                fruitService.updateFruit(" ", fruit)
        );

        assertThrows(IllegalArgumentException.class, () ->
                fruitService.updateFruit(null, fruit)
        );

        verifyNoInteractions(fruitRepository);
    }

    // 2️⃣ fruit 为 null
    @Test
    void updateFruit_shouldThrowException_whenFruitIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                fruitService.updateFruit("apple", null)
        );

        verifyNoInteractions(fruitRepository);
    }

    // 3️⃣ 原 fruit 不存在
    @Test
    void updateFruit_shouldThrowFruitNotExistsException_whenFruitNotFound() {
        String name = "apple";
        Fruit updateData = new Fruit(null, "apple", 20);

        when(fruitRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(FruitNotExistsException.class, () ->
                fruitService.updateFruit(name, updateData)
        );

        verify(fruitRepository).findByName(name);
        verify(fruitRepository, never()).save(any());
    }

    // 4️⃣ 修改 name，但新 name 已存在
    @Test
    void updateFruit_shouldThrowFruitExistsException_whenNewNameAlreadyExists() {
        String name = "apple";

        Fruit existingFruit = new Fruit(1L, "apple", 10);
        Fruit updateData = new Fruit(null, "banana", 20);

        when(fruitRepository.findByName(name))
                .thenReturn(Optional.of(existingFruit));
        when(fruitRepository.findByName("banana"))
                .thenReturn(Optional.of(new Fruit(2L, "banana", 15)));

        assertThrows(FruitExistsException.class, () ->
                fruitService.updateFruit(name, updateData)
        );

        verify(fruitRepository).findByName(name);
        verify(fruitRepository).findByName("banana");
        verify(fruitRepository, never()).save(any());
    }


    @Test
    void updateFruit_shouldUpdateWeight_whenNameIsSame() {
        String name = "apple";

        Fruit existingFruit = new Fruit(1L, "apple", 10);
        Fruit updateData = new Fruit(null, "apple", 25);

        when(fruitRepository.findByName(name))
                .thenReturn(Optional.of(existingFruit));
        when(fruitRepository.save(any(Fruit.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Fruit result = fruitService.updateFruit(name, updateData);

        assertEquals("apple", result.getName());
        assertEquals(25, result.getWeightInKilos());

        verify(fruitRepository).findByName(name);
        verify(fruitRepository).save(existingFruit);
    }

    //
    @Test
    void updateFruit_shouldUpdateNameAndWeight_whenNewNameIsAvailable() {
        String name = "apple";

        Fruit existingFruit = new Fruit(1L, "apple", 15);
        Fruit updateData = new Fruit(null, "banana", 30);

        when(fruitRepository.findByName(name))
                .thenReturn(Optional.of(existingFruit));
        when(fruitRepository.findByName("banana"))
                .thenReturn(Optional.empty());
        when(fruitRepository.save(any(Fruit.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Fruit result = fruitService.updateFruit(name, updateData);

        assertEquals("banana", result.getName());
        assertEquals(30, result.getWeightInKilos());

        verify(fruitRepository).findByName(name);
        verify(fruitRepository).findByName("banana");
        verify(fruitRepository).save(existingFruit);
    }

    @Test
    void deleteFruitByName_shouldThrowException_whenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                fruitService.deleteFruitByName(" ")
        );

        verifyNoInteractions(fruitRepository);
    }

    @Test
    void deleteFruitByName_shouldThrowFruitNotExistsException_whenFruitNotFound() {
        String name = "apple";

        when(fruitRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(FruitNotExistsException.class, () ->
                fruitService.deleteFruitByName(name)
        );

        verify(fruitRepository).findByName(name);
        verify(fruitRepository, never()).delete(any());
    }

    @Test
    void deleteFruitByName_shouldDeleteFruit_whenFruitExists() {
        String name = "banana";
        Fruit fruit = new Fruit(1L, name, 12);

        when(fruitRepository.findByName(name)).thenReturn(Optional.of(fruit));

        fruitService.deleteFruitByName(name);

        verify(fruitRepository).findByName(name);
        verify(fruitRepository).delete(fruit);
    }



}
