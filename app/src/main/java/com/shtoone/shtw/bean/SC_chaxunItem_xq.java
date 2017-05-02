package com.shtoone.shtw.bean;

import java.io.Serializable;
import java.util.List;

//生产数据查询详情页 数据实体
public class SC_chaxunItem_xq implements Serializable {

	private static final long serialVersionUID = -5945977122810369746L;

	private String banhezhanmingchen; // 拌合站名称
	private String chuliaoshijian;// 出料时间
	private String shijiyoushibi; // 实际油石比
	private String lilunyoushibi; // 理论油石比
	private String youshibiwucha; // 油石比误差
	private String liqingwendu;// 沥青温度
	private String shiliaowend;// 石料温度
	private String chuliaowendu;// 出料温度
	private String username;
	private String reason;
	private String type;
	private String result;
	private String time;
	private List<SC_chaxunItem_xq_data> lists; // #仓等数据

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBanhezhanmingchen() {
		return banhezhanmingchen;
	}

	public void setBanhezhanmingchen(String banhezhanmingchen) {
		this.banhezhanmingchen = banhezhanmingchen;
	}

	public String getChuliaoshijian() {
		return chuliaoshijian;
	}

	public void setChuliaoshijian(String chuliaoshijian) {
		this.chuliaoshijian = chuliaoshijian;
	}

	public String getShijiyoushibi() {
		return shijiyoushibi;
	}

	public void setShijiyoushibi(String shijiyoushibi) {
		this.shijiyoushibi = shijiyoushibi;
	}

	public String getLilunyoushibi() {
		return lilunyoushibi;
	}

	public void setLilunyoushibi(String lilunyoushibi) {
		this.lilunyoushibi = lilunyoushibi;
	}

	public String getYoushibiwucha() {
		return youshibiwucha;
	}

	public void setYoushibiwucha(String youshibiwucha) {
		this.youshibiwucha = youshibiwucha;
	}

	public String getLiqingwendu() {
		return liqingwendu;
	}

	public void setLiqingwendu(String liqingwendu) {
		this.liqingwendu = liqingwendu;
	}

	public String getShiliaowend() {
		return shiliaowend;
	}

	public void setShiliaowend(String shiliaowend) {
		this.shiliaowend = shiliaowend;
	}

	public String getChuliaowendu() {
		return chuliaowendu;
	}

	public void setChuliaowendu(String chuliaowendu) {
		this.chuliaowendu = chuliaowendu;
	}

	public List<SC_chaxunItem_xq_data> getLists() {
		return lists;
	}

	public void setLists(List<SC_chaxunItem_xq_data> lists) {
		this.lists = lists;
	}

	@Override
	public String toString() {
		return "SC_chaxunItem_xq [chuliaoshijian=" + chuliaoshijian + ", shijiyoushibi=" + shijiyoushibi
				+ ", lilunyoushibi=" + lilunyoushibi + ", youshibiwucha=" + youshibiwucha + ", liqingwendu="
				+ liqingwendu + ", shiliaowend=" + shiliaowend + ", chuliaowendu=" + chuliaowendu + ", lists=" + lists
				+ "]";
	}

}
