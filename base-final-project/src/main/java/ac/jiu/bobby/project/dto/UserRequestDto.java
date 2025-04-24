package ac.jiu.bobby.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserRequestDto {

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String photo;
    private UUID roleId;

}
