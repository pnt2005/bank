package com.pnt2005.bank.controller;

import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> userResponseDTOList = userService.getUsers();
        if (userResponseDTOList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        URI location = URI.create("/users/" + userResponseDTO.getId());
        return ResponseEntity.created(location).body(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
