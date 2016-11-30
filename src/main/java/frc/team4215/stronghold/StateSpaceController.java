package frc.team4215.stronghold;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

/**
 * A State space controller
 * @author waweros
 *
 */
public class StateSpaceController {
	// TODO implement auto hierachy
	
	// 
	private static final double period = .5;
	private boolean enabled = false;
	
	private DenseMatrix64F K;
	private DenseMatrix64F r;
	private DenseMatrix64F x;
	private DenseMatrix64F u;
	private DoubleConsumer[] motors;
	private DoubleSupplier[] sensors;
	
	private Timer controlLoop;
	
	/**
	 * 
	 * Currently needs a gain matrix, a reference point, sensors and motors
	 * 
	 * @param K
	 * @param r
	 * @param sensors
	 * @param motors
	 * @throws Exception
	 */
	public StateSpaceController(double[][] K, double[] r, DoubleSupplier[] sensors, DoubleConsumer[] motors) throws Exception {
		controlLoop = new Timer();
		
		if(r.length != sensors.length) throw new Exception("Dimension mismatch between x and r");
		if(K.length != motors.length) throw new Exception("Dimension mismatch between K and u");
		if(K[0].length != sensors.length) throw new Exception("Dimension mismatch between K and x");
		
		double[][] modR = new double[1][r.length];
		for(int i = 0; r.length > i; i++){
			// Adjusting reference point
			modR[0][i] = sensors[i].getAsDouble() - r[i];
		}
		
		this.K = new DenseMatrix64F(K);
		this.r = new DenseMatrix64F(modR);
		this.motors = motors;
		this.sensors = sensors;
		
		controlLoop.schedule(new StateSpaceTask(this),(long) (1000*period));
	}
	
	private class StateSpaceTask extends TimerTask {
		private StateSpaceController control;
		
		public StateSpaceTask(StateSpaceController control){
			if(control ==  null) throw new NullPointerException();
		    this.control = control;
		}
		
		@Override
		public void run(){
			control.calculate();
		}
	}
	
	/*
	 * Should only be called by StateSpaceTask
	 */
	private void calculate(){
		double[] sensorData = new double[sensors.length];
		boolean ourEnabled;
	    
		//Taking  a snapshots of our variables
		synchronized(this){
			ourEnabled = enabled;
			for(int i = 0; sensors.length > i; i++){
				sensorData[i] = sensors[i].getAsDouble();
			}
	    }
		
		
		if(ourEnabled){
			// Computing u according to u = K(r - x)
			x.setData(sensorData);
			CommonOps.transpose(x);
			CommonOps.scale(-1, x);
			CommonOps.addEquals(x, r);
			CommonOps.mult(K, x, u);
			
			double[] motorSettings = u.data;
			
			for(int i = 0; sensors.length > i; i++){
				motors[i].accept(motorSettings[i]);
			}
		}
	}
	
	/**
	 * Enables controller 
	 * 
	 */
	public synchronized void enable(){
		enabled = true;
	}
	
	/**
	 * Disables controller
	 */
	public synchronized void disable(){
		enabled = false;
	}
	
	/**
	 * Tells you if controller is enabled
	 * @return
	 */
	public synchronized boolean isEnabled(){
		return enabled;
	}
}
