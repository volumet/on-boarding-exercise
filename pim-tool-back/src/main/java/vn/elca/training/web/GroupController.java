package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
public class GroupController extends AbstractApplicationController {
    @Autowired
    GroupService groupService;

    @CrossOrigin
    @GetMapping("/load")
    public List<GroupDto> load() {
        return groupService.getListGroup()
                .stream()
                .map(mapper::groupToGroupDto)
                .collect(Collectors.toList());
    }
}
