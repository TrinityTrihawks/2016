package frc.team4215.stronghold;

/**
 * <dl>
 * <dt>Classes:</dt>
 * <dd><strong>Motor:</strong></dd>
 * <dd>FrontLeft = 0</dd>
 * <dd>BackLeft = 1</dd>
 * <dd>BackRight = 2</dd>
 * <dd>FrontRight = 3</dd>
 * <dd>Arm = 4</dd>
 * <dd>Intake = 5</dd>
 * </dl>
 *
 * @author James Yu
 */
public final class Const {
    public final class Motor {
        public final static int FrontLeft = 0, BackLeft = 1, BackRight = 2, FrontRight = 3, Arm = 4, Intake = 5;
    }

    public final class JoyStick {
        public final static int PlayStation = 1, GameCube = 2;

        public final class Axis {
            public final static int PlayStationCtrlLeft_UD = 1, PlayStationCtrlRight_UD = 5, GameCubeCtrl_UD = 1;
        }
    }
}