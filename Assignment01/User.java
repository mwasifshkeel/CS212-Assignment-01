package Assignment01;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Assignment01.Library.nu;

public class User{
    private int UserID;
    private String Name;
    private String ContactInfo;
    @Expose private List<Book> BorrowedBooks = new ArrayList<Book>();
    private static int NumBooks =0;
    public User(String name, String Contact_info){
        UserID = nu;
        Name = name;
        ContactInfo = Contact_info;
    }
    public int GetUserID(){
        return UserID;
    }
    public String GetName(){
        return Name;
    }
    public String GetContactInfo(){
        return ContactInfo;
    }
    public void ChangeName(String name){
        Name = name;
    }
    public void ChangeContactInfo(String contactInfo){
        ContactInfo = contactInfo;
    }
    public void SetBorrowedBooks(Book book){
        int success = 0;

        BorrowedBooks.add(book);
        NumBooks++;
    }
    public int GetBorrowedBooks(Book book) {
        int success = 0;
        Iterator<Book> iterator = BorrowedBooks.iterator();
        while (iterator.hasNext()) {
            Book bookBorrowed = iterator.next();
            if (bookBorrowed == book) {
                iterator.remove();
                NumBooks--;
                success = 1;
                break; // exit loop since book is found and removed
            }
        }
        return success;
    }
    public String ShowBookCollection(){
        String message = "";
        for(Book i:BorrowedBooks){
            message = i.GetTitle() + "\n" +  i.GetBookID() + "\n" + i.GetAuthor() + "\n" + i.GetGenre() + "\n" +i.GetAvailability() + "\n";
        }
        return message;
    }
}