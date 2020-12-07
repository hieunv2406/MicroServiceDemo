package com.example.admin.data.dto;

import com.example.admin.common.dto.BaseDTO;
import com.example.admin.data.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredTypes()
public class EmployeeDTO extends BaseDTO {

    private Long employeeId;
    @NotNull(message = "code is not null")
    private String code;
    private String username;
    private String fullName;
    private String email;
    private Date birthday;
    private Long gender;
    private String address;
    private MultipartFile file;

    public EmployeeDTO(Long employeeId, String code, String username, String fullName, String email, Date birthday, Long gender, String address) {
        this.employeeId = employeeId;
        this.code = code;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
    }

    public EmployeeEntity toEntity() {
        return new EmployeeEntity(
                employeeId,
                code,
                username,
                fullName,
                email,
                birthday,
                gender,
                address
        );
    }
}
