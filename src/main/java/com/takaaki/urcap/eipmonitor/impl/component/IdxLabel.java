package com.takaaki.urcap.eipmonitor.impl.component;

import java.util.ArrayList;

import javax.swing.JLabel;

/***
 * 
 * インデックス付きラベル
 * 
 * @author T.FUJIKI
 *
 */
public class IdxLabel extends JLabel {

    private static final long serialVersionUID = 0;

    // インデックス番号
    private int index;

    public IdxLabel() {
        super();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;

    }

    public static IdxLabel[] createLabelArray(int count) {
        ArrayList<IdxLabel> comp_list = new ArrayList<IdxLabel>();

        for (int i = 0; i < count; i++) {
            comp_list.add(new IdxLabel());
        }

        return comp_list.toArray(new IdxLabel[] {});
    }

}