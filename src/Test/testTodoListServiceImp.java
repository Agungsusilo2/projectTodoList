package Test;

import Domain.Categories;
import Entity.TodoList;
import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;

import java.time.LocalDate;

public class testTodoListServiceImp {
    public static void main(String[] args) {
        TodoListRepositoryImp todoListRepositoryImp = new TodoListRepositoryImp();
        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(todoListRepositoryImp);

        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDate.of(2023,12,4),
                Categories.MEDIUM));

        todoListServiceImp.AddTodoListService(new TodoList("Belajar Todo","Belajar todo", LocalDate.of(2023,11,9),
                Categories.IMPORTANT));

        // Ambil daftar tugas yang sudah diurutkan
        TodoList[] sortedTasks = todoListServiceImp.getTodoListSortedCategories();

        // Tampilkan daftar tugas yang sudah diurutkan
        for (TodoList task : sortedTasks) {
            if (task != null) {
                System.out.println("Task: " + task.getAddTask() + ", Category: " + task.getCategories());
            }
        }
    }
}

