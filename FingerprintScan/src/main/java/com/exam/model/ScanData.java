package com.exam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name = "scan_data")
public class ScanData {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id_scan")
	    private Integer idScan;

	    @Lob
	    private String strScanDataSG400;
	    
	    @Lob
	    private String strScanDataISO;
	    
	    @Lob
	    private String strScanDataANSI;
	    
	    @Column(name = "str_finger_number")
	    private Integer strFingerNumber;
	    
	    @Column(name = "str_extra1")
	    private Integer strExtra1;
	    
	    @Column(name = "str_extra2")
	    private Integer strExtra2;

		public Integer getIdScan() {
			return idScan;
		}

		public void setIdScan(Integer idScan) {
			this.idScan = idScan;
		}

		public String getStrScanDataSG400() {
			return strScanDataSG400;
		}

		public void setStrScanDataSG400(String strScanDataSG400) {
			this.strScanDataSG400 = strScanDataSG400;
		}

		public String getStrScanDataISO() {
			return strScanDataISO;
		}

		public void setStrScanDataISO(String strScanDataISO) {
			this.strScanDataISO = strScanDataISO;
		}

		public String getStrScanDataANSI() {
			return strScanDataANSI;
		}

		public void setStrScanDataANSI(String strScanDataANSI) {
			this.strScanDataANSI = strScanDataANSI;
		}

		public Integer getStrFingerNumber() {
			return strFingerNumber;
		}

		public void setStrFingerNumber(Integer strFingerNumber) {
			this.strFingerNumber = strFingerNumber;
		}

		public Integer getStrExtra1() {
			return strExtra1;
		}

		public void setStrExtra1(Integer strExtra1) {
			this.strExtra1 = strExtra1;
		}

		public Integer getStrExtra2() {
			return strExtra2;
		}

		public void setStrExtra2(Integer strExtra2) {
			this.strExtra2 = strExtra2;
		}

		public ScanData(Integer idScan, String strScanDataSG400, String strScanDataISO, String strScanDataANSI,
				Integer strFingerNumber, Integer strExtra1, Integer strExtra2) {
			this.idScan = idScan;
			this.strScanDataSG400 = strScanDataSG400;
			this.strScanDataISO = strScanDataISO;
			this.strScanDataANSI = strScanDataANSI;
			this.strFingerNumber = strFingerNumber;
			this.strExtra1 = strExtra1;
			this.strExtra2 = strExtra2;
		}

		public ScanData() {
		}

		@Override
		public String toString() {
			return "ScanData [idScan=" + idScan + ", strScanDataSG400=" + strScanDataSG400 + ", strScanDataISO="
					+ strScanDataISO + ", strScanDataANSI=" + strScanDataANSI + ", strFingerNumber=" + strFingerNumber
					+ ", strExtra1=" + strExtra1 + ", strExtra2=" + strExtra2 + "]";
		}

		
		

}
