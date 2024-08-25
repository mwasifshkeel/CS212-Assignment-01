# CS212-Assignment-01: Library Management System Using OOP

## Project Description

The Library Management System is a Java application designed to facilitate the management of a library's resources and users. It offers librarians a graphical interface to perform various tasks such as adding new books, updating book details, checking out books to users, managing user accounts, and searching for books by title or author. Moreover, it allows users themselves to borrow, return, and search for books without the aid of the librarian. The system also incorporates file-based data persistence to store book and user information between application runs, ensuring data continuity.

## Setup and Run Instructions

### Option 1: Using IntelliJ IDEA (Recommended)

#### Prerequisites

- IntelliJ IDEA 2023.3 (or later version) installed on your system.

#### Setup Instructions

1. Clone the project repository from the source, or download it as a ZIP archive and extract it to your local machine.
    ```bash
    git clone https://github.com/mwasifshkeel/CS212-Assignment-01.git
    ```
2. Launch IntelliJ IDEA 2023.3 on your computer.
3. Open the project by selecting "Open Project" from the welcome screen or navigating to `File > Open` and selecting the project directory.
4. Trust the author of the project if prompted to do so by IntelliJ IDEA.

#### Running the Project

1. Locate the main class of the project, typically the class containing the `main` method.
2. Click on the "Run" button in the toolbar or right-click on the main class file and select `Run Main.java` from the context menu.

### Option 2: Command-Line Compilation and Execution

#### Prerequisites

- JDK 21 installed on your system.

#### Setup Instructions

1. Clone the project repository from the source, or download it as a ZIP archive and extract it to your local machine.
    ```bash
    git clone https://github.com/mwasifshkeel/CS212-Assignment-01.git
    ```
2. Open a terminal or command prompt and navigate to the root directory of the project.

#### Compilation

-
    ```bash
    javac -d OUT -cp Dependencies/gson-2.10.1.jar src/Assignment01/Main.java src/Assignment01/User.java src/Assignment01/Book.java src/Assignment01/Library.java
    ```

#### Running the Program

-
    ```bash
    java -cp "OUT;Dependencies/gson-2.10.1.jar" Assignment01.Main
    ```

Ensure that you have set up your environment variables properly to point to the JDK installation directory. You can verify your JDK installation by running `java -version` and `javac -version` commands in your terminal or command prompt.

By following either of these methods, you should be able to successfully set up and run the project locally on your machine. If you encounter any issues during the setup or execution process, feel free to reach out for assistance.

## Key Features and Functionalities Implemented

1. **Graphical User Interface (GUI) using Swing**:
    - The application provides a user-friendly graphical interface built using Swing, allowing librarians to interact with the system seamlessly.
    - The GUI presents menus and forms for librarians to perform various tasks such as adding books, adding users, checking out books, returning books, and searching for books.


2. **Serialization and Deserialization using Gson**:
    - Gson library is utilized to serialize and deserialize ArrayLists of users and books.
    - Serialized data is saved to files to ensure persistence between application runs.
    - Deserialization loads previously saved data, enabling the system to resume its state from the previous session.


3. **Error Handling**:
    - Robust error handling mechanisms are implemented to gracefully manage invalid user inputs and edge cases.
    - Error messages are displayed to alert users about incorrect inputs or unexpected scenarios, guiding them to rectify errors effectively.


4. **Validation Checks**:
    - Validation checks are incorporated to ensure that data entered by librarians is accurate and follows the required format.
    - Validations are performed on inputs such as book details, user information, and search queries to maintain data integrity and consistency.


5. **Menu-Driven Interface**:
    - The main class (`Main`) provides a menu-driven interface, allowing librarians to easily navigate through different functionalities of the system.
    - Librarians can select desired actions from the menu, triggering corresponding methods to perform tasks efficiently.


6. **Adding and Managing Books**:
    - Librarians can add new books to the library by providing details such as book ID, title, author, genre, and availability status.
    - The system allows librarians to update book details, ensuring accurate and up-to-date information is maintained.


7. **Managing Users and Borrowed Books**:
    - Librarians can add new users to the system, including details such as user ID, name, and contact information.
    - The application facilitates checking out books to users and returning books, updating the borrowed book lists for each user accordingly.


8. **Search Functionality**:
    - Librarians can search for books by title, author, or user ID, enabling quick and efficient retrieval of relevant information.
    - The search feature enhances the usability of the system by providing librarians with targeted results based on their queries.


## Authors

- [Muhammad Wasif Shakeel](https://github.com/mwasifshkeel)

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Acknowledgements

- [Java](https://docs.oracle.com/en/java/)
- [Gson](https://github.com/google/gson)
- [Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [IntelliJ IDEA](https://www.jetbrains.com/help/idea/getting-started.html)
