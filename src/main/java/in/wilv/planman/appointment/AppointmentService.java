package in.wilv.planman.appointment;

import in.wilv.planman.daytree.FreeTimeDB;
import in.wilv.planman.daytree.FreeTimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService
{

    private final AppointmentRepository appointmentRepository;
    private final FreeTimeDB freeTimeDB;

    @Autowired
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            FreeTimeDB freeTimeDB)
    {
        this.appointmentRepository = appointmentRepository;
        this.freeTimeDB = freeTimeDB;
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public void addAppointment(Appointment appointment)
        throws AppointmentException
    {
        List<Appointment> overlapping = appointmentRepository.findAppointmentsBetween(
                appointment.getStartDTime(),
                appointment.getEndDTime()
        );

        if(!overlapping.isEmpty()) {
            throw new AppointmentException();
        }

        appointmentRepository.save(appointment);
        freeTimeDB.addAppointment(appointment);

        return;
    }

    public List<Appointment> getOverlappingAppointments()
    {
        return appointmentRepository.findAppointmentsBetween(
                LocalDateTime.of(2021,06,18,0,0),
                LocalDateTime.of(2021,06,21,0,0)
        );
    }

    public FreeTimeSlot getFreeSlotFrom(LocalDate from, long qDuration)
    {
        return freeTimeDB.findFreePeriod(from, qDuration);
    }
}
