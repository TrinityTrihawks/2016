package frc.team4215.stronghold;



import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class I2CGyro {
   
    static I2C gyro;
    static boolean gyroExiest;
    static boolean pingFlag;
    
    static HardwareTimer hardTimer = new HardwareTimer();
    static Timer.Interface timer;
    
    final static byte WHO_AM_I = 0x0F,
    				  CTRL_REG = 0x20,
    				  OUT_REG = 0x28;
    
    private static double[] angles;
    private static double offsetX,offsetY, offsetZ;
    private static int numOfCalibPings = 100;
    static byte[] ID = new byte[1],
    			  dataBuffer = new byte[1];
    
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
    	
    	double deltat = timer.get();
    	int[] angularSpeed = new int[3]; 
    	
    	/*
    	 * The velocities are stored in registers 0x28-0x2D.
    	 * And are stored in two's complement form with the
    	 * first byte being the left half of the number and the
    	 * second being the right half.
    	 */
    	
    	for(int i = 0; i < angularSpeed.length; i++){
    		gyro.read(OUT_REG+i, 1, dataBuffer);
    		byte gL = dataBuffer[0];
    		gyro.read(OUT_REG+i+1, 1, dataBuffer);
    		byte gH = dataBuffer[0];
    		angularSpeed[i] = concatCorrect(gL,gH);
    	}
    	
    	double cX, cY, cZ;
    	cX = angles[0] + (angularSpeed[0] - offsetX)*deltat;
    	cY = angles[1] + (angularSpeed[1] - offsetY)*deltat;
    	cZ = angles[2] + (angularSpeed[2] - offsetZ)*deltat;
    	
    	angles = new double[] {cX % 360, cY  % 360, cZ % 360};
    }
    
     public static void pingerStart(){
    	// Starts a thread to continually update our status variables
    	 Runnable pinger = () -> {
    		 while(pingFlag){
 				pingGyro();
 				try {
 					Thread.sleep(700);
 				} catch (InterruptedException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 			}
    	};
    	
    	threadPing = new Thread(pinger);
    	threadPing.start();
    }
     
    public static byte normalize(byte in){
    	return (byte) ((byte) 0x00FF & in);
    }
    
    static public int concatCorrect(byte h, byte l){
		int high = Byte.toUnsignedInt(h);
		int low = Byte.toUnsignedInt(l);
        String concat = Integer.toHexString(high) + Integer.toHexString(low);
        int preCheck = Integer.valueOf(concat,16);
        return (preCheck > 32767) ? preCheck - 65536: preCheck;

	}
    
    public static void calibrate(){
    	
    	double totalSumX = 0;
    	double totalSumY = 0;
    	double totalSumZ = 0;
    	
    	for(int i = 0; i < numOfCalibPings; i++){
    		pingGyro();
    		totalSumX += angles[0];
    		totalSumY += angles[1];
    		totalSumZ += angles[2];
    	}
    	
    	offsetX = totalSumX/numOfCalibPings;
    	offsetY = totalSumY/numOfCalibPings;
    	offsetZ = totalSumZ/numOfCalibPings;
    	
    }
    
    public static double[] getAngles(){
    	return angles;
    }
     
}