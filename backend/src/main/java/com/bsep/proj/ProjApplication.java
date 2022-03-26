package com.bsep.proj;

import com.bsep.proj.model.User;
import com.bsep.proj.model.UserRole;
import com.bsep.proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class ProjApplication implements ApplicationRunner {
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.save(new User("user", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", UserRole.ROLE_CLIENT));
		userRepository.save(new User("admin", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", UserRole.ROLE_ADMIN));
	}
}
