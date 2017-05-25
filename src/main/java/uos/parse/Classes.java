package uos.parse;

import java.util.List;

/**
 * This interface stand for save class data.
 * 
 * @author 2016920054_JUHAYONG
 */
public interface Classes {

	// < Name >

	/**
	 * Set name of (@link Classes}.
	 * 
	 * @param className
	 *            name of class
	 */
	public void setName( String className );

	/**
	 * Return name of {@link Classes}.
	 * 
	 * @return name of class.
	 */
	public String getName();

	// < /Name >
	// < Method List >

	/**
	 * Set {@link Methods}
	 * 
	 * @param methods
	 *            method to add method list of the class.
	 */
	public void setMethod( Methods methods );

	/**
	 * Return {@link Methods}
	 * 
	 * @param index
	 *            index of method in method-list of the class.
	 * @return method instance which specified.
	 */
	public Methods getMethod( int index );

	/**
	 * Return {@link Methods}
	 * 
	 * @param methodName
	 *            name of method in method-list of the class.
	 * @return method instance which specified.
	 */
	public Methods getMethod( String methodName );

	/**
	 * Return {@link List} of {@link Methods}.
	 * 
	 * @return method-list of the class.
	 */
	public List<Methods> getMethodList();

	/**
	 * Remove method from method-list of the class.
	 * 
	 * @param index
	 *            index of method to remove from method-list of the class.
	 */
	public void removeMethod( int index );

	/**
	 * Remove method from method-list of the class.
	 * 
	 * @param methods
	 *            method to remove from method-list of the class.
	 */
	public void removeMethod( Methods methods );

	/**
	 * Clear method-list in the class.
	 */
	public void clearMethodList();

	// < /Method List >
	// < Member List >

	/**
	 * Set {@link Members}
	 * 
	 * @param members
	 *            member to add member-list of the class
	 */
	public void setMember( Members members );

	/**
	 * Return {@link Members} in class member-list
	 * 
	 * @param index
	 *            index of member in class member-list
	 * @return member in the class member-list
	 */
	public Members getMember( int index );

	/**
	 * Return {@link Members} in class member-list
	 * 
	 * @param memberName
	 *            name of member in class member-list
	 * @return member in the class member-list
	 */
	public Members getMember( String memberName );

	/**
	 * Return {@link List} of {@link Members} of class
	 * 
	 * @return member-list of the class
	 */
	public List<Members> getMemberList();

	/**
	 * Remove member in member-list of the class
	 * 
	 * @param index
	 *            index of member-list of the class
	 */
	public void removeMember( int index );

	/**
	 * Remove member in member-list of the class
	 * 
	 * @param members
	 *            member in member-list of the class
	 */
	public void removeMember( Members members );

	/**
	 * Clear member-list of the class.
	 */
	public void clearMemberList();
	// < /Member List >
}
