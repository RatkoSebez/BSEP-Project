package com.bsep.proj;

import com.bsep.proj.model.User;
import com.bsep.proj.model.UserRole;
import com.bsep.proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@AllArgsConstructor
public class ProjApplication implements ApplicationRunner {
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String password = "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra";
		userRepository.save(new User("user", password, UserRole.ROLE_CLIENT, "user", "user"));
		userRepository.save(new User("admin", password, UserRole.ROLE_ADMIN, "admin", "admin"));
		String firstNames[] = {"Marko", "Jovan", "Milica", "Jovana", "Teodora", "Anja", "Stefan"};
		String lastNames[] = {"Marković", "Jovanović", "Pejović", "Mitrović", "Jović", "Vlahović"};
		for(int i=0; i<100; i++){
			int firstNameIndex = ThreadLocalRandom.current().nextInt(0, firstNames.length-1);
			int lastNameIndex = ThreadLocalRandom.current().nextInt(0, lastNames.length-1);
			userRepository.save(new User(firstNames[firstNameIndex] + lastNames[lastNameIndex], password, UserRole.ROLE_CLIENT, firstNames[firstNameIndex], lastNames[lastNameIndex]));
		}
	}
}
