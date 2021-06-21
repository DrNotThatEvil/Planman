package in.wilv.planman.daytree;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FreeTimeSlotRequest
{
    LocalDate from;
    Duration duration;
    Long qDuration;

    public FreeTimeSlotRequest(LocalDate from, Duration duration)
    {
        this.from = from;
        this.duration = duration;
        this.qDuration = calculateQDuration();
    }

    // Rounds the duration to the nearest quarter.
    private long calculateQDuration()
    {
        long minutes = this.duration.toMinutes();
        long mod = this.duration.toMinutes() % 15;
        long res = 0;

        if ((mod) >= 8L) {
            res = minutes + (15 - mod);
        } else {
            res = minutes - mod;
        }

        return (res / 15);
    }

    public LocalDate getFrom()
    {
        return from;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public long getQDuration()
    {
        return this.qDuration;
    }
}
