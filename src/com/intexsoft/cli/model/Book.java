package com.intexsoft.cli.model;

import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * The class defining a book in a library.
 */
public class Book implements Cloneable {
    
    private static Logger log = Logger.getLogger(Book.class);
    
    /** Unique index of the book.*/
    private String id;

    /** Author of the book.*/
    private String author;

    /** Name of the book.*/
    private String name;

    /** Date of the book issuance.*/
    private Date issued = null;

    /** Abonent who issued the book.*/
    private String abonent = null;

    /**
     * Constructor for 3 arguments.
     * @param id Unique index of the book.
     * @param author Author of the book.
     * @param name Name of the book.
     */
    public Book(String id, String author, String name) {
        this.id = (String) id;
        this.author = (String) author;
        this.name = (String) name;
    }

    /**
     * Constructor for 5 arguments.
     * @param id Unique index of the book.
     * @param author Author of the book.
     * @param name Name of the book.
     * @param issued Date of the book issuance.
     * @param abonent Abonent who issued the book.
     */
    public Book(String id, String author, String name,
            String issued, String abonent) {
        this(id, author, name);
        this.issued = parseDate((String) issued);
        this.abonent = (String) abonent;
    }

    /**
     * Implementation the Cloneable interface
     */
    public Object clone() {
        Book cloned = null;
        try {
            cloned = (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error(e);
        }

        return cloned;
    }

    /**
     * Method for finding books on author or name fields.
     *
     * @param parameters Parameters for finding.
     * @return           true  - if the book satisfies the find parameters, 
     *                   false - if not.
     */
    public boolean findOnAuthorOrName(Map<String, String> parameters) {
        Boolean result = false;

        if (parameters.size() == 0) result = true;
        
        if (parameters.containsKey("author")) {
            String fAuthor = parameters.get("author");
            if (author.indexOf(fAuthor) != -1)
                result = true;
        }

        if (parameters.containsKey("name")) {
            String fName = parameters.get("name");
            if (name.indexOf(fName) != -1) 
                result = true;
        }

        return result;
    }

    /**
     * Method for finding the book on index field.
     *
     * @param parameters Parameters for finding.
     * @return           true  - if the book has id as in the parameters,
     *                   false - if not.
     */
    public boolean findOnId(Map<String, String> parameters) {
        if (parameters.get("id").equals(id))
            return true;
        else
            return false;
    }
    /**
     * Method for creating a Date object from string in format "yyyy.MM.dd".
     *
     * @param date String in format "yyyy.MM.dd".
     */
    private Date parseDate(String date) {
        if (date == null) return null;

        Date result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        
        try {
            result = dateFormat.parse(date); 
        } catch (ParseException e) {
            log.error(e);
        }

        return result;
    }

    /**
     * Method for getting string representation date of issuance.
     * @return String representation date of issuance.
     */
    public String getIssued() {
        if (issued == null) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(issued);
    }

    public void setIssued(Date date) {
        this.issued = date;
    }
    
    public void setAbonent(String abonent) {
        this.abonent = abonent;
    }
   
    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
    
    public String getName() {
        return name;
    }

    public String getAbonent() {
        return abonent;
    }

}

