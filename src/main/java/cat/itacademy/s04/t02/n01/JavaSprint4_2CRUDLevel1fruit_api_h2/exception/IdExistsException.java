package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception;

public class IdExistsException extends RuntimeException {
    public IdExistsException(Long id) {
        super(id + " already exists");
    }
}
