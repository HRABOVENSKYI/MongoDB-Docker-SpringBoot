package ua.lviv.dataart.springmongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ua.lviv.dataart.springmongo.dao.StudentDao;
import ua.lviv.dataart.springmongo.model.Address;
import ua.lviv.dataart.springmongo.model.Gender;
import ua.lviv.dataart.springmongo.model.Student;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentDao studentDao, MongoTemplate mongoTemplate) {
        return args -> {
            Address address = new Address("Ukraine", "79058", "Lviv");
            String email = "thr@gmail.com";
            Student student = new Student("Teodor", "Gr", email, Gender.MALE, address,
                    List.of("it", "philosophy"), BigDecimal.TEN, LocalDateTime.now());

//            usingMongoTemplateAndQuery(studentDao, mongoTemplate, email, student);

            studentDao.findStudentByEmail(email).ifPresentOrElse(s -> {
                System.out.println(s + " already exists");
            }, () -> {
                System.out.println("Inserting student " + student);
                studentDao.insert(student);
            });
        };
    }

    private void usingMongoTemplateAndQuery(StudentDao studentDao, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Student> students = mongoTemplate.find(query, Student.class);
        System.out.println(students);

        if (students.size() > 1) {
            throw new IllegalStateException("Found many students with email " + email);
        }

        if (students.isEmpty()) {
            System.out.println("Inserting student " + student);
            studentDao.insert(student);
        } else {
            System.out.println(student + " already exists");
        }
    }

}
