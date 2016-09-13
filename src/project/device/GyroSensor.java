package project.device;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;


public class GyroSensor {
	private static GyroSensor gyroSensor = new GyroSensor();

    // ジャイロセンサー
    private EV3GyroSensor gyro;
    private SampleProvider rate;          // 角速度検出モード
    private float[] sampleGyro;
    private final Port  SENSORPORT_GYRO      = SensorPort.S4;  // ジャイロセンサーポート

    private GyroSensor() {
        gyro = new EV3GyroSensor(SENSORPORT_GYRO);
        rate = gyro.getRateMode();              // 角速度検出モード
        sampleGyro = new float[rate.sampleSize()];
    }

    public static GyroSensor getInstance() {
    	return gyroSensor;
    }

    public float getGyro() {
        rate.fetchSample(sampleGyro, 0);
        return -sampleGyro[0];
    }
}
