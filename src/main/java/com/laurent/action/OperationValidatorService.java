package com.laurent.action;

import java.util.List;

public interface OperationValidatorService<T> {
    List<InvalidReason> validate(T toValidate);
}
