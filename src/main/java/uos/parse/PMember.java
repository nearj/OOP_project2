package uos.parse;

import java.util.ArrayList;
import java.util.List;

import uos.Type;

public final class PMember implements Members {
	
	private Classes refClass;
	private List<Methods> refMethodList = new ArrayList<>();
	private String memberName;
	private Type returnType;
	private MemberType memberType;
	private Methods memberOfMember;
	//PMember에는 Map이 없어도 되죠?
	
	private PMember() {} 
	
	public static PMember newInstance() {
		return new PMember();
	}

	@Override
	public void setRefClass(Classes classes) {
		this.refClass = classes;
		
	}

	@Override
	public Classes getRefClass() {
		return refClass;
	}

	@Override
	public void setRefMethod(Methods methods) {
		refMethodList.add(methods);
	}

	@Override
	public Methods getRefMethod( String methodName ) {
		for( Methods methods : refMethodList ) {
			if(methods.getName().equals(methodName)) {
				return methods;
			} else return null;			
		}
		return null;
	}

	@Override
	public List<Methods> getRefMethodList() {
		return refMethodList;
	}

	@Override
	public Type getReturnType() {
		return returnType;
	}

	@Override
	public void setReturnType(Type type) {
		this.returnType=type;
		
	}

	public boolean isArray(String memberName) {
		if(memberName=="arr")
			return false;
		else if(memberName=="size")
			return true;
		else if(memberName=="first")
			return true;
		else if(memberName=="last")
			return true;
		return false;
	}
	
	@Override
	public void setMemberType(String memberName) {
		if(isArray(memberName)==true)
			this.memberType=MemberType.DEFAULT;
		else if(isArray(memberName)==false)
			this.memberType=MemberType.ARRAY;
	}

	@Override
	public MemberType getMemberType() {
		return memberType;
	}

	

	@Override
	public void setName(String name) {
		this.memberName=name;		
	}

	@Override
	public String getName() {
		return memberName;
	}

	@Override
	public void setMember(Methods methods) {
		this.memberOfMember=methods;
		
	}

	@Override
	public Methods getMember() {
		return memberOfMember;
	}

}