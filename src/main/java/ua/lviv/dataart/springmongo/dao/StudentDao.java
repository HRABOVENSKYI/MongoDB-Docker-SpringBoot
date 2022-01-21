package ua.lviv.dataart.springmongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.lviv.dataart.springmongo.model.Student;

import java.util.Optional;

public interface StudentDao extends MongoRepository<Student, String> {
    Optional<Student> findStudentByEmail(String email);
}
