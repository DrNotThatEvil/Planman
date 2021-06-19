package in.wilv.planman.appointment;

import javax.persistence.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Entity
@Table
public class Appointment
{
    @Id
    @SequenceGenerator(
            name = "appointment_sequence",
            sequenceName = "appointment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appointment_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR", length = 128)
    private String title;
    @Column(name = "organizer", nullable = false, columnDefinition = "VARCHAR", length = 64)
    private String organizer;
    @Column(name = "description", length = 512, columnDefinition = "TEXT")
    private String description;

    @Column(name = "startDTime", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDTime;
    @Column(name = "endDTime", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDTime;

    @Transient
    private long duration;

    @Transient
    private long qStartIndex;
    @Transient
    private long qEndIndex;
    @Transient
    private long qDuration;

    protected Appointment() {}

    public Appointment(
            String title,
            String organizer,
            String description,
            LocalDateTime startDTime,
            LocalDateTime endDTime)
    {
        this.title = title;
        this.organizer = organizer;
        this.description = description;
        this.startDTime = startDTime;
        this.endDTime = endDTime;
    }

    public Long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getOrganizer()
    {
        return organizer;
    }

    public String getDescription()
    {
        return description;
    }

    public LocalDateTime getStartDTime()
    {
        return startDTime;
    }

    public LocalDateTime getEndDTime()
    {
        return endDTime;
    }

    public long getDuration() {
        return Duration.between(this.startDTime, this.endDTime).toMillis();
    }

    public static long getQIndex(LocalDateTime start, LocalDateTime end)
    {
        //LocalDateTime dayStart = LocalDateTime.of(start.toLocalDate(), LocalTime.MIDNIGHT);
        long minutes = ChronoUnit.MINUTES.between(start, end);

        long mod = minutes % 15;
        long res = 0;
        if ((mod) >= 8L) {
            res = minutes + (15 - mod);
        } else {
            res = minutes - mod;
        }

        return res / 15;
    }

    public long getqStartIndex()
    {
        LocalDateTime dayStart = LocalDateTime.of(this.startDTime.toLocalDate(), LocalTime.MIDNIGHT);
        return Appointment.getQIndex(dayStart, this.startDTime);
    }

    public long getqEndIndex()
    {
        LocalDateTime dayStart = LocalDateTime.of(this.startDTime.toLocalDate(), LocalTime.MIDNIGHT);
        return Appointment.getQIndex(dayStart, this.endDTime);
    }

    public long getqDuration()
    {
        return (this.getqEndIndex() - this.getqStartIndex());
    }

    public List<LocalDate> getDatesOfAppointment()
    {
        List<LocalDate> dates = this.startDTime.toLocalDate().datesUntil(this.endDTime.toLocalDate())
                .collect(Collectors.toList());
        dates.add(this.endDTime.toLocalDate());

        return dates;
    }
}
