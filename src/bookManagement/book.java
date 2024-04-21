/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookManagement;

/**
 *
 * @author admin
 */
public class book {
    String isbn;
    String author;
    String title;
    int yearpub;
    int type;
    public book(String isbn,String title,String author,int yearpub,int type){
        this.isbn=isbn;
        this.title=title;
        this.author=author;
        this.yearpub=yearpub;
        this.type=type;
    }
    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public int getYearpub(){
        return yearpub;
    }
    public int getType(){
        return type;
    }
}
