import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static MongoDatabase todoDB;
    //create Lables and TextFields for todoApp
    static JLabel taskNamelb, taskDatelb, tasktimelb;
    static JTextField taskNametf, taskDatetf, tasktimetf;
    static JButton addTaskbt,deletetask,updatetask,viewtask;
    public static void main(String[] args) {
        //Add maven in your project
        //Create client and establish the mongo database connection
        var client = MongoClients.create("mongodb://localhost:27017/");
        //create or access the database
        todoDB = client.getDatabase("todoApp");
        //to create the collection
        todoDB.createCollection("todoList");
        //create GUI for TodoApp using swing
        createTodoUI();
    }
    private static void createTodoUI() {
        JFrame frame = new JFrame("Todo App");

        //to initialize the UI components
        taskNamelb = new JLabel("Enter the task name");
        taskDatelb = new JLabel("Enter the task date");
        tasktimelb = new JLabel("Enter the task time");

        taskNametf = new JTextField();
        taskDatetf = new JTextField();
        tasktimetf = new JTextField();

        addTaskbt = new JButton("Add Task");
        deletetask=new JButton("Delete");
        updatetask=new JButton("Update");
        viewtask=new JButton("View");

        //to set the size and position of UI components
        taskNamelb.setBounds(50, 50, 150, 40);
        taskDatelb.setBounds(50, 80, 150, 40);
        tasktimelb.setBounds(50, 110, 150, 40);

        taskNametf.setBounds(180, 50, 120, 30);
        taskDatetf.setBounds(180, 80, 120, 30);
        tasktimetf.setBounds(180, 110, 120, 30);
        addTaskbt.setBounds(50, 150, 100, 30);
        deletetask.setBounds(155,150,100,30);
        updatetask.setBounds(260,150,100,30);
        viewtask.setBounds(365,150,100,30);

        //to attach the UI components into frame
        frame.add(taskNamelb);
        frame.add(taskDatelb);
        frame.add(tasktimelb);
        frame.add(taskNametf);
        frame.add(taskDatetf);
        frame.add(tasktimetf);
        frame.add(addTaskbt);
        frame.add(deletetask);
        frame.add(updatetask);
        frame.add(viewtask);

        //to set the size of frame
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setVisible(true);

        //to click on the add task button
        addTaskbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //insert data into mongo db
                insertTaskIntoDB();
            }
        });

        //to click on the update task button
        updatetask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //enter the data to update
                updatetaskindb();
            }
        });

    }
    private static void insertTaskIntoDB() {
        //to access the collection
        var todoCollection = todoDB.getCollection("todoList");
        //to create the document and add in collection
        var document = new Document();
        //add data into document
        document.append("taskName", taskNametf.getText());
        document.append("taskDate", taskDatetf.getText());
        document.append("taskTime", tasktimetf.getText());
        document.append("taskStatus", false);
        //to add the document into collection
        todoCollection.insertOne(document);
        JOptionPane.showMessageDialog(null, "Task Added Successfully");
        //clear the form
        clearForm();
    }

    private static void updatetaskindb(){
        //to access the collection
        var todoCollection=todoDB.getCollection("todoList");
        //to update the task status
        String updatetask=JOptionPane.showInputDialog(null,"Enter the task id to update");
        todoCollection.updateOne(new Document("taskid",Integer.parseInt(updatetask)),new Document("$set",new Document("task status",true)));
    }

    private static void clearForm() {
        taskDatetf.setText("");
        tasktimetf.setText("");
        taskNametf.setText("");
    }
}