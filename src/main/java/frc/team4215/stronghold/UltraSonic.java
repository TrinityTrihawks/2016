package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.registry.Registrar;

/**
 * UltraSonic class.
 * @author James Yu<br/>
 * Jack Rausch
 * 
 */
public class UltraSonic extends SensorBase {

	private final double INCH2CM_CONV = 2.54;
	private boolean usingUnit;    // Using units or just returning voltage
    private double minVoltageINCH;	  // Minimum voltage the ultrasonic sensor can return
    private double voltageRangeINCH; // The range of the voltages returned by the sensor(maximum - minimum)
    private double minDistanceINCH;  // Minimum distance the ultrasonic sensor can return
    private double distanceRangeINCH;// The range of the distances returned by this class(maximum - minimum)
    AnalogInput channel; // ISSUE HERE
	
    /**
     * Constructor with 1 parameter.
     * @author James Yu
     * @param channel_
     * is the parameter passed into
     * {@code new AnalogInput}.
     */
	public UltraSonic(int channel_) {
		this.channel = Registrar.analogInput(channel_);
		// Default Values
		this.usingUnit = true;
		this.minVoltageINCH = .01;
		this.voltageRangeINCH = // 5d is type of double
				5d - this.minVoltageINCH;
		this.minDistanceINCH = 0d;
		this.distanceRangeINCH = 
				60d - this.minDistanceINCH;
	}
	
	/**
	 * Constructor with 6 parameters.
	 * @author James Yu
	 * @param channel_
	 * is the parameter passed into
	 * {@code new AnalogInput}. 
	 * @param isUnit_
	 * tells whether you are using units or just
	 *  returning the voltage.
	 * @param minVoltage_
	 * is the minimal voltage
	 * @param maxVoltage_
	 * is the maximal voltage
	 * @param minDistance_
	 * is the minimal distance
	 * @param maxDistance_
	 * is the maximal distance
	 */
	
	public UltraSonic(int channel_, boolean usingUnit_,
			double minVoltage_, double maxVoltage_,
			double minDistance_, double maxDistance_) {
		this.channel = new AnalogInput(channel_);
		// Only use unit-specific vars if using units
		if(usingUnit_){
			this.usingUnit = true;
			this.minVoltageINCH = minVoltage_;
			this.voltageRangeINCH = 
					maxDistance_ - minDistance_;
		} // is there anything if usingUnit_ is false? 
		// by James
	}
	
	/**
	 * A function to get the Voltage.
	 * @author James Yu
	 * @return Voltage in double.
	 */
	public double getVoltage() {
		return this.channel.getVoltage(); 
	}
	
	/**
	 * A function to get the Range in inches.
	 * @author James Yu
	 * @return {@literal -1.0}
	 * if units are not being used;<br/>
	 * {@literal -2.0} if the voltage is below the
	 *  minimum voltage.
	 */
	public double getRangeINCH() {
		double range;
		// if not using units, return -1,
		// a range that'll most likely never be returned.
		if(!this.usingUnit) return -1d;
		range = this.channel.getVoltage();
		if(range < this.minVoltageINCH) return -2d;
		// First, normalize the voltage
		range = (range - this.minVoltageINCH)
				/ this.voltageRangeINCH;
		// Then, denormalize to the unit range
		range = (range * this.distanceRangeINCH)
				+ this.minDistanceINCH;
		
		return range;
	}
	
	/**
	 * A function to get the Range in cm.
	 * @return {@literal -1.0}
	 * if units are not being used;<br/>
	 * {@literal -2.0} if the voltage is below the
	 *  minimum voltage.
	 */
	public double getRangeCM() {
		/*
		 *  This function is different from the original
		 *  function GetRangeInCM in
		 *  AnalogUltrasonic.java. Calling getRange
		 *  directly.
		 */
		double range = this.getRangeINCH();
		// Then all the if-else checking are done.
		if(-1d != range && -2d != range)
			range *= this.INCH2CM_CONV;
		
		return range;
	}
	
}
