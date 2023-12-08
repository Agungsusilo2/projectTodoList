import Domain.Categories;
import Entity.TodoList;
import Repository.LoginRegisterRepositoryImp;
import Repository.TodoListRepositoryImp;
import Service.LoginRegisterServiceImp;
import Service.TodoListServiceImp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

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
    private JLabel lblMessage;

    public JPanel getJpanelTodo() {
        return jpanelTodo;
    }

    public void setJpanelTodo(JPanel jpanelTodo) {
        this.jpanelTodo = jpanelTodo;
    }

    public Todolist() {
        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(btnInformational);
        categoryGroup.add(btnProductivity);
        categoryGroup.add(btnCreative);
        categoryGroup.add(btnTrasactional);

        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(new TodoListRepositoryImp());
        LoginRegisterServiceImp loginRegisterServiceImp = new LoginRegisterServiceImp(new LoginRegisterRepositoryImp());

        TableModelTodo tableModel = new TableModelTodo(todoListServiceImp);
        tableTodo.setModel(tableModel);

        ListSelectionModel selectionModel = tableTodo.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    int selectedRow = tableTodo.getSelectedRow();
                    if(selectedRow >=0){
                        Object taskObject = tableTodo.getValueAt(selectedRow, 0);
                        Object dateObject = tableTodo.getValueAt(selectedRow, 1);
                        Object descriptionObject = tableTodo.getValueAt(selectedRow, 2);
                        Object categoryObject = tableTodo.getValueAt(selectedRow, 3);

                        String task = (taskObject!= null) ? taskObject.toString() : "";
                        String date = (dateObject != null) ? dateObject.toString() : "";
                        String description = (descriptionObject != null) ? descriptionObject.toString() : "";
                        Categories category = (categoryObject != null) ? (Categories) categoryObject : null;

                        if (category == Categories.INFORMATIONAL) {
                            btnInformational.setSelected(true);
                        } else if (category == Categories.CREATIVE) {
                            btnCreative.setSelected(true);
                        } else if (category == Categories.TRASACTIONAL) {
                            btnTrasactional.setSelected(true);
                        } else if (category == Categories.PRODUCTIVITY) {
                            btnTrasactional.setSelected(true);
                        } else {
                            categoryGroup.clearSelection();
                        }

                        fieldTask.setText(task);
                        fieldDate.setText(date);
                        fieldDescription.setText(description);

                        btnDelete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tableTodo.getSelectedRow();

                                if (selectedRow != -1) {
                                    try {
                                        TodoList selectedTodo = todoListServiceImp.getTodoLists()[selectedRow];
                                        UUID nomorTodoList = selectedTodo.getNoIdentity();

                                        boolean removed = todoListServiceImp.RemoveTodoListService(nomorTodoList);

                                        if (removed) {
                                            JOptionPane.showMessageDialog(null, "Data deleted successfully");
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Invalid delete data");
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(null, "No identity not valid");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Select the data to delete");
                                }
                            }
                        });

                        btnUpdate.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tableTodo.getSelectedRow();

                                if (selectedRow != -1) {
                                    try {
                                        TodoList selectedTodo = todoListServiceImp.getTodoLists()[selectedRow];

                                        UUID todoID = selectedTodo.getNoIdentity();

                                        String task = fieldTask.getText();
                                        String date = fieldDate.getText();
                                        String description = fieldDescription.getText();

                                        Categories category = null;
                                        if (btnInformational.isSelected()) {
                                            category = Categories.INFORMATIONAL;
                                        } else if (btnProductivity.isSelected()) {
                                            category = Categories.PRODUCTIVITY;
                                        } else if (btnCreative.isSelected()) {
                                            category = Categories.CREATIVE;
                                        } else if (btnTrasactional.isSelected()) {
                                            category = Categories.TRASACTIONAL;
                                        }

                                        TodoList updatedTodo = new TodoList(task, description, LocalDate.parse(date), category);

                                        boolean isUpdated = todoListServiceImp.UpdateTodoListService(todoID, updatedTodo);
                                        if (isUpdated) {
                                            lblMessage.setText("Data updated successfully ");
                                        } else {
                                            lblMessage.setText("Invalid update data");
                                        }
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(null, "Invalid update data: " + ex.getMessage());
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Select data to update");
                                }
                            }
                        });



                    }
                }
            }
        });

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
                    throw new DateTimeParseException("Format invalid : " +
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
