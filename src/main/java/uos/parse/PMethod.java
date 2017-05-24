package uos.parse;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import uos.AccessModifier;
import uos.Type;

/**
 * This class stand for to save parsed methods, implements Methods and Parse Class.
 * </p> 
 * 
 * <p> To carry out, this class has members as name( String Class ), 
 * return( Type Class ), params( Map<Parameter name, Type> Class ),
 * member list( List<Members> Class ) and contents. </p>
 * 
 * <p> When contents member is set, member list will be set automatically, with
 * the reverse is identically. </p>
 * 
 * @author imp
 *
 */
public final class PMethod implements Methods, Parse, Field {
	
	// -------------------------------- Member ----------------------------------
	private Classes refClass;
	
	private String contents;
	private List<Members> memberList = new ArrayList<>();
	
	private String field;
	private String methodName;
	private uos.AccessModifier am;
	private MethodType methodType;
	private Type returnType;
	private Map<String, Type> params = new HashMap<>();
	
	
	
	private PMethod() {} // To prevent instantiation
	
	// -------------------------------- Member ----------------------------------
	
	// -------------------------------- Method ----------------------------------
	
	/**
	 * Create new instance of PMethod with static factory method.
	 * 
	 * @return new instance of PMethod
	 */
	public static PMethod newInstance() {
		return new PMethod();
	}
	
	// < Reference >
	
	@Override
	public void setRefClass(Classes classes) {
		this.refClass = classes;
	}

	@Override
	public Classes getRefClass() {
		return refClass;
	}

	// < /Reference >
	// < Information >

	@Override
	public void setPropField(String field) {
		this.field = field;
	}

	@Override
	public String getField() {
		return field;
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
	public void setAccessModifier(AccessModifier am) {
		this.am = am;
	}

	@Override
	public AccessModifier getAccessModifier() {
		return am;
	}

	@Override
	public void setMethodType(MethodType methodType) {
		this.methodType = methodType;
	}

	@Override
	public MethodType getMethodType() {
		return methodType;
	}
	
	// < /Information >
	// < Return >
	
	@Override
	public void setReturnType( Type type ) {
		this.returnType = type;
	}

	@Override
	public Type getReturnType() {
		return returnType;
	}
	
	// < /Return >
	// < Parameter >
	
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
	
	// < /Parameter >
	// < Member >
	
	@Override
	public void setMember( Members members ) {
		memberList.add(members);
	}

	@Override
	public Members getMember( int index ) {
		return memberList.get(index);
	}
	
	@Override
	public Members getMember( String memberName ) {
		for( Members members : memberList ) {
			if( members.getName()== memberName )
				return members;
		}
		return null;
	}

	@Override
	public List<Members> getMemberList( ) {
		return memberList;
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
	public void clearMemberList() {
		memberList.clear();
	}

	// < /Member >
	// < Contents >
	
	@Override
	public void setContents(String contents) {
		
		this.contents = contents;
		for( Members members : refClass.getMemberList() ) {
			if( contents.contains( members.getName() ) &&
					!memberList.contains(members) ) {
				memberList.add(members);
				members.setRefMethod(this);
				members.setRefClass(this.getRefClass());
			}
		}
	}

	@Override
	public String getContents() {
		return contents;
	}




	// < /Contents >
	// -------------------------------- Method ----------------------------------
}
