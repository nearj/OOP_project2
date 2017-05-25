package uos.parse;

import java.util.List;
import java.util.Map;

import uos.AccessModifier;
import uos.Type;

/**
 * This interface stands for to save method data.
 * 
 * @author 2016920054_주하용
 */
public interface Methods {

	// < Reference class >

	enum MethodType {
		Constructor, Deconstructor, Default
	}

	/**
	 * Set reference {@link Classes} of the method.
	 * 
	 * @param classes
	 *            reference class of the method.
	 */
	public void setRefClass( Classes classes );

	/**
	 * Return reference {@link Classes} of the method.
	 * 
	 * @return reference Classes of the method.
	 */
	public Classes getRefClass();

	// < /Reference class >
	// < Information >

	/**
	 * Set name of method.
	 * 
	 * @param methodName
	 *            name of method.
	 */
	public void setName( String methodName );

	/**
	 * Return name of method.
	 * 
	 * @return name of method.
	 */
	public String getName();

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

	/**
	 * Set type of method<br>
	 * Type of method: Default, Constructor
	 * 
	 * @param methodType
	 */
	public void setMethodType( MethodType methodType );

	/**
	 * Set type of method<br>
	 * Type of method: Default, Constructor
	 * 
	 * @param methodType
	 */
	public MethodType getMethodType();

	// < /Information >
	// < Return >

	/**
	 * Set return type of method
	 * 
	 * @param type
	 *            type of return
	 */
	public void setReturnType( Type type );

	/**
	 * Return type of method
	 * 
	 * @return type of method
	 */
	public Type getReturnType();

	// < /Return >

	// ------------------------------- Parameter -------------------------------

	/**
	 * Set name of parameter and type. Since one method can has plular parameters, each parameter
	 * will be set as map with unique name.
	 * 
	 * @param type
	 *            type of parameter
	 * @param paramName
	 *            name of parameter
	 */
	public void setParam( Type type, String paramName );

	/**
	 * Get map of parameters and type.
	 * 
	 * @return Map< Parameter name, Parameter Type >
	 */
	public Map<String, Type> getParams();

	/**
	 * Get type of parameter with unique name.
	 * 
	 * @param paramName
	 *            name of parameter.
	 * @return type of specified parameter.
	 */
	public Type getParamType( String paramName );

	// ------------------------------- Parameter -------------------------------

	// --------------------------------- Member --------------------------------
	/**
	 * Set member which method has.
	 * 
	 * @param members
	 *            setting {@link Members}
	 */
	public void setMember( Members members );

	/**
	 * Return member which in list on index.
	 * 
	 * @param index
	 *            index of parameter in list.
	 * @return Specified {@link Members}
	 */
	public Members getMember( int index );

	/**
	 * Return {@link Members} in method member-list
	 * 
	 * @param memberName
	 *            name of member in method member-list
	 * @return member in the method member-list
	 */
	public Members getMember( String memberName );

	/**
	 * Return member list.
	 * 
	 * @return list of member.
	 */
	public List<Members> getMemberList();

	/**
	 * Remove member from list.
	 * 
	 * @param index
	 *            index fo member to delete.
	 */
	public void removeMember( int index );

	/**
	 * Remove member from list with member class.
	 * 
	 * @param members
	 *            member to delete
	 */
	public void removeMember( Members members );

	/**
	 * Clear all list of members.
	 */
	public void clearMemberList();
	// --------------------------------- Member --------------------------------
}
