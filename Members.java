package uos.parse;

import java.util.List;

import uos.Type;

public interface Members{
	
	// < Reference class >
	
	/**
	 * Set reference {@link Classes} of the member.
	 * @param classes reference class of the member.
	 */
	public void setRefClass( Classes classes );
	
	/**
	 * Return reference {@link Classes} of the member.
	 * @return the reference class of the member.
	 */
	public Classes getRefClass();

	// < /Reference class >
	// < Reference class >
	
	/**
	 * Set reference {@link Methods} of the member to reference method-list.
	 * @param classes the reference method of the member.
	 */
	public void setRefMethod( Methods methods );

	/**
	 * Return reference {@link Methods} of the member.
	 * @return the reference method of the member.
	 */
	public Methods getRefMethod();

	/**
	 * Return reference {@link List} of {@link Methods} related to the member.
	 * @return the reference method-list of the member.
	 */
	public List<Methods> getRefMethodList();

	// < /Reference class >

	public Type getMemberType();
	public void setMemberType(Type type);
	//public boolean isArray(); 어떻게 써야할지 모르겠습니다
	//public String getArray();         "
	
	public void setMemberName(String name);
	public String getMemberName();
	
	//public void setMember(); 어떻게 써야할지 모르겠습니다
	//public Members getMember();      "
}
