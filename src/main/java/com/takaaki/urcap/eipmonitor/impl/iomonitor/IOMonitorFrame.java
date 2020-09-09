package com.takaaki.urcap.eipmonitor.impl.iomonitor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.takaaki.urcap.eipmonitor.impl.EthernetIPMonitorInstallationNodeContribution;
import com.takaaki.urcap.eipmonitor.impl.component.*;
import com.takaaki.urcap.eipmonitor.impl.converter.TypeConverter;
import com.takaaki.urcap.eipmonitor.impl.dialog.*;
import com.takaaki.urcap.eipmonitor.impl.realtime.RealTimeClient;
import com.takaaki.urcap.eipmonitor.impl.rtde.*;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.io.BooleanRegister;
import com.ur.urcap.api.domain.io.FloatRegister;
import com.ur.urcap.api.domain.io.IntegerRegister;

/**
 * Ehternet/IPのIO状態のモニタ用フレーム
 */
public class IOMonitorFrame extends DialogFrame {

    RTDEClientOfBitRegister rtdeClientOfBitRegister;
    RTDEClientOfIntegerRegister rtdeClientOfIntegerRegister;
    RTDEClientOfDoubleRegister rtdeClientOfDoubleRegister;

    private JPanel panel;
    private JLabel label_title;

    private static final long serialVersionUID = 1L;

    private JButton btn_hide;
    private IdxLabel[] label_inputTitle;
    private IdxLabel[] label_outputTitle;
    private IdxLabel[] label_inputValue;
    private IdxLabel[] label_outputValue;
    private IdxRadioButton[] radio_mode;

    private ButtonGroup group_radio;

    private Box box_title;
    private Box box_radios;
    private Box[] box_iovalue;

    private final String TITLE = "Ethernet/IP: I/O Monitor";
    private final String BTN_HIDE = "Close";

    private final int FRAME_WIDTH = 660;
    private final int FRAME_HEIGHT = 530;

    private final int TITLE_WIDTH = 480;
    private final int TITLE_HEIGHT = 30;
    private final int TITLEIO_WIDTH = 100;
    private final int TITLEIO_HEIGHT = 14;
    private final int VALUEIO_WIDTH = 35;
    private final int VALUEIO_HEIGHT = 14;
    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 30;
    private final int LEFT_PADDING = 10;

    private final int MODE_BITS = 0;
    private final int MODE_INTEGERS = 1;
    private final int MODE_FLOATS = 2;

    private final int FREQ_BITS = 50;
    private final int FREQ_INTEGERS = 50;
    private final int FREQ_FLOATS = 50;

    private final Color BACKCOLOR = new Color(0, 100, 25);
    private final Color DEFAULT_FORECOLOR = new Color(255, 255, 255);
    private final Color ON_COLOR = Color.YELLOW;
    private final Color NOUSE_COLOR = Color.GRAY;

    private Font TITLE_FONT = new Font("Lucida Sans", Font.BOLD, 18);
    private Font BUTTON_FONT = new Font("Lucida Sans", Font.BOLD, 15);
    private Font DEFAULT_FONT = new Font("Lucida Sans", Font.BOLD, 10);
    private Font RADIO_FONT = new Font("Lucida Sans", Font.BOLD, 15);

    private int prev_input_0to31 = 0;
    private int prev_input_32to63 = 0;
    private int prev_output_0to31 = 0;
    private int prev_output_32to63 = 0;

    private boolean[] isDefinedInputBits = new boolean[64];
    private boolean[] isDefinedOutputBits = new boolean[64];

    private boolean[] isDefinedInputIntegers = new boolean[64];
    private boolean[] isDefinedOutputIntegers = new boolean[64];

    private boolean[] isDefinedInputFloats = new boolean[64];
    private boolean[] isDefinedOutputFloats = new boolean[64];

    private EthernetIPMonitorInstallationNodeContribution contribution;

    /**
     * コンストラクタ
     * 
     * @param contribution インストールノードの属性オブジェクト
     */
    public IOMonitorFrame(EthernetIPMonitorInstallationNodeContribution contribution) {
        super();

        this.contribution = contribution;

        createForm();
        setEventHandlers();

    }

