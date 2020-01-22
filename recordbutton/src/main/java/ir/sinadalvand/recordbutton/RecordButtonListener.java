package ir.sinadalvand.recordbutton;

public interface RecordButtonListener {
    Boolean onStartProgress();
    void onProgressing(Float percent);
    void onFinish();
    void onCancell();
}
