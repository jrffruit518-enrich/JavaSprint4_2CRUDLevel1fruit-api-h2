package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.IdExistsException;
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
    private FruitServiceImp fruitServiceImp;

    @Test
    void create_whenIdExists_shouldThrowException() {
        // GIVEN: fruit id already exists
        Fruit fruit = new Fruit(1505L, "apple", 15);

        // WHEN: createFruit is called
        when(fruitRepository.findById(1505L))
                .thenReturn(Optional.of(fruit));

        // THEN: IdExistsException is thrown and repository.save() is never called
        assertThrows(IdExistsException.class, () -> fruitServiceImp.createFruit(fruit));

        verify(fruitRepository, never()).save(any());

    }

    @Test
    void create_whenIdNotExists_shouldCreateFruit() {
        // GIVEN: fruit id does NOT exist
        Fruit fruit = new Fruit(1505L, "apple", 15);

        when(fruitRepository.findById(1505L))
                .thenReturn(Optional.empty());

        when(fruitRepository.save(fruit)).thenReturn(fruit);

        Fruit savedFruit = fruitServiceImp.createFruit(fruit);

        //verify
        assertNotNull(savedFruit);
        assertNotNull(savedFruit.getId());
        assertEquals("apple",fruit.getName());
        verify(fruitRepository,times(1)).save(fruit);

    }


}
