package in.wilv.planman.daytree;

public class DayTree
{
    public DayNode root;

    public DayTree()
    {
        this.root = DayNode.constructRootDayNode();
    }

    public DayNode getNodeContainingIndexes(DayNode currentNode, long start, long end)
    {
        start = Math.min(Math.max(start, 0), 96);
        end = Math.min(Math.max(end, 0), 96);

        if (currentNode.isContiguous()) {
            if(currentNode.getqIndexStart() <= start && currentNode.getqIndexEnd() >= end) {
                return currentNode;
            }
            return null;
        }

        DayNode left = this.getNodeContainingIndexes(currentNode.left, start, end);
        if(left != null) {
            return left;
        }

        return this.getNodeContainingIndexes(currentNode.right, start, end);
    }

    public DayNode getFistContiguousWithMinSize(DayNode currentNode, Long size)
    {
        if(currentNode.isContiguous()) {
            // Current node is one block of time
            if(size <= currentNode.qFreeTime) {
                // Size fits in this block so return it.
                return currentNode;
            }

            return null;
        }

        // Current block is not Contiguous lets try the left node...
        // Left nodes have a lower qStartIndex so are earlier in the day.
        DayNode left = this.getFistContiguousWithMinSize(currentNode.left, size);
        if(left != null) {
            return left;
        }

        // Does not fit in the first part of the day lets try the second part.
        return this.getFistContiguousWithMinSize(currentNode.right, size);

    }
}
