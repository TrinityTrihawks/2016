
package frc.team4215.stronghold;

/**
 * <pre>
 * <strong>Structure of this class:</strong>
 * <b>class</b> <spam style="color:#f00">Const</spam>
 *   <b>class</b> <spam style="color:#f0f">Motor</spam>
 *     <b>class</b> <spam style="color:#00f">Num</spam>
 *       <spam style="color:#bbb">FrontLeft</spam> = 0
 *       <spam style="color:#bbb">BackLeft</spam> = 1
 *       <spam style="color:#bbb">BackRight</spam> = 2
 *       <spam style="color:#0f0">FrontRight</spam> = 3
 *       <spam style="color:#f00">Arm</spam> = 4
 *       <spam style="color:#000">Intake</spam> = 5
 *     <b>class</b> <spam style="color:#00f">Run</spam>
 *       <spam style="color:#000">Forward</spam> = 1
 *       <spam style="color:#000">Backward</spam> = -1
 *       <spam style="color:#000">Stop</spam> = 0
 *   <b>class</b> <spam style="color:#f0f">JoyStick</spam>
 *     <b>class</b> <spam style="color:#00f">Num</spam>
 *       <spam style="color:#3d8">PlayStation</spam> = 1
 *       <spam style="color:#3d8">GameCube</spam> = 2
 *     <b>class</b> <spam style="color:#00f">Axis</spam>
 *       <spam style="color:#3d8">PlayStationCtrlLeft_UD</spam> = 1
 *       <spam style="color:#3d8">PlayStationCtrlRight_UD</spam> = 5
<<<<<<< HEAD
=======
 *       <spam style="color:#3d8">GameCubeCtrl_LR</spam> = 0
>>>>>>> Autonomous
 *       <spam style="color:#3d8">GameCubeCtrl_UD</spam> = 1
 *     <b>class</b> <spam style="color:#00f">Button</spam>
 *       <spam style="color:#3d8">GameCube_Y</spam> = 1
 *       <spam style="color:#3d8">GameCube_X</spam> = 2
 *       <spam style="color:#3d8">GameCube_A</spam> = 3
 *       <spam style="color:#3d8">GameCube_B</spam> = 4
 * </pre>
 *
 * @author James Yu
 */
public final class Const {

    
    public final class Motor {
        
        public final class Num {
            
            public final static int FrontLeft = 3, BackLeft = 1,
                    BackRight = 2, FrontRight = 0, Arm1 = 4,
                    Intake = 5, Arm2 = 6;
        }

        public final class Run {
            
            public final static double Forward = 1d, Backward = -1d,
                    Stop = 0d;

        }
    }

    public final class JoyStick {
        
        public final class Num {
            

            public final static int PlayStation = 1, GameCube = 2;
        }

        public final class Axis {
            
            public final static int PlayStationCtrlLeft_UD = 1,
                    PlayStationCtrlRight_UD = 5;

            public final static int GameCubeCtrl_UD = 1,
                    GameCubeCtrl_LR = 0;
        }

        public final class Button {
            
            public final static int GameCube_Y = 1, GameCube_X = 2,
                    GameCube_A = 3, GameCube_B = 4;
        }
    }
}
