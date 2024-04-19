package sesc.assement.student.repositories;

import sesc.assement.student.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findStudentByEmail (String email);
    //User findUserByUserName(String email);
}