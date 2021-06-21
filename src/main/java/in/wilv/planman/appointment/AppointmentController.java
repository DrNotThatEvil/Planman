package in.wilv.planman.appointment;

import in.wilv.planman.daytree.FreeTimeSlot;
import in.wilv.planman.daytree.FreeTimeSlotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/appointment")
public class AppointmentController
{
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAppointments()
    {

        return appointmentService.getAppointments();
    }

    @PostMapping
    public void addAppointment(@RequestBody Appointment appointment)
    {
        try {
            appointmentService.addAppointment(appointment);
        } catch (AppointmentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment overlaps with other appointment", e);
        }
    }

    @PostMapping(path = "getFirstFreeSlotFrom")
    public FreeTimeSlot getFreeSlotFrom(@RequestBody
                                                       FreeTimeSlotRequest freeTimeSlotRequest)
    {
        return appointmentService.getFreeSlotFrom(
                freeTimeSlotRequest.getFrom(),
                freeTimeSlotRequest.getQDuration()
        );
    }
}
