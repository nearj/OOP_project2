package uos.parse;

import java.util.List;

public interface Classes {
	
	public void setName( String className );
	public String getName();
	
	
	public void setClass( PClass pClass );
	public List<PClass> getClassList();
	public Classes getKlass();
	
	public void setMethod( Methods methods );
	public List<Methods> getMethodList();
	public void clearMethodList();
	public Methods getMethod(int index);
	public void removeMethod( int index );
	public void removeMethod( Methods methods );
	
	
	public void setMember( Members members );
	public List<Members> getMemberList();
	public void clearMemberList();
	public Members getMember( int index );
	public void removeMember( int index );
	public void removeMember( Members members );
}
