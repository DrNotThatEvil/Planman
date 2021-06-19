package in.wilv.planman.appointment;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;


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
}
