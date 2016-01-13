import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {
    private static ArrayList<Task> instances = new ArrayList<Task>();

    private String mDescription;
    private boolean mIsCompleted;
    private int mID;
    private LocalDateTime mCreatedAt;

    //CONSTRUCTOR

    public Task(String description) {
        mDescription = description;
        mIsCompleted = false;
        mCreatedAt = LocalDateTime.now();
        instances.add(this);
        mID = instances.size();
    }

    //STATIC METHODS

    public static ArrayList<Task> all() {
        return instances;
    }

    public static Task find(int id) {
        try {
            return instances.get(id - 1);
        } catch (IndexOutOfBoundsException iobe) {
            return null;
        }
    }

    public static void clear() {
        instances.clear();
    }

    //INSTANCE METHODS

    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public void completeTask() {
        this.mIsCompleted = true;
    }

    public LocalDateTime getCreatedAt() {
        return mCreatedAt;
    }

    public int getId() {
        return mID;
    }



}
