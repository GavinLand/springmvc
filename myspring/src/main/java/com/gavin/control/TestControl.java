package com.gavin.control;

import com.gavin.annt.ExtAuto;
import com.gavin.annt.ExtControl;
import com.gavin.annt.ExtRequestMap;
import com.gavin.service.TestService;


@ExtControl
@ExtRequestMap("/test")
public class TestControl {
	@ExtAuto
	public TestService testService;
	
	@ExtRequestMap("/test")
	public String test() {
		return "index";
	}

}
