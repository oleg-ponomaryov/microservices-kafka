package com.ponomaryov.commonbeans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Group  implements Serializable {
	private static final long serialVersionUID = 12342367434876234L;

	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String description;
}
