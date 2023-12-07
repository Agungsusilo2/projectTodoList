package Test;

import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;
import View.ViewTodoList;

public class testTodoListView {


    public static void main(String[] args) {
        TodoListRepositoryImp todoListRepositoryImp = new TodoListRepositoryImp();
        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(todoListRepositoryImp);
        ViewTodoList viewTodoList = new ViewTodoList(todoListServiceImp);

        viewTodoList.viewTodoList();
    }
}
