package org.algotithmcontestdatacollect.managebackend.Utils;

import org.algotithmcontestdatacollect.managebackend.Entities.Admin;
import org.algotithmcontestdatacollect.managebackend.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class InitialUtil {
    @Value("${security.rootUsername}")
    public String username;
    @Value("${security.rootPassword}")
    public String password;
    @Autowired
    AdminRepository adminRepository;
    @PostConstruct
    public void initSuperUser() throws NoSuchAlgorithmException {
        Admin root;
        try {
            root = adminRepository.findByUsername(username);
            if (root == null) {
                root = new Admin();
            }
        }catch (Exception e){
            root = new Admin();
        }
        root.setIsSuper((short) 1);
        root.setUsername(username);
        root.setSchool(null);
        var sha = MessageDigest.getInstance("SHA-512");
        var bytes = sha.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        root.setPassword(sb.toString());
        adminRepository.save(root);
    }
}
