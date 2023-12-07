package Test;

import Domain.Categories;
import Entity.TodoList;
import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;

import java.time.LocalDate;

public class testTodoListRepositoryImp {
    public static void main(String[] args) {
        TodoListRepositoryImp todoListRepositoryImp = new TodoListRepositoryImp();
        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(todoListRepositoryImp);

        LocalDate current = LocalDate.of(2023,12,14);
        Categories categories = Categories.INFORMATIONAL;
        todoListRepositoryImp.todoLists[0] = new TodoList("Belajar PHP",
                "Kita belajar php sampai 1 minggu",current,categories);
        todoListServiceImp.ShowTodoListService();
        todoListServiceImp.ShowTodoListService();

    }
}
