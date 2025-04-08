package bodies_caravan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bodies_caravan.model.User;
import bodies_caravan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers(UserFilter filter) {
        var probe = User.builder()
                .name(filter.name)
                .email(filter.email)
                .build();

        var matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(StringMatcher.CONTAINING);

        var example = Example.of(probe, matcher);

        return userRepository.findAll(example);
    }

    @GetMapping("/userProfile/{id}")
    public ResponseEntity<User> getUniqueUser(@PathVariable Long id) {
        var userFiltered = userRepository.findById(id);
        return userFiltered.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUserInfo(@PathVariable Long id, @RequestBody User user) {
        log.info("Atualizando informações do usuário com ID: " + id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        user.setIdUser(id);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Processando a exclusão do usuário de ID: " + id);
        userRepository.delete(getUserRepository(id));
    }

    record UserFilter(String name, String email) {
    }

    private User getUserRepository(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Histórico de Busca do Usuário  " + id + "  não encontrados"));
    }
}
