package project.task;

import lejos.hardware.lcd.LCD;

public class DriveTask implements Runnable {

    /**
     * EV3本体の制御。
     */
    @Override
    public void run() {
    	LCD.drawString("aaa", 0, 0);
    }
}
