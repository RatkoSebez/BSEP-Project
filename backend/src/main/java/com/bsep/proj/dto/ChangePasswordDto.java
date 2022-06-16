package com.bsep.proj.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangePasswordDto {
    private String currentPassword;
    private String newPassword;
}
