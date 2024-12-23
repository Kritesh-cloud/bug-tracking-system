package cm.ex.bug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BugTrackingSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(BugTrackingSystemApplication.class, args);
		System.out.println("\n=====================================");
		System.out.println("|| Bug Tracking System Application ||");
		System.out.println("=====================================\n");
	}
}
/*
entity > repository -> interface -> User Details -> (Filters) -> (exception handling) -> service -> controller
drop database bug_tracking; create database bug_tracking; use bug_tracking;
*/