package uos.parse;

import java.util.List;
import java.util.ArrayList;

/**
 * This class stand for to save parsed classes, implements Classes and Parse Class.
 * </p> 
 * 
 * <p> To carry out, this class has members as name( String Class ), 
 * method list( List<Methods> Class ), member list( List<Members> Class ),
 * class list( List<Classes> Class ) and contents. As a top hierarchy the 
 * Classes has it own list with static member and to keep track of number of
 * class instnace.</p>
 * 
 * @author imp
 *
 */
public final class PClass implements Classes, Parse {

	// -------------------------------- Member ----------------------------------
	private String className;
	private String contents;
	
	private List<Methods> methodList = new ArrayList<>();
	private List<Members> memberList = new ArrayList<>();
	
	private PClass() {} // To prevent instantiation
	
	// -------------------------------- Member ----------------------------------
	// -------------------------------- Method ----------------------------------
	
	/**
	 * Return new instance of PClass, which add the instance to class list.
	 * @return {@link PClass}
	 */
	public static PClass newInstance() {
		return new PClass();
	}
		
	// < Name >
	
	@Override
	public void setName( String className ) {
		this.className = className;
	}

	@Override
	public String getName() {
		return className;
	}
	
	// < /Name >
	// < Method List >

	@Override
	public void setMethod( Methods methods ) {
		methodList.add(methods);
	}
	

	@Override
	public Methods getMethod( int index ) {
		return methodList.get(index);
	}

	@Override
	public Methods getMethod( String methodName ) {
		for( Methods methods : methodList ) {
			if( methods.getName()== methodName )
				return methods;
		}
		
		return null;
	}

	@Override
	public List<Methods> getMethodList() {
		return methodList;
	}

	@Override
	public void removeMethod(int index) {
		methodList.remove(index);
	}

	@Override
	public void removeMethod(Methods methods) {
		methodList.remove(methods);		
	}

	@Override
	public void clearMethodList() {
		methodList.clear();
	}

	// < /Method List >
	// < Member List >
	
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
	public List<Members> getMemberList() {
		return memberList;
	}
	
	@Override
	public void removeMember(int index) {
		memberList.remove(index);
	}

	@Override
	public void removeMember(Members members) {
		memberList.remove(members);		
	}
	
	@Override
	public void clearMemberList() {
		memberList.clear();
	}
	
	// < /Member List >
	// < Content >
	
	@Override
	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String getContents() {
		return contents;
	}
	// < /Content >
	// -------------------------------- Method ----------------------------------

}
