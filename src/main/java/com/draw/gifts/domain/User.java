package com.draw.gifts.domain;

import com.draw.gifts.dto.UserDto;
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

    private boolean ifHasDrawn;
    private boolean ifWasDrawn;
    private boolean excludedTemporarily;

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

    public User(String name, boolean ifHasDrawn, boolean ifWasDrawn, boolean excludedTemporarily, Drawing drawing) {
        this.name = name;
        this.ifHasDrawn = ifHasDrawn;
        this.ifWasDrawn = ifWasDrawn;
        this.excludedTemporarily = excludedTemporarily;
        this.drawing = drawing;
    }

    public void setIfHasDrawn(boolean ifHasDrawn) {
        this.ifHasDrawn = ifHasDrawn;
    }

    public void setIfWasDrawn(boolean ifWasDrawn) {
        this.ifWasDrawn = ifWasDrawn;
    }

    public void setDrawingUser(User drawingUser) {
        this.drawingUser = drawingUser;
    }

    public void setDrawnUser(User drawnUser) {
        this.drawnUser = drawnUser;
    }

    public void setExcludedTemporarily(boolean excludedTemporarily) {
        this.excludedTemporarily = excludedTemporarily;
    }

    public static UserDto mapToUserDto(final User user) {
        UserDto userDto = new UserDto(user.id, user.name);
        return userDto;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ifHasDrawn=" + ifHasDrawn +
                ", ifWasDrawn=" + ifWasDrawn +
                ", excludedTemporarily=" + excludedTemporarily +
                ", drawing=" + drawing +
                ", drawingUser=" + drawingUser +
                ", drawnUser=" + drawnUser +
                '}';
    }
}
