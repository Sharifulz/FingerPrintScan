package com.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exam.dao.IScanDataDao;
import com.exam.model.ScanData;
import com.exam.service.DeviceScan;

@Controller
public class NavigationCntroller {

	@Autowired
	IScanDataDao scanService;
	
	
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		System.out.println("First Call ----------------- >>>>>>>>>>>>>> ");
		return "index";
	}
   
	
	 
	/*
	 @RequestMapping("/s") 
	 public ModelAndView getVendorTests(){
		 
	  System.out.println("Start Scanning ----------------- >>>>>>>>>>>>>> ");
	  Map<String, Object> data = new HashMap(); data.put("data","Scan Completed");
	  return new ModelAndView("index","data",data); 
	  
	 }
	
	 	@RequestMapping("/saveScan")
		public ModelAndView scanAndSave(HttpServletRequest request){
			 
			//------- Get Template type from UI
			Map<String, String[]> reqMap = request.getParameterMap();
			reqMap.forEach((key, value)->{ 
		 		  System.out.println(" Key ----- " + key +" Value ----- " + value[0].toString());
		 	}); 
			System.out.println("Request Map Size "+ reqMap.size());
			String templateType = reqMap.get("temp_type")[0];
			System.out.println("Template Type --------------------- "+ templateType);
			
			
			
			ScanData scanData = new ScanData();
			Map<String, Object> data = new HashMap<String, Object>();
			
			
			data = DeviceScan.scanFingerData(templateType);
			System.out.println("byteImage------------------------");
		
			String byteStr = (String) data.get("byteStr");
			scanData.setStrScanData(byteStr);
		 	scanService.save(scanData);
			System.out.println(scanData);
			System.out.println("================= Saved To Db ================");
			return new ModelAndView("index","data",data); 
		}
	 	*/

}
