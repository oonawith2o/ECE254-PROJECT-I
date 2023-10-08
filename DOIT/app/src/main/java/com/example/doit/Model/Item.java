package com.example.doit.Model;

/**
 * LIBRARY IMPORT
 */
import java.time.LocalDateTime;
/**
 * ITEM CLASS
 */
public class Item {

    /**
     * ATTRIBUTES
     */
    private int itemID;
    private String subject;
    private String note;
    private boolean completed;
    private LocalDateTime creationDateTime;
    private LocalDateTime dueDateTime;
    private int priority;
    private User[] participants;

    /**
     * CONSTRUCTORS
     */
    public Item() { }

    public Item(int itemID, String subject, String note, boolean completed, LocalDateTime creationDateTime, LocalDateTime dueDateTime, int priority, User[] participants) {
        this.itemID = itemID;
        this.subject = subject;
        this.note = note;
        this.completed = completed;
        this.creationDateTime = creationDateTime;
        this.dueDateTime = dueDateTime;
        this.priority = priority;
        this.participants = participants;
    }

    /**
     * GET METHODS
     */
    public int getItemID() { return itemID; }
    public String getSubject() { return subject; }
    public String getNote() { return note; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreationDateTime() { return creationDateTime; }
    public LocalDateTime getDueDateTime() { return dueDateTime; }
    public int getPriority() { return priority; }
    public User[] getParticipants() { return participants; }

    /**
     * SET METHODS
     */
    private void setItemID(int itemID) { this.itemID = itemID; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setNote(String note) { this.note = note; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setCreationDateTime(LocalDateTime creationDateTime) { this.creationDateTime = creationDateTime; }
    public void setDueDateTime(LocalDateTime dueDateTime) { this.dueDateTime = dueDateTime; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setParticipants(User[] participants) { this.participants = participants; }
    public void setAll(int itemID, String subject, String note, boolean completed, LocalDateTime creationDateTime, LocalDateTime dueDateTime, int priority, User[] participants) {
        this.itemID = itemID;
        this.subject = subject;
        this.note = note;
        this.completed = completed;
        this.creationDateTime = creationDateTime;
        this.dueDateTime = dueDateTime;
        this.priority = priority;
        this.participants = participants;
    }

}
