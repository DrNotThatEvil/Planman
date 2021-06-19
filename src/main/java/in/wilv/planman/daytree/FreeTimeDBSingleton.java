package in.wilv.planman.daytree;

import in.wilv.planman.appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FreeTimeDBSingleton
{
    private static FreeTimeDBSingleton freeTimeDBSingleton;
    private HashMap<LocalDate, DayTree> dateTreeMap;


    private FreeTimeDBSingleton()
    {
        this.dateTreeMap = new HashMap<LocalDate, DayTree>();
    }

    public static FreeTimeDBSingleton getInstance()
    {
        if (freeTimeDBSingleton == null) {
            freeTimeDBSingleton = new FreeTimeDBSingleton();
        }

        return freeTimeDBSingleton;
    }

    // TODO Calculate this info again on added info or expand it by adding nodes.
    public void calculateDateInfo(List<Appointment> appointments)
    {
        for (Appointment appointment : appointments) {
            for (LocalDate date : appointment.getDatesOfAppointment()) {
                if (!dateTreeMap.containsKey(date)) {
                    dateTreeMap.put(date, new DayTree());
                }

                DayTree dayTree = dateTreeMap.get(date);
                // DayNode dNode = dayTree.getFistContiguousWithMinSize(dayTree.root, appointment.getqDuration());
                DayNode dNode = dayTree.getNodeContainingIndexes(
                        dayTree.root,
                        appointment.getqStartIndex(),
                        appointment.getqEndIndex()
                );

                System.out.println("Current DNode: " + dNode.toString());

                if (appointment.getqStartIndex() == dNode.getqIndexStart()) {
                    // Appointment planned at the start of this free block.
                    // We will move the startIndex to the end of the appointment to schink it.

                    dNode.moveQIndexStart(appointment.getqEndIndex());
                } else if (appointment.getqEndIndex() == dNode.getqIndexEnd()) {
                    // Appointment planned to end at the end of the block.
                    // We will move the Endindex to the start of the appointment to schink it.

                    dNode.moveQEndIndex(appointment.getqStartIndex());
                } else {
                    // Not at the start or end of the free time.
                    // To represent the free time before and after the appointment we add nodes to the tree.

                    // Calculate the indexes for Node 1
                    DayNode node0 = new DayNode(dNode.getqIndexStart(), appointment.getqStartIndex());

                    // Calculate the indexes for Node 2
                    DayNode node1 = new DayNode(appointment.getqEndIndex(), dNode.getqIndexEnd());
                    dNode.addNodes(node0, node1);

                    System.out.println("Node 0:: " + node0.toString());
                    System.out.println("Node 1:: " + node1.toString());
                    continue;
                }

                System.out.println("New start index for the thing! " + dNode.toString());
            }
        }
    }

    // TODO: Translate this to the Controller / API
    public Appointment findFreePeriod(LocalDate date, long QDuration)
    {
        // Check if date is newer then we calculated free time for.
        ArrayList<LocalDate> keys = new ArrayList<LocalDate>(dateTreeMap.keySet());
        if (!keys.contains(date)) {
            System.out.println("That's easy just plan it in!");
            LocalDateTime startDTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

            Long qDurationToMinutes = (QDuration * 15);
            LocalDateTime endDTime = LocalDateTime.from(startDTime);
            endDTime = endDTime.plusMinutes(qDurationToMinutes);

            return new Appointment("", "", "", startDTime, endDTime);
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


            return new Appointment("", "", "", startDTime, endDTime);
        }

        return findFreePeriod(date.plusDays(1), QDuration);
    }

}
