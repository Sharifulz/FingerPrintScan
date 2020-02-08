package com.exam.service;

import SecuGen.FDxSDKPro.jni.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class DeviceScan {

	public DeviceScan() {
		System.out.println("DeviceScan Called");
		//secugenDeviceCom();
	}
	
	public static Map<String, Object> scanFingerData(String templateType) {
			Map<String, Object> data = new HashMap<String, Object>();
		
		 	long err;
	        byte kbBuffer[] = new byte[100];
	        byte kbWhichFinger[] = new byte[100];
	        int fingerLength = 0;
	        String finger = new String("Finger");
	        byte[] imageBuffer1;
	        byte[] imageBuffer2;
	        byte[] SG400minutiaeBuffer1;
	        byte[] ANSIminutiaeBuffer1;
	        byte[] ISOminutiaeBuffer1;
	        byte[] SG400minutiaeBuffer2;
	        byte[] ANSIminutiaeBuffer2;
	        byte[] ISOminutiaeBuffer2;
	        FileOutputStream fout = null;
	        PrintStream fp = null;

	        //Initialize fingerprint prompt buffer
	        for (int i=0; i < kbWhichFinger.length; ++i)
	           kbWhichFinger[i] = 0x00;
	        
	        // Instantiate JSGFPLib object
	        JSGFPLib sgfplib = new JSGFPLib();
	        if ((sgfplib != null) && (sgfplib.jniLoadStatus != SGFDxErrorCode.SGFDX_ERROR_JNI_DLLLOAD_FAILED))
	        {
	            System.out.println(sgfplib);
	        }
	        else
	        {
	            System.out.println("An error occurred while loading JSGFPLIB.DLL JNI Wrapper");
	        }

	        // Init()
	        err = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
	        // GetLastError()v nm,.
	        err = sgfplib.GetLastError();
	        //-----------------------------------------------------------
	        // GetMinexVersion()
	        int[] extractorVersion = new int[1];
	        int[] matcherVersion = new int[1];
	        err = sgfplib.GetMinexVersion(extractorVersion, matcherVersion);        
	        //----------------------------------------------------------
	        // OpenDevice()
	        System.out.println("Call OpenDevice(SGPPPortAddr.AUTO_DETECT)");
	        err = sgfplib.OpenDevice(SGPPPortAddr.AUTO_DETECT);
	        System.out.println("OpenDevice returned : [" + err + "]");

	        //-------------------------------------------------------------
	        // GetError()
	        err = sgfplib.GetLastError();
	        // GetDeviceInfo()
	        SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
	        err = sgfplib.GetDeviceInfo(deviceInfo);
	        //---------=================== PROCESSING START ========================== -----------------------------

	        int[] quality = new int[1];
	        int[] maxSize = new int[1];
	        int[] size = new int[1];
	        SGFingerInfo fingerInfo = new SGFingerInfo();
	        fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
	        fingerInfo.ImageQuality = quality[0];
	        fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
	        fingerInfo.ViewNumber = 1;



	        // Finger 1 ----------------------------------------------------- getImage() - 1st Capture
	        err =sgfplib.SetLedOn(true);
	        System.out.print("Capture 1. Please place [ finger ] on sensor with LEDs on and press <ENTER> ");
	        imageBuffer1 = new byte[deviceInfo.imageHeight*deviceInfo.imageWidth];
	        
	        
	        try
	        {
	          //  System.in.read(kbBuffer);
	            err = sgfplib.GetImage(imageBuffer1);
	            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
	            {
	                err = sgfplib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight, imageBuffer1, quality);
	                System.out.println("Image Quality is : [" + quality[0] + "]");
	                fout = new FileOutputStream(finger + "1.txt");
	                fp = new PrintStream(fout);
	                fp.write(imageBuffer1,0, imageBuffer1.length);
	                fp.close();
	                fout.close();
	                fp = null;
	                fout = null;
	                
	               // byte [] data1 = imageBuffer1.toByteArray();
	    	       // ByteArrayInputStream bis = new ByteArrayInputStream(imageBuffer1);
	    	       // BufferedImage bImage2 = ImageIO.read(bis);
	    	        try {
	    	        	// ImageIO.write(bImage2, "jpg", new File("output.jpg") );
					} catch (Exception e) {
						e.printStackTrace();
					}
	    	       
	            }
	            else
	            {
	                System.out.println("ERROR: Fingerprint image capture failed for sample1.");
	               //Cannot continue test if image not captured
	            }
	        }
	        catch (IOException e)
	        {
	            System.out.println("Exception reading keyboard : " + e);
	        }
	       
	        //-------------- START : TEMPLATING --------------------------
	        if (templateType.equals("SG400")) {
	        	System.out.println("----------- >>>>>>>>> 1 "+ templateType);
	        	 //-------------- Image Processing 1 : ==========================================================
	            // Set Template format SG400
	            err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
	            // Get Max Template Size for SG400
	            err = sgfplib.GetMaxTemplateSize(maxSize);
	            // Create SG400 Template for Finger 1
	            SG400minutiaeBuffer1 = new byte[maxSize[0]];
	            err = sgfplib.CreateTemplate(fingerInfo, imageBuffer1, SG400minutiaeBuffer1);
	            
	            String str = Base64.getEncoder().encodeToString(SG400minutiaeBuffer1);
	            data.put("tempType", "SG400");
		        data.put("byteImage", SG400minutiaeBuffer1);
		        data.put("byteStr", str);
		        
	            err = sgfplib.GetTemplateSize(SG400minutiaeBuffer1, size);
	           
	            try
	            {
	                if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
	                {
	                    fout = new FileOutputStream(finger +"1.sg400");
	                    fp = new PrintStream(fout);
	                    fp.write(SG400minutiaeBuffer1,0, size[0]);
	                    fp.close();
	                    fout.close();
	                    fp = null;
	                    fout = null;
	                }
	            }
	            catch (IOException e)
	            {
	                System.out.println("Exception writing minutiae file : " + e);
	            }
			}else if(templateType.equals("ISO")) {
				System.out.println("----------- >>>>>>>>> 2 "+ templateType);
				// Set Template format ISO19794
		        err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_ISO19794);
		        // Get Max Template Size for ISO19794
		        err = sgfplib.GetMaxTemplateSize(maxSize);
		        // Greate ISO19794 Template for Finger1
		        ISOminutiaeBuffer1 = new byte[maxSize[0]];
		        
		        err = sgfplib.CreateTemplate(fingerInfo, imageBuffer1, ISOminutiaeBuffer1);
		       
		        String str = Base64.getEncoder().encodeToString(ISOminutiaeBuffer1);
		        data.put("tempType", "ISO");
		        data.put("byteImage", ISOminutiaeBuffer1);
		        data.put("byteStr", str);
		        
		        err = sgfplib.GetTemplateSize(ISOminutiaeBuffer1, size);
		        try
		        {
		            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
		            {
		                fout = new FileOutputStream(finger +"1.jpg");
		                fp = new PrintStream(fout);
		                fp.write(ISOminutiaeBuffer1,0, size[0]);
		                fp.close();
		                fout.close();
		                fp = null;
		                fout = null;
		            }
		        }
		        catch (IOException e)
		        {
		            System.out.println("Exception writing minutiae file : " + e);
		        }
			}else if(templateType.equals("ANSI")) {
				System.out.println("----------- >>>>>>>>> 3 "+ templateType);
				
				err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_ANSI378);
		        // Get Max Template Size for ANSI378
		        err = sgfplib.GetMaxTemplateSize(maxSize);
		        // Create ANSI378 Template for Finger1
		        ANSIminutiaeBuffer1 = new byte[maxSize[0]];
		        err = sgfplib.CreateTemplate(fingerInfo, imageBuffer1, ANSIminutiaeBuffer1);
		        
		        String str = Base64.getEncoder().encodeToString(ANSIminutiaeBuffer1);
		        data.put("tempType", "ANSI");
		        data.put("byteImage", ANSIminutiaeBuffer1);
		        data.put("byteStr", str);
		        
		        err = sgfplib.GetTemplateSize(ANSIminutiaeBuffer1, size);
		        try
		        {
		            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
		            {
		                fout = new FileOutputStream(finger +"1.ansi378");
		                fp = new PrintStream(fout);
		                fp.write(ANSIminutiaeBuffer1,0, size[0]);
		                fp.close();
		                fout.close();
		                fp = null;
		                fout = null;
		            }
		        }
		        catch (IOException e)
		        {
		            System.out.println("Exception writing minutiae file : " + e);
		        }
			}
	        
	        
	        // CloseDevice()
	        err = sgfplib.CloseDevice();
	        System.out.println("CloseDevice returned : [" + err + "]");

	        // Close JSGFPLib native library
	        System.out.println("Call Close()");
	        sgfplib.Close();

	       //
	        sgfplib = null;
	        imageBuffer1 = null;
	        imageBuffer2 = null;
	        SG400minutiaeBuffer1 = null;
	        ANSIminutiaeBuffer1 = null;
	        ISOminutiaeBuffer1 = null;
	        SG400minutiaeBuffer2 = null;
	        ANSIminutiaeBuffer2 = null;
	        ISOminutiaeBuffer2 = null;
	        
	        return data;
	}
	
	/*
	 * TEMPLATE_FORMAT_SG400 TEMPLATE_FORMAT_ISO19794 TEMPLATE_FORMAT_ANSI378
	 */
    
	private Map<String, Object> getSG400Template(){
		Map<String, Object> data = new HashMap<String, Object>();
		
		return data;
	}
	
	
	
	public static boolean matchScanData(byte[] oldScan, byte[] newScan) {
		long err;
		boolean[] matched = new boolean[1];
        int[] score = new int[1];
        
        JSGFPLib sgfplib = new JSGFPLib();

        matched[0] = false;
        score[0] = 0;
        err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
        err = sgfplib.MatchTemplate(oldScan, newScan, SGFDxSecurityLevel.SL_HIGH, matched);
        err = sgfplib.GetMatchingScore(oldScan, newScan, score);
        System.out.println(">>>>>>>>>>>>>> ----- >>>>>>>>>>>>>>> "+ score[0]);
        
        if(score[0]>150) {
        	return true;
        }else {
        	return false;
        }
	}
	
}
