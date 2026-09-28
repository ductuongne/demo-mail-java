package murach.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable{
    @Id
    private String email;
    private String firstName;
    private String lastName;

    public User(){
    }

    public User(String email, String firstName, String lastName){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{email='" + email + "', firstName='" + firstName
                + "', lastName='" + lastName + "'}";
    }
}
