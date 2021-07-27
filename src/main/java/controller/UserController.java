package controller;

import lombok.extern.slf4j.Slf4j;
import model.User;
import model.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ModelAndView createUser(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String email) {
        ModelAndView modelAndView = new ModelAndView("entities");
        User user = new UserImpl(name, email);

        modelAndView.addObject("entities", user);
        modelAndView.addObject("message", "create entities");
        return modelAndView;
    }

    @PutMapping("/{id}")
    public ModelAndView updateUser(@PathVariable long id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String email) {
        ModelAndView modelAndView = new ModelAndView("entities");
        User user = userService.getUserById(id);
        if (Objects.nonNull(user)) {
            user.setName(name);
            user.setEmail(email);
            user = userService.updateUser(user);
            modelAndView.addObject("entities", user);
            modelAndView.addObject("message", "update entities");
        } else {
            modelAndView.addObject("message", "not found entity");
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteUser(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("entities");
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            modelAndView.addObject("message", "delete entities");
        } else {
            modelAndView.addObject("message", "not found entity");
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getUserById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("entities");
        User user = userService.getUserById(id);
        if (Objects.nonNull(user)) {
            modelAndView.addObject("entities", user);
            modelAndView.addObject("message", "found entity");
        } else {
            modelAndView.addObject("message", "not found entity");
        }
        return modelAndView;
    }

    @GetMapping("/name/{name}")
    public ModelAndView getUsersByName(@PathVariable String name,
                                       @RequestParam(required = false, defaultValue = "100") int pageSize,
                                       @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("entities");
        List<User> users = userService.getUsersByName(name, pageSize, pageNum);
        if (!users.isEmpty()) {
            modelAndView.addObject("entities", users);
            modelAndView.addObject("message", "found entity");
        } else {
            modelAndView.addObject("message", "not found entity");
        }
        return modelAndView;
    }

    @GetMapping("/email/{email}")
    public ModelAndView getUsersByEmail(@PathVariable String email) {
        ModelAndView modelAndView = new ModelAndView("entities");
        User user = userService.getUserByEmail(email);
        if (Objects.nonNull(user)) {
            modelAndView.addObject("entities", user);
            modelAndView.addObject("message", "found entity");
        } else {
            modelAndView.addObject("message", "not found entity");
        }
        return modelAndView;
    }
}
