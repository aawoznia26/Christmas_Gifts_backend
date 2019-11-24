package com.draw.gifts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    private boolean excludedFromDrawing;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DRAWING_ID")
    private Drawing drawing;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "DRAWING_RESULT", joinColumns = {
            @JoinColumn(name = "DRAWING_USER", referencedColumnName = "ID", nullable =   false)}, inverseJoinColumns = {
            @JoinColumn(name = "DRAWN_USER", referencedColumnName = "ID", nullable = false)})
    private User drawingUser;

    @OneToOne(mappedBy = "drawingUser", cascade = CascadeType.PERSIST)
    private User drawnUser;

    public User(String name, boolean excludedFromDrawing, Drawing drawing) {
        this.name = name;
        this.excludedFromDrawing = excludedFromDrawing;
        this.drawing = drawing;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", excludedFromDrawing=" + excludedFromDrawing +
                '}';
    }

    public void setExcludedFromDrawing(boolean excludedFromDrawing) {
        this.excludedFromDrawing = excludedFromDrawing;
    }

    public void setDrawingUser(User drawingUser) {
        this.drawingUser = drawingUser;
    }

    public void setDrawnUser(User drawnUser) {
        this.drawnUser = drawnUser;
    }
}
