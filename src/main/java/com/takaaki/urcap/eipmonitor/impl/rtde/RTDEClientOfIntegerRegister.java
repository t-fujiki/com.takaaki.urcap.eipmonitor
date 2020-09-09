package com.takaaki.urcap.eipmonitor.impl.rtde;

import java.io.IOException;

public abstract class RTDEClientOfIntegerRegister extends RTDEClient {

    public RTDEClientOfIntegerRegister(int frequency) {
        super("127.0.0.1", frequency, "Integers");

        addOutput(RTDEOutput.input_int_register_0);
        addOutput(RTDEOutput.input_int_register_1);
        addOutput(RTDEOutput.input_int_register_2);
        addOutput(RTDEOutput.input_int_register_3);
        addOutput(RTDEOutput.input_int_register_4);
        addOutput(RTDEOutput.input_int_register_5);
        addOutput(RTDEOutput.input_int_register_6);
        addOutput(RTDEOutput.input_int_register_7);
        addOutput(RTDEOutput.input_int_register_8);
        addOutput(RTDEOutput.input_int_register_9);
        addOutput(RTDEOutput.input_int_register_10);
        addOutput(RTDEOutput.input_int_register_11);
        addOutput(RTDEOutput.input_int_register_12);
        addOutput(RTDEOutput.input_int_register_13);
        addOutput(RTDEOutput.input_int_register_14);
        addOutput(RTDEOutput.input_int_register_15);
        addOutput(RTDEOutput.input_int_register_16);
        addOutput(RTDEOutput.input_int_register_17);
        addOutput(RTDEOutput.input_int_register_18);
        addOutput(RTDEOutput.input_int_register_19);
        addOutput(RTDEOutput.input_int_register_20);
        addOutput(RTDEOutput.input_int_register_21);
        addOutput(RTDEOutput.input_int_register_22);
        addOutput(RTDEOutput.input_int_register_23);

        addOutput(RTDEOutput.output_int_register_0);
        addOutput(RTDEOutput.output_int_register_1);
        addOutput(RTDEOutput.output_int_register_2);
        addOutput(RTDEOutput.output_int_register_3);
        addOutput(RTDEOutput.output_int_register_4);
        addOutput(RTDEOutput.output_int_register_5);
        addOutput(RTDEOutput.output_int_register_6);
        addOutput(RTDEOutput.output_int_register_7);
        addOutput(RTDEOutput.output_int_register_8);
        addOutput(RTDEOutput.output_int_register_9);
        addOutput(RTDEOutput.output_int_register_10);
        addOutput(RTDEOutput.output_int_register_11);
        addOutput(RTDEOutput.output_int_register_12);
        addOutput(RTDEOutput.output_int_register_13);
        addOutput(RTDEOutput.output_int_register_14);
        addOutput(RTDEOutput.output_int_register_15);
        addOutput(RTDEOutput.output_int_register_16);
        addOutput(RTDEOutput.output_int_register_17);
        addOutput(RTDEOutput.output_int_register_18);
        addOutput(RTDEOutput.output_int_register_19);
        addOutput(RTDEOutput.output_int_register_20);
        addOutput(RTDEOutput.output_int_register_21);
        addOutput(RTDEOutput.output_int_register_22);
        addOutput(RTDEOutput.output_int_register_23);
    }

    public abstract void onGetValues(int[] input_int_registers, int[] output_int_registers);

    @Override
    public void onReceive(Object[] values) throws IOException {

        int[] input_int_registers = new int[24];
        int[] output_int_registers = new int[24];

        for (int i = 0; i < 24; i++) {
            input_int_registers[i] = (Integer) values[i];
        }

        for (int i = 0; i < 24; i++) {
            output_int_registers[i] = (Integer) values[24 + i];
        }

        onGetValues(input_int_registers, output_int_registers);

    }

    @Override
    public Object[] onSend() throws IOException {

        return null;
    }

}