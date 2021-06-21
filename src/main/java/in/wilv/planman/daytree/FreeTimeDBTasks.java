package in.wilv.planman.daytree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FreeTimeDBTasks
{
    private static final Logger log = LoggerFactory.getLogger(FreeTimeDBTasks.class);
    private final FreeTimeDB freeTimeDB;

    @Autowired
    public FreeTimeDBTasks(FreeTimeDB freeTimeDB)
    {
        this.freeTimeDB = freeTimeDB;
    }

    @Scheduled(cron = "0 0 * * *")
    public void cleanFreeTimeDB()
    {
        freeTimeDB.cleanOldKeys();
    }
}
