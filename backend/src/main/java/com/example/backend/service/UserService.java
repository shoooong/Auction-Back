package com.example.backend.service;


import com.example.backend.dto.user.UserDTO;
import com.example.backend.dto.user.UserModifyDTO;
import com.example.backend.dto.user.UserRegisterDTO;
import com.example.backend.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

   void registerUser(UserRegisterDTO userRegisterDTO, boolean isAdmin);

   UserDTO getKakaoMember(String accessToken);

   default UserDTO entityToDTO(User user) {
      UserDTO userDTO = new UserDTO(
               user.getEmail(),
               user.getPassword(),
               user.getGrade(),
               user.getNickname(),
               user.getPhone(),
               user.getDefaultAddr(),
               user.isSocial(),
               user.isRole());

      return userDTO;
   }

   String makeTempPassword();

   User makeSocialUser(List<String> socialAccountList);

   void modifyUser(UserModifyDTO userModifyDTO);
}