    /**
     * フォームの形成
     */
    private void createForm() {
        /* 各オブジェクトの初期化 */

        panel = new JPanel();
        label_title = new JLabel(TITLE);
        btn_hide = new JButton(BTN_HIDE);
        radio_mode = createRadioButtonArray(3);
        label_inputTitle = createLabelArray(64);
        label_outputTitle = createLabelArray(64);
        label_inputValue = createLabelArray(64);
        label_outputValue = createLabelArray(64);
        box_title = Box.createHorizontalBox();
        box_radios = Box.createHorizontalBox();
        box_iovalue = createHorizontalBoxArray(32);

        /* フォームの形成 */

        this.setAlwaysOnTop(true);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setFont(DEFAULT_FONT);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(Color.BLACK, 2));
        panel.setBackground(BACKCOLOR);

        /* コンポーネントの形成 */
        setFixedSize(label_title, TITLE_WIDTH, TITLE_HEIGHT);

        label_title.setText(TITLE);
        label_title.setFont(TITLE_FONT);
        label_title.setForeground(DEFAULT_FORECOLOR);
        label_title.setHorizontalAlignment(JLabel.LEFT);

        group_radio = new ButtonGroup();

        radio_mode[MODE_BITS].setText("Bits");
        radio_mode[MODE_INTEGERS].setText("Integers");
        radio_mode[MODE_FLOATS].setText("Doubles");
        for (int i = 0; i < 3; i++) {
            radio_mode[i].setIndex(i);
            radio_mode[i].setFont(RADIO_FONT);
            radio_mode[i].setForeground(DEFAULT_FORECOLOR);
            radio_mode[i].setBackground(BACKCOLOR);
            radio_mode[i].setHorizontalAlignment(JLabel.LEFT);

            group_radio.add(radio_mode[i]);
        }

        for (int i = 0; i < 64; i++) {

            setFixedSize(label_inputTitle[i], TITLEIO_WIDTH, TITLEIO_HEIGHT);
            label_inputTitle[i].setIndex(i);
            label_inputTitle[i].setFont(DEFAULT_FONT);
            label_inputTitle[i].setForeground(DEFAULT_FORECOLOR);
            label_inputTitle[i].setHorizontalAlignment(JLabel.LEFT);
            label_inputTitle[i].setVisible(false);

            setFixedSize(label_outputTitle[i], TITLEIO_WIDTH, TITLEIO_HEIGHT);
            label_outputTitle[i].setIndex(i);
            label_outputTitle[i].setFont(DEFAULT_FONT);
            label_outputTitle[i].setForeground(DEFAULT_FORECOLOR);
            label_outputTitle[i].setHorizontalAlignment(JLabel.LEFT);
            label_outputTitle[i].setVisible(false);

            setFixedSize(label_inputValue[i], VALUEIO_WIDTH, VALUEIO_HEIGHT);
            label_inputValue[i].setIndex(i);
            label_inputValue[i].setFont(DEFAULT_FONT);
            label_inputValue[i].setForeground(DEFAULT_FORECOLOR);
            label_inputValue[i].setHorizontalAlignment(JLabel.LEFT);
            label_inputValue[i].setVisible(false);

            setFixedSize(label_outputValue[i], VALUEIO_WIDTH, VALUEIO_HEIGHT);
            label_outputValue[i].setIndex(i);
            label_outputValue[i].setFont(DEFAULT_FONT);
            label_outputValue[i].setForeground(DEFAULT_FORECOLOR);
            label_outputValue[i].setHorizontalAlignment(JLabel.LEFT);
            label_outputValue[i].setVisible(false);

        }

        btn_hide.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        btn_hide.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        btn_hide.setFont(BUTTON_FONT);

        /* ボックスへの配置 */
        box_title.setAlignmentX(Component.LEFT_ALIGNMENT);
        box_title.add(setHorizonalSpace(LEFT_PADDING));
        box_title.add(label_title);
        box_title.add(btn_hide);

