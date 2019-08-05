/*
 * TaskDeadlineConstraintValidator
 * 
 * Project: Training
 * 
 * Copyright 2015 by ELCA
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package vn.elca.training.dom.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.elca.training.dom.Task;

/**
 * @author vlp
 *
 */
public class TaskDeadlineConstraintValidator implements ConstraintValidator<TaskDeadlineValid, Task> {
    @Override
    public void initialize(TaskDeadlineValid constraintAnnotation) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Task value, ConstraintValidatorContext context) {
        return (value.getProject().getFinishingDate().compareTo(value.getDeadline()) >= 0);
    }

}
