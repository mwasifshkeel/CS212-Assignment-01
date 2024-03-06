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
        userPanel.add(forgotIDButton);
        userPanel.add(adminButton);

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

            try {
                // Validate the text fields
                validateTextField(name, "Name");
                validateContactInfo(contactInfo);

                // Create and add user
                User temp = new User(name, contactInfo);
                Library1.addUser(temp);
                String message = "User created successfully! Note Down ID: " + temp.GetUserID();
                JOptionPane.showMessageDialog(frame, message);
                if (x == 0) {
                    LoginDetails();
                } else {
                    adminView();
                }
            } catch (IllegalArgumentException ex) {
                // Handle validation errors by displaying an error message
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                if (x == 0) {
                    LoginDetails();
                } else {
                    adminView();
                }
            }
        });

        // Add components to the panel
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(nameField);
        userInfoPanel.add(contactLabel);
        userInfoPanel.add(contactField);
        userInfoPanel.add(submitButton);
        userInfoPanel.add(ReturnBackButton);
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
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                LoginDetails();
            }
        });
        // Add components to the panel
        loginPanel.add(idLabel);
        loginPanel.add(idField);
        loginPanel.add(loginButton);
        loginPanel.add(ReturnBackButton);
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
                    JOptionPane.showMessageDialog(frame, "Incorrect Password! Try Again.");
                }
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                LoginDetails();
            }
        });

        // Add components to the panel
        AdminLoginPanel.add(idLabel);
        AdminLoginPanel.add(passwordField);
        AdminLoginPanel.add(loginButton);
        AdminLoginPanel.add(ReturnBackButton);
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

        NewMenuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().removeAll();
        frame.add(NewMenuPanel, BorderLayout.CENTER);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
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
            // Retrieve the entered ID
            String idText = idField.getText();
            try {
                // Validate the ID
                validateID(idText, "User ID");

                // If validation passes, parse the ID and remove user
                int id = Integer.parseInt(idText);
                if (Library1.removeUser(id) == 1) {
                    JOptionPane.showMessageDialog(frame, "Removed User Successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed");
                }
                adminView();
            } catch (IllegalArgumentException ex) {
                // Handle validation errors by displaying an error message
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                adminView();
            }
        });
        removeUserPanel.add(idLabel);
        removeUserPanel.add(idField);
        removeUserPanel.add(removeButton);
        removeUserPanel.add(ReturnBackButton);
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

            try {
                // Validate the text fields
                validateTextField(name, "Title");
                validateTextField(author, "Author");
                validateTextField(genre, "Genre");

                // Create and add book
                Book temp = new Book(name, author, genre);
                Library1.addBook(temp);
                JOptionPane.showMessageDialog(frame, "Book added successfully!");
                adminView();
            } catch (IllegalArgumentException ex) {
                // Handle validation errors by displaying an error message
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                adminView();
            }
        });
        // Add components to the panel
        addBookPanel.add(nameLabel);
        addBookPanel.add(nameField);
        addBookPanel.add(authorLabel);
        addBookPanel.add(authorField);
        addBookPanel.add(genreLabel);
        addBookPanel.add(genreField);
        addBookPanel.add(submitButton);
        addBookPanel.add(ReturnBackButton);
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
            try {
                // Retrieve the entered book ID
                String id = idField.getText();

                // Validate the book ID
                validateID(id, "Book ID");

                // Convert the ID to integer
                int x = Integer.parseInt(id);

                // Remove the book
                if (Library1.removeBook(x) == 1) {
                    JOptionPane.showMessageDialog(frame, "Removed Book Successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to remove book!");
                }
            } catch (IllegalArgumentException ex) {
                // Handle validation errors
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            } finally {
                // Navigate back to admin view
                adminView();
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                adminView();
            }
        });

        // Add components to the panel
        removeBookPanel.add(idLabel);
        removeBookPanel.add(idField);
        removeBookPanel.add(removeButton);
        removeBookPanel.add(ReturnBackButton);

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
            if (authorCheckbox.isSelected() && !titleCheckbox.isSelected() && !idCheckbox.isSelected()) {
                // Search by author selected
                try {
                    // Validate author input
                    String authorInput = JOptionPane.showInputDialog("Enter Author Name:");
                    validateTextField(authorInput, "Author Name");

                    // Perform search and display results
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
                        JOptionPane.showMessageDialog(null, "No books found with this author name!");
                    }
                } catch (IllegalArgumentException ex) {
                    // Handle validation errors
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else if (titleCheckbox.isSelected() && !authorCheckbox.isSelected() && !idCheckbox.isSelected()) {
                // Search by title selected
                try {
                    // Validate title input
                    String titleInput = JOptionPane.showInputDialog("Enter Title:");
                    validateTextField(titleInput, "Title");

                    // Perform search and display results
                    String userData = Library1.SearchBookByTitle(titleInput);
                    if (!userData.isEmpty()) {
                        JOptionPane.showMessageDialog(null, userData);
                    } else {
                        JOptionPane.showMessageDialog(null, "No books found with this title!");
                    }
                } catch (IllegalArgumentException ex) {
                    // Handle validation errors
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else if (idCheckbox.isSelected() && !authorCheckbox.isSelected() && !titleCheckbox.isSelected()) {
                // Search by user ID selected
                try {
                    // Validate user ID input
                    String idInput = JOptionPane.showInputDialog("Enter User ID:");
                    validateID(idInput, "User ID");

                    // Perform search and display results
                    int uid = Integer.parseInt(idInput);
                    String userData = Library1.SearchBookByUserID(uid);
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
                } catch (NumberFormatException ex) {
                    // Handle invalid user ID input
                    JOptionPane.showMessageDialog(null, "Invalid User ID! Please enter a numerical value.");
                } catch (IllegalArgumentException ex) {
                    // Handle validation errors
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select only one search option!");
            }

            // Navigate back to appropriate view
            if (x == 1) {
                adminView();
            } else {
                Menu();
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
            try {
                // Retrieve the entered book ID and user ID
                String bookId = bookIdField.getText();
                String userId = userIdField.getText();

                // Validate book ID and user ID
                validateID(bookId, "Book ID");
                validateID(userId, "User ID");

                int bid = Integer.parseInt(bookId);
                int uid = Integer.parseInt(userId);
                int returnStatus = Library1.returnBook(bid, uid);

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
            } catch (IllegalArgumentException ex) {
                // Handle validation errors by displaying an error message and allowing user to retry
                JOptionPane.showMessageDialog(frame, ex.getMessage());
                returnBook(x); // Loop back to returnBook() method
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                if (x == 0) {
                    adminView();
                } else {
                    Menu();
                }
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
        returnPanel.add(ReturnBackButton);

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
            try {
                // Retrieve the entered book ID and user ID
                String bookId = bookIdField.getText();
                String userId = userIdField.getText();

                // Validate book ID and user ID
                validateID(bookId, "Book ID");
                validateID(userId, "User ID");

                int bid = Integer.parseInt(bookId);
                int uid = Integer.parseInt(userId);
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
            } catch (IllegalArgumentException ex) {
                // Handle validation errors by displaying an error message and allowing user to retry
                JOptionPane.showMessageDialog(frame, ex.getMessage());
                borrowBook(x); // Loop back to borrowBook() method
            }
        });
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                if (x == 0) {
                    adminView();
                } else {
                    Menu();
                }
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
        borrowBookPanel.add(ReturnBackButton);

        frame.getContentPane().removeAll();
        frame.add(borrowBookPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void forgotID() {
        JPanel forgotIDPanel = new JPanel(new GridLayout(4,1));
        JLabel nameLabel = new JLabel("Name:");
        JLabel contactLabel = new JLabel("Contact Information:");
        JTextField nameField = new JTextField(20);
        JTextField contactField = new JTextField(20);

        forgotIDPanel.add(nameLabel);
        forgotIDPanel.add(nameField);
        forgotIDPanel.add(contactLabel);
        forgotIDPanel.add(contactField);
        JButton ReturnBackButton = new JButton("Go Back");
        ReturnBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                LoginDetails();
            }
        });
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String contactInfo = contactField.getText();

                try {
                    // Validate name and contact information
                    validateTextField(name, "Name");
                    validateContactInfo(contactInfo);

                    // If validation passes, proceed with Library1.forgotID() method call
                    int temp = Library1.forgotID(name, contactInfo);
                    if (temp != 0) {
                        // Show message dialog with the entered information and user ID
                        JOptionPane.showMessageDialog(frame,
                                "Name: " + name + "\n" + "Contact Information: " + contactInfo + "\nUser ID: " + temp);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error Finding ID!");
                    }
                    LoginDetails(); // Assuming this method moves to the next step after obtaining user ID
                } catch (IllegalArgumentException ex) {
                    // Handle validation errors by displaying an error message and allowing user to retry
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        forgotIDPanel.add(submitButton);
        forgotIDPanel.add(ReturnBackButton);

        // Display the panel for user input
        forgotIDPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().removeAll();
        frame.add(forgotIDPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public static void validateTextField(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty!");
        }

        // Perform type checking
        if (!text.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException(fieldName + " must contain only alphabets!");
        }

        // You can add more type checking rules here as needed
    }
    public static void validateContactInfo(String contactInfo) {
        if (contactInfo == null || contactInfo.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact Information cannot be empty!");
        }

        // Ensure contact information contains only numerical characters
        if (!contactInfo.matches("[0-9]+")) {
            throw new IllegalArgumentException("Contact Information must contain only numerical characters!");
        }

        // Ensure contact information has a length of 11 characters
        if (contactInfo.length() != 11) {
            throw new IllegalArgumentException("Contact Information must be 11 characters long!");
        }
    }
    public static void validateID(String id, String fieldName) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty!");
        }

        // Ensure the ID contains only numerical characters
        if (!id.matches("[0-9]+")) {
            throw new IllegalArgumentException(fieldName + " must contain only numerical characters!");
        }
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
        File usersFile = new File(USERS_JSON_FILE);
        File booksFile = new File(BOOKS_JSON_FILE);

        // Check if files exist
        if (!usersFile.exists() || !booksFile.exists()) {
            JOptionPane.showMessageDialog(null, "Welcome! It seems like this is your first time running the program. No data loaded.");
            return;
        }

        try (Reader reader = new FileReader(USERS_JSON_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            ArrayList<User> loadedUsers = gson.fromJson(reader, userListType);
            if (loadedUsers != null) {
                users.addAll(loadedUsers);
                nu = loadedUsers.size() + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader(BOOKS_JSON_FILE)) {
            Type bookListType = new TypeToken<ArrayList<Book>>() {}.getType();
            ArrayList<Book> loadedBooks = gson.fromJson(reader, bookListType);
            if (loadedBooks != null) {
                books.addAll(loadedBooks);
                bu = loadedBooks.size() + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}