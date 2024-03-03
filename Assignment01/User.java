package Assignment01;

import java.util.ArrayList;
import java.util.List;

import static Assignment01.Library.nu;

public class User{
    private int UserID;
    private String Name;
    private String ContactInfo;
    private static List<Book> BorrowedBooks = new ArrayList<Book>();
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
    public static void AddBook(Book book){
        BorrowedBooks.add(book);
        NumBooks++;
    }
    public static void RemoveBook(Book book){
        BorrowedBooks.remove(book);
        NumBooks--;
    }
    public String ShowBookCollection(){
        String message = "";
        for(Book i:BorrowedBooks){
            message = i.GetTitle() + " " + i.GetBookID()+" " +i.GetAuthor()+ " " + i.GetGenre() + " " + i.GetAvailability() + "\n";
        }
        return message;
    }
}