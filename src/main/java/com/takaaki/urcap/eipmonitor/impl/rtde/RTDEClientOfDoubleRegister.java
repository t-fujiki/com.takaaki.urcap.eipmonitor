package com.takaaki.urcap.eipmonitor.impl.rtde;

import java.io.IOException;

public abstract class RTDEClientOfDoubleRegister extends RTDEClient {

    public RTDEClientOfDoubleRegister(int frequency) {
        super("127.0.0.1", frequency, "Doubles");

        addOutput(RTDEOutput.input_double_register_0);
        addOutput(RTDEOutput.input_double_register_1);
        addOutput(RTDEOutput.input_double_register_2);
        addOutput(RTDEOutput.input_double_register_3);
        addOutput(RTDEOutput.input_double_register_4);
        addOutput(RTDEOutput.input_double_register_5);
        addOutput(RTDEOutput.input_double_register_6);
        addOutput(RTDEOutput.input_double_register_7);
        addOutput(RTDEOutput.input_double_register_8);
        addOutput(RTDEOutput.input_double_register_9);
        addOutput(RTDEOutput.input_double_register_10);
        addOutput(RTDEOutput.input_double_register_11);
        addOutput(RTDEOutput.input_double_register_12);
        addOutput(RTDEOutput.input_double_register_13);
        addOutput(RTDEOutput.input_double_register_14);
        addOutput(RTDEOutput.input_double_register_15);
        addOutput(RTDEOutput.input_double_register_16);
        addOutput(RTDEOutput.input_double_register_17);
        addOutput(RTDEOutput.input_double_register_18);
        addOutput(RTDEOutput.input_double_register_19);
        addOutput(RTDEOutput.input_double_register_20);
        addOutput(RTDEOutput.input_double_register_21);
        addOutput(RTDEOutput.input_double_register_22);
        addOutput(RTDEOutput.input_double_register_23);

        addOutput(RTDEOutput.output_double_register_0);
        addOutput(RTDEOutput.output_double_register_1);
        addOutput(RTDEOutput.output_double_register_2);
        addOutput(RTDEOutput.output_double_register_3);
        addOutput(RTDEOutput.output_double_register_4);
        addOutput(RTDEOutput.output_double_register_5);
        addOutput(RTDEOutput.output_double_register_6);
        addOutput(RTDEOutput.output_double_register_7);
        addOutput(RTDEOutput.output_double_register_8);
        addOutput(RTDEOutput.output_double_register_9);
        addOutput(RTDEOutput.output_double_register_10);
        addOutput(RTDEOutput.output_double_register_11);
        addOutput(RTDEOutput.output_double_register_12);
        addOutput(RTDEOutput.output_double_register_13);
        addOutput(RTDEOutput.output_double_register_14);
        addOutput(RTDEOutput.output_double_register_15);
        addOutput(RTDEOutput.output_double_register_16);
        addOutput(RTDEOutput.output_double_register_17);
        addOutput(RTDEOutput.output_double_register_18);
        addOutput(RTDEOutput.output_double_register_19);
        addOutput(RTDEOutput.output_double_register_20);
        addOutput(RTDEOutput.output_double_register_21);
        addOutput(RTDEOutput.output_double_register_22);
        addOutput(RTDEOutput.output_double_register_23);

    }

    public abstract void onGetValues(double[] input_double_registers, double[] output_double_registers);

    @Override
    public void onReceive(Object[] values) throws IOException {
        double[] input_double_registers = new double[24];
        double[] output_double_registers = new double[24];

        for (int i = 0; i < 24; i++) {
            input_double_registers[i] = (Double) values[i];
        }

        for (int i = 0; i < 24; i++) {
            output_double_registers[i] = (Double) values[24 + i];
        }

        onGetValues(input_double_registers, output_double_registers);

    }

    @Override
    public Object[] onSend() throws IOException {

        return null;
    }
}