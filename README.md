# CS212-Assignment-01
Library Management System Using OOP

To set up and run the project locally, you have two options based on your preference and environment. Below are the instructions for each method:

Option 1: Using IntelliJ IDEA (Recommended)

Prerequisites:

IntelliJ IDEA 2023.3 (or later version) installed on your system.
Setup Instructions:

Clone the project repository from the source, or download it as a ZIP archive and extract it to your local machine.
Launch IntelliJ IDEA 2023.3 on your computer.
Open the project by selecting "Open Project" from the welcome screen or navigating to "File" > "Open" and selecting the project directory.
Trust the author of the project if prompted to do so by IntelliJ IDEA.
Running the Project:

Locate the main class of the project, typically the class containing the main method.
Click on the "Run" button in the toolbar or right-click on the main class file and select "Run <MainClassName>" from the context menu.

Option 2: Command-Line Compilation and Execution

Prerequisites:

JDK 21 installed on your system.
Setup Instructions:

Clone the project repository from the source, or download it as a ZIP archive and extract it to your local machine.
Open a terminal or command prompt and navigate to the root directory of the project.

Compilation:

javac -d OUT -cp Dependencies/gson-2.10.1.jar src/Assignment01/Main.java src/Assignment01/Class1.java src/Assignment01/Class2.java

Running the Program:

java -cp "OUT;Dependencies/gson-2.10.1.jar" Assignment01.Main
Ensure that you have set up your environment variables properly to point to the JDK installation directory. You can verify your JDK installation by running java -version and javac -version commands in your terminal or command prompt.

By following either of these methods, you should be able to successfully set up and run the project locally on your machine. If you encounter any issues during the setup or execution process, feel free to reach out for assistance.
