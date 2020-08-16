package vn.elca.training.util;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;

public class Mapper {
    public Mapper() {
        // Utility class
    }

    public static ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFinishingDate(entity.getFinishingDate());

        return dto;
    }
}
