package com.optum.data.pipeline.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import com.google.gson.Gson;

import com.optum.data.pipeline.Pipeline;
import com.optum.data.pipeline.pojo.Referral;
import com.optum.data.pipeline.pojo.Referral.Headers;
import com.optum.icue.kafka.KafkaWriter;

public class Navigate4MePipeline implements Pipeline {

	public void pipeline() {
		String sourceFilePath = "C:\\Users\\kbussire\\Desktop\\navigate4me\\Shikha\\ICUE-bulk-referrals-2019-05-12.txt";
		//String destFilePath = "C:\\Users\\kbussire\\Desktop\\navigate4me\\Shikha\\ICUE-bulk-referrals-2019-05-12_out.txt";
		String destFilePath = "C:\\Users\\kbussire\\Desktop\\navigate4me\\Shikha\\Target\\ICUE-bulk-referrals-2019-05-12_out.txt";
		pipelineSteps(sourceFilePath,destFilePath);
	}

	protected void pipelineSteps(String sourceFilePath, String destFilePath) {
		// file read
		List<Object> referrals = read(sourceFilePath, destFilePath);
		// apply the transformations
		
		// data writing to the external system
		dataWriting(referrals);
	}

	private void dataWriting(List<Object> referrals) {
		Gson gson = new Gson();
		String json = gson.toJson(referrals);
		
		/*
		 * for(Object o:referrals) { System.out.println(((Referral)o).toString());
		 * jsons.add(gson.toJson(((Referral)o))); }
		 */
		
		System.out.println(json);
		KafkaWriter kafkaWriter = new KafkaWriter();
		kafkaWriter.write(json);
	}

	private List<Object> read(String filePath, String destFile) {
		// read the file
		List<Object> referralList = new ArrayList<Object>();
		try {
			List<String> lines = FileUtils.readLines(FileUtils.getFile(filePath),Charset.forName("UTF-8"));
			String[] headers = lines.get(0).split("\\|");
			
			for(int i=1; i < lines.size(); i++) {
				String st = lines.get(i);
				// read the values
				String[] values = st.split("\\|");
				// prepare the json
				Referral referral = prepareJosn(headers, values);
				referralList.add(referral);
			
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * try { FileUtils.moveFile(FileUtils.getFile(filePath),
		 * FileUtils.getFile(destFile)); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		return referralList;
	}

	private Referral prepareJosn(String[] headers, String[] values) {
		Referral referral = new Referral();
		for (int i = 0; i < headers.length; i++) {
			String header = headers[i];
			Headers headerEnum = Referral.Headers.valueOf(header.toUpperCase());
			switch (headerEnum) {

			case HICN: referral.setHICN(values[i]);
				break;
			case MBI: referral.setMBI(values[i]);
				break;
			case SUBSCRIBER_ID: referral.setSubscriber_ID(values[i]);
				break;
			case ICUE_MEMBER_ID: 
				if(null != values[i] && !values[i].isEmpty())
				{
					referral.setICUE_Member_ID(Integer.parseInt(values[i]));
				}
				break;
			case POLICY_NUMBER: referral.setPolicy_Number(values[i]);
				break;
			case FIRST_NAME: referral.setFirst_Name(values[i]);
				break;
			case LAST_NAME: referral.setLast_Name(values[i]);
				break;
			case MIDDLE_NAME: referral.setMiddle_Name(values[i]);
				break;
			case DOB: referral.setDOB(values[i]);
				break;
			case PROGRAM_ID: referral.setProgram_ID(values[i]);
				break;
			case PROGRAM_NAME: referral.setProgram_Name(values[i]);
				break;
			case HCONTRACT: referral.setHCONTRACT(values[i]);
				break;

			default:
				System.out.println(" Unexpected Header : "+headerEnum);
				break; // print the option is a not valid.
			}
		}
		return referral;
	}
	
	

	
	public static void main(String[] args) {
		Navigate4MePipeline pipeline = new Navigate4MePipeline();
		pipeline.pipeline();
		
	}
	// return the JSON objects
}
