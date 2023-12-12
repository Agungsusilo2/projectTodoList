package Service;

import Entity.TodoList;
import Repository.TodoListRepositoryImp;

import java.util.Arrays;
import java.util.Comparator;
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
                        + " dueDate : " + todo.getDeadLine().toString() + " Categories : " + todo.getCategories().toString());
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
            System.out.println("Success deleted TODO");
        } else {
            System.out.println("Invalid deleted TODO");
        }
        return true;
    }

    @Override
    public boolean UpdateTodoListService(UUID number, TodoList updateTodoList) {
        if (this.todoListRepositoryImp.update(number, updateTodoList)) {
            System.out.println("Success Update TODO");
        } else {
            System.out.println("Invalid Update TODO");
        }
        return true;
    }

    @Override
    public TodoList[] getTodoListSortedCategories() {
        TodoList[] allTodoLists = todoListRepositoryImp.findAll();

        Arrays.sort(allTodoLists, Comparator.comparing(todoList -> {
            if (todoList != null) {
                String category = todoList.getCategories() != null ? todoList.getCategories().name() : "";
                return switch (category) {
                    case "IMPORTANT" -> 1;
                    case "MEDIUM" -> 2;
                    case "NORMAL" -> 3;
                    default -> 4;
                };
            }
            return 4;
        }));

        return allTodoLists;
    }

    public TodoList[] getTodoLists() {
        return todoListRepositoryImp.findAll();
    }
    @Override
    public boolean RemoveTodoListService(UUID number) {
        return todoListRepositoryImp.deleteUUID(number);
    }
}