package assignment02.csc214.project3.Item;

/**
 * Created by Kennedy on 4/28/2017.
 */

public class JobCompletePoster implements Runnable {
    private final JobListener<?> mListener;

    public JobCompletePoster(JobListener<?> listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        mListener.jobComplete();
    }
}
