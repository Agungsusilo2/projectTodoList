package Test;

import Domain.Categories;
import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;

public class testTodoListServiceImp {
    public static void main(String[] args) {
        TodoListRepositoryImp todoListRepositoryImp = new TodoListRepositoryImp();
        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(todoListRepositoryImp);

        Categories categories = Categories.INFORMATIONAL;


        todoListServiceImp.ShowTodoListService();


        todoListServiceImp.RemoveTodoListService(1);

        todoListServiceImp.ShowTodoListService();
    }
}
