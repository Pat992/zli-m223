package ch.zli.m223.api19a.crm.init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.zli.m223.api19a.crm.repository.UserRepository;

@Component
public class ServerInitialize implements ApplicationRunner {
	@Autowired UserRepository userRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Set<String> user = new HashSet<>(); user.add("user");
		Set<String> admin = new HashSet<>(); admin.add("admin");
		Set<String> usmin = new HashSet<>(); usmin.add("user");
		usmin.add("admin");
		
		userRepository.createUser("user", "1234", user);
		userRepository.createUser("admin", "1234", admin);
		userRepository.createUser("usmin", "1234", usmin);
	}

}
