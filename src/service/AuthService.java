package service;

import container.ComponentContainer;
import controller.AdminController;
import controller.ProfileController;
import dto.Profile;
import enums.GeneralStatus;
import enums.ProfileRole;
import repository.ProfileRepository;
import util.MD5Util;

import java.time.LocalDateTime;

public class AuthService {

    public AuthService() {

    }

    public void login(String phone, String password) {
        ProfileRepository profileRepository = ComponentContainer.profileRepository;
        Profile profile = profileRepository.getProfileByPhoneAndPassword(phone, MD5Util.encode(password));

        if (profile == null) {
            System.out.println("Phone or Password incorrect");
            return;
        }

        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            System.out.println("You not allowed.MF");
            return;
        }

        ComponentContainer.currentProfile = profile;
        if (profile.getRole().equals(ProfileRole.ADMIN)) {
            AdminController adminController = new AdminController();
            adminController.start();
        } else if (profile.getRole().equals(ProfileRole.USER)) {
            ProfileController profileController = new ProfileController();
            profileController.start();
        } else {
            System.out.println("You don't have any role.");
        }

    }

    public void registration(Profile profile) {
        ProfileRepository profileRepository = ComponentContainer.profileRepository;
        // check
        Boolean exist = profileRepository.isPhoneExist(profile.getPhone()); // unique
        if (exist) {
            System.out.println(" Phone already exist.");
            return;
        }

        profile.setStatus(GeneralStatus.ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setRole(ProfileRole.USER);
        profile.setPassword(MD5Util.encode(profile.getPassword()));
        int result = profileRepository.saveProfile(profile);

        if (result != 0) {
            System.out.println("Profile created.");
        }

    }
}
