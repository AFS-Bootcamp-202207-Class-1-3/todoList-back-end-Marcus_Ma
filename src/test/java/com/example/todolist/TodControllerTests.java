package com.example.todolist;

import com.example.todolist.repository.JpaTodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TodControllerTests {
    @Resource
    Mock client;
    @Autowired
    JpaTodoRepository todoRepository;

    @BeforeEach
    public void cleanAll(){
        todoRepository.deleteAll();
    }



}
