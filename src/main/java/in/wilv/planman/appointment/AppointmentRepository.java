package in.wilv.planman.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{

    @Query(value = "SELECT * FROM appointment AS a WHERE (a.startdtime, a.enddtime) OVERLAPS (?1, ?2)", nativeQuery = true)
    List<Appointment> findAppointmentsBetween(LocalDateTime startDTime, LocalDateTime endDTime);
}
