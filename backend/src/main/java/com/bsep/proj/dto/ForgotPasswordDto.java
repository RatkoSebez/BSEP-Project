package com.bsep.proj.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgotPasswordDto {
    private String forgotPasswordVerificationCode;
    private String newPassword;
}
