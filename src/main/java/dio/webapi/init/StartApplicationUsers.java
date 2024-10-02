package dio.webapi.init;

import dio.webapi.model.User;
import dio.webapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartApplicationUsers implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setNome("Administrador");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("master123"));
            user.getRoles().add("MANAGER");
            userRepository.save(user);
        }

        user = userRepository.findByUsername("user");
        if (user == null) {
            user = new User();
            user.setNome("Usuário 1");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.getRoles().add("USER");
            userRepository.save(user);
        }
    }
}
