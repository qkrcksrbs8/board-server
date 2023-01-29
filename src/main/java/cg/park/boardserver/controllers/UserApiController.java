package cg.park.boardserver.controllers;

import cg.park.boardserver.model.User;
import cg.park.boardserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    //@RestController
    //@RestController
    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> all() {
        List<User> users = repository.findAll();
        users.get(0).getBoards().size();
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    User.setTitle(newUser.getTitle());
//                    User.setContent(newUser.getContent());
                    user.getBoards().clear();
                    user.setBoards(newUser.getBoards());
                    user.getBoards().stream().forEach(board -> {board.setUser(user);});
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

}