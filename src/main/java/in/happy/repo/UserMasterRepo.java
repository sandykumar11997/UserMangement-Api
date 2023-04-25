package in.happy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.happy.entity.UserMaster;

public interface UserMasterRepo extends JpaRepository<UserMaster, Integer>{
	
	
       public UserMaster findByMail(String mail);
}
