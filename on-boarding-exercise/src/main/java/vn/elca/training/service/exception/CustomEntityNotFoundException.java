package vn.elca.training.service.exception;

import javax.persistence.EntityNotFoundException;

public class CustomEntityNotFoundException extends EntityNotFoundException {

    private Long id;
    private Class<?> clazz;

    public CustomEntityNotFoundException(Long id, Class<?> clazz) {
        super("Cannot find " + clazz.getSimpleName() + " with id " + id);
        this.id = id;
        this.clazz = clazz;
    }
}
