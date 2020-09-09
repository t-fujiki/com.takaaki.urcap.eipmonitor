package com.takaaki.urcap.eipmonitor.impl.component;

import java.util.ArrayList;

import javax.swing.JRadioButton;

/***
 * 
 * インデックス付きボタン
 * 
 * @author T.FUJIKI
 *
 */
public class IdxRadioButton extends JRadioButton {

    private static final long serialVersionUID = 0;

    // インデックス番号
    private int index;

    public IdxRadioButton() {
        super();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static IdxRadioButton[] createRadioButtonArray(int count) {
        ArrayList<IdxRadioButton> comp_list = new ArrayList<IdxRadioButton>();

        for (int i = 0; i < count; i++) {
            comp_list.add(new IdxRadioButton());
        }

        return comp_list.toArray(new IdxRadioButton[] {});
    }

}