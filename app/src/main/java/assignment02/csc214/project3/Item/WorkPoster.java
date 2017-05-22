package assignment02.csc214.project3.Item;

/**
 * Created by Kennedy on 4/28/2017.
 */

public class WorkPoster<W> implements Runnable {
    private final W mWork;
    private final JobListener<W> mListener;

    public WorkPoster(W work, JobListener<W> listener) {
        mWork = work;
        mListener = listener;
    }

    @Override
    public void run() {
        mListener.someWorkCompleted(mWork);
    }
}
