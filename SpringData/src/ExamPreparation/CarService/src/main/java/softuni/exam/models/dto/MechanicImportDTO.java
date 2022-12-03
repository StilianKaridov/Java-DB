package ExamPreparation.CarService.src.main.java.softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MechanicImportDTO {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String email;

    @Expose
    private String phone;

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

    public void setPhone(String phone) {
        if (phone.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.phone = phone;
    }

    public boolean validate() {
        try {
            setFirstName(this.firstName);
            setLastName(this.lastName);
            setEmail(this.email);
            setPhone(this.phone);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
