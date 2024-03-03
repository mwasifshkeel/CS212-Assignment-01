package Assignment01;

import static Assignment01.Library.bu;

public class Book{
    private int BookID;
    private String Title;
    private String Author;
    private String Genre;
    private boolean Available = false;
    public Book(String title,String author,String genre){
        BookID = bu;
        Title= title;
        Author = author;
        Genre = genre;
        Available = true;
    }
    public int GetBookID(){
        return BookID;
    }
    public String GetTitle(){
        return Title;
    }
    public String GetAuthor(){
        return Author;
    }
    public String GetGenre(){
        return Genre;
    }
    public boolean GetAvailability(){
        return Available;
    }
    public void ChangeTitle(String title){
        Title = title;
    }
    public void ChangeAuthor(String author){
        Author = author;
    }
    public void ChangeGenre(String genre){
        Genre = genre;
    }
    public void ChangeAvailability(char option){
        if(option=='y'){
            Available = true;
        }else{
            Available = false;
        }
    }
}