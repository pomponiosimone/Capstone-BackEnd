package pomponiosimone.Capstone_BackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.NewEntityRespDTO;
import pomponiosimone.Capstone_BackEnd.payloads.UserDTO;
import pomponiosimone.Capstone_BackEnd.payloads.UserLoginDTO;
import pomponiosimone.Capstone_BackEnd.payloads.UserLoginRespDTO;
import pomponiosimone.Capstone_BackEnd.services.AuthService;
import pomponiosimone.Capstone_BackEnd.services.UsersService;

import java.util.stream.Collectors;


    @RestController
    @RequestMapping("/auth")
    public class AuthController {
        @Autowired
        private UsersService userService;
        @Autowired
        private AuthService authService;
        @PostMapping("/login")

        public UserLoginRespDTO login(@RequestBody UserLoginDTO payload) {
            return new UserLoginRespDTO (this.authService.checkCredentialsAndGenerateToken(payload));}

        @PostMapping("/register")
        @ResponseStatus(HttpStatus.CREATED)
        public NewEntityRespDTO save(@RequestBody @Validated UserDTO body, BindingResult validationResult) {

            if (validationResult.hasErrors()) {

                String messages = validationResult.getAllErrors().stream()
                        .map(objectError -> objectError.getDefaultMessage())
                        .collect(Collectors.joining(". "));

                throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
            } else {


                return new NewEntityRespDTO(this.userService.saveUser(body).getId());
            }

        }}


