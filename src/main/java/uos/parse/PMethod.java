package uos.parse;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import uos.Type;

public final class PMethod implements Methods {
	private String methodName;
	private String contents;
	private Type returnType;
	private Map<String, Type> params = new HashMap<>();
	private List<Members> memberList = new ArrayList<>();
	private PMethod() {} // To prevent instantiation 
	
	public static PMethod newInstance() {
		return new PMethod();
	}
	
	@Override
	public void setName( String methodName ) {
		this.methodName = methodName;
	}

	@Override
	public String getName() {
		return methodName;
	}

	@Override
	public void setReturnType( Type type ) {
		this.returnType = type;
	}

	@Override
	public Type getReturnType() {
		return returnType;
	}

	@Override
	public void setParam( Type type, String nameOfParam ) {
		params.put( nameOfParam, type );
	}

	@Override
	public Map<String, Type> getParams() {
		return params;
	}

	@Override
	public Type getParamType( String nameOfParam ) {
		return params.get( nameOfParam );
	}

	@Override
	public void setMember( Members members ) {
		memberList.add(members);
	}

	@Override
	public List<Members> getMemberList( int index ) {
		return memberList;
	}

	@Override
	public void clearMemberList() {
		memberList.clear();
	}

	@Override
	public Members getMember( int index ) {
		return memberList.get(index);
	}

	@Override
	public void removeMember( int index ) {
		memberList.remove(index);
	}

	@Override
	public void removeMember( Members members ) {
		memberList.remove(members);
	}

	@Override
	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String getContents() {
		return contents;
	}
	
}
