package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.UserDto;
import vn.elca.training.model.entity.User;
import vn.elca.training.service.UserService;
import vn.elca.training.util.Mapper;

import java.util.List;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/id/{id}")
    public User findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/{username}")
    public UserDto findOne(@PathVariable String username) {
        User user = userService.findOne(username);
        return Mapper.userToUserDto(user);
    }

    @PostMapping("/{username}/addTasks")
    public UserDto addTasks(@RequestBody List<Long> taskIds, @PathVariable String username) {
        if (CollectionUtils.isEmpty(taskIds) || StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Invalid request!");
        }

        User user = userService.addTasksToUser(taskIds, username);
        return Mapper.userToUserDto(user);
    }
}
