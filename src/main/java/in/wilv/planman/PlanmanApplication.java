package in.wilv.planman;

import in.wilv.planman.appointment.Appointment;
import in.wilv.planman.appointment.AppointmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = {"in.wilv.planman.appointment"})
public class PlanmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanmanApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AppointmentRepository repository)
	{
		return (args) -> {
			repository.save(new Appointment(
					"Testing",
					"Tester",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 19, 6, 0, 0),
					LocalDateTime.of(2021, 06, 19, 12, 0, 0)
			));
		};
	}
}
