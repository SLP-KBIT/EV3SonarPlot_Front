package project.task;

import java.util.Arrays;

import lejos.hardware.lcd.LCD;
import project.device.LeftMotor;
import project.device.RightMotor;
import project.device.SonarSensor;
import project.http.Post;

public class DriveTask implements Runnable {

	RightMotor motorR = RightMotor.getInstance();
	LeftMotor motorL = LeftMotor.getInstance();
	SonarSensor sonar = SonarSensor.getInstance();

	static final int ROTATE_PWM_VALUE = 20;
	static final int ROTATE_MOTOR_ANGLE = 720;
	static final int ROTATE_BODY_ANGLE = 360;

	private int plotData[] = new int[ROTATE_BODY_ANGLE];
	private int currentAnglePoint;
	private boolean postFlag;

	private int lcdCount;

	public DriveTask(){
		Arrays.fill(plotData, -1);  // 配列を-1で初期化
		currentAnglePoint = 0;
		lcdCount = 0;
		postFlag = true;
	}

    /**
     * EV3本体の制御。
     */
    @Override
    public void run() {
    	// デバイスの値取得
    	int tachoR = motorR.getTachoCount();
    	int tachoL = motorL.getTachoCount();
    	float sonarValue = sonar.getDistance();
    	int sonarCMValue = (int)(sonarValue * 100); // センチ単位に変換

    	// LCD表示
    	if (lcdCount == 5) {
    		LCD.clear();
    		LCD.drawString("Right:" + tachoR, 0, 0);
    		LCD.drawString("Left:" + tachoL, 0, 1);
    		LCD.drawString("Sonar:" + sonarCMValue, 0, 2);
    		LCD.drawString("Angle:" + calcAngle(), 0, 4);
    		lcdCount = 0;
    	} else {
    		lcdCount++;
    	}

    	// モーター制御
    	if(motorR.getTachoCount() < ROTATE_MOTOR_ANGLE) {
    		motorRotate(ROTATE_PWM_VALUE);
    	} else {
    		motorStop();
    	}

    	// プロット記録
    	int currentAngle = calcAngle();
    	if (currentAnglePoint < currentAngle) {
    		currentAnglePoint = currentAngle;
    		if (Float.isInfinite(sonarValue) || sonarCMValue > 150) {
    			//Sound.beep();
    		} else if (currentAnglePoint < ROTATE_BODY_ANGLE) {
    			plotData[currentAnglePoint] = sonarCMValue;
    		}

    		// Post送信
    		if (currentAnglePoint > ROTATE_BODY_ANGLE  && postFlag) {
    			Post.executePost("http://****/reciever.rb", createStringPlot(plotData));
    			postFlag = false;
    		}
    	}


    }

    /*
     *  ステアリング
     */
    private void motorRotate(int value) {
    	motorR.controlMotor(value);
    	motorL.controlMotor(-value);
    }

    private void motorStop() {
    	motorR.controlMotor(0);
    	motorL.controlMotor(0);
    }

    /*
     * 機体の向いている角度を計算
     */
    private int calcAngle() {
    	double comp = ROTATE_MOTOR_ANGLE;
    	double current = motorR.getTachoCount();
    	double percent = current /comp;

    	return (int)(ROTATE_BODY_ANGLE * percent);
    }

    private String createStringPlot(int arr[]) {
    	int i = 0;
    	String str = "plots=";
    	for(i = 0; i < ROTATE_BODY_ANGLE - 1; i++) {
    		str = str + String.valueOf(arr[i]) + "," ;
    	}
    	str = str + String.valueOf(arr[ROTATE_BODY_ANGLE - 1]);

    	return str;
    }
}
