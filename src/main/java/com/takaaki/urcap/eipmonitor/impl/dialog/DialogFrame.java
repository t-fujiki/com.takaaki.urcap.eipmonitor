package com.takaaki.urcap.eipmonitor.impl.dialog;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * ダイアログボックスの基本フレーム
 */
public abstract class DialogFrame extends JFrame {

    protected int response;

    protected boolean closed = false;

    public int DIALOG_OK = 1;
    public int DIALOG_CANCEL = 0;
    public int DIALOG_ERROR = -1;

    private static final long serialVersionUID = 1;

    /**
     * コンストラクタ
     */
    public DialogFrame() {
    }

    /**
     * 縦方向のスペースを生成
     * 
     * @param height サイズ<pixel>
     * @return
     */
    protected Component setVerticalSpace(int height) {
        return Box.createRigidArea(new Dimension(0, height));

    }

    /**
     * 横方向のスペースを生成
     * 
     * @param height サイズ<pixel>
     * @return
     */
    protected Component setHorizonalSpace(int width) {
        return Box.createRigidArea(new Dimension(width, 0));

    }

    /**
     * ボタンコンポーネントを生成
     * 
     * @param text      テキスト
     * @param font      フォント
     * @param width     幅サイズ<pixel>
     * @param height    縦サイス<pixel>
     * @param alignment 文字配置方向
     * @return コンポーネント
     */
    protected JButton createButton(String text, Font font, int width, int height, int alignment) {
        JButton button = new JButton(text);

        setCompSize(button, width, height);
        button.setFont(font);
        button.setHorizontalAlignment(alignment);

        return button;
    }

    /**
     * ラベルコンポーネントを生成
     * 
     * @param text       テキスト
     * @param font       フォント
     * @param background 背景色
     * @param foreground 前景色
     * @param width      幅サイズ<pixel>
     * @param height     縦サイス<pixel>
     * @param alignment  文字配置方向
     * @return コンポーネント
     */
    protected JLabel createLabel(String text, Font font, Color background, Color foreground, int width, int height,
            int alignment) {
        JLabel label = new JLabel(text);

        setCompSize(label, width, height);
        label.setFont(font);
        label.setBackground(background == null ? getBackground() : background);
        label.setBackground(foreground == null ? Color.BLACK : foreground);
        label.setHorizontalAlignment(alignment);

        return label;
    }

    /**
     * 
     * @param font       フォント
     * @param foreground 前景色
     * @param width      幅サイズ<pixel>
     * @param height     縦サイス<pixel>
     * @param alignment  文字配置方向
     * @return
     */
    protected JTextField createTextField(Font font, Color foreground, int width, int height, int alignment) {
        JTextField textField = new JTextField();

        setCompSize(textField, width, height);
        textField.setFont(font);
        textField.setForeground(foreground == null ? Color.BLACK : foreground);
        textField.setHorizontalAlignment(alignment);

        return textField;
    }

    /**
     * コンポーネントのサイズ指定
     * 
     * @param comp   コンポーネント
     * @param width  幅サイズ<pixel>
     * @param height 縦サイス<pixel>
     */
    protected void setCompSize(Component comp, int width, int height) {

        comp.setPreferredSize(new Dimension(width, height));
        comp.setMinimumSize(new Dimension(width, height));
        comp.setMaximumSize(new Dimension(width, height));
    }

    /**
     * イベントハンドラの親フレームを取得
     * 
     * @param e イベントオブジェクト
     * @return フレームオブジェクト
     */

    protected JFrame getFrame(MouseEvent e) {
        Component comp = (Component) e.getComponent();
        return (JFrame) SwingUtilities.getWindowAncestor(comp);
    }

    /**
     * イベントハンドラの親フレームを取得
     * 
     * @param e イベントオブジェクト
     * @return フレームオブジェクト
     */

    protected JFrame getFrame(WindowEvent e) {
        Component comp = (Component) e.getComponent();
        return (JFrame) SwingUtilities.getWindowAncestor(comp);
    }

    /**
     * 水平方向のBoxオブジェクト配列を生成
     * 
     * @param count 数
     * @return ボックスオブジェクト
     */
    protected Box[] createHorizontalBoxArray(int count) {
        ArrayList<Box> box_list = new ArrayList<Box>();

        for (int i = 0; i < count; i++) {
            box_list.add(Box.createHorizontalBox());
        }

        return box_list.toArray(new Box[] {});
    }

}
