package Assignment01;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;
import static Assignment01.Library.nu;
public class User{
    //ATTRIBUTES:
    private int UserID;
    private String Name;
    private String ContactInfo;
    //Using @Expose to help GSON find and serialize this.
    @Expose private List<Book> BorrowedBooks = new ArrayList<Book>();
    //METHODS
    //Defined constructor for creating User objects with user defined values
    public User(String name, String Contact_info){
        //UserID is assigned value one greater the number of User objects in USERS.JSON
        UserID = nu;
        Name = name;
        ContactInfo = Contact_info;
    }
    //Getter for UserID
    public int GetUserID(){
        return UserID;
    }
    //Getter for Name
    public String GetName(){
        return Name;
    }
    //Getter for ContactInfo
    public String GetContactInfo(){
        return ContactInfo;
    }
    /**
     * Sets the list of borrowed books for the user.
     *
     * @param book The book to be added to the user's borrowed books list.
     */
    public void SetBorrowedBooks(Book book){
        int success = 0;
        BorrowedBooks.add(book);
    }
    //Getter for BorrowedBooks
    public List<Book> RetrieveBorrowedBooks() {
        return BorrowedBooks;
    }
    /**
     * Modifies the list of borrowed books for the user.
     *
     * @param _BorrowedBooks The modified List of BorrowedBooks.
     */
    public void ModifyBorrowedBooks(List<Book> _BorrowedBooks){
        BorrowedBooks = _BorrowedBooks;
    }
    //Converts all Books in BorrowedBooks to one String
    public String ShowBookCollection(){
        String message = "";
        //Appends Book attributes to message along with \n to separate each attribute
        for(Book i:BorrowedBooks){
            message += i.GetTitle() + "\n" +  i.GetBookID() + "\n" + i.GetAuthor() + "\n" + i.GetGenre() + "\n" +i.GetAvailability() + "\n";
        }
        return message;
    }
}