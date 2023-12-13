package Test;

import Domain.Categories;
import Entity.TodoList;
import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class testTodoListServiceImp {
    public static void main(String[] args) {
        TodoListRepositoryImp todoListRepositoryImp = new TodoListRepositoryImp();
        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(todoListRepositoryImp);

        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,12,4,12,12),
                Categories.MEDIUM));

        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT)); todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));
        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDateTime.of(2023,11,9,12,12),
                Categories.IMPORTANT));








        TodoList[] sortedTasks = todoListServiceImp.getTodoListSortedCategories();


        for (TodoList task : sortedTasks) {
            if (task != null) {
                System.out.println("Task: " + task.getAddTask() + ", Category: " + task.getCategories());
            }
        }
    }
}

