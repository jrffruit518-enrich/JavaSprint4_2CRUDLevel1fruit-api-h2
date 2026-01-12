package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.controller;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.DTO.FruitRequest;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception.FruitNotExistsException;
import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.service.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // 使用新的 API
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockitoBean
    private FruitService fruitService;

    @Test
    void createFruit_whenValidRequest_shouldReturnFruit() throws Exception {
        FruitRequest request = new FruitRequest("apple", 15);
        Fruit savedFruit = new Fruit();
        savedFruit.setId(1L);
        savedFruit.setName("apple");
        savedFruit.setWeightInKilos(15);
        when(fruitService.createFruit(any(Fruit.class))).thenReturn(savedFruit);


        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // 或者 .isOk()，取决于你的 Controller
                .andExpect(jsonPath("$.name").value("apple"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createFruit_whenNameExists_shouldReturn409() throws Exception {
        FruitRequest request = new FruitRequest("apple", 15);

        when(fruitService.createFruit(any(Fruit.class)))
                .thenThrow(new FruitExistsException("apple"));

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("apple already exists"));
    }


    @Test
    void findAll_shouldReturnListOfFruits() throws Exception {
        List<Fruit> fruits = List.of(
                new Fruit(1L, "apple", 10),
                new Fruit(2L, "banana", 5)
        );

        when(fruitService.findAllFruits()).thenReturn(fruits);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("apple"));
    }

    @Test
    void findByName_whenFruitExists_shouldReturnFruit() throws Exception {
        Fruit fruit = new Fruit(1L, "apple", 15);
        // 确保 Mock 行为正确
        when(fruitService.findFruitByName("apple")).thenReturn(fruit);

        // 修正：URL 应该是 /fruits/apple 而不是使用 .param()
        mockMvc.perform(get("/fruits/{name}", "apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("apple"));
    }

    @Test
    void findByName_whenFruitNotExists_shouldReturn404() throws Exception {
        // 确保消息和异常类匹配
        when(fruitService.findFruitByName("pear"))
                .thenThrow(new FruitNotExistsException("pear"));

        // 修正 URL
        mockMvc.perform(get("/fruits/{name}", "pear"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("pear does not exist."));
    }


    @Test
    void updateFruit_shouldReturnUpdatedFruit_whenValid() throws Exception {
        String name = "apple";
        FruitRequest request = new FruitRequest("banana", 2);
        Fruit updatedFruit = new Fruit(1L, "banana", 2);

        when(fruitService.updateFruit(eq(name), any(Fruit.class)))
                .thenReturn(updatedFruit);

        mockMvc.perform(put("/fruits/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("banana"));
    }

    @Test
    void updateFruit_shouldReturn400_whenValidationFails() throws Exception {
        // 测试校验逻辑（如 @NotBlank），通常不需要 Mock Service，因为还没运行到那
        FruitRequest request = new FruitRequest("", -1);

        mockMvc.perform(put("/fruits/{name}", "apple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(fruitService);
    }


    @Test
    void deleteFruit_shouldReturn204_whenDeleted() throws Exception {
        doNothing().when(fruitService).deleteFruitByName("apple");

        mockMvc.perform(delete("/fruits/{name}", "apple"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFruit_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new FruitNotExistsException("apple"))
                .when(fruitService).deleteFruitByName("apple");

        mockMvc.perform(delete("/fruits/{name}", "apple"))
                .andExpect(status().isNotFound());
    }


}
