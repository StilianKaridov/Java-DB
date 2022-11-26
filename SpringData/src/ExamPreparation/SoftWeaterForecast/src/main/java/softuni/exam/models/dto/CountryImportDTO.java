package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountryImportDTO {

    @Expose
    private String countryName;

    @Expose
    private String currency;

    public void setCountryName(String countryName) {
        if (countryName.length() < 2 || countryName.length() > 60) {
            throw new IllegalArgumentException();
        }

        this.countryName = countryName;
    }

    public void setCurrency(String currency) {
        if (currency.length() < 2 || currency.length() > 20) {
            throw new IllegalArgumentException();
        }

        this.currency = currency;
    }

    public boolean validate() {
        try {
            setCountryName(this.countryName);
            setCurrency(this.currency);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