        box_radios.setAlignmentX(Component.LEFT_ALIGNMENT);
        box_radios.add(setHorizonalSpace(LEFT_PADDING));
        box_radios.add(radio_mode[MODE_BITS]);
        box_radios.add(setHorizonalSpace(LEFT_PADDING));
        box_radios.add(radio_mode[MODE_INTEGERS]);
        box_radios.add(setHorizonalSpace(LEFT_PADDING));
        box_radios.add(radio_mode[MODE_FLOATS]);

        for (int i = 0; i < 32; i++) {
            box_iovalue[i].setAlignmentX(Component.LEFT_ALIGNMENT);

            box_iovalue[i].add(setHorizonalSpace(10));
            box_iovalue[i].add(label_inputTitle[i]);
            box_iovalue[i].add(label_inputValue[i]);
            box_iovalue[i].add(setHorizonalSpace(30));
            box_iovalue[i].add(label_inputTitle[32 + i]);
            box_iovalue[i].add(label_inputValue[32 + i]);
            box_iovalue[i].add(setHorizonalSpace(30));
            box_iovalue[i].add(label_outputTitle[i]);
            box_iovalue[i].add(label_outputValue[i]);
            box_iovalue[i].add(setHorizonalSpace(30));
            box_iovalue[i].add(label_outputTitle[32 + i]);
            box_iovalue[i].add(label_outputValue[32 + i]);
        }

        /* フォームへの配置 */
        panel.add(setVerticalSpace(5));
        panel.add(box_title);
        panel.add(setVerticalSpace(5));
        panel.add(box_radios);

        panel.add(setVerticalSpace(5));

        for (int i = 0; i < 32; i++) {
            panel.add(box_iovalue[i]);
        }

