package com.takaaki.urcap.eipmonitor.impl.rtde;

import java.io.IOException;

public abstract class RTDEClientOfBitRegister extends RTDEClient {

    public RTDEClientOfBitRegister(int frequency) {
        super("127.0.0.1", frequency, "Bits");

        addOutput(RTDEOutput.input_bit_registers0_to_31);
        addOutput(RTDEOutput.input_bit_registers32_to_63);
        addOutput(RTDEOutput.output_bit_registers0_to_31);
        addOutput(RTDEOutput.output_bit_registers32_to_63);

    }

    public abstract void onGetValues(int input_bit_registers0_to_31, int input_bit_registers32_to_63,
            int output_bit_registers0_to_31, int output_bit_registers32_to_63);

    @Override
    public void onReceive(Object[] values) throws IOException {
        int input_bit_registers0_to_31 = (Integer) values[0];
        int input_bit_registers32_to_63 = (Integer) values[1];
        int output_bit_registers0_to_31 = (Integer) values[2];
        int output_bit_registers32_to_63 = (Integer) values[3];

        onGetValues(input_bit_registers0_to_31, input_bit_registers32_to_63, output_bit_registers0_to_31,
                output_bit_registers32_to_63);

    }

    @Override
    public Object[] onSend() throws IOException {

        return null;
    }

}