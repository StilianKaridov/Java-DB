package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgentImportDTO {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String email;

    @Expose
    private String town;

    public void setFirstName(String firstName) {
        if (firstName.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.lastName = lastName;
    }

    public void setEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException();
        }

        this.email = email;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public boolean validate() {
        try {
            setFirstName(this.firstName);
            setLastName(this.lastName);
            setEmail(this.email);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
