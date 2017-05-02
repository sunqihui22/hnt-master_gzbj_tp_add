package com.shtoone.shtw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/2.
 */
public class PitchOverproofFragmentViewPagerFragmentListData implements Serializable{

    private int bianhao;
    private String shijian;
    private String shebeibianhao;
    private String chuli;
    private List<PitchOverproofFragmentViewPagerFragmentItemData> lists;

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getChuli() {
        return chuli;
    }

    public void setChuli(String chuli) {
        this.chuli = chuli;
    }

    public int getBianhao() {
        return bianhao;
    }

    public void setBianhao(int bianhao) {
        this.bianhao = bianhao;
    }

    public String getShebeibianhao() {
        return shebeibianhao;
    }

    public void setShebeibianhao(String shebeibianhao) {
        this.shebeibianhao = shebeibianhao;
    }

    public List<PitchOverproofFragmentViewPagerFragmentItemData> getLists() {
        return lists;
    }

    public void setLists(List<PitchOverproofFragmentViewPagerFragmentItemData> lists) {
        this.lists = lists;
    }


}
