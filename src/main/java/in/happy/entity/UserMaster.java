package in.happy.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table (name="User_Master")
public class UserMaster {
	
	
	@Id
	@GeneratedValue
	private Integer userId;
	private String name;
	private String mail;
	private Long number;
	private String status;
	private Character gender;
	private LocalDate dob;
	private Long ssn;
	private String password;
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	private String createdBy;
	private String updatedBy;
	
	

}
