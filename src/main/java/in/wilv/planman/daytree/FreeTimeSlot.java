package in.wilv.planman.daytree;

import java.time.LocalDateTime;

public class FreeTimeSlot
{
    LocalDateTime startDTime;
    LocalDateTime endDTime;

    public FreeTimeSlot(LocalDateTime startDTime, LocalDateTime endDTime)
    {
        this.startDTime = startDTime;
        this.endDTime = endDTime;
    }

    public LocalDateTime getStartDTime()
    {
        return startDTime;
    }

    public void setStartDTime(LocalDateTime startDTime)
    {
        this.startDTime = startDTime;
    }

    public LocalDateTime getEndDTime()
    {
        return endDTime;
    }

    public void setEndDTime(LocalDateTime endDTime)
    {
        this.endDTime = endDTime;
    }
}
