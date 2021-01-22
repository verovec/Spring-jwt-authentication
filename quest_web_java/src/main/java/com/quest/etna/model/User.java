package com.quest.etna.model;


import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "creationDate", nullable = false)
    private Timestamp creationDate;

    @Column(name = "updatedDate", nullable = false)
    private Timestamp updatedDate;

    protected User() {};

    public User(String username, String password, String role, Timestamp creationDate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.creationDate = creationDate;
        this.updatedDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
    	return role;
    }

    public void setRole(String role) {
    	this.role = role;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationdate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdateddate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.role);
        hash = 79 * hash + Objects.hashCode(this.creationDate);
        hash = 79 * hash + Objects.hashCode(this.updatedDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.username != other.username) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password=").append(password).append('\'');
        sb.append(", role=").append(role).append('\'');
        sb.append(", creationDate=").append(creationDate).append('\'');
        sb.append(", updatedDate=").append(updatedDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}