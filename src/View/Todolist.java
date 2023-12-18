package View;

import Domain.Categories;
import Entity.TodoList;
import Repository.TodoListRepositoryImp;
import Service.TodoListServiceImp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class Todolist {
    private JTextField fieldTask;
    private JTextField fieldDate;
    private JTextArea fieldDescription;
    private JRadioButton btnProductivity;
    private JButton btnAdd;
    private JLabel lblTask;
    private JLabel lblDate;
    private JLabel lblDescription;
    private JLabel descriptionLabel;
    private JLabel lblCategories;
    private JPanel jpanelTodo;
    private JTable tableTodo;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JLabel lblMessage;
    private JComboBox comboBoxCategories;
    private JButton saveToFileButton;
    private JButton btnGetSortedTasks;

    public JPanel getJpanelTodo() {
        return jpanelTodo;
    }

    private void printToFile(String fileName){
        try ( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){

            for (int i = 0; i < this.tableTodo.getColumnCount(); i++){
                bufferedWriter.write(this.tableTodo.getColumnName(i));
                if(i<this.tableTodo.getColumnCount() - 1){
                    bufferedWriter.write("\t");
                }else {
                    bufferedWriter.write("\n");
                }
            }

            for (int row = 0; row < this.tableTodo.getRowCount(); row++) {
                for (int col = 0; col < this.tableTodo.getColumnCount(); col++) {
                    Object value = this.tableTodo.getValueAt(row, col);
                    if (value != null) {
                        bufferedWriter.write(value.toString());
                    }
                    if (col < this.tableTodo.getColumnCount() - 1) {
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

        TodoListServiceImp todoListServiceImp = new TodoListServiceImp(new TodoListRepositoryImp());

        btnGetSortedTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoList[] sortedTasks = todoListServiceImp.getTodoListSortedCategories();
                updateTable(sortedTasks);
            }
        });

        jpanelTodo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableTodo.clearSelection(); // Membersihkan pemilihan di dalam tabel
            }
        });

        for (Categories categories : Categories.values()){
            comboBoxCategories.addItem(categories);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
                        case IMPORTANT -> category[0] = Categories.IMPORTANT.name();
                        case MEDIUM -> category[0] = Categories.MEDIUM.name();
                        case NORMAL -> category[0] = Categories.NORMAL.name();
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
                                        TodoList[] todoLists = todoListServiceImp.getTodoLists();

                                        if (todoLists != null && selectedRow < todoLists.length) {
                                            TodoList selectedTodo = todoLists[selectedRow];

                                            if (selectedTodo != null) {
                                                UUID numberTodoList = selectedTodo.getNoIdentity();

                                                boolean removed = todoListServiceImp.RemoveTodoListService(numberTodoList);

                                                if (removed) {
                                                    lblMessage.setText("Data deleted successfully");

                                                    updateTable(todoListServiceImp.getTodoLists());
                                                } else {
                                                    lblMessage.setText("Invalid delete data");
                                                }
                                            } else {
                                                lblMessage.setText("Selected data is invalid");
                                            }
                                        } else {
                                            lblMessage.setText("Invalid selected row");
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(null, "No identity not valid");
                                    }
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

                                        TodoList updatedTodo = new TodoList(task, description, LocalDateTime.parse(date), category);

                                        boolean isUpdated = todoListServiceImp.UpdateTodoListService(todoID, updatedTodo);
                                        todoListServiceImp.intervalTime(LocalDateTime.parse(date));
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
                String dateString = fieldDate.getText();

                try {
                    LocalDateTime dueDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                    String description = fieldDescription.getText();

                    if (!category[0].isEmpty()) {
                        try {
                            TodoList newTodo = new TodoList(task, description, dueDate, Categories.valueOf(category[0]));
                            try {
                                todoListServiceImp.AddTodoListService(newTodo);
                                todoListServiceImp.intervalTime(dueDate);
                                lblMessage.setText("Successfully add TODO");
                            } catch (IllegalStateException ex) {
                                JOptionPane.showMessageDialog(null,ex.getMessage());
                            }
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Invalid category selected: " + category[0]);
                        }
                    } else {
                        System.err.println("No category selected");
                    }

                    fieldTask.setText("");
                    fieldDate.setText("");
                    fieldDescription.setText("");
                } catch (DateTimeParseException ex) {
                    System.err.println("Format invalid: Please enter date in 'yyyy-MM-ddTHH:mm:ss' format.");
                }
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

    private void updateTable(TodoList[] tasks) {
        TableModelTodo tableModel = new TableModelTodo(tasks);
        tableTodo.setModel(tableModel);
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

        public TableModelTodo(TodoList[] tasks) {
            this.todoLists = tasks;
        }


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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            if (todoLists[rowIndex] != null) {
                TodoList todo = todoLists[rowIndex];
                return switch (columnIndex) {
                    case 0 -> todo.getAddTask();
                    case 1 -> todo.getDeadLine().format(formatter);
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