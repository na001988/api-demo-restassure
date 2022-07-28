import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Users {

    static String _id;

    private String FirstName;
    private String LastName;
    private String DateOfBirth;
    private String StartDate;
    private String Department;
    private String JobTitle;
    private String Email;
    private String Mobile;
    private String Address ;
    private Double BaseSalary;

}
