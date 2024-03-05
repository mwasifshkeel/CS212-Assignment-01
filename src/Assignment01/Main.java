package Assignment01;
import java.awt.*;
import java.io.*;
import java.lang.Throwable;
import java.awt.event.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import static Assignment01.Library.bu;
import static Assignment01.Library.nu;
import static Assignment01.Library.Books;
import static Assignment01.Library.Users;
public class Main {
    /**
     Main Window that will be used for GUI
     */
    static JFrame frame = new JFrame("Library Management System");
    /**
     Name of JSON file that will store Users
     */
    private static final String USERS_JSON_FILE = "users.json";
    /**
     Name of JSON file that will store Books
     */
    private static final String BOOKS_JSON_FILE = "books.json";
    /**
     The only instance of Library that will handle library management
     */
    static Library Library1 = new Library();
    /**
     User ID of Logged In User
     */
    private static int userId;
    /**
     Password For Managing Library
     */
    private static int librarianPass;
    public static void main(String[] args) {
        //Loads data from JSONs to ArrayLists
        loadData(Users, Books);
        //Displays Welcome Menu
        WelcomeMenu();
        //Method to save data from ArrayLists to JSON Files upon closing of Main JFrame Window
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData(Users, Books);
            }
        });
    }
    //Display Welcome Menu to User
    public static void WelcomeMenu() {
        // Setting the layout of the 'frame' to BorderLayout to organize components
        frame.setLayout(new BorderLayout());
        // Creating a JPanel named 'welcomePanel' with a GridLayout of 2 rows, 1 column, and spacing of 10 pixels
        JPanel welcomePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        //Creating a Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to the Library!",SwingConstants.CENTER);
        //Creating a Continue Button
        JButton continueButton = new JButton("Continue");
        //Method to call next panel on button click
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDetails();
            }
        });
        //Append Button and Label to the Panel
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(continueButton);
        //Append Panel to Main Frame and adjust properties of Frame
        frame.add(welcomePanel, BorderLayout.NORTH);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }
    //Display Login Menu to User
    public static void LoginDetails() {
        //Creating a JPanel named 'userPanel' with a GridLayout of 4 rows and 1 column
        JPanel userPanel = new JPanel(new GridLayout(4, 1));
        //Creating a Create User button
        JButton createUserButton = new JButton("Create New User");
        //Method to call Create User panel on button click
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to create new user goes here
                createUser(0);
            }
        });
        //Creating a Login button
        JButton loginButton = new JButton("Login");
        //Method to call Login panel on button click
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        //Creating a Manage Library Button
        JButton adminButton = new JButton("Manage Library");
        //Method to call Admin panel on button click
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Authenticate();
            }
        });
        //Creating a Forgot ID Button
        JButton forgotIDButton = new JButton("Forgot ID?");
        //Method to call Forget ID panel on button click
        forgotIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to create new user goes here
                forgotID();
            }
        });

        //Append all items to the panel
        userPanel.add(createUserButton);
        userPanel.add(loginButton);
        userPanel.add(adminButton);
        userPanel.add(forgotIDButton);

        // Center align text and buttons
        createUserButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        forgotIDButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Center align panel and append to main frame
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().removeAll();
        frame.add(userPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    // Method to display the main menu options for users
    public static void Menu() {
        // Create a panel to hold menu options
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        // Create buttons for different menu options
        JButton lookCatalogButton = new JButton("Browse Books");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton searchBookButton = new JButton("Search Book");
        JButton returnHomeButton = new JButton("Log Out");

        // Set font style for buttons
        lookCatalogButton.setFont(new Font("Arial", Font.BOLD, 14));
        borrowBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnHomeButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add action listeners for each button
        lookCatalogButton.addActionListener(e -> displayBookCollection(0));
        borrowBookButton.addActionListener(e -> borrowBook(1));
        returnBookButton.addActionListener(e -> returnBook(1));
        searchBookButton.addActionListener(e -> searchBook(0));
        returnHomeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logged out successfully");
            LoginDetails();
        });

        // Add buttons to the menu panel
        menuPanel.add(lookCatalogButton);
        menuPanel.add(borrowBookButton);
        menuPanel.add(returnBookButton);
        menuPanel.add(searchBookButton);
        menuPanel.add(returnHomeButton);

        // Clear previous content from frame and add the menu panel
        frame.getContentPane().removeAll();
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void createUser(int x) {
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
            User temp = new User(name, contactInfo);
            Library1.addUser(temp);
            String message = "User created successfully! Note Down ID: " + temp.GetUserID();
            JOptionPane.showMessageDialog(frame, message);
            if (x == 0) {
                LoginDetails();
            } else {
                adminView();
            }
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
    public static void login() {
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
                    if (Library1.findUser(userId) == 0) {
                        JOptionPane.showMessageDialog(frame, "Logged in successfully with ID: " + userId);
                        Menu();
                    } else {
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
    public static void Authenticate() {
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
                    librarianPass = Integer.parseInt(idText);
                    if (librarianPass == 2005) {
                        JOptionPane.showMessageDialog(frame, "Logged in successfully");
                        adminView();
                    } else {
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
    public static void adminView() {
        JPanel NewMenuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        JButton removeBookButton = new JButton("Remove Book");
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });
        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser();
            }
        });
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser(1);
            }
        });
        JButton lookCatalogButton = new JButton("Book Collection");
        lookCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBookCollection(1);
            }
        });
        JButton borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook(0);
            }
        });

        JButton searchBookButton = new JButton("Search Book");
        searchBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook(1);
            }
        });
        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook(0);
            }
        });
        JButton returnHomeButton = new JButton("Log Out");
        returnHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Logged out successfully");
                LoginDetails();
            }
        });

        lookCatalogButton.setFont(new Font("Arial", Font.BOLD, 14));
        borrowBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        addBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnHomeButton.setFont(new Font("Arial", Font.BOLD, 14));

        NewMenuPanel.add(borrowBookButton);
        NewMenuPanel.add(lookCatalogButton);
        NewMenuPanel.add(searchBookButton);
        NewMenuPanel.add(returnBookButton);
        NewMenuPanel.add(addUserButton);
        NewMenuPanel.add(removeUserButton);
        NewMenuPanel.add(addBookButton);
        NewMenuPanel.add(removeBookButton);
        NewMenuPanel.add(returnHomeButton);

        frame.getContentPane().removeAll();
        frame.add(NewMenuPanel, BorderLayout.CENTER);
        frame.setSize(500, 500);
        frame.revalidate();
        frame.repaint();
    }
    public static void displayBookCollection(int x) {
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
        JButton returnButton = new JButton("Go Back");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x == 0) {
                    Menu();
                } else {
                    adminView();
                }
            }
        });
        bookcatalog.add(returnButton, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(bookcatalog, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void removeUser() {
        JPanel removeUserPanel = new JPanel(new GridLayout(3, 1));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            // Retrieve the entered name and contact information
            int id = Integer.parseInt(idField.getText());
            if (Library1.removeUser(id) == 1) {
                JOptionPane.showMessageDialog(frame, "Removed User Successfully!");
                adminView();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed");
                adminView();
            }
        });
        removeUserPanel.add(idLabel);
        removeUserPanel.add(idField);
        removeUserPanel.add(removeButton);
        frame.getContentPane().removeAll();
        frame.add(removeUserPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void addBook() {
        JPanel addBookPanel = new JPanel(new GridLayout(4, 1));

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
            Book temp = new Book(name, author, genre);
            Library1.addBook(temp);
            String message = "Book added successfully!";
            JOptionPane.showMessageDialog(frame, message);
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
    public static void removeBook() {
        JPanel removeBookPanel = new JPanel(new GridLayout(2, 1));

        JLabel idLabel = new JLabel("Book ID:");
        JTextField idField = new JTextField();

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            int x = Integer.parseInt(idField.getText());
            if (Library1.removeBook(x) == 1) {
                JOptionPane.showMessageDialog(frame, "Removed Book Successfully!");
                adminView();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed!");
                adminView();
            }
        });

        // Add components to the panel
        removeBookPanel.add(idLabel);
        removeBookPanel.add(idField);
        removeBookPanel.add(removeButton);

        frame.getContentPane().removeAll();
        frame.add(removeBookPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void searchBook(int x) {
        JCheckBox authorCheckbox = new JCheckBox("Search by Author");
        JCheckBox titleCheckbox = new JCheckBox("Search by Title");
        JCheckBox idCheckbox = new JCheckBox("Search by User ID");

        // Create a panel to hold the checkboxes
        JPanel checkboxPanel = new JPanel(new GridLayout(3, 1));
        checkboxPanel.add(authorCheckbox);
        checkboxPanel.add(titleCheckbox);
        checkboxPanel.add(idCheckbox);

        // Show the checkbox panel in a dialog
        int option = JOptionPane.showConfirmDialog(null, checkboxPanel, "Choose Search Options", JOptionPane.OK_CANCEL_OPTION);

        // Check if "OK" was clicked and process user's choice
        if (option == JOptionPane.OK_OPTION) {
            if (authorCheckbox.isSelected()) {
                // Create a new panel to hold text fields for author input
                JPanel inputPanel = new JPanel(new GridLayout(0, 2));
                JLabel authorLabel = new JLabel("Author:");
                JTextField authorField = new JTextField();
                inputPanel.add(authorLabel);
                inputPanel.add(authorField);

                // Show the input panel in a dialog
                int inputOption = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Author Name", JOptionPane.OK_CANCEL_OPTION);
                if (inputOption == JOptionPane.OK_OPTION) {
                    String authorInput = authorField.getText();
                    String userData = Library1.SearchBookByAuthor(authorInput);
                    if (!userData.isEmpty()) {
                        // Display data in tabular form
                        String[] columnNames = {"Title", "BookID", "Author", "Genre", "Available"};
                        String[] rowData = userData.split("\n");
                        Object[][] tableData = new Object[rowData.length / 5][columnNames.length];

                        for (int i = 0; i < rowData.length / 5; i++) {
                            for (int j = 0; j < columnNames.length; j++) {
                                tableData[i][j] = rowData[(i * 5) + j];
                            }
                        }
                        JTable table = new JTable(tableData, columnNames);
                        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Books", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No books found with this user ID!");
                    }
                    if (x == 1) {
                        adminView();
                    } else {
                        Menu();
                    }
                }
            }
            if (titleCheckbox.isSelected()) {
                // Create a new panel to hold text fields for title input
                JPanel inputPanel = new JPanel(new GridLayout(0, 2));
                JLabel titleLabel = new JLabel("Title:");
                JTextField titleField = new JTextField();
                inputPanel.add(titleLabel);
                inputPanel.add(titleField);

                // Show the input panel in a dialog
                int inputOption = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Title", JOptionPane.OK_CANCEL_OPTION);
                if (inputOption == JOptionPane.OK_OPTION) {
                    String titleInput = titleField.getText();
                    if (!Library1.SearchBookByTitle(titleInput).isEmpty()) {
                        JOptionPane.showMessageDialog(null, Library1.SearchBookByTitle(titleInput));
                    } else {
                        JOptionPane.showMessageDialog(null, "No books found with this title!");
                    }
                    if (x == 1) {
                        adminView();
                    } else {
                        Menu();
                    }
                }
            }
            if (idCheckbox.isSelected()) {
                // Create a new panel to hold text fields for ID input
                JPanel inputPanel = new JPanel(new GridLayout(0, 2));
                JLabel idLabel = new JLabel("ID:");
                JTextField idField = new JTextField();
                inputPanel.add(idLabel);
                inputPanel.add(idField);

                // Show the input panel in a dialog
                int inputOption = JOptionPane.showConfirmDialog(null, inputPanel, "Enter User ID", JOptionPane.OK_CANCEL_OPTION);
                if (inputOption == JOptionPane.OK_OPTION) {
                    int idInput = Integer.parseInt(idField.getText());
                    String userData = Library1.SearchBookByUserID(idInput);
                    if (!userData.isEmpty()) {
                        // Display data in tabular form
                        String[] columnNames = {"Title", "BookID", "Author", "Genre", "Available"};
                        String[] rowData = userData.split("\n");
                        Object[][] tableData = new Object[rowData.length / 5][columnNames.length];

                        for (int i = 0; i < rowData.length / 5; i++) {
                            for (int j = 0; j < columnNames.length; j++) {
                                tableData[i][j] = rowData[(i * 5) + j];
                            }
                        }
                        JTable table = new JTable(tableData, columnNames);
                        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Books", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No books found with this user ID!");
                    }
                    if (x == 1) {
                        adminView();
                    } else {
                        Menu();
                    }
                }

            }

        }
    }
    public static void returnBook(int x) {
        // Create components
        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(10);
        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField(10);
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> {
            // Retrieve the entered name and contact information
            int bid = Integer.parseInt(bookIdField.getText());
            int uid = Integer.parseInt(userIdField.getText());
            int returnStatus = Library1.returnBook(bid, uid);
//            for(User i:Users){
//                if(i.GetUserID()==uid){
//                    List<Book> temp = i.RetrieveBorrowedBooks();
//                    for(Book j: temp){
//                        if(j.GetBookID()==bid){
//                            temp.remove(j);
//                            i.ModifyBorrowedBooks(temp);
//                            j.ChangeAvailability('y');
//                            for(Book l:Books){
//                                if(l.GetBookID()==bid){
//                                    l.ChangeAvailability('y');
//                                }
//                            }
//                            returnStatus=1;
//                            break;
//                        }
//                    }
//                }
//            }
            if (returnStatus == 1) {
                JOptionPane.showMessageDialog(frame, "Returned Book Successfully!");

            } else {
                JOptionPane.showMessageDialog(frame, "Return Failed!");
            }
            if (x == 0) {
                adminView();
            } else {
                Menu();
            }
        });
        // Create panel to hold components
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new GridLayout(4, 2));
        returnPanel.add(bookIdLabel);
        returnPanel.add(bookIdField);
        returnPanel.add(userIdLabel);
        returnPanel.add(userIdField);
        returnPanel.add(returnButton);
        frame.getContentPane().removeAll();
        frame.add(returnPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void borrowBook(int x) {
        // Create components
        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(10);
        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField(10);
        JButton returnButton = new JButton("Check Out");
        returnButton.addActionListener(e -> {
            // Retrieve the entered name and contact information
            int bid = Integer.parseInt(bookIdField.getText());
            int uid = Integer.parseInt(userIdField.getText());
            if (Library1.checkOut(bid, uid) == 1) {
                JOptionPane.showMessageDialog(frame, "Checked Book Successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Check out failed!");
            }
            if (x == 0) {
                adminView();
            } else {
                Menu();
            }
        });
        // Create panel to hold components
        JPanel borrowBookPanel = new JPanel();
        borrowBookPanel.setLayout(new GridLayout(4, 2));
        borrowBookPanel.add(bookIdLabel);
        borrowBookPanel.add(bookIdField);
        borrowBookPanel.add(userIdLabel);
        borrowBookPanel.add(userIdField);
        borrowBookPanel.add(returnButton);
        frame.getContentPane().removeAll();
        frame.add(borrowBookPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void saveData(List<User> users, List<Book> books) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = new FileWriter(USERS_JSON_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new FileWriter(BOOKS_JSON_FILE)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadData(List<User> users, List<Book> books) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(USERS_JSON_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            ArrayList<User> loadedUsers = gson.fromJson(reader, userListType);
            if (loadedUsers != null) {
                users.addAll(loadedUsers);
                nu = loadedUsers.size() + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader(BOOKS_JSON_FILE)) {
            Type bookListType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            ArrayList<Book> loadedBooks = gson.fromJson(reader, bookListType);
            if (loadedBooks != null) {
                books.addAll(loadedBooks);
                bu = loadedBooks.size() + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void forgotID() {
        JPanel forgotID = new JPanel();
        JLabel nameLabel = new JLabel("Name:");
        JLabel contactLabel = new JLabel("Contact Information:");
        JTextField nameField = new JTextField(20);
        JTextField contactField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();

                String contactInfo = contactField.getText();
                int temp = 0;

                while (true) {
                    try {
                        validateTextField(contactInfo, "Contact Info");
                        temp = Library1.forgotID(name, contactInfo);
                        if (temp != 0) {
                            // Show message dialog with the entered information
                            JOptionPane.showMessageDialog(frame,
                                    "Name: " + name + "\n" + "Contact Information: " + contactInfo + "\nUser ID: " + temp);
                            break; // Break the loop if input is valid and ID is found
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error Finding ID!");
                            break; // Break the loop if ID is not found
                        }
                    } catch (IllegalArgumentException f) {
                        // Show error message
                        JOptionPane.showMessageDialog(frame, "Error: " + f.getMessage());
                        // Prompt user to enter correct input again
                        name = JOptionPane.showInputDialog(frame, "Enter Name:");
                        contactInfo = JOptionPane.showInputDialog(frame, "Enter Contact Info:");
                    }
                }
                LoginDetails();
            }
        });

        // Add components to the panel
        forgotID.add(nameLabel);
        forgotID.add(nameField);
        forgotID.add(contactLabel);
        forgotID.add(contactField);
        forgotID.add(submitButton);

        frame.getContentPane().removeAll();
        frame.add(forgotID, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void validateTextField(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, fieldName + " cannot be empty!");
            return; // Return from the method if text is empty
        }

        // Perform type checking
        if (!text.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(frame, fieldName + " must contain only alphabets!");
        }

        // You can add more type checking rules here as needed
    }
}