package sesc.assement.student.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import sesc.assement.student.enums.*;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {
    private Long id;
    private String reference;
    private Double amount;
    private LocalDate dueDate;
    private PayType type;
    private Status status;
    private Account account;

    public enum PayType {
        LIBRARY_FINE,
        TUITION_FEES
    }
}
