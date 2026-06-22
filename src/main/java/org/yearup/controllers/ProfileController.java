package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    private ProfileService profileService;

    private UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }


    // add the appropriate annotation for a get action
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> getProfile(Principal principal) {
        String username = principal.getName();

        int userId = userService.getIdByUsername(username);

        return profileService.getById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public Profile updateProfile(Principal principal, @RequestBody Profile profile) {
        String username = principal.getName();

        int userId = userService.getIdByUsername(username);

        return profileService.update(userId, profile);
    }
}
