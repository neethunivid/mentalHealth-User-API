package com.mentalhealthapi.dto;

import java.util.List;

import com.mentalhealthapi.model.DiagnoseCheck;

public class ArchieveRecordResponse {
		
	    public ArchieveRecordResponse() {
	    	
	    }
		private List<String> finYear;
		private List<DiagnoseCheck> diagnoseCheck;
		public List<String> getFinYear() {
			return finYear;
		}
		public void setFinYear(List<String> finYear) {
			this.finYear = finYear;
		}
		public List<DiagnoseCheck> getDiagnoseCheck() {
			return diagnoseCheck;
		}
		public void setDiagnoseCheck(List<DiagnoseCheck> diagnoseCheck) {
			this.diagnoseCheck = diagnoseCheck;
		}

	
}
