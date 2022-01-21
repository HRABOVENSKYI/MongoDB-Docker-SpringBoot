package ua.lviv.dataart.springmongo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.dataart.springmongo.dao.StudentDao;
import ua.lviv.dataart.springmongo.model.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentDao studentDao;

    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
}
