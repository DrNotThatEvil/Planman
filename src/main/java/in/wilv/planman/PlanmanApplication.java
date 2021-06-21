package in.wilv.planman;

import in.wilv.planman.appointment.Appointment;
import in.wilv.planman.appointment.AppointmentRepository;
import in.wilv.planman.daytree.DayNode;
import in.wilv.planman.daytree.DayTree;
import in.wilv.planman.daytree.FreeTimeDB;
import in.wilv.planman.daytree.FreeTimeSlot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = {"in.wilv.planman.appointment", "in.wilv.planman.daytree"})
public class PlanmanApplication
{

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"));
		System.out.println(TimeZone.getDefault());
	}

	public static void main(String[] args)
	{
		SpringApplication.run(PlanmanApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AppointmentRepository repository, FreeTimeDB freeTimeDB)
	{
		return (args) -> {
			/*
			repository.save(new Appointment(
					"Testing",
					"Tester",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 20, 0, 0, 0),
					LocalDateTime.of(2021, 06, 20, 2, 0, 0)
			));

			repository.save(new Appointment(
					"Testing",
					"Tester",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 21, 0, 0, 0),
					LocalDateTime.of(2021, 06, 21, 2, 0, 0)
			));

			repository.save(new Appointment(
					"Testing 3",
					"Tester 3",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 21, 4, 45, 0),
					LocalDateTime.of(2021, 06, 21, 11, 0, 0)
			));

			repository.save(new Appointment(
					"Testing 2",
					"Tester 2",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 21, 12, 0, 0),
					LocalDateTime.of(2021, 06, 21, 15, 0, 0)
			));

			repository.save(new Appointment(
					"Testing 3",
					"Tester 3",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 21, 23, 45, 0),
					LocalDateTime.of(2021, 06, 21, 23, 59, 0)
			));

			 */
			freeTimeDB.calculateDateInfo(repository.findActiveAppointments());
			freeTimeDB.cleanOldKeys();

		};
	}
}
