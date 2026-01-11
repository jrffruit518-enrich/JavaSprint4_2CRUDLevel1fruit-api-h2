package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception;

public class IdNotExistsException extends RuntimeException {
    public IdNotExistsException(Long id) {
        super(id + " does not exist");
    }
}
