package com.optum.data.pipeline.pojo;

public class Referral {

	public enum Headers {
		HICN, MBI, SUBSCRIBER_ID, ICUE_MEMBER_ID, POLICY_NUMBER, FIRST_NAME, LAST_NAME, MIDDLE_NAME, DOB, PROGRAM_ID,
		PROGRAM_NAME, HCONTRACT;
	};

	public String HICN;
	public String MBI;
	public String Subscriber_ID;
	public Integer ICUE_Member_ID;
	public String Policy_Number;
	public String First_Name;
	public String Last_Name;
	public String Middle_Name;
	public String DOB;
	public String Program_ID;
	public String Program_Name;
	public String HCONTRACT;
	public String getHICN() {
		return HICN;
	}
	public void setHICN(String hICN) {
		HICN = hICN;			
	}
	public String getMBI() {
		return MBI;
	}
	public void setMBI(String mBI) {
		MBI = mBI;
	}
	public String getSubscriber_ID() {
		return Subscriber_ID;
	}
	public void setSubscriber_ID(String subscriber_ID) {
		Subscriber_ID = subscriber_ID;
	}
	public Integer getICUE_Member_ID() {
		return ICUE_Member_ID;
	}
	public void setICUE_Member_ID(Integer iCUE_Member_ID) {
		ICUE_Member_ID = iCUE_Member_ID;
	}
	public String getPolicy_Number() {
		return Policy_Number;
	}
	public void setPolicy_Number(String policy_Number) {
		Policy_Number = policy_Number;
	}
	public String getFirst_Name() {
		return First_Name;
	}
	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}
	public String getLast_Name() {
		return Last_Name;
	}
	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}
	public String getMiddle_Name() {
		return Middle_Name;
	}
	public void setMiddle_Name(String middle_Name) {
		Middle_Name = middle_Name;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getProgram_ID() {
		return Program_ID;
	}
	public void setProgram_ID(String program_ID) {
		Program_ID = program_ID;
	}
	public String getProgram_Name() {
		return Program_Name;
	}
	public void setProgram_Name(String program_Name) {
		Program_Name = program_Name;
	}
	public String getHCONTRACT() {
		return HCONTRACT;
	}
	public void setHCONTRACT(String hCONTRACT) {
		HCONTRACT = hCONTRACT;
	}
	@Override
	public String toString() {
		return "Referral [HICN=" + HICN + ", MBI=" + MBI + ", Subscriber_ID=" + Subscriber_ID + ", ICUE_Member_ID="
				+ ICUE_Member_ID + ", Policy_Number=" + Policy_Number + ", First_Name=" + First_Name + ", Last_Name="
				+ Last_Name + ", Middle_Name=" + Middle_Name + ", DOB=" + DOB + ", Program_ID=" + Program_ID
				+ ", Program_Name=" + Program_Name + ", HCONTRACT=" + HCONTRACT + "]";
	}

	
}
