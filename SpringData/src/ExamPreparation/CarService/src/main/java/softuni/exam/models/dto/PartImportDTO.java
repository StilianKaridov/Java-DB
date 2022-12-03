package ExamPreparation.CarService.src.main.java.softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartImportDTO {

    @Expose
    private String partName;

    @Expose
    private BigDecimal price;

    @Expose
    private int quantity;

    public void setPartName(String partName) {
        if (partName.length() < 2 || partName.length() > 19) {
            throw new IllegalArgumentException();
        }

        this.partName = partName;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.TEN) < 0 || price.compareTo(new BigDecimal("2000.0")) > 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }

        this.quantity = quantity;
    }

    public boolean validate() {
        try {
            setPartName(this.partName);
            setPrice(this.price);
            setQuantity(this.quantity);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
