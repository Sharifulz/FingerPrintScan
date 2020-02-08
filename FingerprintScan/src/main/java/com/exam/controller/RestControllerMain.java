package com.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dao.IScanDataDao;
import com.exam.model.ScanData;
import com.exam.service.DeviceScan;

@RestController
public class RestControllerMain {
	@Autowired
	IScanDataDao scanService;
	 
	@RequestMapping("/scan") 
    public Map<String, Object> getVendorTests(){
		System.out.println("Start Scanning ----------------- >>>>>>>>>>>>>> ");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data","Scan Completed");
		String fp = "Scan Completed";
        return data;   
    }

	/* 
	 * data.put("tempType", "ANSI"); data.put("byteImage", ANSIminutiaeBuffer1);
	 * data.put("byteStr", str);
	 */
	@GetMapping("/saveScanData") 
	public Map<String, Object> scan(HttpServletRequest request){
		Map<String, Object> data = new HashMap<String, Object>();
		String templateType = request.getParameter("templateType");
		System.out.println("Template Type --------------------- "+ templateType);
		data = DeviceScan.scanFingerData(templateType);
		ScanData scanData = new ScanData();
		
		if(data.get("tempType").toString().equals("SG400")) {
			scanData.setStrScanDataSG400(data.get("byteStr").toString());
		}else if(data.get("tempType").toString().equals("ISO")) {
			scanData.setStrScanDataISO(data.get("byteStr").toString());
		}else if(data.get("tempType").toString().equals("ANSI")) {
			scanData.setStrScanDataANSI(data.get("byteStr").toString());
		}
		
		scanService.save(scanData);
 		return data;
	}

}
