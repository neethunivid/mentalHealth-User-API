package com.mentalhealthapi.dto;

import java.util.Date;

public class MemberWithdrawExportDTO {
	    private boolean withdrawList=false;
	    private Date fromDate;
	    private Date toDate;
		public boolean isWithdrawList() {
			return withdrawList;
		}
		public void setWithdrawList(boolean withdrawList) {
			this.withdrawList = withdrawList;
		}
		public Date getFromDate() {
			return fromDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		public Date getToDate() {
			return toDate;
		}
		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}
		

	 

}
