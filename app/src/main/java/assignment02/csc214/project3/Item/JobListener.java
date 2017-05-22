package assignment02.csc214.project3.Item;

/**
 * Created by Kennedy on 4/28/2017.
 */

public interface JobListener<W> {
    public void someWorkCompleted(W work);

    public void jobComplete();
}

