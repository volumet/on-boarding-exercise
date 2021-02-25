package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.training.util.ApplicationMapper;

public abstract class AbstractService {
    @Autowired
    ApplicationMapper mapper;
}
