package Assignment01;
import static Assignment01.Library.bu;
public class Book{
    //ATTRIBUTES:
    private int BookID;
    private String Title;
    private String Author;
    private String Genre;
    private boolean Available = false;
    //METHODS:
    //Defined constructor for creating Book objects with librarian defined values
    public Book(String title,String author,String genre){
        BookID = bu;
        Title= title;
        Author = author;
        Genre = genre;
        Available = true;
    }
    //Getter for BookID
    public int GetBookID(){
        return BookID;
    }
    //Getter for Title
    public String GetTitle(){
        return Title;
    }
    //Getter for Author
    public String GetAuthor(){
        return Author;
    }
    //Getter for Genre
    public String GetGenre(){
        return Genre;
    }
    //Getter for Availability
    public boolean GetAvailability(){
        return Available;
    }
    //Modifier for Availability
    public void ChangeAvailability(char available){
        if(available=='y'||available=='Y'){
            Available = true;
        }else{
            Available = false;
        }
    }
}