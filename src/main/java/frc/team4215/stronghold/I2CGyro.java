package frc.team4215.stronghold;



import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class I2CGyro {
   
    static I2C gyro;
    static boolean gyroExiest;
    
    static HardwareTimer hardTimer = new HardwareTimer();
    static Timer.Interface timer;
    
    final static byte WHO_AM_I = 0x0F,
    				  CTRL_REG = 0x20,
    				  OUT_REG = 0x28;
    
    private static double[] angles;
    
    static byte[] ID = new byte[1],
    			  dataBuffer = new byte[6];
    
    static private Thread threadPing;
    
    
    public static void initGyro() {
    	
    	// Instantiating the gyro Object
       gyro = new I2C(I2C.Port.kOnboard, 0x1D);
       
       // Checking if this gyro actually exists;
       
       gyro.write(CTRL_REG,   0x0F); 
       gyro.write(CTRL_REG+1, 0x00);
       gyro.write(CTRL_REG+2, 0x88);
       gyro.write(CTRL_REG+3, 0x00);
       gyro.write(CTRL_REG+4, 0x00);
       
       boolean worked = gyro.read(WHO_AM_I, 1, ID);
       ID[0] = normalize(ID[0]);
       
       if(ID[0] == 0xD4){
    	   RobotModule.logger.info("Gyro active!");
       }
       else{
    	   RobotModule.logger.error("Gyro not operating, please check wiring! " + Integer.toBinaryString(ID[0]) + " " + worked);   
       }
       
       /*
        * So, without explanation the following code is going to
        * seem a bit cryptic. Essentially the Gyro is set up with
        * a series of bits(1's or 0's) and these base 16 numbers correspond 
        * to the particular series of bits you need. the numbers and such are found at
        * *so and so*
        */
       
       
       
       /*
        *  The gyroscope only gives us angular velocity
        *  so we need to integrate it.
        */
       timer = hardTimer.newTimer();
       timer.start();
       
       // Resetting the tracker varibles
       angles = new double[] {0,0,0};
    }
    
     public static void pingGyro(){
    	
    	double time = timer.get();
    	gyro.read(OUT_REG, dataBuffer.length, dataBuffer);
    	
    	/*
    	 * The velocities are stored in registers 0x28-0x2D.
    	 * And are stored in two's complement form with the
    	 * first byte being the left half of the number and the
    	 * second being the right half.
    	 */
    	for(int i = 0; i < dataBuffer.length; i++){
    		dataBuffer[i] = normalize(dataBuffer[i]);
    	}
    	
    	double cX, cY, cZ;
    	cX = ((dataBuffer[1] << 8 | 0xFF & dataBuffer[0]));
    	cY = ((dataBuffer[3] << 8 | 0xFF & dataBuffer[2]));
    	cZ = ((dataBuffer[5] << 8 | 0xFF & dataBuffer[4]));
    	
    	if(cX > 32767);
    		cX -= 65536;
    	if(cY > 32767)
    		cY -= 65536;
    	if(cZ > 32767)
    		cZ -= 65536;
    	
    	angles = new double[] {cX*time + angles[0], cY*time + angles[1], cZ*time + angles[2]};
    }
    
     public static void pingerStart(){
    	// Starts a thread to continually update our status variables
    	 Runnable pinger = () -> {
    		while(true)
    			pingGyro();
    	};
    	
    	threadPing = new Thread(pinger);
    	threadPing.start();
    }
     
    public static byte normalize(byte in){
    	return (byte) ((byte) 0x00FF & in);
    }
    
    public static void calibrate(){
    	for(int i = 0; i < 100; i++){
    		pingGyro();
    		
    	}
    }
    public static double[] getAngles(){
    	return angles;
    }
     
}