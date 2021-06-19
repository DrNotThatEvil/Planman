package in.wilv.planman.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService
{
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository)
    {
        this.appointmentRepository = appointmentRepository;
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
    }

    public List<Appointment> getOverlappingAppointments()
    {
        return appointmentRepository.findAppointmentsBetween(
                LocalDateTime.of(2021,06,18,0,0),
                LocalDateTime.of(2021,06,21,0,0)
        );
    }
}
