//Semester: Summer 2023
//Author: Myat Thiha
//Last run: 03/29/2024

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class taskManager extends JFrame {
    private List<Task> tasks;
    // GUI components
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField dueDateField;
    private JTable tasksTable;

    public taskManager() {
        tasks = new ArrayList<>();

        setTitle("Task Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        JPanel tasksPanel = createTasksPanel();
        JPanel buttonPanel = createButtonPanel();

        add(inputPanel, BorderLayout.NORTH);
        add(tasksPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Task Name:");
        nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JLabel dueDateLabel = new JLabel("Due Date:");
        dueDateField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(dueDateLabel);
        inputPanel.add(dueDateField);

        return inputPanel;
    }

    private JPanel createTasksPanel() {
        JPanel tasksPanel = new JPanel(new BorderLayout());
        tasksTable = new JTable(new TaskTableModel(tasks));
        JScrollPane scrollPane = new JScrollPane(tasksTable);
        tasksPanel.add(scrollPane, BorderLayout.CENTER);

        return tasksPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        JButton editButton = new JButton("Edit Task");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTask();
            }
        });
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void addTask() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String dueDate = dueDateField.getText();

        // Validate the input fields (e.g., check for empty fields, validate date format)
        if (name.isEmpty() || dueDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a task name and due date.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Create a new Task object with the input data
        Task newTask = new Task(name, description, dueDate, false);
        // Add the task to the list and update the table model
        tasks.add(newTask);
        tasksTable.setModel(new TaskTableModel(tasks)); // Update the table model
        tasksTable.repaint();
        // Clear the input fields
        nameField.setText("");
        descriptionField.setText("");
        dueDateField.setText("");
    }

    private void editTask() {
        int selectedRow = tasksTable.getSelectedRow();

    // Check if a task is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a task to edit.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Retrieve the selected task from the table
    Task selectedTask = tasks.get(selectedRow);

    // Update the input fields with the task details for editing
    if(nameField.getText().isEmpty())
    {
        nameField.setText(selectedTask.getName());
    }
    selectedTask.setName(nameField.getText());
    if(descriptionField.getText().isEmpty())
    {
        descriptionField.setText(selectedTask.getDescription());
    }
    selectedTask.setDescription(descriptionField.getText());
    if(dueDateField.getText().isEmpty())
    {
        dueDateField.setText(selectedTask.getDueDate());
    }
    selectedTask.setDueDate(dueDateField.getText());
    // Update the task in the list and update the table model
    // You can modify the task properties based on the updated input
    // For example, you can update the task name, description, and due date
    // selectedTask.setName(nameField.getText().trim());
    // selectedTask.setDescription(descriptionArea.getText().trim());
    // selectedTask.setDueDate(dueDateField.getText().trim());
    tasksTable.setModel(new TaskTableModel(tasks));
    tasksTable.repaint(); // Repaint the table to reflect the changes
    nameField.setText("");
    descriptionField.setText("");
    dueDateField.setText("");
    }

    private void deleteTask() {
        int selectedRow = tasksTable.getSelectedRow();

    // Check if a task is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a task to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Retrieve the selected task from the table
    Task selectedTask = tasks.get(selectedRow);

    // Remove the task from the list and update the table model
    tasks.remove(selectedTask);
    tasksTable.repaint(); // Repaint the table to reflect the changes
    }

    // Inner class representing the Task data model for the table
    private class TaskTableModel extends AbstractTableModel implements TableModel {
        private List<Task> tasks;

        public TaskTableModel(List<Task> tasks) {
            this.tasks = tasks;
        }

        @Override
        public int getRowCount() {
            return tasks.size();
        }

        @Override
        public int getColumnCount() {
            return 3; // Adjust the number of columns as per your task data structure
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Task task = tasks.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return task.getName();
            case 1:
                return task.getDescription();
            case 2:
                return task.getDueDate();
            case 3:
                return task.isCompleted();
            default:
                return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Task Name";
                case 1:
                    return "Description";
                case 2:
                    return "Due Date";
                default:
                    return null;
            }
        }
    }

    // Task class representing a single task
    private class Task {
        private String name;
        private String description;
        private String dueDate;
        private boolean completed;

        // Constructor, getters, setters, etc.
        public Task(String name, String description, String dueDate, boolean completed) {
            this.name = name;
            this.description = description;
            this.dueDate = dueDate;
            this.completed = completed;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getDescription() {
            return description;
        }
    
        public void setDescription(String description) {
            this.description = description;
        }
    
        public String getDueDate() {
            return dueDate;
        }
    
        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }
    
        public boolean isCompleted() {
            return completed;
        }
    
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new taskManager();
            }
        });
    }
}
