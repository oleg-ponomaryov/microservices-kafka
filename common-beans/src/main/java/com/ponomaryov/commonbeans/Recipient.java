package com.ponomaryov.commonbeans;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Recipient  implements Serializable {
	private static final long serialVersionUID = 98742367434876234L;

	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String lastName;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private Date dob;

	@Getter
	@Setter
	private String group_id;
}
