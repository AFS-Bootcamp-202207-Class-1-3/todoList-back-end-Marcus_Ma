package com.example.todolist;

import com.example.todolist.dto.TodoRequest;
import com.example.todolist.entity.Todo;
import com.example.todolist.mapper.TodoMapper;
import com.example.todolist.repository.JpaTodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureMockMvc
@SpringBootTest
//@ActiveProfiles("test")
class TodoControllerTests {
    @Resource
    MockMvc client;
    @Autowired
    JpaTodoRepository todoRepository;
    @Autowired
    TodoMapper todoMapper;
    @BeforeEach
    public void cleanAll(){
        todoRepository.deleteAll();
    }

    @Test
    void should_return_allToDoList_when_getAllTodo_given_none() throws Exception {
        // given
        Todo todo1 = new Todo(1,"test1",false);
        Todo todo2 = new Todo(2,"test2",false);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        // when then
        client.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].context").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].done").value(false));
    }
    @Test
    void should_return_Todo_when_addNewTodo_given_NewTodo() throws Exception {
        // given
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setContext("test3");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(todoRequest);
        // when then
        client.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.context").value("test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
        //        should
        List<Todo> allTodos = todoRepository.findAll();
        assertThat(allTodos, hasSize(1));
        assertThat(allTodos.get(0).getContext(), equalTo("test3"));
    }
    @Test
    void should_return_rightTodo_when_updateTodoDone_given_Done() throws Exception {
        // given
        Todo todo =new Todo(1,"test4",false);
        Todo oldTodo = todoRepository.save(todo);
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setDone(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(todoRequest);
        // when then
        client.perform(MockMvcRequestBuilders.put("/todos/{id}",oldTodo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(oldTodo.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.context").value("test4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(true));
    }
}
