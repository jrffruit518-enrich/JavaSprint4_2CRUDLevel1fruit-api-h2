package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.repository;

import cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FruitRepository extends JpaRepository<Fruit,Long> {
    Optional<Fruit> findByName(String name);
    boolean existsByName(String name);  // 可用于 create 验证
    void deleteByName(String name);
}
