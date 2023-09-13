package com.streamershelper.streamers.dto.user;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
@AllArgsConstructor
public class UserDto
{

    private String email;
    private String displayName;
    private boolean enabled;
    private boolean google_registration = false;




}
