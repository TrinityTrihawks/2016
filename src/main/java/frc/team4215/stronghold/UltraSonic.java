
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SensorBase;

import jaci.openrio.toast.lib.registry.Registrar;

/**
 * UltraSonic class. UltraSonic class, extends {@link SensorBase}.<br/>
 * Using the file AnalogUltrasonic.java from \2015 as the prototype. <br/>
 * <dl>
 * <dt><strong>Properties:</strong></dt>
 * <dd><strong>Private:</strong></dd>
 * <dd>&emsp;<strong>final</strong> double {@link UltraSonic#INCH2CM}</dd>
 * <dd>&emsp;boolean {@link UltraSonic#isUsingUnit}</dd>
 * <dd>&emsp;double {@link UltraSonic#minVoltageInch}</dd>
 * <dd>&emsp;double {@link UltraSonic#voltageRangeInch}</dd>
 * <dd>&emsp;double {@link UltraSonic#minDistanceInch}</dd>
 * <dd>&emsp;double {@link UltraSonic#distanceRangeInch}</dd>
 * <dd><strong>Public:</strong></dd>
 * <dd>&emsp;{@link AnalogInput} {@link UltraSonic#channel}</dd>
 * <dt><strong>Methods:</strong></dt>
 * <dd><strong>Constructors:</strong></dd>
 * <dd>&emsp;{@link UltraSonic#UltraSonic(int)}</dd>
 * <dd>&nbsp;&nbsp;&nbsp;&nbsp;
 * {@link UltraSonic#UltraSonic(int, boolean, double, double, double, double)}
 * </dd>
 * <dd><strong>Other methods:</strong></dd>
 * <dd>&emsp;{@link UltraSonic#getVoltage()}</dd>
 * <dd>&emsp;{@link UltraSonic#getRangeInch()}</dd>
 * <dd>&emsp;{@link UltraSonic#getRangeCM()}</dd>
 * </dl>
 *
 * @author James Yu
 * @version 2016.3.3 <!-- Year.WeekNum.DayNum from the Kickoff Saturday; Started
 *          from 2016.0.0 -->
 */

public class UltraSonic extends SensorBase {
    
    /**
     * <strong>Private</strong>, <strong>Final</strong><br/>
     * &emsp;&emsp;The conversion ratio from Inches to centimeters.
     */
    
    private final double INCH2CM = 2.54d;
    
    /**
     * <strong>Private</strong><br/>
     * &emsp;&emsp;Whether using units or just returning voltage. <br/>
     * &emsp;&emsp;<spam style="color:#777">By the way, I don't understand this
     * sentence. <br/>
     * &emsp;&emsp;&emsp;&emsp;&emsp;--James</spam>
     */
    
    private boolean isUsingUnit;
    
    /**
     * <strong>Private</strong><br/>
     * &emsp;&emsp;Minimum voltage the ultrasonic sensor can return.
     */
    
    private double minVoltageInch;
    
    /**
     * <strong>Private</strong><br/>
     * &emsp;&emsp;The range of the voltage returned by the sensor. Calculated
     * with <i>Max - Min</i>.
     */
    
    private double voltageRangeInch;
    
    /**
     * <strong>Private</strong><br/>
     * &emsp;&emsp;Minimum distance the ultrasonic sensor can return.
     */
    
    private double minDistanceInch;
    
    /**
     * <strong>Private</strong><br/>
     * &emsp;&emsp;The range of the distances returned by this class. Calculated
     * by <i>Max - Min</i>.
     */
    private double distanceRangeInch;
    
    /**
     * <strong>Public</strong><br/>
     * &emsp;&emsp;There is no description.
     */
    
    public AnalogInput channel;
    
    /**
     * UltraSonic(int), Public constructor with 1 parameter. Copied from 2015.
     *
     * @author 2015
     * @param channel_
     *            is the parameter passed into {@code new AnalogInput}.
     */
    
    public UltraSonic(int channel_) {
        this.channel = new AnalogInput(channel_);
        // Default Values
        this.isUsingUnit = true;
        this.minVoltageInch = .01;
        this.voltageRangeInch = 5d - this.minVoltageInch;
        this.minDistanceInch = 0d;
        this.distanceRangeInch = 60d - this.minDistanceInch;
    }
    
    /**
     * UltraSonic(int, boolean, double, double, double, double), Public
     * constructor with 6 parameters. Copied from 2015.
     *
     * @author 2015
     * @param channel_
     *            is the parameter passed into {@code new AnalogInput}.
     * @param isUnit_
     *            tells whether you are using units or just returning the
     *            voltage.
     * @param minVoltage_
     *            is the minimal voltage
     * @param maxVoltage_
     *            is the maximal voltage
     * @param minDistance_
     *            is the minimal distance
     * @param maxDistance_
     *            is the maximal distance
     */
    
    public UltraSonic(int channel_,
            boolean isUsingUnit_,
            double minVoltage_,
            double maxVoltage_,
            double minDistance_,
            double maxDistance_) {
        this.channel = Registrar.analogInput(channel_);
        // Only use unit-specific vars if using units
        if (isUsingUnit_) {
            this.isUsingUnit = true;
            this.minVoltageInch = minVoltage_;
            this.voltageRangeInch = maxDistance_ - minDistance_;
        }
        // QUESTION:
        // Is there anything to be done if usingUnit_ is false?
        // Asked by James
    }
    
    /**
     * A function to get the Voltage. Copied from 2015.
     *
     * @author 2015
     * @return Voltage in double.
     */
    
    public double getVoltage() {
        
        return this.channel.getVoltage();
    }
    
    /**
     * A function to get the Range in Inches. Copied from 2015.
     *
     * @author 2015
     * @return <spam style="color:#f00">{@literal -1.0}</spam> if units are not
     *         being used;<br/>
     *         <spam style="color:#f00">{@literal -2.0}</spam> if the voltage is
     *         below the minimum voltage;<br/>
     *         <spam style="color:#f0f">Positive values</spam> are what we want.
     */
    
    public double getRangeInch() {
        
        double range;
        // if not using units, return -1,
        // a range that'll most likely never be returned.
        if (!this.isUsingUnit) return -1d;
        range = this.channel.getVoltage();
        if (range < this.minVoltageInch) return -2d;
        // First, normalize the voltage
        range = (range - this.minVoltageInch) / this.voltageRangeInch;
        // Then, denormalize to the unit range
        range = (range * this.distanceRangeInch) + this.minDistanceInch;
        
        return range;
    }
    
    /**
     * A function to get the Range in CentiMeters.
     * 
     * @return {@literal -1.0} if units are not being used;<br/>
     *         {@literal -2.0} if the voltage is below the minimum voltage.
     * @return <spam style="color:#f00">{@literal -1.0}</spam> if units are not
     *         being used.<br/>
     *         <spam style="color:#f00">{@literal -2.0}</spam> if the voltage is
     *         below the minimum voltage.<br/>
     *         <spam style="color:#f0f">Positive values</spam> are what we want.
     * @see UltraSonic#getRangeInch()
     */
    
    public double getRangeCM() {
        
        /*
         * This function is different from the original function GetRangeInCM in
         * AnalogUltrasonic.java. Calling getRange directly.
         */
        double range = this.getRangeInch();
        // All the if-else checking are done in the gRI method.
        if (-1d != range && -2d != range) range *= this.INCH2CM;
        // Then the multiplication
        
        return range;
    }
}
