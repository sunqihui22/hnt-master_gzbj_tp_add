package com.shtoone.shtw.bean;

import java.io.Serializable;

public class SC_chaxunItem_xq_data implements Serializable {

	private static final long serialVersionUID = -4724187201308915221L;

	private String name; // 名称
	private String yongliang; // 用量
	private String shiji;// 实际
	private String peibi;// 配比
	private String wucha;// 误差
	private String cb;

	public String getCb() {
		return cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYongliang() {
		return yongliang;
	}

	public void setYongliang(String yongliang) {
		this.yongliang = yongliang;
	}

	public String getShiji() {
		return shiji;
	}

	public void setShiji(String shiji) {
		this.shiji = shiji;
	}

	public String getPeibi() {
		return peibi;
	}

	public void setPeibi(String peibi) {
		this.peibi = peibi;
	}

	public String getWucha() {
		return wucha;
	}

	public void setWucha(String wucha) {
		this.wucha = wucha;
	}

	@Override
	public String toString() {
		return "SC_chaxunItem_xq_data [name=" + name + ", yongliang=" + yongliang + ", shiji=" + shiji + ", peibi="
				+ peibi + ", wucha=" + wucha + "]";
	}

}
