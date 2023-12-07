package Service;

import Entity.TodoList;
import Repository.TodoListRepositoryImp;

import java.util.UUID;


public class TodoListServiceImp implements TodoListService {
    private TodoListRepositoryImp todoListRepositoryImp;

    public TodoListServiceImp(TodoListRepositoryImp todoListRepositoryImp) {
        this.todoListRepositoryImp = todoListRepositoryImp;
    }

    @Override
    public void ShowTodoListService() {
        System.out.println("TODO LIST");
        Integer i = 0;
        for (var todo : this.todoListRepositoryImp.findAll()) {
            if (todo != null) {
                System.out.println(i + 1 + ". " + " No Identity " + todo.getNoIdentity() + " Todo : " + todo.getAddTask() +
                        " Description : " + todo.getDescription()
                        + " dueDate : " + todo.getDueDate().toString() + " Categories : " + todo.getCategories().toString());
                i++;
            }
        }
    }

    @Override
    public void AddTodoListService(TodoList todoList) {
        this.todoListRepositoryImp.save(todoList);
    }

    @Override
    public boolean RemoveTodoListService(Integer number) {
        if (this.todoListRepositoryImp.delete(number)) {
            System.out.println("Sukses menghapus TODO");
        } else {
            System.out.println("Gagal Menghapus TODO");
        }
        return true;
    }

    @Override
    public boolean UpdateTodoListService(UUID number, TodoList updateTodoList) {
        if (this.todoListRepositoryImp.update(number, updateTodoList)) {
            System.out.println("Sukses mengupdate TODO");
        } else {
            System.out.println("Gagal Menghapus TODO");
        }
        return true;
    }

    public TodoList[] getTodoLists() {
        return todoListRepositoryImp.findAll();
    }

}
