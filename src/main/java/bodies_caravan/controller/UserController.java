package bodies_caravan.controller;

import bodies_caravan.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {
    private Logger log = LoggerFactory.getLogger(getClass());
    private  List<User> userInfo = new ArrayList<>();

    @GetMapping("/userProfile")
    public List<User> index(){
        return userInfo;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUniqueUser(@PathVariable Long id){
        var userFiltred = userInfo.stream().filter( user -> user.getIdUser().equals(id))
                .findFirst();
        System.out.println("Buscando usuário " + id);
        if(userFiltred.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userFiltred.get());
    }


    @PostMapping("/user")
    public ResponseEntity create(@RequestBody User user){
        userInfo.add(user);
        System.out.println("Usuário cadastrado " + user);
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping("{id}")
    public User updateUserInfo(@PathVariable Long id, @RequestBody User user) {
        log.info("Atualizando conta de: " + id + " " + user);

        userInfo.remove(getUserInfo(id));
        user.setIdUser(id);
        userInfo.add(user);

        return user;
    }

    @DeleteMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Processando o Deletar do usu[ario de ID: " + id);
        userInfo.remove(id);
    }

    private User getUserInfo(Long id) {
        return userInfo.stream()
                .filter(us -> us.getIdUser().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Informações do Usuário  " + id + "  não encontradas")
                );
    }
}
