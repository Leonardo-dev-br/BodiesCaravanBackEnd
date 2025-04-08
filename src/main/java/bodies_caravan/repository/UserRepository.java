package bodies_caravan.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import bodies_caravan.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
