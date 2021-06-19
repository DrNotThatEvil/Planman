package in.wilv.planman;

import in.wilv.planman.appointment.Appointment;
import in.wilv.planman.appointment.AppointmentRepository;
import in.wilv.planman.daytree.DayNode;
import in.wilv.planman.daytree.DayTree;
import in.wilv.planman.daytree.FreeTimeDBSingleton;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootApplication(scanBasePackages = {"in.wilv.planman.appointment"})
public class PlanmanApplication {

	public static void main(String[] args)
	{
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
					LocalDateTime.of(2021, 06, 19, 0, 0, 0),
					LocalDateTime.of(2021, 06, 19, 2, 0, 0)
			));

			repository.save(new Appointment(
					"Testing 3",
					"Tester 3",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 19, 4, 45, 0),
					LocalDateTime.of(2021, 06, 19, 11, 0, 0)
			));

			repository.save(new Appointment(
					"Testing 2",
					"Tester 2",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 19, 12, 0, 0),
					LocalDateTime.of(2021, 06, 19, 15, 0, 0)
			));

			repository.save(new Appointment(
					"Testing 3",
					"Tester 3",
					"Testing this thing!",
					LocalDateTime.of(2021, 06, 19, 23, 45, 0),
					LocalDateTime.of(2021, 06, 19, 23, 59, 0)
			));

			FreeTimeDBSingleton instance = FreeTimeDBSingleton.getInstance();
			instance.calculateDateInfo(repository.findActiveAppointments());

			Appointment free = instance.findFreePeriod(
					LocalDate.of(2021, 06, 19),
					8
			);

			System.out.println(free);

				/*
				if (appointment.getqEndIndex() > 96) {
					// Appointment passes end of the day boundary
					long qEndNextDayIndex = appointment.getqEndIndex() % 96;
					for (LocalDate date : appointment.getDatesOfAppointment())
					{
						if(date.isEqual(appointment.getStartDTime().toLocalDate())) {
							// Date is same as start date,
						}
					}

					//System.out.println("Passed day boundary!! : " + qDays);
					continue;
				}
				 */

				// Appointment does not pass end of day boundary.



		};
	}
}
