package project.device;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorSensor {
	private static ColorSensor colorSensor = new ColorSensor();

    // カラーセンサー
    static EV3ColorSensor color;
    static SensorMode redMode;           // 輝度検出モード
    static float[] sampleLight;
    final Port  SENSORPORT_COLOR     = SensorPort.S3;  // カラーセンサーポート

    private ColorSensor() {
        color = new EV3ColorSensor(SENSORPORT_COLOR);
        redMode = color.getRedMode();     // 輝度検出モード
        sampleLight = new float[redMode.sampleSize()];
    }

    public static ColorSensor getInstance() {
    	return colorSensor;
    }

    public float getBrightness() {
        redMode.fetchSample(sampleLight, 0);
        return sampleLight[0];
    }
}
