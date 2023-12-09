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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private JComboBox comboBoxCategories;
    private JButton saveToFileButton;

    public JPanel getJpanelTodo() {
        return jpanelTodo;
    }

    public void setJpanelTodo(JPanel jpanelTodo) {
        this.jpanelTodo = jpanelTodo;
    }

    private void printToFile(String fileName){
        try ( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){

            for (int i = 0; i < tableTodo.getColumnCount(); i++){
                bufferedWriter.write(tableTodo.getColumnName(i));
                if(i<tableTodo.getColumnCount() - 1){
                    bufferedWriter.write("\t");
                }else {
                    bufferedWriter.write("\n");
                }
            }

            for (int row = 0; row < tableTodo.getRowCount(); row++) {
                for (int col = 0; col < tableTodo.getColumnCount(); col++) {
                    Object value = tableTodo.getValueAt(row, col);
                    if (value != null) {
                        bufferedWriter.write(value.toString());
                    }
                    if (col < tableTodo.getColumnCount() - 1) {
                        bufferedWriter.write("\t");
                    } else {
                        bufferedWriter.write("\n");
                    }
                }
            }

            bufferedWriter.flush();
            System.out.println("Success "+fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Todolist() {
        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(btnInformational);
        categoryGroup.add(btnProductivity);
        categoryGroup.add(btnCreative);
        categoryGroup.add(btnTrasactional);

        for (Categories categories : Categories.values()){
            comboBoxCategories.addItem(categories);
        }

        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(new TodoListRepositoryImp());
        LoginRegisterServiceImp loginRegisterServiceImp = new LoginRegisterServiceImp(new LoginRegisterRepositoryImp());

        TableModelTodo tableModel = new TableModelTodo(todoListServiceImp);
        tableTodo.setModel(tableModel);

        ListSelectionModel selectionModel = tableTodo.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final String[] category = {""};
        comboBoxCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Categories selectedCategory = (Categories) comboBoxCategories.getSelectedItem();

                if (selectedCategory != null) {
                    switch (selectedCategory) {
                        case INFORMATIONAL -> category[0] = Categories.INFORMATIONAL.name();
                        case CREATIVE -> category[0] = Categories.CREATIVE.name();
                        case TRASACTIONAL -> category[0] = Categories.TRASACTIONAL.name();
                        case PRODUCTIVITY -> category[0] = Categories.PRODUCTIVITY.name();
                        default -> {
                            return;
                        }
                    }
                }
            }
        });

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    int selectedRow = tableTodo.getSelectedRow();
                    if(selectedRow >=0){
                        Object taskObject = tableTodo.getValueAt(selectedRow, 0);
                        Object dateObject = tableTodo.getValueAt(selectedRow, 1);
                        Object descriptionObject = tableTodo.getValueAt(selectedRow, 2);
                        Object categoryObject = tableTodo.getValueAt(selectedRow,3);

                        String task = (taskObject!= null) ? taskObject.toString() : "";
                        String date = (dateObject != null) ? dateObject.toString() : "";
                        String description = (descriptionObject != null) ? descriptionObject.toString() : "";

                        if (categoryObject != null) {
                            Categories category = (Categories) categoryObject;
                            comboBoxCategories.setSelectedItem(category);
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
                                            lblMessage.setText("Data deleted successfully");
                                        } else {
                                           lblMessage.setText("Invalid delete data");
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

                                        Object categoriesSelected = comboBoxCategories.getSelectedItem();
                                        if(categoriesSelected !=null){
                                            category = (Categories) categoriesSelected;
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

                if (!category[0].isEmpty()) {

                    try {
                        TodoList newTodo = new TodoList(task, description, dueDate, Categories.valueOf(category[0]));
                        todoListServiceImp.AddTodoListService(newTodo);
                        lblMessage.setText("Successfully add TODO");
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Invalid category selected: " + category[0]);
                    }
                } else {
                    System.err.println("No category selected");
                }

                fieldTask.setText("");
                fieldDate.setText("");
                fieldDescription.setText("");

            }
        });
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int result = jFileChooser.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    String path = jFileChooser.getSelectedFile().getAbsolutePath();
                    printToFile(path);
                }
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