package project;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lejos.hardware.lcd.LCD;
import project.task.DriveTask;

public class Main {

	// スケジューラ
	private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureDrive;

    // タスク
    private DriveTask  driveTask;

    /**
     * コンストラクタ。
     * スケジューラとタスクオブジェクトを作成。
     */
    public Main() {
    	scheduler  = Executors.newScheduledThreadPool(1);
    	driveTask  = new DriveTask();
    }

    /**
     * 走行開始時の作業スケジューリング。
     */
    public void start() {
        futureDrive = scheduler.scheduleAtFixedRate(driveTask, 0, 5, TimeUnit.MILLISECONDS);
    }

    /**
     * 走行終了時のタスク終了後処理。
     */
    public void stop () {
        if (futureDrive != null) {
            futureDrive.cancel(true);
        }
    }

    /**
     * スケジューラのシャットダウン。
     */
    public void shutdown() {
        scheduler.shutdownNow();
    }

    /**
     * メイン
     */
	public static void main(String[] args) {
		LCD.drawString("Please Wait...  ", 0, 4);

		Main main = new Main();

		LCD.drawString("Running       ", 0, 4);

        main.stop();
        main.shutdown();
	}
}
