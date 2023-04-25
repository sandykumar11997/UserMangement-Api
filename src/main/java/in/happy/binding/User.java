package in.happy.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	
	private String name;
	private String mail;
	private Long number;
	private Character gender;
	private LocalDate dob;
	private Long ssn;
	

}
