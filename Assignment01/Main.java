package Assignment01;
import java.awt.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.Throwable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static Assignment01.Library.bu;
import static Assignment01.Library.nu;
import static Assignment01.Library.Books;
import static Assignment01.Library.Users;

public class Main {
    static JFrame frame = new JFrame("Library Management System");
    static Library Library1 = new Library();
    static User current_user;
    static JTable table;
    static DefaultTableModel tableModel;
    private static int userId;
    private static int userPass;

    public static void main(String[] args){
        String file_Name_1 = "Users.txt";
        String file_Name_2 = "Books.txt";
        File file1 = new File(file_Name_1);
        File file2 = new File(file_Name_2);

        try {
            if (!file1.exists()) {
                file1.createNewFile();
                System.out.println("File created: " + file_Name_1);
            } /*else {
                BufferedReader reader = new BufferedReader(new FileReader(file1));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                String[] dataArray = stringBuilder.toString().split("\n");
                reader.close();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!file2.exists()) {
                file2.createNewFile();
                System.out.println("File created: " + file_Name_2);
            } /*else {
                BufferedReader reader = new BufferedReader(new FileReader(file1));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                String[] dataArray = stringBuilder.toString().split("\n");
                reader.close();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        WelcomeMenu();

    }
    public static void WelcomeMenu(){
        JButton continueButton;
        frame.setLayout(new BorderLayout());

//        JPanel menuPanel;
        JPanel welcomePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel welcomeLabel = new JLabel("Welcome to the Library!");
        welcomePanel.add(welcomeLabel);
        continueButton = new JButton("Continue");
        welcomePanel.add(continueButton);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDetails();
            }
        });

        frame.add(welcomePanel, BorderLayout.NORTH);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }
    public static void LoginDetails(){
        JPanel userPanel = new JPanel(new GridLayout(4, 1));

        JButton createUserButton = new JButton("Create New User");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to create new user goes here
                createUser();
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        JButton adminButton = new JButton("Manage Library");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Authenticate();
            }
        });

        JButton forgotIDButton = new JButton("Forgot ID?");
//        adminButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Authenticate();
//            }
//        });

        userPanel.add(createUserButton);
        userPanel.add(loginButton);
        userPanel.add(adminButton);
        userPanel.add(forgotIDButton);
        // Center align text and buttons
        createUserButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        forgotIDButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Center align panel
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().removeAll();
        frame.add(userPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void Menu(){
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton lookCatalogButton = new JButton("Book Collection");
        lookCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to create new user goes here
                displayBookCollection();
            }
        });
        JButton buyBookButton = new JButton("Buy Book");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton searchBookButton = new JButton("Search Book");
        JButton returnBookButton = new JButton("Return Book");
        lookCatalogButton.setFont(new Font("Arial", Font.BOLD, 14));
        buyBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        borrowBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        menuPanel.add(lookCatalogButton);
        menuPanel.add(buyBookButton);
        menuPanel.add(borrowBookButton);
        menuPanel.add(returnBookButton);
        menuPanel.add(searchBookButton);
        frame.getContentPane().removeAll();
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void createUser(){
        JPanel userInfoPanel = new JPanel(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel contactLabel = new JLabel("Contact Info:");
        JTextField contactField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Retrieve the entered name and contact information
            String name = nameField.getText();
            String contactInfo = contactField.getText();
            User temp = new User(name,contactInfo);
            Library1.addUser(temp);
            String message = "User created successfully! Note Down ID: " + temp.GetUserID();
            JOptionPane.showMessageDialog(frame, message );
            LoginDetails();
        });

        // Add components to the panel
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(nameField);
        userInfoPanel.add(contactLabel);
        userInfoPanel.add(contactField);
        userInfoPanel.add(submitButton);
        frame.getContentPane().removeAll();
        frame.add(userInfoPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void login(){
        JPanel loginPanel = new JPanel(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("User ID:");
        JTextField idField = new JTextField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered user ID
                String idText = idField.getText();

                // Validate if the entered ID is an integer
                try {
                    userId = Integer.parseInt(idText);
                    if(Library1.findUser(userId)==0){
                        JOptionPane.showMessageDialog(frame, "Logged in successfully with ID: " + userId);
                        Menu();
                    }else{
                        JOptionPane.showMessageDialog(frame, "No Such ID Exists!");
                        LoginDetails();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer ID.");
                }
            }
        });

        // Add components to the panel
        loginPanel.add(idLabel);
        loginPanel.add(idField);
        loginPanel.add(loginButton);
        frame.getContentPane().removeAll();
        frame.add(loginPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void Authenticate(){
        JPanel AdminLoginPanel = new JPanel(new GridLayout(2, 2));
        JLabel idLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered user ID
                String idText = passwordField.getText();

                try {
                    userPass = Integer.parseInt(idText);
                    if(userPass==2005){
                        JOptionPane.showMessageDialog(frame, "Logged in successfully");
                        adminView();
                    }else{
                        JOptionPane.showMessageDialog(frame, "Wrong Password");
                        LoginDetails();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer ID.");
                }
            }
        });

        // Add components to the panel
        AdminLoginPanel.add(idLabel);
        AdminLoginPanel.add(passwordField);
        AdminLoginPanel.add(loginButton);
        frame.getContentPane().removeAll();
        frame.add(AdminLoginPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void adminView(){
        JPanel NewMenuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        JButton removeBookButton = new JButton("Remove Book");
        JButton removeUserButton = new JButton("Remove User");
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        JButton lookCatalogButton = new JButton("Book Collection");
        lookCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBookCollection();
            }
        });
        JButton buyBookButton = new JButton("Buy Book");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton searchBookButton = new JButton("Search Book");
        JButton returnBookButton = new JButton("Return Book");

        lookCatalogButton.setFont(new Font("Arial", Font.BOLD, 14));
        buyBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        borrowBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        addBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        NewMenuPanel.add(buyBookButton);
        NewMenuPanel.add(lookCatalogButton);
        NewMenuPanel.add(searchBookButton);
        NewMenuPanel.add(borrowBookButton);
        NewMenuPanel.add(addUserButton);
        NewMenuPanel.add(removeUserButton);
        NewMenuPanel.add(returnBookButton);
        NewMenuPanel.add(addBookButton);
        NewMenuPanel.add(removeBookButton);

        frame.getContentPane().removeAll();
        frame.add(NewMenuPanel, BorderLayout.CENTER);
        frame.setSize(500,500);
        frame.revalidate();
        frame.repaint();
    }
    public static void displayBookCollection() {
        JPanel bookcatalog = new JPanel();
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Availability");

        // Create a table using the model
        JTable table = new JTable(tableModel);

        // Populate the table with user data
        for (Book i : Books) {
            tableModel.addRow(new Object[]{i.GetBookID(), i.GetTitle(), i.GetAuthor(), i.GetGenre(), i.GetAvailability()});
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        bookcatalog.setLayout(new BorderLayout());
        bookcatalog.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.add(bookcatalog, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void removeUser(int id){
        for(User i:Users){
            if(i.GetUserID()==id){
                Users.remove(i);
            }
        }
    }
    public static void addBook(){
        JPanel addBookPanel = new JPanel(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Title:");
        JTextField nameField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Retrieve the entered name and contact information
            String name = nameField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            Book temp = new Book(name,author,genre);
            Library1.addBook(temp);
            String message = "Book added successfully!";
            JOptionPane.showMessageDialog(frame, message );
            adminView();
        });

        // Add components to the panel
        addBookPanel.add(nameLabel);
        addBookPanel.add(nameField);
        addBookPanel.add(authorLabel);
        addBookPanel.add(authorField);
        addBookPanel.add(genreLabel);
        addBookPanel.add(genreField);
        addBookPanel.add(submitButton);
        frame.getContentPane().removeAll();
        frame.add(addBookPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

}
