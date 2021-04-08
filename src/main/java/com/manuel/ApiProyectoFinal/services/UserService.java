package com.manuel.ApiProyectoFinal.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.manuel.ApiProyectoFinal.enums.SearchByUsers;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;
import java.io.IOException;
import java.util.Map;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	@SuppressWarnings("unchecked")
	public User cretateUser(User newUser,String password) {
		//Poner errores si el id existe o el gmail existe
		User newUserAux=new User();
		newUserAux.setAddress(newUser.getAddress());
		newUserAux.setGmail(newUser.getGmail());
		newUserAux.setName(newUser.getName().toUpperCase());
		newUserAux.setPhone(newUser.getPhone());
		newUserAux.setActive(newUser.isActive());
		boolean disabled=false;
		if(!newUser.isActive()) {
			disabled=true;
		}
		CreateRequest request = new CreateRequest()
			    .setEmail(newUser.getGmail())
			    .setDisabled(disabled)
			    .setPassword(password);
		try {
			UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
			newUserAux.setUid(userRecord.getUid());
			
			
		} catch (FirebaseAuthException e1) {
		
			return new User();
		}
		if(newUser.getAvatar()!=null&&newUser.getAvatar().length()>0) {
			Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
					"cloud_name", "dplfcuv7k",
					"api_key", "446249413969957",
					"api_secret", "ypJ3kBbYQoiZ0zyzuUiyCRgg6A8"));
			
			try {
				Map<String,Object> uploadResult=cloudinary.uploader().upload(newUser.getAvatar(), ObjectUtils.asMap("public_id",newUserAux.getUid(),
																													"overwrite", true,
																													"unique_filename" ,true,
																													"folder", "Users"));
				newUserAux.setAvatar((String)uploadResult.get("url"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				newUserAux.setAvatar("");
			}
		}else {
			newUserAux.setAvatar("");
		}
		return this.userRepository.save(newUserAux);
		
	}
	
	public Integer getTotalPages(int sizePage) {
		//Poner errores si el size es menor a 1
		Page<User> page=this.userRepository.findAll(PageRequest.of(0,sizePage));
		return page.getTotalPages();
	}
	
	public Page<User> findByName(String name,Pageable pageable){
	
		return this.userRepository.findByNameStartsWith(name.toUpperCase(),pageable);
		
	}
	
	public Page<User> getAllUser(Pageable pageable){
		return this.userRepository.findAll(pageable);
	}
	public Page<User> getAllUserWith(Pageable pageable,SearchByUsers sbU,String characters){
		Page<User> page=null;
		if(characters!=null&&!characters.equals("")) {
			switch (sbU) {
			case uid:
				page=this.userRepository.findByUidStartsWith(characters, pageable);
				break;
			case email:
				page=this.userRepository.findByGmailStartsWith(characters, pageable);
				break;
			case name:
				page=this.userRepository.findByNameStartsWith(characters, pageable);
				break;
			case phone:
				page=this.userRepository.findByPhoneStartsWith(characters, pageable);
				break;
			default:
				page= this.userRepository.findAll(pageable);
			}
			return null;
		}else {
			page= this.userRepository.findAll(pageable);
		}
		
		return page;
		
	}
	@SuppressWarnings("unchecked")
	public boolean deleteUser(String uid) {
		boolean borrado=false;
		Optional<User> to_delete=this.userRepository.findById(uid);
		if(to_delete.isPresent()) {
			try {
				FirebaseAuth.getInstance().deleteUser(uid);
				
				Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
						"cloud_name", "dplfcuv7k",
						"api_key", "446249413969957",
						"api_secret", "ypJ3kBbYQoiZ0zyzuUiyCRgg6A8"));
				String url=to_delete.get().getAvatar();
				if(url!=null&&url.length()>0) {
					Map<String, Object> mapp=cloudinary.uploader().destroy("Users/"+to_delete.get().getUid(),  ObjectUtils.asMap("resource_type","image"));
				}
				this.userRepository.delete(to_delete.get());	
				borrado=true;
			} catch (FirebaseAuthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return borrado;
	}
	
	public boolean activateUser(String uid) {
		boolean changed=false;
		Optional<User> to_update=this.userRepository.findById(uid);
		if(to_update.isPresent()) {
			User activate=to_update.get();
			if(to_update.get().isActive()==false) {
				try {
					UpdateRequest request=new UpdateRequest(uid).setDisabled(false);
					FirebaseAuth.getInstance().updateUser(request);
					activate.setActive(true);
					this.userRepository.save(activate);
					changed=true;
				} catch (FirebaseAuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return changed;
	}
	
	public boolean disabledUser(String uid) {
		boolean changed=false;
		Optional<User> to_update=this.userRepository.findById(uid);
		if(to_update.isPresent()) {
			User disable=to_update.get();
			if(to_update.get().isActive()==true) {
				try {
					UpdateRequest request=new UpdateRequest(uid).setDisabled(true);
					FirebaseAuth.getInstance().updateUser(request);
					disable.setActive(false);
					this.userRepository.save(disable);
					changed=true;
				} catch (FirebaseAuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return changed;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateUser(User u,String password) {
		boolean updated=false;
		if(u.getUid()!=null) {
			Optional<User> to_update=this.userRepository.findById(u.getUid());
			boolean do_update=false;
			if(to_update.isPresent()){
				User to_updated_user=to_update.get();
				to_updated_user.setPhone(u.getPhone());
				to_updated_user.setAddress(u.getAddress());
				to_updated_user.setName(u.getName().toUpperCase());
		
				UpdateRequest request=new UpdateRequest(to_updated_user.getUid());
				if(to_updated_user.getGmail()!=u.getGmail()){
					to_updated_user.setGmail(u.getGmail());
					request.setEmail(u.getGmail());
					do_update=true;
				}
				if(password!=null&&password.length()>=6) {
					request.setPassword(password);
					do_update=true;
				}
				if(u.isActive()!=to_updated_user.isActive()) {
					to_updated_user.setActive(u.isActive());
					if(u.isActive()==true) {
						request.setDisabled(false);
					}else {
						request.setDisabled(true);
					}
					do_update=true;
				}
				
				
				if(do_update) {
					try {
						FirebaseAuth.getInstance().updateUser(request);
					} catch (FirebaseAuthException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
				
				if(!to_updated_user.getAvatar().equals(u.getAvatar())) {
					Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
							"cloud_name", "dplfcuv7k",
							"api_key", "446249413969957",
							"api_secret", "ypJ3kBbYQoiZ0zyzuUiyCRgg6A8"));
					if(u.getAvatar().equals("")&&!to_updated_user.getAvatar().equals("")) {
						try {
							Map<String, Object> mapp=cloudinary.uploader().destroy("Users/"+to_updated_user.getUid(),  ObjectUtils.asMap("resource_type","image"));
							to_updated_user.setAvatar("");
						} catch (IOException e) {
							to_updated_user.setAvatar("");
						}
					}else {
						try {
							Map<String,Object> uploadResult=cloudinary.uploader().upload(u.getAvatar(), ObjectUtils.asMap("public_id",to_updated_user.getUid(),
									"overwrite", true,
									"unique_filename" ,true,
									"folder", "Users"));
							to_updated_user.setAvatar((String)uploadResult.get("url"));
						} catch (IOException e) {
							to_updated_user.setAvatar("");
						}
					}
				}
				
				this.userRepository.save(to_updated_user);
				updated=true;
			}
		}
		
		
		return updated;
	}
	
}
	
