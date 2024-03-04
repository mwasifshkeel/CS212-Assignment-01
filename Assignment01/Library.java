package Assignment01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static Assignment01.Main.current_user;


public class Library {
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
    public int checkOut(int book_id, int user_id){
        int x = 0;
        for(User i: Users){
            if(i.GetUserID()==user_id){
                for(Book j:Books){
                    if(j.GetBookID()==book_id){
                        if(j.GetAvailability()) {
                            i.SetBorrowedBooks(j);
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
    public int returnBook(int book_id,int user_id){
        int x = 0;
        for(User i: Users){
            if(i.GetUserID()==user_id){
                for(Book j:Books){
                    if(j.GetBookID()==book_id){
                        x = i.GetBorrowedBooks(j);
                        j.ChangeAvailability('y');
                        break;
                    }
                }
                break;
            }
        }
        return x;
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
    public String SearchBookByAuthor(String author) {
        String message = "";
        for (Book i : Books) {
            if (i.GetAuthor().equals(author)) {
                message += "Title: " + i.GetTitle() + "\n" + "BookID: " +i.GetBookID() + "\n" + "Author: " +i.GetAuthor() + "\n" + "Genre: " + i.GetGenre() + "\n" + "Available: " + i.GetAvailability() + "\n";
            }
        }
        return message;
    }
    public String SearchBookByTitle(String title){
        String message = "";
        for(Book i:Books){
            if(i.GetTitle().equals(title)){
                message += "Title: " + i.GetTitle() + "\n" + "BookID: "+ i.GetBookID() + "\n" + "Author: " +i.GetAuthor() + "\n" + "Genre: " + i.GetGenre() + "\n" + "Available: " + i.GetAvailability() + "\n";
            }
        }
        return message;
    }
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
    public String SearchBookByUserID(int id){
        String x = "";
        for(User i : Users){
            if(i.GetUserID()==id){
                x= i.ShowBookCollection();
                return x;
            }
        }
        return x;
    }
}
