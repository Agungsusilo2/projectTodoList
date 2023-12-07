package Service;

import Entity.TodoList;

import java.util.UUID;

public interface TodoListService {
    void ShowTodoListService();
    public void AddTodoListService(TodoList todoList);
    boolean RemoveTodoListService(Integer number);
    boolean UpdateTodoListService(UUID number, TodoList updateTodoList);
}