        this.add(panel);

    }

    /**
     * 各イベントハンドラの定義
     */
    private void setEventHandlers() {

        // イベントハンドラ
        btn_hide.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (!((JButton) mouseEvent.getSource()).isEnabled())
                    return;
                disposeAllRTDEThreads();
                clearFormTexts();
                getFrame(mouseEvent).setVisible(false);

            }

        });

        // ラジオボタンのイベントハンドラ
        for (int i = 0; i < 3; i++) {
            radio_mode[i].addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    IdxRadioButton radioBtn = (IdxRadioButton) e.getSource();
                    int mode = radioBtn.getIndex();

                    if (!radioBtn.isSelected())
                        return;

                    if (mode == MODE_BITS && rtdeClientOfBitRegister != null)
                        return;
                    if (mode == MODE_INTEGERS && rtdeClientOfIntegerRegister != null)
                        return;
                    if (mode == MODE_FLOATS && rtdeClientOfDoubleRegister != null)
                        return;

                    disposeAllRTDEThreads();

                    startRTDEClient(mode);
                }
            });
        }

        // Bitモードのみラベルのイベントハンドラを実行
        for (int i = 0; i < 64; i++) {
            label_outputTitle[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                    int index = ((IdxLabel) mouseEvent.getSource()).getIndex();

                    int mode = 0;
                    for (int i = 0; i < 3; i++) {
                        if (radio_mode[i].isSelected()) {
                            mode = i;
                            break;
                        }
                    }

                    if ((mode == MODE_BITS) && (rtdeClientOfBitRegister != null)) {
                        RealTimeClient realTimeClient = new RealTimeClient("127.0.0.1");

                        if (label_outputValue[index].getText().equals("ON"))
                            realTimeClient.setBitToRegister(index, false);
                        else
                            realTimeClient.setBitToRegister(index, true);
                    }
                }
            });
        }

    }

    /**
     * RadioButtonの配列を生成
     * 
     * @param count 数
     * @return オブジェクト配列
     */
    private IdxRadioButton[] createRadioButtonArray(int count) {
        ArrayList<IdxRadioButton> comp_list = new ArrayList<IdxRadioButton>();

        for (int i = 0; i < count; i++) {
            comp_list.add(new IdxRadioButton());
        }

        return comp_list.toArray(new IdxRadioButton[] {});
    }

    /**
     * JLabelのオブジェクト配列を生成
     * 
     * @param count 数
     * @return オブジェクト配列
     */
    private IdxLabel[] createLabelArray(int count) {
        ArrayList<IdxLabel> comp_list = new ArrayList<IdxLabel>();

        for (int i = 0; i < count; i++) {
            comp_list.add(new IdxLabel());
        }

        return comp_list.toArray(new IdxLabel[] {});
    }

    /**
     * 0～31番のGP_Inputのビットを表示する。
     * 
     * @param valueIO ビット格納した数値
     * @param reload  フレーム表示の更新の有無
     */
    public void setInputValueLower(int valueIO, boolean reload) {

        if (reload) {
            boolean[] newValues = TypeConverter.intToBits(valueIO);
            boolean[] prevValues = TypeConverter.intToBits(prev_input_0to31);

            for (int i = 0; i < 32; i++) {
                if (newValues[i] != prevValues[i]) {

                    Color forecolor = isDefinedInputBits[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                    label_inputTitle[i].setForeground(newValues[i] ? ON_COLOR : forecolor);

                    label_inputValue[i].setText(newValues[i] ? "ON" : "-");
                    label_inputValue[i].setForeground(newValues[i] ? ON_COLOR : forecolor);
                }
            }
        } else {
            boolean[] values = TypeConverter.intToBits(valueIO);

            for (int i = 0; i < 32; i++) {
                Color forecolor = isDefinedInputBits[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                label_inputTitle[i].setForeground(values[i] ? ON_COLOR : forecolor);

                label_inputValue[i].setText(values[i] ? "ON" : "-");
                label_inputValue[i].setForeground(values[i] ? ON_COLOR : forecolor);
            }
        }

        prev_input_0to31 = valueIO;
    }

    /**
     * 32～63番のGP_Inputのビットを表示する。
     * 
     * @param valueIO ビット格納した数値
     * @param reload  フレーム表示の更新の有無
     */
    public void setInputValueUpper(int valueIO, boolean reload) {

        if (reload) {
            boolean[] newValues = TypeConverter.intToBits(valueIO);
            boolean[] prevValues = TypeConverter.intToBits(prev_input_32to63);

            for (int i = 0; i < 32; i++) {
                if (newValues[i] != prevValues[i]) {
                    Color forecolor = isDefinedInputBits[i + 32] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                    label_inputTitle[i + 32].setForeground(newValues[i] ? ON_COLOR : forecolor);

                    label_inputValue[i + 32].setText(newValues[i] ? "ON" : "-");
                    label_inputValue[i + 32].setForeground(newValues[i] ? ON_COLOR : forecolor);
                }
            }
        } else {

            boolean[] values = TypeConverter.intToBits(valueIO);

            for (int i = 0; i < 32; i++) {
                Color forecolor = isDefinedInputBits[i + 32] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                label_inputTitle[i + 32].setForeground(values[i] ? ON_COLOR : forecolor);

                label_inputValue[i + 32].setText(values[i] ? "ON" : "-");
                label_inputValue[i + 32].setForeground(values[i] ? ON_COLOR : forecolor);
            }
        }

        prev_input_32to63 = valueIO;
    }

    /**
     * 0～31番のGP_Outputのビットを表示する。
     * 
     * @param valueIO ビット格納した数値
     * @param reload  フレーム表示の更新の有無
     */
    public void setOutputValueLower(int valueIO, boolean reload) {

        if (reload) {
            boolean[] newValues = TypeConverter.intToBits(valueIO);
            boolean[] prevValues = TypeConverter.intToBits(prev_output_0to31);

            for (int i = 0; i < 32; i++) {
                if (newValues[i] != prevValues[i]) {
                    Color forecolor = isDefinedOutputBits[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                    label_outputTitle[i].setForeground(newValues[i] ? ON_COLOR : forecolor);

                    label_outputValue[i].setText(newValues[i] ? "ON" : "-");
                    label_outputValue[i].setForeground(newValues[i] ? ON_COLOR : forecolor);
                }
            }
        } else {
            boolean[] values = TypeConverter.intToBits(valueIO);

            for (int i = 0; i < 32; i++) {
                Color forecolor = isDefinedOutputBits[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                label_outputTitle[i].setForeground(values[i] ? ON_COLOR : forecolor);

                label_outputValue[i].setText(values[i] ? "ON" : "-");
                label_outputValue[i].setForeground(values[i] ? ON_COLOR : forecolor);
            }
        }

        prev_output_0to31 = valueIO;
    }

    /**
     * 32～63番のGP_Outputのビットを表示する。
     * 
     * @param valueIO ビット格納した数値
     * @param reload  フレーム表示の更新の有無
     */
    public void setOutputValueUpper(int valueIO, boolean reload) {
        if (reload) {
            boolean[] newValues = TypeConverter.intToBits(valueIO);
            boolean[] prevValues = TypeConverter.intToBits(prev_output_32to63);

            for (int i = 0; i < 32; i++) {
                if (newValues[i] != prevValues[i]) {
                    Color forecolor = isDefinedOutputBits[i + 32] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                    label_outputTitle[i + 32].setForeground(newValues[i] ? ON_COLOR : forecolor);

                    label_outputValue[i + 32].setText(newValues[i] ? "ON" : "-");
                    label_outputValue[i + 32].setForeground(newValues[i] ? ON_COLOR : forecolor);
                }
            }
        } else {

            boolean[] values = TypeConverter.intToBits(valueIO);

            for (int i = 0; i < 32; i++) {
                Color forecolor = isDefinedOutputBits[i + 32] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                label_outputTitle[i + 32].setForeground(values[i] ? ON_COLOR : forecolor);

                label_outputValue[i + 32].setText(values[i] ? "ON" : "-");
                label_outputValue[i + 32].setForeground(values[i] ? ON_COLOR : forecolor);
            }
        }

        prev_output_32to63 = valueIO;

    }

    /**
     * コンポーネントのサイズを設定
     * 
     * @param comp   コンポーネント
     * @param width  幅
     * @param height 高さ
     */
    private void setFixedSize(Component comp, int width, int height) {

        comp.setPreferredSize(new Dimension(width, height));
        comp.setMinimumSize(new Dimension(width, height));
        comp.setMaximumSize(new Dimension(width, height));
    }

    /**
     * I/Oのラベルに名称を反映。 名称が設定されていないときはデフォルト名称を表示する。
     */
    public void setTextOfIOLabels(int mode) {

        URCapAPI api = contribution.api;

        // すべてのラベルを非表示
        for (int i = 0; i < 64; i++) {
            label_inputTitle[i].setVisible(false);
            label_inputValue[i].setVisible(false);
            label_outputTitle[i].setVisible(false);
            label_outputValue[i].setVisible(false);
        }
        for (int i = 0; i < 64; i++) {
            label_inputTitle[i].setText("");
            label_inputValue[i].setText("");
            label_outputTitle[i].setText("");
            label_outputValue[i].setText("");

        }

        switch (mode) {
            case MODE_BITS:

                for (int i = 0; i < 64; i++) {
                    label_inputTitle[i].setText("Input" + String.valueOf(i));
                    label_outputTitle[i].setText("Output" + String.valueOf(i));

                    isDefinedInputBits[i] = false;
                    isDefinedOutputBits[i] = false;
                }

                // IOの名称を反映させる。
                Collection<BooleanRegister> bitIOs = api.getInstallationAPIProvider().getInstallationAPI().getIOModel()
                        .getIOs(BooleanRegister.class);

                for (BooleanRegister io : bitIOs) {
                    String defname = io.getDefaultName();
                    String ioname = io.getName();

                    if (defname.indexOf("GP_bool_in") != -1) {
                        int index = Integer.valueOf(defname.replace("GP_bool_in[", "").replace("]", ""));
                        isDefinedInputBits[index] = true;

                        label_inputTitle[index].setText(ioname);

                    } else if (defname.indexOf("GP_bool_out") != -1) {
                        int index = Integer.valueOf(defname.replace("GP_bool_out[", "").replace("]", ""));
                        isDefinedOutputBits[index] = true;

                        label_outputTitle[index].setText(ioname);

                    }

                }

                break;

            case MODE_INTEGERS:
                for (int i = 0; i < 24; i++) {
                    label_inputTitle[i].setText("In_Int" + String.valueOf(i));
                    label_outputTitle[i].setText("Out_Int" + String.valueOf(i));

                    isDefinedInputIntegers[i] = false;
                    isDefinedOutputIntegers[i] = false;

                }

                // IOの名称を反映させる。
                Collection<IntegerRegister> intIOs = api.getInstallationAPIProvider().getInstallationAPI().getIOModel()
                        .getIOs(IntegerRegister.class);

                for (IntegerRegister io : intIOs) {
                    String defname = io.getDefaultName();
                    String ioname = io.getName();

                    if (defname.indexOf("GP_int_in") != -1) {
                        int index = Integer.valueOf(defname.replace("GP_int_in[", "").replace("]", ""));
                        isDefinedInputIntegers[index] = true;

                        label_inputTitle[index].setText(ioname);

                    } else if (defname.indexOf("GP_int_out") != -1) {
                        int index = Integer.valueOf(defname.replace("GP_int_out[", "").replace("]", ""));
                        isDefinedOutputIntegers[index] = true;

                        label_outputTitle[index].setText(ioname);

                    }

                }

                break;

            case MODE_FLOATS:
                for (int i = 0; i < 24; i++) {
                    label_inputTitle[i].setText("In_Float" + String.valueOf(i));
                    label_outputTitle[i].setText("Out_Float" + String.valueOf(i));

                    isDefinedInputFloats[i] = false;
                    isDefinedOutputFloats[i] = false;
                }
                // IOの名称を反映させる。
                Collection<FloatRegister> floatIOs = api.getInstallationAPIProvider().getInstallationAPI().getIOModel()
                        .getIOs(FloatRegister.class);

                for (FloatRegister io : floatIOs) {
                    String defname = io.getDefaultName();
                    String ioname = io.getName();

                    if (defname.indexOf("GP_float_in") != -1) {
                        int index = Integer.valueOf(defname.replace("GP_float_in[", "").replace("]", ""));
                        isDefinedInputFloats[index] = true;

                        label_inputTitle[index].setText(ioname);

                    } else if (defname.indexOf("GP_float_out") != -1) {
                        int index = Integer.valueOf(defname.replace("GP_float_out[", "").replace("]", ""));
                        isDefinedOutputFloats[index] = true;

                        label_outputTitle[index].setText(ioname);

                    }

                }

                break;
            default:
                break;

        }

        // すべてのラベルを表示
        for (int i = 0; i < 64; i++) {
            label_inputTitle[i].setVisible(true);
            label_inputValue[i].setVisible(true);
            label_outputTitle[i].setVisible(true);
            label_outputValue[i].setVisible(true);
        }

    }

    private void clearFormTexts() {
        for (int i = 0; i < 3; i++) {
            radio_mode[i].setSelected(false);
        }

        // すべてのラベルを非表示
        for (int i = 0; i < 64; i++) {
            label_inputTitle[i].setVisible(false);
            label_inputValue[i].setVisible(false);
            label_outputTitle[i].setVisible(false);
            label_outputValue[i].setVisible(false);
        }

        for (int i = 0; i < 64; i++) {
            label_inputTitle[i].setText("");
            label_inputValue[i].setText("");
            label_outputTitle[i].setText("");
            label_outputValue[i].setText("");
        }
    }

    private void disposeAllRTDEThreads() {
        if (rtdeClientOfBitRegister != null) {
            rtdeClientOfBitRegister.interrupt();
            while (rtdeClientOfBitRegister.isAlive()) {
                Thread.yield();
            }
            rtdeClientOfBitRegister = null;
        }

        if (rtdeClientOfIntegerRegister != null) {
            rtdeClientOfIntegerRegister.interrupt();
            while (rtdeClientOfIntegerRegister.isAlive()) {
                Thread.yield();
            }
            rtdeClientOfIntegerRegister = null;
        }

        if (rtdeClientOfDoubleRegister != null) {
            rtdeClientOfDoubleRegister.interrupt();
            while (rtdeClientOfDoubleRegister.isAlive()) {
                Thread.yield();
            }
            rtdeClientOfDoubleRegister = null;
        }

    }

    private void startRTDEClient(int mode) {

        switch (mode) {
            case MODE_BITS:
                rtdeClientOfBitRegister = new RTDEClientOfBitRegister(FREQ_BITS) {

                    @Override
                    public void onGetValues(final int input_bit_registers0_to_31, final int input_bit_registers32_to_63,
                            final int output_bit_registers0_to_31, final int output_bit_registers32_to_63) {

                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                setInputValueLower(input_bit_registers0_to_31, false);
                                setInputValueUpper(input_bit_registers32_to_63, false);

                                setOutputValueLower(output_bit_registers0_to_31, false);
                                setOutputValueUpper(output_bit_registers32_to_63, false);

                            }

                        });

                    }
                };
                setTextOfIOLabels(MODE_BITS);

                rtdeClientOfBitRegister.start();

                break;

            case MODE_INTEGERS:
                rtdeClientOfIntegerRegister = new RTDEClientOfIntegerRegister(FREQ_INTEGERS) {

                    @Override
                    public void onGetValues(final int[] input_int_registers, final int[] output_int_registers) {

                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                for (int i = 0; i < 24; i++) {

                                    Color forecolor = isDefinedInputIntegers[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                                    label_inputTitle[i]
                                            .setForeground(input_int_registers[i] == 0 ? forecolor : ON_COLOR);
                                    label_inputValue[i]
                                            .setForeground(input_int_registers[i] == 0 ? forecolor : ON_COLOR);

                                    forecolor = isDefinedOutputIntegers[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                                    label_outputTitle[i]
                                            .setForeground(output_int_registers[i] == 0 ? forecolor : ON_COLOR);
                                    label_outputValue[i]
                                            .setForeground(output_int_registers[i] == 0 ? forecolor : ON_COLOR);

                                    if (!label_inputValue[i].getText().equals(String.valueOf(input_int_registers[i])))
                                        label_inputValue[i].setText(String.valueOf(input_int_registers[i]));
                                    if (!label_outputValue[i].getText().equals(String.valueOf(output_int_registers[i])))
                                        label_outputValue[i].setText(String.valueOf(output_int_registers[i]));
                                }

                                for (int i = 24; i < 64; i++) {
                                    label_inputValue[i].setText("");
                                    label_outputValue[i].setText("");
                                }
                            }
                        });

                    }
                };
                setTextOfIOLabels(MODE_INTEGERS);

                rtdeClientOfIntegerRegister.start();

                break;

            case MODE_FLOATS:
                rtdeClientOfDoubleRegister = new RTDEClientOfDoubleRegister(FREQ_FLOATS) {

                    @Override
                    public void onGetValues(final double[] input_double_registers,
                            final double[] output_double_registers) {

                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                for (int i = 0; i < 24; i++) {

                                    Color forecolor = isDefinedInputFloats[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                                    label_inputTitle[i]
                                            .setForeground(input_double_registers[i] == 0 ? forecolor : ON_COLOR);
                                    label_inputValue[i]
                                            .setForeground(input_double_registers[i] == 0 ? forecolor : ON_COLOR);

                                    forecolor = isDefinedOutputFloats[i] ? DEFAULT_FORECOLOR : NOUSE_COLOR;

                                    label_outputTitle[i]
                                            .setForeground(output_double_registers[i] == 0 ? forecolor : ON_COLOR);
                                    label_outputValue[i]
                                            .setForeground(output_double_registers[i] == 0 ? forecolor : ON_COLOR);

                                    if (!label_inputValue[i].getText()
                                            .equals(String.valueOf(input_double_registers[i])))
                                        label_inputValue[i].setText(String.format("%.3f", input_double_registers[i]));
                                    if (!label_outputValue[i].getText()
                                            .equals(String.valueOf(output_double_registers[i])))
                                        label_outputValue[i].setText(String.format("%.3f", output_double_registers[i]));
                                }

                                for (int i = 24; i < 64; i++) {
                                    label_inputValue[i].setText("");
                                    label_outputValue[i].setText("");
                                }

                            }
                        });

                    }
                };
                setTextOfIOLabels(MODE_FLOATS);

                rtdeClientOfDoubleRegister.start();

                break;

            default:
                break;
        }

    }
}