import Domain.Categories;
import Entity.TodoList;
import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Todolist {
    private JTextField fieldTask;
    private JTextField fieldDate;
    private JTextArea fieldDescription;
    private JRadioButton btnInformational;
    private JRadioButton btnProductivity;
    private JButton btnAdd;
    private JLabel lblTask;
    private JLabel lblDate;
    private JLabel lblDescription;
    private JLabel descriptionLabel;
    private JLabel lblCategories;
    private JRadioButton btnCreative;
    private JRadioButton btnTrasactional;
    private JPanel jpanelTodo;
    private JTable tableTodo;
    private JButton btnDelete;
    private JButton btnUpdate;

    public Todolist() {
        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(new TodoListRepositoryImp());

        TableModelTodo tableModel = new TableModelTodo(todoListServiceImp);
        tableTodo.setModel(tableModel);

        // Set preferred width for columns
        TableColumnModel columnModel;
        columnModel = tableTodo.getColumnModel();
        if (columnModel.getColumnCount() > 2) {
            TableColumn column = columnModel.getColumn(2);
            column.setPreferredWidth(300);
        }

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = fieldTask.getText();
                String date = fieldDate.getText();
                String dateString = fieldDate.getText();


                LocalDate dueDate;
                try {
                    dueDate = LocalDate.parse(dateString);
                } catch (DateTimeParseException dateTimeParseException) {
                    throw new DateTimeParseException("Format tanggal tidak valid: " +
                            dateTimeParseException.getMessage(), dateString, dateTimeParseException.getErrorIndex(),
                            dateTimeParseException);
                }


                String description = fieldDescription.getText();

                String category = "";
                if (btnInformational.isSelected()) {
                    category = "INFORMATIONAL";
                } else if (btnProductivity.isSelected()) {
                    category = "CREATIVE";
                } else if (btnCreative.isSelected()) {
                    category = "TRASACTIONAL";
                } else if (btnTrasactional.isSelected()) {
                    category = "PRODUCTIVITY";
                }


                TodoList newTodo = new TodoList(task,description,dueDate,Categories.valueOf(category));
                todoListServiceImp.AddTodoListService(newTodo);

                fieldTask.setText("");
                fieldDate.setText("");
                fieldDescription.setText("");

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Todolist");
        frame.setContentPane(new Todolist().jpanelTodo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static class TableModelTodo extends AbstractTableModel {
        private final String[] COLUMNS = {"TASK", "DATE", "DESCRIPTION", "CATEGORIES"};
        private TodoListServiceImp todoListServiceImp;
        private TodoList[] todoLists;

        public TableModelTodo(TodoListServiceImp todoListServiceImp) {
            this.todoListServiceImp = todoListServiceImp;
            this.todoLists = todoListServiceImp.getTodoLists();
        }

        @Override
        public int getRowCount() {
            return todoLists.length;
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (todoLists[rowIndex] != null) {
                TodoList todo = todoLists[rowIndex];
                return switch (columnIndex) {
                    case 0 -> todo.getAddTask();
                    case 1 -> todo.getDueDate();
                    case 2 -> todo.getDescription();
                    case 3 -> todo.getCategories();
                    default -> null;
                };
            }
            return null;
        }


        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }


        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 3) {
                return Categories.class;
            }
            return super.getColumnClass(columnIndex);
        }
    }
}
