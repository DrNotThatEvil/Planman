package in.wilv.planman.daytree;

public class DayNode
{
    static final long Q_DAY_LENGTH = 96;

    long qFreeTime;
    long qIndexStart;
    long qIndexEnd;
    DayNode left;
    DayNode right;

    public DayNode(long qIndexStart, long qIndexEnd)
    {
        this.qIndexStart = qIndexStart;
        this.qIndexEnd = qIndexEnd;
        this.qFreeTime = (qIndexEnd - qIndexStart);

        this.left = null;
        this.right = null;
    }

    public void addNodes(DayNode node0, DayNode node1)
    {
        if(!this.isContiguous())
        {
            Long totalFreeTime = node0.qFreeTime + node1.qFreeTime;
            if(totalFreeTime <= this.left.qFreeTime) {
                this.left.addNodes(node0, node1);
            } else {
                this.right.addNodes(node0, node1);
            }
            return;
        }

        this.qFreeTime = (node0.qFreeTime + node1.qFreeTime);
        this.left = (node0.qIndexStart < node1.qIndexEnd ? node0 : node1);
        this.right = (node0.qIndexStart > node1.qIndexEnd ? node0 : node1);
    }

    public static DayNode constructRootDayNode()
    {
        return new DayNode(0, Q_DAY_LENGTH);
    }

    public boolean isContiguous()
    {
        return (this.left == null || this.right == null);
    }

    public long getqFreeTime()
    {
        return qFreeTime;
    }

    public long getqIndexStart()
    {
        return qIndexStart;
    }

    public void moveQIndexStart(long newIndex)
    {
        this.qIndexStart = newIndex;
        this.qFreeTime = (qIndexEnd - qIndexStart);
    }

    public void moveQEndIndex(long newIndex)
    {
        this.qIndexEnd = newIndex;
        this.qFreeTime = (qIndexEnd - qIndexStart);
    }

    public long getqIndexEnd()
    {
        return qIndexEnd;
    }

    @Override
    public String toString()
    {
        return "DayNode{" +
                "qFreeTime=" + qFreeTime +
                ", qIndexStart=" + qIndexStart +
                ", qIndexEnd=" + qIndexEnd +
                ", left=" + (left == null ? "null" : left)+
                ", right=" + (right == null ? "null" : right) +
                '}';
    }
}
