package uos.parse;

import java.util.List;

import uos.AccessModifier;
import uos.Type;

/**
 * 
 * @author 2016920009_±Ë¡æ»∆
 *
 */
public interface Members {

	public enum MemberType {
		DEFAULT, ARRAY;
	}
	// < Reference class >

	/**
	 * Set reference {@link Classes} of the member.
	 * 
	 * @param classes
	 *            reference class of the member.
	 */
	public void setRefClass( Classes classes );

	/**
	 * Return reference {@link Classes} of the member.
	 * 
	 * @return the reference class of the member.
	 */
	public Classes getRefClass();

	// < /Reference class >
	// < Reference class >

	/**
	 * Set reference {@link Methods} of the member to reference method-list.
	 * 
	 * @param classes
	 *            the reference method of the member.
	 */
	public void setRefMethod( Methods methods );

	/**
	 * Return reference {@link Methods} of the member.
	 * 
	 * @return the reference method of the member.
	 */
	public Methods getRefMethod( String methodName );

	/**
	 * Return reference {@link List} of {@link Methods} related to the member.
	 * 
	 * @return the reference method-list of the member.
	 */
	public List<Methods> getRefMethodList();

	// < /Reference class >

	/**
	 * Set modifier of method<br>
	 * Type of access modifier: public, protected, private
	 * 
	 * @param am
	 *            Access modifier of method.
	 */
	public void setAccessModifier( AccessModifier am );

	/**
	 * Get modifier of method<br>
	 * Type of access modifier: public, protected, private
	 * 
	 * @return Access modifier of method.
	 */
	public AccessModifier getAccessModifier();
	
	public Type getReturnType();

	public void setReturnType( Type type );

	public boolean isArray( Members members );

	public boolean isArray();

	public MemberType getMemberType();

	public void setMemberType( String memberType );

	public void setName( String name );

	public String getName();

	public void setMember( Methods methods );

	public Methods getMember();
}
