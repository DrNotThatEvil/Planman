package in.wilv.planman.daytree;

import in.wilv.planman.appointment.Appointment;
import in.wilv.planman.daytree.FreeTimeSlot;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FreeTimeDB
{
    private HashMap<LocalDate, DayTree> dateTreeMap;

    public FreeTimeDB()
    {
        this.dateTreeMap = new HashMap<LocalDate, DayTree>();
    }

    public void addAppointment(Appointment appointment)
    {
        // Add the appointment to the system, at this point we assume this appointment
        // has been checked and does not contain any overlap.

        this.calculateDateInfo(List.of(appointment));
    }

    // TODO Calculate this info again on added info or expand it by adding nodes.
    public void calculateDateInfo(List<Appointment> appointments)
    {
        for (Appointment appointment : appointments) {

            int dateIndex = 0;
            for (LocalDate date : appointment.getDatesOfAppointment()) {
                if (!dateTreeMap.containsKey(date)) {
                    dateTreeMap.put(date, new DayTree());
                }

                DayTree dayTree = dateTreeMap.get(date);
                DayNode dNode = dayTree.getNodeContainingIndexes(
                        dayTree.root,
                        (dateIndex > 0 ? 0 : appointment.getqStartIndex()),
                        appointment.getqEndIndex() - (DayNode.Q_DAY_LENGTH * dateIndex)
                );

                long roundedStartIndex = Math.min(Math.max((dateIndex > 0 ? 0 : appointment.getqStartIndex()), 0), 96);
                long roundedEndIndex = Math.min(
                        Math.max(appointment.getqEndIndex() - (DayNode.Q_DAY_LENGTH * dateIndex), 0), 96
                );

                if (roundedStartIndex == dNode.getqIndexStart()) {
                    // Appointment planned at the start of this free block.
                    // We will move the startIndex to the end of the appointment to schink it.

                    dNode.moveQIndexStart(roundedEndIndex);
                } else if (roundedEndIndex == dNode.getqIndexEnd()) {
                    // Appointment planned to end at the end of the block.
                    // We will move the Endindex to the start of the appointment to schink it.

                    dNode.moveQEndIndex(roundedStartIndex);
                } else {
                    // Not at the start or end of the free time.
                    // To represent the free time before and after the appointment we add nodes to the tree.

                    // Calculate the indexes for Node 1
                    DayNode node0 = new DayNode(dNode.getqIndexStart(), roundedStartIndex);

                    // Calculate the indexes for Node 2
                    DayNode node1 = new DayNode(roundedEndIndex, dNode.getqIndexEnd());
                    dNode.addNodes(node0, node1);
                }

                ++dateIndex;
            }
        }
    }

    // TODO: Translate this to the Controller / API
    public FreeTimeSlot findFreePeriod(LocalDate date, long QDuration)
    {
        // Check if date is newer then we calculated free time for.
        ArrayList<LocalDate> keys = new ArrayList<LocalDate>(dateTreeMap.keySet());
        if (!keys.contains(date)) {
            LocalDateTime startDTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

            Long qDurationToMinutes = (QDuration * 15);
            LocalDateTime endDTime = LocalDateTime.from(startDTime);
            endDTime = endDTime.plusMinutes(qDurationToMinutes);

            return new FreeTimeSlot(startDTime, endDTime);
        }

        // Date is on a day we have stuff planned, lets search for a slot.

        DayTree dayTree = dateTreeMap.get(date);
        DayNode node = dayTree.getFistContiguousWithMinSize(dayTree.root, QDuration);

        if (node != null) {
            System.out.println(node.toString());
            LocalDateTime startDTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            startDTime = startDTime.plusMinutes(node.qIndexStart * 15);

            LocalDateTime endDTime = LocalDateTime.from(startDTime);
            endDTime = endDTime.plusMinutes(QDuration * 15);


            return new FreeTimeSlot(startDTime, endDTime);
        }

        return findFreePeriod(date.plusDays(1), QDuration);
    }

    public void cleanOldKeys()
    {
        // clears dates before today - 1, -1 to be sure appointments are passed.
        dateTreeMap.entrySet().removeIf(
                e -> e.getKey().isBefore(LocalDate.now().minusDays(1))
        );
    }
}
