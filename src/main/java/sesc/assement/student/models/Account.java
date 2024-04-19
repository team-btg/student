package sesc.assement.student.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private Long id;
    private Long studentId;
    private boolean hasOutstandingBalance;
}
