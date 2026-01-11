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
        Fruit fruit = new Fruit(1505L,"apple" ,15);

        when(fruitRepository.findById(1505L))
                .thenReturn(Optional.of(fruit));

        assertThrows(IdExistsException.class,()->fruitRepository.save(fruit));

        verify(fruitRepository, never()).save(any());

    }

}
