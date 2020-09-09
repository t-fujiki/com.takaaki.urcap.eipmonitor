package com.takaaki.urcap.eipmonitor.impl.rtde;

public enum RTDE {
    RTDE_REQUEST_PROTOCOL_VERSION(86), /* */
    RTDE_GET_URCONTROL_VERSION(118), /* */
    RTDE_TEXT_MESSAGE(77), /* */
    RTDE_DATA_PACKAGE(85), /* */
    RTDE_CONTROL_PACKAGE_SETUP_OUTPUTS(79), /*  */
    RTDE_CONTROL_PACKAGE_SETUP_INPUTS(73), /*  */
    RTDE_CONTROL_PACKAGE_START(83), /*  */
    RTDE_CONTROL_PACKAGE_PAUSE(80);/* */

    private int ptype;

    private RTDE(int ptype) {
        this.ptype = ptype;
    }

    public int getType() {
        return ptype;
    }

}