package com.afterschool.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddrDTO {
	
	private int seqno;
	private String zipcode;
	private String province;
	private String road;
	private String building;
	private String oldaddr;
	
}
