package Assignment01;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Library {
    //Declaring Static Variables As Class Will Only Have One Object/Instance
    /**
     Number of Users Registered.
     */
    public static int nu = 1;
    /**
     Number of Books Registered.
     */
    public static int bu = 1;
    /**
     Collection of Books in Library.
     */
    public static List<Book> Books = new ArrayList<Book>();
    /**
     Collection of Users in Library
     */
    public static List<User> Users = new ArrayList<User>();
    //Setter for Books List
    public void addBook(Book user_book){
        bu++;
        Books.add(user_book);
    }
    //Setter for Users List
    public void addUser(User user){
        ++nu;
        Users.add(user);
    }
    /*Appends Book in BorrowedBooks of User
      Returns 1 on success*/
    public int checkOut(int book_id, int user_id){
        int x = 0;
        //Finds User with ID = user_id
        for(User i: Users){
            if(i.GetUserID()==user_id){
                //Finds Book with ID = book_id
                for(Book j:Books){
                    if(j.GetBookID()==book_id){
                        //Checks if book is available
                        if(j.GetAvailability()) {
                            i.SetBorrowedBooks(j);
                            //Updates the book availability
                            j.ChangeAvailability('N');
                            x=1;
                        }
                        break;
                    }
                }
                break;
            }
        }
        return x;
    }
    /*Removes Book in BorrowedBooks of User
      Returns 1 on success*/
    public int returnBook(int bid,int uid){
        int returnStatus = 0;
        //Finds User with user_id
        for(User i:Users){
            if(i.GetUserID()==uid){
                //Retrieves BorrowedBooks List of that User and stores in temp list
                List<Book> temp = i.RetrieveBorrowedBooks();
                //Finds Book inside BorrowedBooks
                for(Book j: temp){
                    if(j.GetBookID()==bid){
                        //Removes the book from temp list
                        temp.remove(j);
                        //Overwrites BorrowedBooks with temp list
                        i.ModifyBorrowedBooks(temp);
                        //Updates Book Availability in BorrowedList
                        j.ChangeAvailability('y');
                        for(Book l:Books){
                            if(l.GetBookID()==bid){
                                //Updates Book Availability in Books ArrayList
                                l.ChangeAvailability('y');
                            }
                        }
                        returnStatus=1;
                        break;
                    }
                }
            }
        }
        return returnStatus;
    }
    //Method for finding User using his ID
    public int findUser(int id){
        for(User i : Users){
            if(i.GetUserID()==id){
                return(0);
            }
        }
        return -1;
    }
    //Converts all Books with same Author to one String
    public String SearchBookByAuthor(String author) {
        String message = "";
        for (Book i : Books) {
            if (i.GetAuthor().equals(author)) {
                //Appends Book attributes to message along with \n to separate each attribute
                message += "Title: " + i.GetTitle() + "\n" + "BookID: " +i.GetBookID() + "\n" + "Author: " +i.GetAuthor() + "\n" + "Genre: " + i.GetGenre() + "\n" + "Available: " + i.GetAvailability() + "\n";
            }
        }
        return message;
    }
    /**
        * Searches for a book by its title in the list of books.
        *
        *     @param title The title of the book to search for.
        *     @return A string containing information about the book if found, otherwise an empty string.
    */
    public String SearchBookByTitle(String title){
        String message = "";
        for(Book i:Books){
            if(i.GetTitle().equals(title)){
                message += "Title: " + i.GetTitle() + "\n" + "BookID: "+ i.GetBookID() + "\n" + "Author: " +i.GetAuthor() + "\n" + "Genre: " + i.GetGenre() + "\n" + "Available: " + i.GetAvailability() + "\n";
                break;
            }
        }
        return message;
    }
    //Removes User from ArrayList Users
    public int removeUser(int userID){
        int finish = 0;
        Iterator<User> iterator = Users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.GetUserID() == userID) {
                iterator.remove();
                finish = 1;
            }
        }
        return finish;
    }
    //Removes Book from ArrayList Books
    public int removeBook(int BookID){
        int finish = 0;
        Iterator<Book> iterator = Books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.GetBookID() == BookID) {
                iterator.remove();
                finish = 1;
            }
        }
        return finish;
    }
    /**
     * Searches for books borrowed by a user with the specified user ID.
     *
     * @param id The user ID to search for.
     * @return A string containing the book collection of the user if found, otherwise an empty string.
     */
    public String SearchBookByUserID(int id){
        String x = "";
        //Finds User with UserID = id
        for(User i : Users){
            if(i.GetUserID()==id){
                x= i.ShowBookCollection();
                return x;
            }
        }
        return x;
    }
    /**
     * Retrieves the user ID associated with the specified name and contact information.
     *
     * @param name The name of the user.
     * @param cinfo The contact information of the user.
     * @return The user ID if a user with the specified name and contact information is found, otherwise 0.
     */
    public int forgotID(String name, String cinfo){
        int y= 0;
        //Finds User with Name and ContactInfo being equal to parameters
        for(User i : Users){
            if(i.GetName().equals(name)&&i.GetContactInfo().equals(cinfo)){
                return i.GetUserID();
            }
        }
        return 0;
    }
}