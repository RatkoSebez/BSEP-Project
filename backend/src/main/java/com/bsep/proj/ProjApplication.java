package com.bsep.proj;

import com.bsep.proj.model.User;
import com.bsep.proj.model.UserRole;
import com.bsep.proj.repository.UserRepository;
import com.bsep.proj.service.CreateService;
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
	private final CreateService certificateService;

	public static void main(String[] args) {
		SpringApplication.run(ProjApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String password = "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra"; // 123 hashed
		List<UserRole> rolesAdmin = new ArrayList<>();
		rolesAdmin.add(UserRole.ROLE_ADMIN);
		List<UserRole> rolesClient = new ArrayList<>();
		rolesClient.add(UserRole.ROLE_CLIENT);
		userRepository.save(new User("user", password, rolesClient, "user", "user"));
		userRepository.save(new User("admin", password, rolesAdmin, "admin", "admin"));
		String firstNames[] = {"Marko", "Jovan", "Milica", "Jovana", "Teodora", "Anja", "Stefan", "Miroljub", "Nina"};
		String lastNames[] = {"Marković", "Jovanović", "Pejović", "Mitrović", "Jović", "Vlahović", "Zec", "Zlatanović", "Zarković"};
//		for(int i=0; i<100; i++){
//			int firstNameIndex = ThreadLocalRandom.current().nextInt(0, firstNames.length-1);
//			int lastNameIndex = ThreadLocalRandom.current().nextInt(0, lastNames.length-1);
//			userRepository.save(new User(firstNames[firstNameIndex] + lastNames[lastNameIndex], password, roles, firstNames[firstNameIndex], lastNames[lastNameIndex]));
//		}
		// figure out why this line don't work
		// certificateService.createCertificate(new CreateCaRequestDto(2l, null));
	}
}
