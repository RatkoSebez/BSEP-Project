package com.bsep.proj.dto;

import com.bsep.proj.model.User;
import com.bsep.proj.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private List<UserRole> role = new ArrayList<>();
    private String firstName;
    private String lastName;
    
    public static UserDto convertToDto(User user){
        if(user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
    
    public static List<UserDto> convertToDtoList(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users)
            userDtos.add(convertToDto(user));
        return userDtos;
    }
}
