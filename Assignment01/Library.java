package Assignment01;

import java.util.ArrayList;
import java.util.List;
import static Assignment01.Main.current_user;


public class Library{
    public static int nu = 1;
    public static int bu = 1;
    public static List<Book> Books = new ArrayList<Book>();
    public static List<User> Users = new ArrayList<User>();
    public void addBook(Book user_book){
        bu++;
        Books.add(user_book);
    }
    public void addUser(User user){
        ++nu;
        Users.add(user);
    }
    public void checkOut(Book book, User user){
        if(book.GetAvailability()==true) {
            User.AddBook(book);
            book.ChangeAvailability('N');
        }
    }
    public int findUser(int id){
        for(User i : Users){
            if(i.GetUserID()==id){
                current_user = i;
                return(0);
            }
        }
        return -1;
    }
    public void ReturnBook(Book book, User user){
        book.ChangeAvailability('y');
        User.RemoveBook(book);
    }
    public String SearchBookByAuthor(String author){
        String message = "";
        for(Book i:Books){
            if(i.GetAuthor()==author){
               message = i.GetTitle() + " " + i.GetBookID()+" " +i.GetAuthor()+ " " + i.GetGenre() + " " + i.GetAvailability() + "\n";
            }
        }
        return message;
    }
    public String SearchBookByTitle(String title){
        String message = "";
        for(Book i:Books){
            if(i.GetTitle()==title){
                message = i.GetTitle() + " " + i.GetBookID()+" " +i.GetAuthor()+ " " + i.GetGenre() + " " + i.GetAvailability() + "\n";
            }
        }
        return message;
    }
    public String ShowBooks(){
        String message = "";
        for(Book i:Books){
            message = i.GetTitle() + " " + i.GetBookID()+" " +i.GetAuthor()+ " " + i.GetGenre() + " " + i.GetAvailability() + "\n";
        }
        return message;
    }
}