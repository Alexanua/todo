package org.campusmolndal;

public class ToDoItem {
    private int id;
    private String text;
    private boolean done;
    private int assignedTo;
    private String category;

    public ToDoItem() {
    }

    public ToDoItem(int id, String text, boolean done, int assignedTo, String category) {
        this.id = id;
        this.text = text;
        this.done = done;
        this.assignedTo = assignedTo;
        this.category = category;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", done=" + done +
                ", assignedTo=" + assignedTo +
                ", category='" + category + '\'' +
                '}';
    }
}

