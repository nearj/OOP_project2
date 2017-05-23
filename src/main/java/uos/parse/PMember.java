package uos.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uos.Type;

public class PMember implements Members,Parse{
	
	private Classes refClass;
	private String refMethod;
	private String memberName;
	private Type memberType;
	private Map<String, Type> params = new HashMap<>();
	private List<Members> memberList =  new ArrayList<>();
	
	private PMember() {}
	
	
	public static PMember newInstance() {
		return new PMember();
	}
	
	@Override
	public void setRefClass(Classes classes) {
		this.refClass=classes;
	}
	
	@Override
	public Classes getRefClass(){
		return refClass;
	}
	
	
	/*
	@Override
	public void setRefMethod(Methods methods) {
		this.refMethod = methods;
	}
	
	@Override
	public String getRefMethod() {
		return refMethod;
	}
	 
	@Override
	public List<Methods> getRefMethodList(){
		
	}
		
	@Override
	public Type getMemberType(){
		return memberType;
	}
	
	@Override
	public void setMemberName(String name){
		this.memberName = name;
	}
	
	@Override
	public String getMemberType(){
		return memberType;
	}
	
	@Override
	public String findUsedMethods(String memberName) {
		
	}


	@Override
	public void setContents(String contents) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getContents() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Methods getRefMethod() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Methods> getRefMethodList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setType() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isArray() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getArray() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setName() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setMember() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Members getMember() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	*/


	@Override
	public void setContents(String contents) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getContents() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setRefMethod(Methods methods) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Methods getRefMethod() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Methods> getRefMethodList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setType() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isArray() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getArray() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setName() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getName() {
		return memberName;
	}


	@Override
	public void setMember() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Members getMember() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setMemberName( String memberName ) {
		this.memberName = memberName;
	}
}
	
	
	